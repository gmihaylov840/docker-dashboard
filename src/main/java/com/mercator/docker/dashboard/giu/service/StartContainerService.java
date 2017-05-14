package com.mercator.docker.dashboard.giu.service;

import com.mercator.docker.dashboard.commandShell.CommandExecutor;
import com.mercator.docker.dashboard.common.FolderUtilities;
import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.property.PropertiesReader;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static com.mercator.docker.dashboard.common.CommonSettings.NEW_LINE_CHARACTER;

/**
 * Created by Joro on 29.04.2017.
 */
public class StartContainerService extends ContainerService {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    private final String imageName;

    public StartContainerService(String imageName, StackPane containerInfoStackPane, VBox buttonPane,
                                 DockerDashboardPane dockerDashboardPane, Button sourceBtn ) {
        super(containerInfoStackPane, buttonPane, dockerDashboardPane, sourceBtn);
        this.imageName = imageName;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
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
            LOGGER.info(String.format("Starting image [%s]", imageName));

            String goToDockerComposeDirCommand = "cd " + getDockerComposeDir();
            String startDockerImageCommand = "docker-compose up -d " + imageName;
            String executionResult = new CommandExecutor().executeCommand(goToDockerComposeDirCommand, startDockerImageCommand);
            LOGGER.info(String.format("Container [%s] status %s", imageName, executionResult));
            updateCursorAfterOperation();
        };
        return new ServiceAction(consumer, imageName);
    }

}
