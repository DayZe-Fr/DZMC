package dev.me.DZMC;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Data {
    private Data() {}
    private static boolean _initialsed = false;

    private static JsonNode _preferences;

    private static File _minecraftRoot;

    public static final JsonMapper mapper = new JsonMapper();

// TEMP
    private static String loggedinUsr = "DayZe";

    public static JsonNode getPreferencesData(String key) throws DataLoadingException {
        if (!_initialsed) _loadData();
        return _preferences.get("Users").get(loggedinUsr).get(key);
    }

    public static File get_minecraftRoot() throws DataLoadingException {
        if (!_initialsed) _loadData();
        return _minecraftRoot;
    }



    private static void _loadData() throws DataLoadingException {
        _initialsed = true;
        try {
            InputStream data_stream = Data.class.getResourceAsStream("/preferences.json");
            _preferences = Data.mapper.readTree(data_stream);
            _minecraftRoot = new File(System.getenv("APPDATA") + "/.minecraft");
        } catch (Exception e) {
            System.out.println("Unable to start launcher: preferences.json missing"); // will make a generator later
            System.out.println(e.getMessage());
            throw new DataLoadingException("resources/preferences.json");
        }
    }

    public static URL loadResourcePath(String Location) throws DataLoadingException {

        URL path = Data.class.getResource("/" + Location);
        if (path == null) {
            throw new DataLoadingException(Location);
        } else {
            return path;
        }
    }

    public static class DataLoadingException extends Exception {
        public DataLoadingException(String location) {
            super("Failed to load data from: " + location);
        }
    }
}
