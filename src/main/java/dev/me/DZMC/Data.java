package dev.me.DZMC;

import com.fasterxml.jackson.databind.JsonNode;

import javax.sound.sampled.FloatControl;
import java.io.InputStream;
import java.net.URL;

public class Data {
    private Data() {}
    private static boolean _initialsed = false;

    private static JsonNode _preferences;



    public static JsonNode getPreferencesData(String key) {
        if (!_initialsed) _loadPreferencesData();
        return _preferences.get(key);
    }





    private static void _loadPreferencesData() {
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
