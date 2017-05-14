package com.mercator.docker.dashboard;

import com.mercator.docker.dashboard.giu.container.DockerDashboardPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DockerDashboardApplication extends Application {

    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Docker Containers Life-cycle Manager");
        DockerDashboardPane dockerDashboardPane = new DockerDashboardPane();
        scene = new Scene(dockerDashboardPane, 1200, 475);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
