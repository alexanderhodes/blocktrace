package me.alexanderhodes.blocktrace.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.alexanderhodes.blocktrace.model.Country;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class CountryService {

	private static List<Country> countryList;

	static {
		CountryService cs = new CountryService();
		String json = cs.readFile();
		countryList = cs.parseJson(json);
	}

	/**
	 * read Countries.json File for getting countries
	 * 
	 * @return String of Countries.json file
	 */
	private String readFile() {
		StringBuffer buffer = new StringBuffer();

		try {
			// Initialize InputStream
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("countries.json");
			// Initialize BufferedReader
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String currentLine;
			// read file
			while ((currentLine = bufferedReader.readLine()) != null) {
				buffer.append(currentLine);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

	/**
	 * Parsing json String to country objects
	 * 
	 * @param json String of countries.json file
	 * @return List of countries
	 */
	private List<Country> parseJson(String json) {
		// Initialize GsonBuilder
		Gson gson = new GsonBuilder().create();
		// convert String to Object
		Country[] countries = gson.fromJson(json, Country[].class);

		return Arrays.asList(countries);
	}

	/**
	 * Return country list
	 * 
	 * @return List of countries
	 */
	public static List<Country> getCountryList() {
		return countryList;
	}
}
