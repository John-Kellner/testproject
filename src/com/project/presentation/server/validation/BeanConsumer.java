package com.project.presentation.server.validation;

import groovy.lang.Closure;

import javax.validation.constraints.NotNull;

/**
 * Created by john on 02.10.2016.
 */
public class BeanConsumer {
    private String value;

    public void load(@NotNull String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
