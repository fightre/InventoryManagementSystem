package com.example.ems.dao;

import org.bson.Document;

import com.example.ems.Constants.Constants;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Connection {

	private MongoClient mongoClient = null;
	

	private synchronized MongoClient getMongoConnection() {
		if (mongoClient == null) {
			mongoClient = new MongoClient("localhost", 27017);
		}
		return mongoClient;
	}
	
	
	public MongoCollection<Document> getCollection(String collectionName) {
		getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(Constants.INVENTORY);
		return mongoDatabase.getCollection(collectionName);
	}
}
