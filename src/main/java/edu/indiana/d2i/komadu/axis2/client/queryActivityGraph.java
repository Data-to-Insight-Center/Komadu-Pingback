package edu.indiana.d2i.komadu.axis2.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;

import edu.indiana.d2i.komadu.query.DetailEnumType;
import edu.indiana.d2i.komadu.query.GetActivityGraphRequestDocument;
import edu.indiana.d2i.komadu.query.GetActivityGraphRequestType;
import edu.indiana.d2i.komadu.query.GetActivityGraphResponseDocument;


public class queryActivityGraph {
private KomaduServiceStub stub = null;
    
	public queryActivityGraph(String komaduURL)
	{
		try {
            // create stub
            stub = new KomaduServiceStub(komaduURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public String getActivityGraph(String activityURI) {
    	try {
    	    System.out.println("\n\n Activity Graph without context \n\n");
    	    GetActivityGraphRequestDocument activityGraphRequest = GetActivityGraphRequestDocument.Factory.newInstance();
    	    GetActivityGraphRequestType actRequestType = GetActivityGraphRequestType.Factory.newInstance();
    	    actRequestType.setInformationDetailLevel(DetailEnumType.FINE);
    	    actRequestType.setActivityURI(activityURI);
    	    activityGraphRequest.setGetActivityGraphRequest(actRequestType);
    	    GetActivityGraphResponseDocument actResponse = stub.getActivityGraph(activityGraphRequest);
    	     // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);
            // Print some output: goes to your special stream
            System.out.println(actResponse.getGetActivityGraphResponse().getDocument());
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

