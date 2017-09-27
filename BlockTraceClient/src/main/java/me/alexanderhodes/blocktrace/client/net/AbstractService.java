package me.alexanderhodes.blocktrace.client.net;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 26.09.2017.
 */
public abstract class AbstractService<T> {

    private static final String REST_API_URL = "http://localhost:8080/blocktrace/rest/";

    private Class<T> type;

    public AbstractService() {
    }

    public AbstractService(Class<T> type) {
        this.type = type;
    }

    public InputStream requestInputStreamSingle(String name, String param) throws Exception {
        URL url = new URL(REST_API_URL + name + "/" + param);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");

        return urlConnection.getInputStream();
    }

    public InputStream requestInputStreamList (String name) throws Exception {
        URL url = new URL(REST_API_URL + name + "/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");

        return urlConnection.getInputStream();
    }

    public InputStream requestInputStreamList (String name, String param) throws Exception {
        URL url = new URL(REST_API_URL + name + "/" + param);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");

        return urlConnection.getInputStream();
    }

    public T post (T object, String name) throws Exception {
        String json = toJson(object);

        URL url = new URL(REST_API_URL + name + "/");

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("charset", "UTF-8");
        urlConnection.setDoOutput(true);

        byte[] utf8Json = json.getBytes("UTF-8");


        DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
        outputStream.write(utf8Json, 0, utf8Json.length);

//        outputStream.writeBytes(json);
        outputStream.flush();
        outputStream.close();

        T receivedObject = createEntity(urlConnection.getInputStream());
        return receivedObject;
    }

    public T createEntity(InputStream inputStream) throws Exception {
        String json = readInputStream(inputStream);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement jsonElement, Type typeOf, JsonDeserializationContext context)
                    throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();

        return gson.fromJson(json, type);
    }

    public Gson initGson () {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement jsonElement, Type typeOf, JsonDeserializationContext context)
                    throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        });

        return builder.create();
    }

    public String readInputStream (InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine = null;
        StringBuffer buffer = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine);
        }
        reader.close();

        return buffer.toString();
    }

    public String toJson(T object) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                Gson gson = new Gson();
                return gson.toJsonTree(date.getTime());
            }
        });


        Gson gson = builder.create();

        return gson.toJson(object);
    }

}
