package com.mercator.docker.dashboard.giu.service;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Joro on 07.05.2017.
 */
public abstract class ContainerAllService extends Service<Void> {

    protected final DockerDashboardPane dockerDashboardPane;
    protected final VBox buttonPane;

    public ContainerAllService(DockerDashboardPane dockerDashboardPane, VBox buttonPane) {
        this.dockerDashboardPane = dockerDashboardPane;
        this.buttonPane = buttonPane;
    }

    protected synchronized void updateCursorBeforeAllOperation() {
        dockerDashboardPane.getScene().setCursor(Cursor.WAIT);
        buttonPane.setDisable(true);
    }

    protected synchronized void updateCursorAfterAllOperation() {
        dockerDashboardPane.getScene().setCursor(Cursor.DEFAULT);
        buttonPane.setDisable(false);
    }

    protected void refreshDashboard() {
        Platform.runLater(() -> {
            try {
                dockerDashboardPane.refreshDashboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void stopAllServices(List<ServiceAction> serviceActionList) throws InterruptedException {
        serviceActionList.parallelStream().forEach(c -> c.getConsumerAction().accept(""));
    }
    protected void startAllServices(List<ServiceAction> serviceActionList) throws InterruptedException {
        serviceActionList.stream().filter(c ->
                c.getImageName().equals("iorder-boot") || c.getImageName().equals("sso-server")
        ).forEach(c -> c.getConsumerAction().accept(""));
    }
}
