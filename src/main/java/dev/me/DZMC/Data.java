package dev.me.DZMC;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.InputStream;

public class Data {
    private Data() {}
    private static boolean _initialsed = false;

    private static JsonNode _preferences;

    private static File _minecraftRoot;



    public static JsonNode getPreferencesData(String key) {
        if (!_initialsed) _loadData();
        return _preferences.get(key);
    }

    public static File get_minecraftRoot() {
        if (!_initialsed) _loadData();
        return _minecraftRoot;
    }



    private static void _loadData() {
        _initialsed = true;
        try {
            InputStream data_stream = Application.class.getResourceAsStream("/preferences.json");
            _preferences = Application.mapper.readTree(data_stream);
            _minecraftRoot = new File(System.getenv("APPDATA") + "/.minecraft");
        } catch (Exception e) {
            System.out.println("Unable to start launcher: preferences.json missing"); // will make a generator later
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static java.net.URL loadResource(String Location) {
        try {
            return Application.class.getResource("/" + Location);
        } catch (Exception e) {
            System.out.println("Unable to find '" + Location + "'");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
