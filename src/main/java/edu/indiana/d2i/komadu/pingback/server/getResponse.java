package edu.indiana.d2i.komadu.pingback.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/source/komadu/dataset/pingback/version")
public class getResponse {
	private String query_Key;
	private String data_root_path;
	private String query_dir_path;
	
	public getResponse() {
		// TODO Auto-generated constructor stub
		// Get Data Root Information
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data_root_path = props.getProperty("Data-Root");

	}
	
	@GET
	@Path("/{parameter1}/{parameter2}")
	public Response get_response(@PathParam("parameter1") String parameter1, @PathParam("parameter2") String parameter2)
	{
		this.query_Key=parameter1;
		this.query_dir_path=this.data_root_path+"/"+this.query_Key;
		
		String response="";
		
		if(parameter2.equalsIgnoreCase("response"))
		{
			try {
				FileReader fReader=new FileReader(new File(this.query_dir_path+"/query_response.xml"));
				BufferedReader bReader=new BufferedReader(fReader);
				String line;
				while((line=bReader.readLine())!=null)
				{
					response+=line+"\n";
				}
				bReader.close();
				fReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return Response.status(200).entity(response).build();
	}
}
