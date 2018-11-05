package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Daten Transfer Objekt kommend vom Frontend
 * Created by john on 22.06.2015.
 */
public class TabItemDTO implements Serializable , Comparable<TabItemDTO>{

    private long tabItemID; // Datenbank ID

    private String name = "";    // Navigations Name
    private String title = "";   // Titel des Contents
    private String content = ""; // Inhalt TextArea langer Text

    private boolean anzeigen; // Soll der Reiter im Frontend angezeigt werden ?

    private int position = 0;   // Position Reihenfolge der Ansicht
    private ImageDTO image;    // Hochgeladenes Bild
    private EinstellungDTO einstellung;

    public TabItemDTO() {

    }

    public TabItemDTO(long id, String name) {
        this.tabItemID = id;
        this.name = name;
    }

    public long getTabItemID() {
        return tabItemID;
    }

    public void setTabItemID(long tabItemID) {
        this.tabItemID = tabItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisible() {
        return anzeigen;
    }

    public void setAnzeigen(boolean anzeigen) {
        this.anzeigen = anzeigen;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public EinstellungDTO getEinstellung() {
        return einstellung;
    }

    public void setEinstellung(EinstellungDTO einstellung) {
        this.einstellung = einstellung;
    }

    @Override
    public int compareTo(TabItemDTO tabItem) {
        final int pos = tabItem.getPosition();
        final int current = this.getPosition();

        if (current > pos){
            return 1;
        }else if (current < pos){
            return -1;
        }else {
            return 0;
        }
    }
}