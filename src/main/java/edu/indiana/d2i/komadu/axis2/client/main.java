package edu.indiana.d2i.komadu.axis2.client;

public class main {
	public static void main(String args[]) {
		queryEntityGraph queryclient=new queryEntityGraph("http://localhost:8080/axis2/services/KomaduService");
		String response=queryclient.getEntityGraph("file://foo/bar/data1.txt_2866");
		
		/*createSpecialization ingestclient=new createSpecialization("http://localhost:8080/axis2/services/KomaduService");
		try {
			ingestclient.newSpecialization("file://foo/bar/data1.txt_2866","file://foo/bar/spec_data1.txt_2866","quzhou");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		System.out.println(response);
	}
}

