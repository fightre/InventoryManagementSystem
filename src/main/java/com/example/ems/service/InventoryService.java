package com.example.ems.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ems.bean.Item;
import com.example.ems.dao.ItemDao;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Service
public class InventoryService {

	@Autowired
	private ItemDao inventoryDao;

	public List<Item> getDevices() {
		return inventoryDao.getAllDevices();
	}

	public Item addDevice(Item device) {
		return inventoryDao.addDevice(device);
	}

	public List<String[]> exportData() {
		List<Item> deviceList = inventoryDao.getAllDevices();
		List<String[]> deviceResult = new ArrayList<>();
		deviceList.stream().forEach(d -> {
			String[] device = new String[2];
			device[0] = d.getName();
			device[1] = String.valueOf(d.getCount());
			deviceResult.add(device);
		});
		return deviceResult;
	}


	public boolean importData(InputStream inputStream) {

		try {

			inventoryDao.addAllDevices(readAll(inputStream));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Item> readAll(InputStream inputStream) {
		CsvToBean<Item> csv = new CsvToBean<>();
		CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
		csv.setCsvReader(reader);
		Map<String, String> mapping = new HashMap<>();
		mapping.put("Name", "name");
		mapping.put("Count", "count");
		HeaderColumnNameTranslateMappingStrategy<Item> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
		strategy.setType(Item.class);
		strategy.setColumnMapping(mapping);
		csv.setMappingStrategy(strategy);
		return csv.parse();

	}

}
