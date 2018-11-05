/*
 * Database scheme for categories 
 * Every topic needs a category (a.e. "FieldBuilder" is in the "Widget" category)
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dom;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Tabellen Mapping
 * The Class CategoryDOM represents the database scheme for categories
 */
@Entity
@Table(name = "Einstellung")
public class EinstellungDOM {
	/** The category id */
	@Id
	@GeneratedValue
	@Column //(name = "PK_einstellung_id")
	private long einstellungId;
	/** Unidirektional */
	@OneToOne(mappedBy = "einstellung" , cascade = CascadeType.ALL, orphanRemoval = true)
	private TemplateDOM template;

	/**bidirektional*/
	@OneToMany(mappedBy = "einstellung", cascade = CascadeType.ALL, fetch = FetchType.LAZY ,orphanRemoval = true)
	private List<TabItemDOM> tabItem;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)//wenn Fotostudio geloescht wird wird auch die Adresse geloescht
	@JoinColumn(name="FK_aktivierung")
	private List<AktivierungDOM> aktivierung;

	@Transient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
	@JoinColumn(name="FK_einstellung")
	private List<ImageDOM> image;

	/** Bidirektional */
	@OneToOne
	@JoinColumn(name = "user")//	-8.7.
	private UserDOM user;
	private String hexColor;

	@OneToMany(mappedBy = "einstellung", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
	private List<AnlageDOM> anlagen;

	public long getEinstellungId() {
		return einstellungId;
	}

	public void setEinstellungId(long einstellungId) {
		this.einstellungId = einstellungId;
	}

	public TemplateDOM getTemplate() {
		return template;
	}

	public void setTemplate(TemplateDOM template) {
		this.template = template;
	}

	public List<TabItemDOM> getTabItem() {
		return tabItem;
	}

	public void setTabItem(List<TabItemDOM> tabItem) {
		this.tabItem = tabItem;
	}

	public List<AktivierungDOM> getAktivierung() {
		return aktivierung;
	}

	public void setAktivierung(List<AktivierungDOM> aktivierung) {
		this.aktivierung = aktivierung;
	}

	public List<ImageDOM> getImage() {
		return image;
	}

	public void setImage(List<ImageDOM> image) {
		this.image = image;
	}

	public UserDOM getUser() {
		return user;
	}

	public void setUser(UserDOM user) {
		this.user = user;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public String getHexColor() {
		return hexColor;
	}

	public List<AnlageDOM> getAnlagen() {
		return anlagen;
	}

	public void setAnlagen(List<AnlageDOM> zeugnisse) {
		this.anlagen = zeugnisse;
	}
}