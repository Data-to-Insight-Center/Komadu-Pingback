package edu.indiana.d2i.komadu.axis2.client;

import java.io.IOException;

public class createDerivation {
	private KomaduServiceStub stub = null;

	public createDerivation(String komaduURL) {
		try {
			// create stub
			stub = new KomaduServiceStub(komaduURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newDerivation(String original_uri, String new_uri,
			String ownerDN) throws Exception {

		EntityType original_entity = createFileEntity.newFileEntity(
				original_uri, "");
		EntityType new_entity = createFileEntity
				.newFileEntity(new_uri, ownerDN);

		AddEntityEntityRelationshipDocument entityEntity = AddEntityEntityRelationshipDocument.Factory
				.newInstance();
		EntityEntityType entityEntityType = EntityEntityType.Factory
				.newInstance();

		DerivationType derivation = DerivationType.Factory.newInstance();
		derivation.setUsedEntityID(original_uri);
		derivation.setGeneratedEntityID(new_uri);

		// Attributes
		AttributesType attributes = AttributesType.Factory.newInstance();
		AttributeType[] attributesArr = new AttributeType[1];
		// Attribute 1
		AttributeType att1 = createAttribute("Data_Derivation", "dummy_val");
		attributesArr[0] = att1;
		attributes.setAttributeArray(attributesArr);
		derivation.setAttributes(attributes);

		entityEntityType.setEntity1(original_entity);
		entityEntityType.setEntity2(new_entity);
		entityEntityType.setDerivation(derivation);
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
