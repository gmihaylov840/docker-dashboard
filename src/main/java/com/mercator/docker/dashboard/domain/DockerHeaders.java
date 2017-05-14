package com.mercator.docker.dashboard.domain;

/**
 * Created by Joro on 23.04.2017.
 */
public enum DockerHeaders {
    NONE(""),
    CONTAINER_ID("CONTAINER ID"),
    IMAGE("IMAGE"),
    COMMAND("COMMAND"),
    CREATED("CREATED"),
    STATUS("STATUS"),
    PORTS("PORTS"),
    NAMES("NAMES");

    private String headerName;

    DockerHeaders(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
