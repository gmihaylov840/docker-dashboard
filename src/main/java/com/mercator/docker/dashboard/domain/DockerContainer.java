package com.mercator.docker.dashboard.domain;

/**
 * Created by Joro on 23.04.2017.
 */
public class DockerContainer {
    private String containerId;
    private String image;
    private String command;
    private String created;
    private String status;
    private String ports;
    private String names;

    public DockerContainer(String containerId, String image, String command, String created, String status, String ports, String names) {
        this.containerId = containerId;
        this.image = image;
        this.command = command;
        this.created = created;
        this.status = status;
        this.ports = ports;
        this.names = names;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getImage() {
        return image;
    }

    public String getCommand() {
        return command;
    }

    public String getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public String getPorts() {
        return ports;
    }

    public String getNames() {
        return names;
    }
}
