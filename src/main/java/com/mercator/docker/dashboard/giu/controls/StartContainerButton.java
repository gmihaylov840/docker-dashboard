package com.mercator.docker.dashboard.giu.controls;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.giu.service.StartContainerService;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

/**
 * Created by Joro on 29.04.2017.
 */
public class StartContainerButton extends Button {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private StartContainerService startContainerService;

    public StartContainerService getStartContainerService() {
        return startContainerService;
    }

    public StartContainerButton(String imageName, StackPane containerInfoStackPane, VBox buttonPane,
                                DockerDashboardPane dockerDashboardPane) {
        setText("Start");
        startContainerService = new StartContainerService(imageName, containerInfoStackPane, buttonPane, dockerDashboardPane, this);
        setOnAction(e -> startContainerService.start());
        setOnMouseEntered(event -> setCursor(Cursor.HAND));
        setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
    }
}
