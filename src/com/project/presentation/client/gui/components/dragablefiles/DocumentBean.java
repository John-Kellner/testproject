package com.project.presentation.client.gui.components.dragablefiles;

/**
 * Created by john on 16.05.2016.
 */
public class DocumentBean {
    private long id;
    private String filename;
    private long einstellungID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getEinstellungID() {
        return einstellungID;
    }

    public void setEinstellungID(long einstellungID) {
        this.einstellungID = einstellungID;
    }
}
