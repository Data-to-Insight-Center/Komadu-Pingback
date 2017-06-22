package edu.indiana.d2i.komadu.pingback.server;

public class createAccess {
	public static String create_access(String pingBack_URI,String hasProvenance_URI, String query_URI, String serviceURI)
	{
		String access="";
		
		//Loading @Prefix
		access+="@prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>."+"\n";
		access+="@prefix foaf:<http://xmlns.com/foaf/0.1/>."+"\n";
		access+="@prefix conversion:<http://purl.org/twc/vocab/conversion/>."+"\n";
		access+="@prefix dcat:<http://www.w3.org/ns/dcat#>."+"\n";
		access+="@prefix void:<http://rdfs.org/ns/void#>."+"\n";
		access+="@prefix nfo:<http://www.semanticdesktop.org/ontologies/nfo/#>."+"\n";
		access+="@prefix prov:<http://www.w3.org/ns/prov#>."+"\n";
		access+="@prefix datafaqs:<http://purl.org/twc/vocab/datafaqs#>."+"\n";
		access+="@prefix:<http://komadu.d2i.indiana.edu>."+"\n";
		
		access+=pingBack_URI+"\n";
		access+="conversion:source_identifier  \"komadu\";"+"\n";
		access+="conversion:dataset_identifier \"pingback\";"+"\n";
		access+="prov:wasDerivedFrom "+hasProvenance_URI+";"+"\n";
		access+="prov:wasDerivedFrom "+query_URI+";"+"\n";
		access+="prov:wasGeneratedBy "+serviceURI+";";
		return access;
	}

}
