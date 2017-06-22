package edu.indiana.d2i.komadu.axis2.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3.www.ns.prov.Document;

import edu.indiana.d2i.komadu.query.DetailEnumType;
import edu.indiana.d2i.komadu.query.GetEntityGraphRequestDocument;
import edu.indiana.d2i.komadu.query.GetEntityGraphRequestType;
import edu.indiana.d2i.komadu.query.GetEntityGraphResponseDocument;

public class queryEntityGraph {
	private KomaduServiceStub stub = null;
    
	public queryEntityGraph(String komaduURL)
	{
		try {
            // create stub
            stub = new KomaduServiceStub(komaduURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public String getEntityGraph(String entityURI) {
    	try {
            //System.out.println("\n\n Entity Graph without context \n\n");
            GetEntityGraphRequestDocument entityGraphRequest = GetEntityGraphRequestDocument.Factory.newInstance();
            GetEntityGraphRequestType entityRequestType = GetEntityGraphRequestType.Factory.newInstance();
            entityRequestType.setInformationDetailLevel(DetailEnumType.FINE);
            entityRequestType.setEntityURI(entityURI);
            entityRequestType.setEntityType(edu.indiana.d2i.komadu.query.EntityEnumType.FILE);
            entityGraphRequest.setGetEntityGraphRequest(entityRequestType);
            GetEntityGraphResponseDocument entityResponse = stub.getEntityGraph(entityGraphRequest);
         // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);
            // Print some output: goes to your special stream
            System.out.println(entityResponse.getGetEntityGraphResponse().getDocument());
            // Put things back
            System.out.flush();
            System.setOut(old);
            // Show what happened
            return baos.toString();
            
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
