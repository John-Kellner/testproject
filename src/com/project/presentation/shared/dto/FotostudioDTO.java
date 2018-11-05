package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Daten Transfer Objekt kommend vom Frontend
 * Created by john on 22.06.2015.
 */
public class FotostudioDTO implements Serializable {
    private long fotostudioId;

    private String name;
    private String password;
    private String email;
    private String telephone_number;
    private String fax;
    private String logo;
    private String page;
    private String oeffnungszeiten;

    private UserDTO user;
    private AdresseDTO adresse;

    public long getFotostudioId() {
        return fotostudioId;
    }

    public void setFotostudioId(long fotostudioId) {
        this.fotostudioId = fotostudioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public AdresseDTO getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public void addAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getOeffnungszeiten() {
        return oeffnungszeiten;
    }

    public void setOeffnungszeiten(String oeffnungszeiten) {
        this.oeffnungszeiten = oeffnungszeiten;
    }
}
