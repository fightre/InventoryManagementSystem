package com.example.ems.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ems.bean.Item;
import com.example.ems.service.InventoryService;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping("/devices")
	public List<Item> getDevices() {
		return inventoryService.getDevices();
	}

	@PostMapping("/device/add")
	public Item addDevice(@RequestBody String deviceString) {

		return inventoryService.addDevice(new Gson().fromJson(deviceString, Item.class));
	}

	@GetMapping("/device/export")
	public boolean exportData(HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=\"sample.csv\"");
		try {
			CSVWriter writer = new CSVWriter(response.getWriter());

			String[] header = { "Name", "Count" };
			writer.writeNext(header);
			List<String[]> deviceList = inventoryService.exportData();
			writer.writeAll(deviceList, true);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@PostMapping(value = "/device/upload", consumes = "multipart/form-data")
	public boolean importData(@RequestParam("file") MultipartFile file) {
		try {
			inventoryService.importData(file.getInputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
