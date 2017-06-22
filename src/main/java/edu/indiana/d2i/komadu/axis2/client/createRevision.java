package edu.indiana.d2i.komadu.axis2.client;

import java.io.IOException;

public class createRevision {
	private KomaduServiceStub stub = null;

	public createRevision(String komaduURL) {
		try {
			// create stub
			stub = new KomaduServiceStub(komaduURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newRevision(String original_uri, String new_uri, String ownerDN)
			throws Exception {

		EntityType original_entity = createFileEntity.newFileEntity(
				original_uri, "");
		EntityType new_entity = createFileEntity
				.newFileEntity(new_uri, ownerDN);

		AddEntityEntityRelationshipDocument entityEntity = AddEntityEntityRelationshipDocument.Factory
				.newInstance();
		EntityEntityType entityEntityType = EntityEntityType.Factory
				.newInstance();

		RevisionType revision = RevisionType.Factory.newInstance();
		revision.setUsedEntityID(original_uri);
		revision.setGeneratedEntityID(new_uri);

		// Attributes
		AttributesType attributes = AttributesType.Factory.newInstance();
		AttributeType[] attributesArr = new AttributeType[1];
		// Attribute 1
		AttributeType att1 = createAttribute("Data Revision", "dummy_val");
		attributesArr[0] = att1;
		attributes.setAttributeArray(attributesArr);
		revision.setAttributes(attributes);

		entityEntityType.setEntity1(original_entity);
		entityEntityType.setEntity2(new_entity);
		entityEntityType.setRevision(revision);
		entityEntity.setAddEntityEntityRelationship(entityEntityType);
		// invoke
		stub.addEntityEntityRelationship((edu.indiana.d2i.komadu.axis2.client.AddEntityEntityRelationshipDocument) entityEntity);
	}

	private static AttributeType createAttribute(String name, String val)
			throws Exception {
		AttributeType att = AttributeType.Factory.newInstance();
		att.setProperty(name);
		att.setValue(val);
		return att;
	}
}
