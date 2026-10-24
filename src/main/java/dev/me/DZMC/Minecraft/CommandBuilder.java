package dev.me.DZMC.Minecraft;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.me.DZMC.Account.Users;
import dev.me.DZMC.Application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    private CommandBuilder() {}

     private static final JsonMapper mapper = new JsonMapper();
     private static final String libsPath = "C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\libraries\\";

     private static final List<String> command = new ArrayList<>();

     private static Versions.Version _VanillaVer; //will be set only if game is modded.

    public static void buildCommand(Versions.Version bootVersion) {
        command.clear();

        boolean modded = checkModded(bootVersion);

        try {
            System.out.println("Configuring Minecraft launch strings...");

            command.add("javaw.exe"); // path to java yk

            command.add("-Xmx3G"); // memory

            command.add("-cp"); //class path

            command.add(createVersionParams(bootVersion, modded)); // version parameters

            command.add(bootVersion.JSON.get("mainClass").asText()); // main class

            // 5. Game parameters
            command.add("--username"); command.add(Users.getCurrentUser().username());
            command.add("--bootVersion"); command.add(bootVersion.JSON.get("id").asText());
            command.add("--gameDir"); command.add("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft");
            command.add("--assetsDir"); command.add("C:\\Users\\sagar\\AppData\\Roaming\\.minecraft\\assets");
            command.add("--assetIndex");

            if (modded) {
                command.add(_VanillaVer.JSON.get("assets").asText());
            } else {
                command.add(bootVersion.JSON.get("assets").asText());
            }

            command.add("--uuid"); command.add(Users.getCurrentUser().UUID());
            command.add("--userType"); command.add(Users.getCurrentUser().userType());
            command.add("--accessToken"); command.add(Users.getCurrentUser().accessToken()); // IN DEV ONLY DUMMY VALUE. THIS WILL BE REWORKED TO BE ENCRYPTED.
            command.add("--version"); command.add(bootVersion.Directory.getName());

            System.out.println("JAVA: " + command.get(0));

            for (int i = 0; i < command.size(); i++) {
                System.out.println("ARG[" + i + "]: " + command.get(i));
            }


        } catch (Exception e) {
            System.err.println("Failed to launch Minecraft:");
            e.printStackTrace();
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

    private static boolean checkModded(Versions.Version ver) {
        JsonNode inherit;
        try {
            inherit = ver.JSON.get("inheritsFrom");
            if (inherit == null) {
                System.out.println("===============Non Modded=================");
            }
            return inherit != null;
        } catch (Exception e) {
            System.out.println("===============Non Modded=================");
            return false;
        }
    }

    private static String createVersionParams(Versions.Version ver, boolean mods) {
        String Params = "";

        Params += (getVersionJarByDir(ver.Directory) + ";"); // adds the first jar.

        String VanillaVerID;
        if (mods) {
            VanillaVerID = ver.JSON.get("inheritsFrom").asText();
            _VanillaVer = Versions.getVersion(VanillaVerID);
            Params += createVersionParams(_VanillaVer, false);
            Params += library_handler(_VanillaVer.JSON.get("libraries"));
        }

        Params += library_handler(ver.JSON.get("libraries"));

        return Params;
    }

    public static String getVersionJarByDir(File dir) {
        return (dir.getAbsolutePath()
                + "/"
                + dir.getName()
                + ".jar"
        );
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
