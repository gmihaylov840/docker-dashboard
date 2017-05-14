package com.mercator.docker.dashboard.giu.service;

import com.mercator.docker.dashboard.commandShell.CommandExecutor;
import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;
import java.util.logging.Logger;

import static com.mercator.docker.dashboard.common.CommonSettings.NEW_LINE_CHARACTER;

/**
 * Created by Joro on 29.04.2017.
 */
public class StopContainerService extends ContainerService {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final String containerId;

    public StopContainerService(String containerId, StackPane containerInfoStackPane, VBox buttonPane,
                                DockerDashboardPane dockerDashboardPane, Button sourceBtn) {
        super(containerInfoStackPane, buttonPane, dockerDashboardPane, sourceBtn);
        this.containerId = containerId;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                // some time consuming task here
                // use the input arguments and perform some action on it
                // then set the process1 result to a Boolean and return after the task is completed
                // also keep hold back any other process to from executing on UI

                getServiceAction().getConsumerAction().accept("");

                return null;
            }
        };
    }

    public ServiceAction getServiceAction() {
        Consumer consumer = (none) -> {
            updateCursorBeforeOperation();
            LOGGER.info(String.format("Stopping container [%s]", containerId));
            String removeContainerCommand = "docker rm -f ";
            String executionResult = new CommandExecutor().executeCommand(removeContainerCommand + containerId);
            logStatusMessage(executionResult);
            updateCursorAfterOperation();
        };
        return new ServiceAction(consumer, "");
    }

    private void logStatusMessage(String executionResult) {
        if (executionResult.equals(containerId + NEW_LINE_CHARACTER)) {
            LOGGER.info(String.format("Container [%s] stopped successfully", containerId));
        } else {
            LOGGER.severe(String.format("Couldn't stop container [%s]", containerId));
        }
    }

}
