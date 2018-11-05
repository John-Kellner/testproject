/*
 * Database scheme for categories 
 * Every topic needs a category (a.e. "FieldBuilder" is in the "Widget" category)
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dom;

import javax.persistence.*;
import java.util.List;

/**
 * Tabellen Mapping
 * The Class CategoryDOM represents the database scheme for categories
 */
@Entity
@Table(name = "Fotostudio")
public class FotostudioDOM {

	/** The category id */
	@Id
	@GeneratedValue
	@Column (name = "PK_fotostudio")
	private long fotostudioId;
	@JoinColumn(name = "fotostudio")
//	private long userID;

	/** The category name */
	@Column(unique = true)
	private String name;
	private String password;
	private String email;
	private String telephone_number;
	private String fax;

	@Lob
	@Column(name = "logo", length = 1000000000)
	private String logo;
	private String page;
	private String oeffnungszeiten;

	//Fuer 1:n zu Adresses
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//wenn Fotostudio geloescht wird wird auch die Adresse geloescht
	@JoinColumn(name="FK_Adresse_von_Studio")
	private List<AdresseDOM> adresses;

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

	public List<AdresseDOM> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<AdresseDOM> adresse) {
		this.adresses = adresse;
	}

	@ManyToMany(mappedBy = "fotostudios")
//	@JoinColumn(name = "FK_User")
	private List<UserDOM> users;


	public List<UserDOM> getUsers() {
		return users;
	}

	public void setUsers(List<UserDOM> users) {
		this.users = users;
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
