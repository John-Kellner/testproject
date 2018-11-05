/*
 * Database scheme for categories 
 * Every topic needs a category (a.e. "FieldBuilder" is in the "Widget" category)
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dom;

import javax.persistence.*;

/**
 * Tabellen Mapping
 * The Class CategoryDOM represents the database scheme for categories
 */
@Entity
@Table(name = "Template")
public class TemplateDOM {

	/** The category id */
	@Id
	@GeneratedValue
	@Column (name = "PK_template")
	private long templateId;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_einstellung")
	private EinstellungDOM einstellung;

	public EinstellungDOM getEinstellung() {
		return einstellung;
	}

	public void setEinstellung(EinstellungDOM einstellung) {
		this.einstellung = einstellung;
	}

	private String name;

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}