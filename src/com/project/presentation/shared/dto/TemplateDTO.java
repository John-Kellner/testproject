package com.project.presentation.shared.dto;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Daten Transfer Objekt kommend vom Frontend
 * Created by john on 22.06.2015.
 */
public class TemplateDTO implements Serializable {
    private long templateId;
    private String name;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }
}
