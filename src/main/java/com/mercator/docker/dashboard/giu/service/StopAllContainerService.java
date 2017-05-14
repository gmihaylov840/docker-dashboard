package com.mercator.docker.dashboard.giu.service;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import com.mercator.docker.dashboard.giu.controls.StopContainerButton;
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
public class StopAllContainerService extends ContainerAllService {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    public StopAllContainerService(VBox buttonPane, DockerDashboardPane dockerDashboardPane) {
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

                updateCursorBeforeAllOperation();
                LOGGER.info("Stopping all containers");

                List<ServiceAction> serviceActionList = new ArrayList<>();
                for (Button button : buttonMap.keySet()) {
                    if (buttonMap.get(button).equals("stop")) {
                        LOGGER.info("Stopping button: " + button);
                        StopContainerService stopContainerService = ((StopContainerButton) button).getStopContainerService();
                        serviceActionList.add(stopContainerService.getServiceAction());
                    }
                }

                if (!serviceActionList.isEmpty()) {
                    stopAllServices(serviceActionList);
                    refreshDashboard();
                }

                updateCursorAfterAllOperation();

                return null;
            }
        };
    }

}
