package com.project.presentation.client.gui.components.table.bean;

/**
 * Created by john on 02.07.2015.
 */
public class TableBean {
    private long id;
    private String text;
    private String image;

    public TableBean(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
