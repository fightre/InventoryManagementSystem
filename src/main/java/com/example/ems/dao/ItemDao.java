package com.example.ems.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.example.ems.Constants.Constants;
import com.example.ems.bean.Item;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Repository
public class ItemDao extends Connection {

	public List<Item> getAllDevices() {

		List<Item> deviceList = new ArrayList<>();
		try {

			MongoCollection<Document> deviceTable = getCollection(Constants.ITEM);

			FindIterable<Document> iterable = deviceTable.find();
			iterable.forEach(new Block<Document>() {
				@Override
				public void apply(final Document document) {
					Gson gson = new Gson();
					deviceList.add(gson.fromJson(document.toJson(), Item.class));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceList;
	}

	public boolean addAllDevices(List<Item> devices) {
		List<Item> checkDevices = new ArrayList<>();
		devices.stream().forEach(d -> checkDevices.add(addDevice(d)));
		if (checkDevices.contains(null)) {
			return false;
		}

		return true;
	}

	public Item getItemById(String id) {
		Item item = null;
		MongoCollection<Document> deviceTable = getCollection(Constants.ITEM);
		Bson filter = Filters.eq("id", id);
		FindIterable<Document> iterable = deviceTable.find(filter);
		Gson gson = new Gson();
		item = gson.fromJson(iterable.first().toJson(), Item.class);
		return item;
	}

	public List<Item> getItemsAssignedToPerson(String personName) {
		List<Item> deviceList = new ArrayList<>();

		return deviceList;
	}

	public Item addDevice(Item device) {
		try {
			MongoCollection<Document> deviceTable = getCollection(Constants.ITEM);
			Gson gson = new Gson();
			deviceTable.insertOne(Document.parse(gson.toJson(device)));
			return device;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
