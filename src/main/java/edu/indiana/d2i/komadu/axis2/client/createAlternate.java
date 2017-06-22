package edu.indiana.d2i.komadu.axis2.client;

import java.io.IOException;

public class createAlternate {
	private KomaduServiceStub stub = null;

	public createAlternate(String komaduURL) {
		try {
			// create stub
			stub = new KomaduServiceStub(komaduURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newAlternate(String original_uri, String new_uri, String ownerDN)
			throws Exception {

		EntityType original_entity = createFileEntity.newFileEntity(
				original_uri, "");
		EntityType new_entity = createFileEntity
				.newFileEntity(new_uri, ownerDN);

		AddEntityEntityRelationshipDocument entityEntity = AddEntityEntityRelationshipDocument.Factory
				.newInstance();
		EntityEntityType entityEntityType = EntityEntityType.Factory
				.newInstance();

		AlternateType alternate = AlternateType.Factory.newInstance();
		alternate.setAlternate1ID(original_uri);
		alternate.setAlternate2ID(new_uri);

		entityEntityType.setEntity1(original_entity);
		entityEntityType.setEntity2(new_entity);
		entityEntityType.setAlternate(alternate);
		entityEntity.setAddEntityEntityRelationship(entityEntityType);
		// invoke
		stub.addEntityEntityRelationship((edu.indiana.d2i.komadu.axis2.client.AddEntityEntityRelationshipDocument) entityEntity);
	}
}
