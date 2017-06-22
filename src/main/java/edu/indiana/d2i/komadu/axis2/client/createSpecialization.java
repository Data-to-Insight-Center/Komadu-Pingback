package edu.indiana.d2i.komadu.axis2.client;

import java.io.IOException;

public class createSpecialization {
	private KomaduServiceStub stub = null;
    
	public createSpecialization(String komaduURL)
	{
		try {
            // create stub
            stub = new KomaduServiceStub(komaduURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void newSpecialization(String original_uri,String new_uri, String ownerDN)
			throws Exception {
		
		EntityType original_entity=createFileEntity.newFileEntity(original_uri,"");
		EntityType new_entity=createFileEntity.newFileEntity(new_uri,ownerDN);
		
		AddEntityEntityRelationshipDocument entityEntity = AddEntityEntityRelationshipDocument.Factory
				.newInstance();
		EntityEntityType entityEntityType = EntityEntityType.Factory
				.newInstance();

		SpecializationType specialization = SpecializationType.Factory
				.newInstance();
		specialization.setSpecificEntityID(original_uri);
		specialization.setGeneralEntityID(new_uri);

		entityEntityType.setEntity1(original_entity);
		entityEntityType.setEntity2(new_entity);
		entityEntityType.setSpecialization(specialization);
		entityEntity.setAddEntityEntityRelationship(entityEntityType);
		// invoke
		stub.addEntityEntityRelationship((edu.indiana.d2i.komadu.axis2.client.AddEntityEntityRelationshipDocument) entityEntity);
	}

}
