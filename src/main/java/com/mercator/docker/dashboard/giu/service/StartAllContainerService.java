package com.mercator.docker.dashboard.giu.service;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.giu.controls.StartContainerButton;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mercator.docker.dashboard.giu.container.DockerDashboardPane.buttonMap;

/**
 * Created by Joro on 29.04.2017.
 */
public class StartAllContainerService extends ContainerAllService {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    public StartAllContainerService(VBox buttonPane, DockerDashboardPane dockerDashboardPane) {
        super(dockerDashboardPane, buttonPane);
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


//                String workspaceDir = new PropertiesReader().lookupProperty("workspace.dir");
//                String dockerComposeDir = workspaceDir + "/deployment/run";
//                FolderUtilities.checkFolderExist(dockerComposeDir);
//
//                CommandExecutor commandExecutor = new CommandExecutor();
//                String goToDockerComposeDirCommand = "cd " + dockerComposeDir;
//                String startDockerImageCommand = "docker-compose up -d ";
//                for (String imageName: imageNames) {
//                    commandExecutor.executeCommand(goToDockerComposeDirCommand, startDockerImageCommand + imageName);
//                }

                updateCursorBeforeAllOperation();
                LOGGER.info("Starting all containers");

                List<ServiceAction> serviceActionList = new ArrayList<>();
                for (Button button : buttonMap.keySet()) {
                    if (buttonMap.get(button).equals("start")) {
                        LOGGER.info("Starting button: " + button);
                        StartContainerService startContainerService = ((StartContainerButton) button).getStartContainerService();
                        serviceActionList.add(startContainerService.getServiceAction());
                    }
                }

                if (!serviceActionList.isEmpty()) {
                    startAllServices(serviceActionList);
                    refreshDashboard();
                }

                updateCursorAfterAllOperation();

                return null;
            }
        };
    }
}
