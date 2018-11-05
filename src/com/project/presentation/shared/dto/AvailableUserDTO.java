package com.project.presentation.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 28.08.2015.
 */
public class AvailableUserDTO implements Serializable{
    private String name;
    private Date von;
    private Date bis;
    private String dauer;
    private String userUrl;
    private String md5Url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getVon() {
        return von;
    }

    public void setVon(Date von) {
        this.von = von;
    }

    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
        this.bis = bis;
    }

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String url) {
        this.userUrl = url;
    }

    public String getMd5Url() {
        return md5Url;
    }

    public void setMd5Url(String md5Url) {
        this.md5Url = md5Url;
    }
}
