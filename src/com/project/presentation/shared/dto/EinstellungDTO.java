package com.project.presentation.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Daten Transfer Objekt kommend vom Frontend
 * Created by john on 22.06.2015.
 */
public class EinstellungDTO implements Serializable{
    private long einstellungId;
    private List<AktivierungDTO> aktivierung;
    private String hexColor = "#404148";
    private List<TabItemDTO> tabItem;
    private TemplateDTO template;
    private ImageDTO image;
    private UserDTO user;

    public long getEinstellungId() {
        return einstellungId;
    }

    public void setEinstellungId(long einstellungId) {
        this.einstellungId = einstellungId;
    }

    public TemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDTO template) {
        this.template = template;
    }

    public List<TabItemDTO> getTabItem() {
        return tabItem;
    }

    public void setTabItem(List<TabItemDTO> tabItem) {
        this.tabItem = tabItem;
    }

    public List<AktivierungDTO> getAktivierung() {
        return aktivierung;
    }

    public void setAktivierung(List<AktivierungDTO> aktivierung) {
        this.aktivierung = aktivierung;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
}