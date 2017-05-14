package com.mercator.docker.dashboard.giu.controls;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.giu.service.StopAllContainerService;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Joro on 01.05.2017.
 */
public class StopAllContainersButton extends Button {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    public StopAllContainersButton(VBox buttonPane, DockerDashboardPane dockerDashboardPane) {
        setText("Stop all");
        setOnAction(e -> {
            try {
                new StopAllContainerService(buttonPane, dockerDashboardPane).start();
            } catch (Exception exception) {
                LOGGER.severe(exception.getMessage());
            }
        });

        setOnMouseEntered(event -> setCursor(Cursor.HAND));
        setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
    }
}
