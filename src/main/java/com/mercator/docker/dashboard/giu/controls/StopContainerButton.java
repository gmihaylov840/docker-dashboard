package com.mercator.docker.dashboard.giu.controls;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.giu.service.StopContainerService;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

/**
 * Created by Joro on 29.04.2017.
 */
public class StopContainerButton extends Button {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private StopContainerService stopContainerService;

    public StopContainerService getStopContainerService() {
        return stopContainerService;
    }

    public StopContainerButton(String containerId, StackPane containerInfoStackPane, VBox buttonPane,
                               DockerDashboardPane dockerDashboardPane) {
        setText("Stop");
        stopContainerService = new StopContainerService(containerId, containerInfoStackPane,
                buttonPane, dockerDashboardPane, this);
        setOnAction(e -> stopContainerService.start());

        setOnMouseEntered(event -> setCursor(Cursor.HAND));
        setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
    }
}
