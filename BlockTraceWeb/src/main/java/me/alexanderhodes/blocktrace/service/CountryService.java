package me.alexanderhodes.blocktrace.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.alexanderhodes.blocktrace.model.Country;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

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

    private String readFile () {
        StringBuffer buffer = new StringBuffer();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("countries.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                buffer.append(currentLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    private List<Country> parseJson (String json) {
        Gson gson = new GsonBuilder().create();
        Country[] countries = gson.fromJson(json, Country[].class);

        return Arrays.asList(countries);
    }

    public static List<Country> getCountryList() {
        return countryList;
    }
}
