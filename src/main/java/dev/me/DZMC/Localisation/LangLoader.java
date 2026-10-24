package dev.me.DZMC.Localisation;

import com.fasterxml.jackson.databind.JsonNode;
import dev.me.DZMC.Account.Users;
import dev.me.DZMC.Data;

import java.io.InputStream;

public class LangLoader {
    private static String _lang_preference;
    private static JsonNode _langData;

    private static boolean _initialsed = false;


    public static String Query(String key) {
        if (!_initialsed) _loadData();
        return _langData.get(key).asText();
    }



    private static void _loadData() {
        try {
            _lang_preference = Users.getCurrentUser().lang();

//            InputStream dataStream = Application.class.getResourceAsStream(
//                    String.format("/lang/%s.json", _lang_preference.trim())
//            );
            InputStream dataStream = Data.getResourceAsInputStream(
                    String.format("lang/%s.json", _lang_preference.trim())
            );

            _langData = Data.mapper.readTree(dataStream);

        } catch (Exception e) {
            System.out.println("Unable to start launcher: lang is missing"); // will make a generator later
            System.out.println(e.getMessage());
            System.exit(1);
        }
        _initialsed = true;
    }
}
