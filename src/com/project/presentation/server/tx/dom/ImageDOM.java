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
@Table(/*schema = "showcase",*/ name = "Images")
public class ImageDOM {

	/** The category id */
	@Id
	@GeneratedValue
	@Column (name = "PK_images")
	private long imagesId;

	private String name;

	@Lob
	@Column(name = "image", length = 1000000000)
	private String image;

	@OneToOne
	@JoinColumn(name = "FK_tabItem")
	private TabItemDOM tabItem;

	@OneToOne
	@JoinColumn(name = "FK_einstellung")
	private EinstellungDOM einstellung;

	public TabItemDOM getTabItem() {
		return tabItem;
	}

	public void setTabItem(TabItemDOM tabItem) {
		this.tabItem = tabItem;
	}

	public EinstellungDOM getEinstellung() {
		return einstellung;
	}

	public void setEinstellung(EinstellungDOM einstellung) {
		this.einstellung = einstellung;
	}

	public long getImagesId() {
		return imagesId;
	}

	public void setImagesId(long imagesId) {
		this.imagesId = imagesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}