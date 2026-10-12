package dev.me.DZMC.Minecraft;

import com.fasterxml.jackson.databind.JsonNode;
import dev.me.DZMC.Application;
import dev.me.DZMC.Data;

import java.io.File;
import java.util.HashMap;

public class Versions {
    private static File _minecraftVersionsRoot;

    private static HashMap<String, Version> _versions = new HashMap<>();

    public static class Version {
        public final JsonNode JSON;
        public final File Directory;

        public Version(JsonNode node, File dir) {this.JSON = node; this.Directory = dir;}
    }

    public static HashMap<String, Version> enumerateVersions() {
        try {
            _versions.clear();

            _minecraftVersionsRoot = new File(Data.get_minecraftRoot() + "/versions");

            File[] versionFolders = _minecraftVersionsRoot.listFiles();

            for (File versionFolder : versionFolders) {

                if (!versionFolder.isDirectory()) continue;

                File jsonfile = new File(versionFolder
                        + "/"
                        + versionFolder.getName()
                        + ".json"
                );

                JsonNode node = Application.mapper.readTree(jsonfile);
                Version ver = new Version(node, versionFolder);

                _versions.put(versionFolder.getName(), ver);
            }
        } catch (Exception e) {
            System.out.println("Unable to load launcher: VERSIONS NOT FOUND");
            System.out.println(e.getMessage());
            System.out.println("===============");
            e.printStackTrace();
            System.exit(1);
        }
        return _versions;
    }





}
