package com.mercator.docker.dashboard.giu.service;

import java.util.function.Consumer;

/**
 * Created by Joro on 10.05.2017.
 */
public class ServiceAction  {

    private Consumer consumerAction;
    private String imageName;

    public ServiceAction(Consumer consumerAction, String imageName) {
        this.consumerAction = consumerAction;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public Consumer getConsumerAction() {
        return consumerAction;
    }
}
