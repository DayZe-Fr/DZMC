package dev.me.DZMC.UI.main;

import dev.me.DZMC.Localisation.LangLoader;
import dev.me.DZMC.Minecraft.CommandBuilder;
import dev.me.DZMC.Minecraft.Versions;
import dev.me.DZMC.UI.LauncherWindow;
import dev.me.DZMC.UI.SceneController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class GamesetContainer extends VBox {

    private static versionButton _selectedVersionButton;

    private ScrollPane _versionsScrollContainer = new ScrollPane();
    private VBox _versionsContainerContent = new VBox();

    private Button _playButton = new Button(LangLoader.Query("dev.me.DZMC.launch_game"));


    public GamesetContainer() {
        this.getStyleClass().add("main-gameset-container");
        this.setMinHeight(SceneController.LauncherConfig.ContentAreaHeight * 0.9);
        this.setMaxWidth(SceneController.LauncherConfig.WindowBaseWidth * 0.20);
        this.setMinWidth(SceneController.LauncherConfig.WindowBaseWidth * 0.20);


        _versionsContainerContent.getStyleClass().add("main-gamesets");
        _versionsContainerContent.setMinHeight(LauncherWindow.LauncherConfig.WindowBaseHeight * 0.75);
        _versionsContainerContent.setSpacing(1.5);
        addVersions(_versionsContainerContent);

        _versionsScrollContainer.setContent(_versionsContainerContent);
        _versionsScrollContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        _versionsScrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        _versionsScrollContainer.getStyleClass().setAll("transparent");
        _versionsScrollContainer.setFitToWidth(true);
        VBox.setVgrow(_versionsScrollContainer, Priority.ALWAYS);

        this.getChildren().add(_versionsScrollContainer);


        _playButton = new Button(LangLoader.Query("dev.me.DZMC.launch_game"));
        this.getChildren().add(_playButton);
        _playButton.getStyleClass().add("main-play-button");
        _playButton.setMaxWidth(Double.MAX_VALUE);
        _playButton.setPrefHeight(50);






//      Connections

        _playButton.setOnAction(event -> {
            if (_selectedVersionButton == null) {
                System.out.println("Cannot start: No version Selected");
                return;
            }
            CommandBuilder.buildCommand(_selectedVersionButton.version);
            CommandBuilder.Run(null);
        });


        HBox.setMargin(this, new Insets(10, 5, 10, 10));
    }






    private void addVersions(VBox _container) {
        HashMap<String, Versions.Version> all_versions = Versions.enumerateVersions();

        all_versions.forEach((verName, _version) -> {
            _container.getChildren().add(createVersionBox(verName, _version));
        });
    }

    private Button createVersionBox(
            String name,
            Versions.Version ver
    ) {
        versionButton _newButton = new versionButton();
        _newButton.version = ver;
        _newButton.setText(name);

        _newButton.setOnAction(e -> {
            _newButton.setSelected();
            _selectedVersionButton = _newButton;
        });

        _newButton.setMaxWidth(Double.MAX_VALUE);

        return _newButton;
    }

    private static class versionButton extends Button {
        public Versions.Version version;
        public static final String selectedClass = "main-version-button-selected";
        public static final String unselectedClass = "main-version-button-unselected";

        public void setSelected() {
            if (_selectedVersionButton == this) return;
            if (_selectedVersionButton != null) {
                _selectedVersionButton.getStyleClass().setAll(
                        "button",
                        unselectedClass
                );
            }
            this.getStyleClass().setAll(
                    "button",
                    selectedClass
            );
        }

        public versionButton() {
            super();
            this.getStyleClass().add(versionButton.unselectedClass);
        }
    }
}
