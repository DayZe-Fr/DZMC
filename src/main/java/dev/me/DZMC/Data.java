package dev.me.DZMC;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.InputStream;

public class Data {
    private Data() {}
    private static boolean _initialsed = false;

    private static JsonNode _preferences;



    public static JsonNode getData(String key) {
        if (!_initialsed) _loadData();
        return _preferences.get(key);
    }





    private static void _loadData() {
        try {
            InputStream data_stream = Application.class.getResourceAsStream("/preferences.json");
            _preferences = Application.mapper.readTree(data_stream);
        } catch (Exception e) {
            System.out.println("Unable to start launcher: preferences.json missing"); // will make a generator later
            System.out.println(e.getMessage());
            System.exit(1);
        }
        _initialsed = true;
    }
}
