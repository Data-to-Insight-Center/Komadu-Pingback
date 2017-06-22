package edu.indiana.d2i.komadu.axis2.client;

import java.util.Calendar;

public class createFileEntity {
	 public static EntityType newFileEntity(String fileURI, String OwnerDN) throws Exception {
	        EntityType entity = EntityType.Factory.newInstance();
	        FileType file = FileType.Factory.newInstance();
	        String fileName = getFileName(fileURI);
	        file.setFileName(fileName);
	        file.setFileURI(fileURI);
	        file.setCreateDate(Calendar.getInstance());
	        file.setMd5Sum("dummy_md5_" + fileName);
	        file.setOwnerDN(OwnerDN);
	        file.setSize(500);

	        entity.setFile(file);
	        return entity;
	    }
	 
	 private static String getFileName(String fileURI) {
	        return fileURI.substring(fileURI.lastIndexOf('/') + 1);
	    }
}
