package edu.indiana.d2i.komadu.pingback.server;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import edu.indiana.d2i.komadu.axis2.client.*;

@Path("/query")
public class queryGraph {
	private String host;
	private String port;
	private String query_Time;
	private String query_ID;
	private String query_Key;
	private String query_dir_path;
	private String data_root_path;
	
	public queryGraph(){
		// TODO Auto-generated constructor stub
		
		//Get Host, Port and Data Root Information
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.host=props.getProperty("Komadu-Pingback-Server-Host");
		this.port=props.getProperty("Komadu-Pingback-Server-Port");
		this.data_root_path=props.getProperty("Data-Root");
	
		//Create Information for this query
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		this.query_Time=dateFormat.format(date);
		this.query_ID = UUID.randomUUID().toString();
		this.query_Key=this.query_Time+"-"+this.query_ID;
		this.query_dir_path=this.data_root_path+"/"+this.query_Key;
	}
	
	@GET
	@Path("/{parameter}")
	public Response responseMsg( @PathParam("parameter") String parameter,
			@QueryParam("serviceURI") String serviceURI,@QueryParam("queryURI") String queryURI) {
		
		String output="";
		String access="";
		String response="";
		
		File data_root_dir=new File(this.data_root_path);
		if(!data_root_dir.exists())
		{
			data_root_dir.mkdirs();
		}
		
		File query_dir=new File(this.query_dir_path);
		if(!query_dir.exists())
		{
			query_dir.mkdirs();
		}
		
		if(parameter.equalsIgnoreCase("entity"))
		{
			queryEntityGraph queryclient=new queryEntityGraph(serviceURI);
			output=queryclient.getEntityGraph(queryURI);
		}
		else if(parameter.equalsIgnoreCase("activity"))
		{
			queryActivityGraph queryclient=new queryActivityGraph(serviceURI);
			output=queryclient.getActivityGraph(queryURI);
		}
		
		try {
			//Write provenance according to this query
			FileWriter fWriter=new FileWriter(this.query_dir_path+"/query_response.xml");
			fWriter.write(output);
			fWriter.flush();
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Respond with Pingback URI and hasProvenance URI
		response+="HTTP/1.1 200 OK"+"\n";
		String pingBack_URI="<http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/prov-pingback/"+this.query_Key+"/mykey>";
		String hasProvenance_URI="<http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/source/komadu/dataset/pingback/version/"+this.query_Key+">";
		response+="Link: <http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/source/komadu/dataset/pingback/version/"+this.query_Key+">;"+"\n";
		response+="rel=\"http://www.w3.org/ns/prov#has_provenance\";"+"\n";
		response+="anchor=\"http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/source/komadu/dataset/pingback/version/"+this.query_Key+"/response\";"+"\n";
		response+="Link: <http://"+this.host+":"+this.port+"/Komadu-Pingback-Server/prov-pingback/"+this.query_Key+"/mykey>;"+"\n";
		response+="rel=\"http://www.w3.org/ns/prov#pingback\";";
		
		// Write access.ttl file for further Pingback Derivation
		access=createAccess.create_access(pingBack_URI, hasProvenance_URI, queryURI,serviceURI);
		try {
			//Write provenance according to this query
			FileWriter fWriter=new FileWriter(this.query_dir_path+"/access.ttl");
			fWriter.write(access);
			fWriter.flush();
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return Response.status(200).entity(response).build();
		
		
	}
}
