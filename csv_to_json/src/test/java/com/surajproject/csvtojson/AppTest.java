package com.surajproject.csvtojson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.json.JSONArray;
import org.json.JSONObject;
import io.jsonwebtoken.io.IOException;

/**
 * Hello world!
 *
 */
public class AppTest {
	public static void main(String[] args) throws FileNotFoundException, java.io.IOException, CsvException {
		System.out.println("Hello World!");
		try (CSVReader csvReader = new CSVReader(new FileReader("input.csv"))) {
			List<String[]> csvData = csvReader.readAll();
			// You may need to adjust this depending on the structure of your CSV file
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray jsonArray = new JSONArray();

		String[][] csvData = null;
		for (String[] rowData : csvData) {
			JSONObject jsonObject = new JSONObject();
			for (int i = 0; i < rowData.length; i++) {
				jsonObject.put("column_" + i, rowData[i]);
			}
			jsonArray.put(jsonObject);
		}

		String jsonOutput = jsonArray.toString(4); // Optional: To pretty-print the JSON

		try (FileWriter fileWriter = new FileWriter("output.json")) {
			fileWriter.write(jsonOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
