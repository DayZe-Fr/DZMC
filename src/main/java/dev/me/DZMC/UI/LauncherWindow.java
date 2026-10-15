package dev.me.DZMC.UI;

import dev.me.DZMC.Data;
import dev.me.DZMC.Localisation.LangLoader;
import dev.me.DZMC.Minecraft.CommandBuilder;
import dev.me.DZMC.Minecraft.Versions;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.util.HashMap;

public class LauncherWindow {
    private static boolean _initialized = false;

    private static LauncherWindow _windowInstance;
    private static FXBooter _booter;
    private static String[] _args;

    private static versionButton _selectedVersion;

    public static Stage _mainstage;

    private static Scene _mainScene;
    private static HBox _mainFrameBox;
    private static VBox _gameSetContainer;
    private static VBox _gameSets;
    private static Button _playButton;

    public static void setArgs(String[] args) {
        _args = args;
    }

    private LauncherWindow() {}

    public static void startWindow() {
        if (_initialized) return;
        javafx.application.Application.launch(
                FXBooter.class, _args
        );
        _initialized = true;
    }

    private static void initializeWindow() {

//        _mainstage.initStyle(StageStyle.TRANSPARENT);
        _mainstage.setHeight(LauncherConfig.WindowBaseHeight);
        _mainstage.setWidth(LauncherConfig.WindowBaseWidth);
        _mainstage.setResizable(false);
        _mainstage.setTitle("DZMC");

        _buildMainScene();

        _mainstage.setScene(_mainScene);

        _mainstage.show();
    }


    private static void _buildMainScene() {
        if (_mainstage == null || _mainScene != null) {
            return;
        }

        _mainFrameBox = new HBox();
        _mainFrameBox.setMaxSize(LauncherConfig.WindowBaseHeight, LauncherConfig.WindowBaseWidth);
        _mainFrameBox.getStyleClass().add("main-container-box");

        _mainScene = new Scene(_mainFrameBox);
        _mainScene.getStylesheets().add(
                Data.loadResource("fx-css/main.css").toExternalForm()
        );
        _mainScene.setFill(Color.TRANSPARENT);

        _gameSetContainer = new VBox();
        _gameSetContainer.getStyleClass().add("main-gameset-container");
        _gameSetContainer.setMinHeight(LauncherConfig.WindowBaseHeight * 0.9);
        _gameSetContainer.setMinWidth(LauncherConfig.WindowBaseWidth * 0.20);

        _gameSets = new VBox();
        _gameSets.getStyleClass().add("main-gamesets");
        _gameSets.setMinHeight(LauncherConfig.WindowBaseHeight * 0.75);
        VBox.setVgrow(_gameSets, Priority.ALWAYS);
        _gameSetContainer.getChildren().add(_gameSets);
        addVersions(_gameSets);


        _playButton = new Button(LangLoader.Query("dev.me.DZMC.launch_game"));
        _gameSetContainer.getChildren().add(_playButton);
        _playButton.getStyleClass().add("main-play-button");
        _playButton.setPrefWidth(Double.MAX_VALUE);
        _playButton.setPrefHeight(50);






//      Connections

        _playButton.setOnAction(event -> {
            if (_selectedVersion == null) {
                System.out.println("Cannot start: No version Selected");
                return;
            }
            CommandBuilder.buildCommand(_selectedVersion.version);
            CommandBuilder.Run(null);
        });



        _mainFrameBox.getChildren().add(_gameSetContainer);
        HBox.setMargin(_gameSetContainer, new Insets(10, 5, 10, 10));
    }

    private static void addVersions(VBox _container) {
        HashMap<String, Versions.Version> all_versions = Versions.enumerateVersions();

        all_versions.forEach((verName, _version) -> {
            _container.getChildren().add(createVersionBox(verName, _version));
        });
    }

    private static Button createVersionBox(
            String name,
            Versions.Version ver
    ) {
        versionButton _newButton = new versionButton();
        _newButton.version = ver;
        _newButton.setText(name);
        _newButton.getStyleClass().add("main-version-button");

        _newButton.setOnAction(e -> {
            if (_selectedVersion != null) {
                _selectedVersion.getStyleClass().remove("main-version-button-selected");
            }
            _selectedVersion = _newButton;
            _selectedVersion.getStyleClass().add("main-version-button-selected");
        });

        return _newButton;
    }

    private static class versionButton extends Button {
        public Versions.Version version;
    }











    public static class FXBooter extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            _mainstage = stage;
            initializeWindow();
        }
    }

    public static class LauncherConfig {
        public static final int WindowBaseHeight = (int)(Screen.getPrimary().getBounds().getHeight() * 0.75);
        public static final int WindowBaseWidth = (int)(Screen.getPrimary().getBounds().getWidth() * 0.75);
    }
}
