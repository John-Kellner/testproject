package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Daten Transfer Objekt kommend vom Frontend
 * Created by john on 22.06.2015.
 */
public class UserDTO implements Serializable {
    private long userId;
    private EinstellungDTO einstellung;
    private FotostudioDTO fotostudio;
    private boolean is128BitLogin;
    private AdresseDTO adresse;
    private String qrcodeMD5;
    private String password;
    private String qrcode;
    private String email;
    private String name;
    private String md5;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public EinstellungDTO getEinstellung() {
        return einstellung;
    }

    public void setEinstellung(EinstellungDTO einstellung) {
        this.einstellung = einstellung;
    }

    public AdresseDTO getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public FotostudioDTO getFotostudio() {
        return fotostudio;
    }

    public void setFotostudio(FotostudioDTO fotostudio) {
        this.fotostudio = fotostudio;
    }

    public void addAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeMD5() {
        return qrcodeMD5;
    }

    public void setQrcodeMD5(String qrcodeMD5) {
        this.qrcodeMD5 = qrcodeMD5;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean is128BitLogin() {
        return is128BitLogin;
    }

    public void setIs128BitLogin(boolean is128BitLogin) {
        this.is128BitLogin = is128BitLogin;
    }

}

