package com.project.presentation.server.tx.dom;

import com.project.presentation.shared.dto.AdresseDTO;
import com.project.presentation.shared.dto.UserDTO;

import javax.persistence.*;

/**
 * Created by Dietmar on 27.06.2015.
 */
@Entity
@Table (name = "Adresse")
public class AdresseDOM {

    @Id
    @GeneratedValue
    @Column(name = "PK_Adresse")
    private long adresseID;

    private String strasse;
    private String plz;
    private String ort;
    private String land;

    @OneToOne
    @JoinColumn(name = "FK_user")
    private UserDOM user;

    @OneToOne
    @JoinColumn(name = "FK_fotostudio")
    private FotostudioDOM fotostudio;

    public UserDOM getUser() {
        return user;
    }

    public void setUser(UserDOM user) {
        this.user = user;
    }

    public long getAdresseID() {
        return adresseID;
    }

    public void setAdresseID(long adresseID) {
        this.adresseID = adresseID;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public FotostudioDOM getFotostudio() {
        return fotostudio;
    }

    public void setFotostudio(FotostudioDOM fotostudio) {
        this.fotostudio = fotostudio;
    }

    public static AdresseDOM fillDTO(AdresseDTO dto) {
        final AdresseDOM dom = new AdresseDOM();
        if (dto != null) {
            dom.setAdresseID(dto.getAdresseID());
            dom.setStrasse(dto.getStrasse());
            dom.setPlz(dto.getPlz());
            dom.setOrt(dto.getOrt());
            dom.setLand(dto.getLand());
        }
        return dom;
    }
}
