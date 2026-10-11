package dev.me.DZMC.Minecraft;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    private CommandBuilder() {}

     private static final JsonMapper mapper = new JsonMapper();
     private static final String libsPath = "C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\libraries\\";

     private static List<String> command = new ArrayList<>();


    public static List<String> buildCommand() {
        command.clear();

        try {
            JsonNode FABRIC_versionJson = mapper.readTree(new File("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\versions\\fabric-loader-0.19.5-1.21.1\\fabric-loader-0.19.5-1.21.1.json"));
            JsonNode FabricLibs = FABRIC_versionJson.get("libraries");

            JsonNode versionJson = mapper.readTree(new File("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\versions\\1.21.1\\1.21.1.json"));
            JsonNode libs = versionJson.get("libraries");

            System.out.println("Configuring Minecraft launch strings...");


            // 1. Path to Java
            command.add("javaw.exe");

            // 2. Memory Settings
            command.add("-Xmx2G");

            // 3. The Classpath (Telling Java where the engine and libraries are)

            // Replace 'fabric-loader-0.16.9-1.21.1' with your exact Fabric version folder name if different

            command.add("-cp");

            String versionParas = "C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\versions\\fabric-loader-0.19.5-1.21.1\\fabric-loader-0.19.5-1.21.1.jar;"
                    + "C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\versions\\1.21.1\\1.21.1.jar;";

            versionParas += library_handler(libs);
            versionParas += library_handler(FabricLibs);


            command.add(versionParas);

            // 4. The Fabric Main Class
            command.add("net.fabricmc.loader.impl.launch.knot.KnotClient");

            // 5. Game parameters (Passing 'dev' as the player name)
            command.add("--username"); command.add("dev");
            command.add("--version"); command.add("fabric-loader-1.21.1");
            command.add("--gameDir"); command.add("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft");
            command.add("--assetsDir"); command.add("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\assets");
            command.add("--assetIndex"); command.add("17"); // 17 is the index file for 1.21+ assets

            return command;

        } catch (Exception e) {
            System.err.println("Failed to launch Minecraft:");
            e.printStackTrace();
            return null;
        }
    }

    public static void Run(List<String> cmd) {
        try {
            if (cmd == null) {
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.directory(new File("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft"));
                pb.inheritIO();
                pb.start();
            } else {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.directory(new File("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft"));
                pb.inheritIO(); // Streams game output to your console so you can track errors
                pb.start();
            }
        } catch (Exception e) {
            System.err.println("Failed to launch Minecraft:");
            e.printStackTrace();
        }
    }

    private static String library_handler(JsonNode lib) {
        String Paras = "";
        for (int i = lib.size() - 1; i >= 0; i--) {
            String dependency = lib.get(i).get("name").asText(); // eg "net.fabricmc:fabric-loader:0.19.5"

            String[] libFullDir = dependency.split(":");

            String fullPath = libsPath;

            for (int argIndex = 0; argIndex <= 2; argIndex++) {
                if (argIndex == 2) {
                    fullPath += (libFullDir[argIndex] + "\\*"); // \\net\\fabricmc\\fabric-loader\\0.19.5\\*
                    continue;
                }

                String[] folders = libFullDir[argIndex].split("\\.");

                for (int folder = 0; folder <= folders.length - 1; folder++) {
                    fullPath += (folders[folder] + "\\"); // \\net\\fabricmc\\fabric-loader\\
                }
            }
            Paras += (fullPath + ";");
        }
        return Paras;
    }




}
