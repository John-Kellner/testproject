package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Daten Transfer Objekt kommend vom Frontend
 * Created by john on 22.06.2015.
 */
public class ImageDTO implements Serializable{
    private Long ImagesID;

    private String name;    // Bezeichner
    private String image;   // eigentliches Bild
    private EinstellungDTO einstellung;

    public Long getImagesID() {
        return ImagesID;
    }


    public void setImagesID(long imagesID) {
        ImagesID = imagesID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEinstellung(EinstellungDTO einstellung) {
        this.einstellung = einstellung;
    }

    public EinstellungDTO getEinstellung() {
        return einstellung;
    }
}
