package com.surajproject.csvtojson;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVToJsonConverter {
	public static void main(String[] args) {
		String inputCSVFile = "suraj.csv";
		String outputJSONFile = "output.json";

		try (CSVReader csvReader = new CSVReader(
				new InputStreamReader(new FileInputStream(inputCSVFile), StandardCharsets.UTF_8))) {
			List<String[]> csvData = csvReader.readAll();

			JSONArray jsonArray = new JSONArray();

			for (String[] rowData : csvData) {
				JSONObject jsonObject = new JSONObject();
				for (int i = 0; i < rowData.length; i++) {
					String cleanedData = cleanString(rowData[i]);
					jsonObject.put("column_" + i, cleanedData);
				}
				jsonArray.put(jsonObject);
			}

			String jsonOutput = jsonArray.toString(4);

			try (BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputJSONFile), StandardCharsets.UTF_8))) {
				bufferedWriter.write(jsonOutput);
				System.out.println("CSV to JSON conversion completed. Output written to " + outputJSONFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}

	private static String cleanString(String input) {
		// Replace or remove non-printable and control characters
		return input.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").replaceAll("[\\p{M}]", "").replaceAll("[^\\p{Print}]",
				"");
	}
}
