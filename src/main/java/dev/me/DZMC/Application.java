package dev.me.DZMC;

import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.me.DZMC.UI.LauncherWindow;

public class Application {
    public static final String username = "JustADude1339";


    static void main(String[] args) {
        LauncherWindow.setArgs(args);
        LauncherWindow.startWindow();
    }
}
