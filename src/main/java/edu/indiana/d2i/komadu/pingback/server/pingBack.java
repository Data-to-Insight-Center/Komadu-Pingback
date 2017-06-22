package edu.indiana.d2i.komadu.pingback.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.w3.www.ns.prov.Derivation;

import edu.indiana.d2i.komadu.axis2.client.createRevision;
import edu.indiana.d2i.komadu.axis2.client.queryEntityGraph;

@Path("/prov-pingback")
public class pingBack {
	private String query_Key;
	private String data_root_path;
	private String host;
	private String port;
	private String query_dir_path;
	private String pingback_Derivation;
	
	public pingBack()
	{
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data_root_path = props.getProperty("Data-Root");
		this.host=props.getProperty("Komadu-Pingback-Server-Host");
		this.port=props.getProperty("Komadu-Pingback-Server-Port");
	}
	
	@GET
	@Path("{parameter1}/mykey/{parameter2}")
	public Response Derivation(@PathParam("parameter1") String parameter1, @PathParam("parameter2") String parameter2,
			@QueryParam("derivationURI") String derivationURI,@QueryParam("agentDN") String agentDN)
	{
		this.query_Key=parameter1;
		this.query_dir_path=this.data_root_path+"/"+this.query_Key;
		this.pingback_Derivation=parameter2;
		
		//Retrieve access metadata from access.ttl
		String access_path=this.query_dir_path+"/access.ttl";
		String originURI="";
		String serviceURI="";
		
		try {
			FileReader fReader=new FileReader(new File(access_path));
			BufferedReader bReader=new BufferedReader(fReader);
			String line;
			while((line=bReader.readLine())!=null)
			{
				if(line.contains("prov:wasDerivedFrom") && !line.contains("source/komadu/dataset/pingback/version"))
				{
					String[] tokens=line.split(" ");
					originURI=tokens[1].replace(";", "");
				}
				if(line.contains("prov:wasGeneratedBy"))
				{
					String[] tokens=line.split(" ");
					serviceURI=tokens[1].replace(";", "");
				}
			}
			bReader.close();
			fReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Perform Pingback Derivation(Revision, Specialization, etc.) and Get response as updated provenance graph
		String response="";
		
		if(pingback_Derivation.equalsIgnoreCase("revision"))
		{
			// Perform Revision and post related provenance back to Pingback Link
			createRevision revisionClient=new createRevision(serviceURI);
			try {
				revisionClient.newRevision(originURI, derivationURI, agentDN);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Giving 2 seconds for processing of notifications
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Get response as Linked Data after pingback revision provenance
			//queryEntityGraph queryclient=new queryEntityGraph(serviceURI);
			//response=queryclient.getEntityGraph(derivationURI);
			response+="curl -L http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/source/komadu/dataset/pingback/version/"+this.query_Key+"\n";
			response+="<http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/source/komadu/dataset/pingback/version/"+this.query_Key+">"+"\n";
			response+="rdf:type ns2:VersionedDataset , ns0:Dataset;"+"\n";
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		    Date current = new Date();
		    String strDate = sdfDate.format(current);
			response+="ns3:modified  \""+strDate+"\""+"^^<http://www.w3.org/2001/XMLSchema#dateTime>;"+"\n";
			response+="ns2:source_identifier \""+"komadu\";"+"\n";
			response+="ns2:version_identifier \""+this.query_Key+"\";"+"\n";
			response+="ns2:dataset_identifier \""+"pingback\";"+"\n";
			response+="ns2:base_uri \""+originURI+"\";"+"\n";
			response+="ns2:derivation_uri \""+derivationURI+"\";"+"\n";
			response+="ns2:agent_DN \""+agentDN+"\".";
			
		}
		return Response.status(200).entity(response).build();
	}
}
