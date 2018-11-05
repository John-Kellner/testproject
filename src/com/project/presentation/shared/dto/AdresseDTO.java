package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Created by Dietmar on 28.06.2015.
 */
public class AdresseDTO implements Serializable {
    private long adresseID;
    private String strasse;
    private String ort;
    private String plz;
    private String land;
    private String phone;

    public long getAdresseID() {
        return adresseID;
    }

    public void setAdresseID(long adresseID) {
        this.adresseID = adresseID;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getOrt() {
        return ort;
    }

    public String getPlz() {
        return plz;
    }

    public String getLand() {
        return land;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setPLZ(String plz) {
        this.plz = plz;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }
}
