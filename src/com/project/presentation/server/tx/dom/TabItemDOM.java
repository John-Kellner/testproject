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
@Table(name = "TabItems")
public class TabItemDOM {

	/** The category id */
	@Id
	@GeneratedValue
	@Column (name = "PK_tabItem")
	private long tabItemId;
	private String ueberschrift;
	private String title;

	@Lob
	@Column(name = "content", length = 1000000000)
	private String content;

	private boolean anzeigen;
	private int position;

	/**unidirektional*/
	@OneToOne
	private ImageDOM images;

	/**bidirektional*/
	@ManyToOne
	@JoinColumn(name = "FK_einstellung", nullable = false)
	private EinstellungDOM einstellung;

	public long getTabItemId() {
		return tabItemId;
	}

	public void setTabItemId(long tabItemId) {
		this.tabItemId = tabItemId;
	}

	public String getUeberschrift() {
		return ueberschrift;
	}

	public void setUeberschrift(String name) {
		this.ueberschrift = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isAnzeigen() {
		return anzeigen;
	}

	public void setAnzeigen(boolean anzeigen) {
		this.anzeigen = anzeigen;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public ImageDOM getImage() {
		return images;
	}

	public void setImage(ImageDOM images) {
		this.images = images;
	}

	public EinstellungDOM getEinstellung() {
		return einstellung;
	}

	public void setEinstellung(EinstellungDOM einstellung) {
		this.einstellung = einstellung;
	}
}