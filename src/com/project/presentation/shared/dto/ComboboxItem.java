package com.project.presentation.shared.dto;

/**
 * Created by john on 26.07.2015.
 */
public class ComboboxItem {
    private long id;
    private String value;

    public ComboboxItem(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
