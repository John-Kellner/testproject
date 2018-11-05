/*
 * Database scheme for categories 
 * Every topic needs a category (a.e. "FieldBuilder" is in the "Widget" category)
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dom;

import com.project.presentation.shared.dto.UserDTO;

import javax.persistence.*;
import java.util.List;

/**
 * Tabellen Mapping
 * The Class CategoryDOM represents the database scheme for categories
 */
@Entity
@Table(/*schema = "showcase",*/ name = "User")
public class UserDOM {

	/** The category id */
	@Id
	@GeneratedValue
	@Column (name = "PK_user")
	private long userId;

	/** The category name */
	@Column(unique = true)
	private String name;
	private String password;
	private String email;

	@Lob
	@Column(name = "qrcode", length = 1000000000)
	private String qrcode;

	@Lob
	@Column(name = "qrcodemd5", length = 1000000000)
	private String qrCodeMD5;

	@Column(name = "md5")
	private String md5;

	@Column(name = "md5login", nullable = false)
	private boolean is128BitLogin;

	/** Bidirektional */
	@OneToOne (fetch = FetchType.LAZY)
	@org.hibernate.annotations.Cascade( {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "FK_einstellung")
	private EinstellungDOM einstellung;

	@OneToOne(mappedBy = "user" )
	private AdresseDOM adresse;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="User_Fotostudio" , joinColumns = {@JoinColumn(name = "user", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "fotostudio", nullable = false)})
	private List<FotostudioDOM> fotostudios;

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

	public EinstellungDOM getEinstellung() {
		return einstellung;
	}

	public void setEinstellung(EinstellungDOM einstellung) {
		this.einstellung = einstellung;
	}

	public AdresseDOM getAdresse() {
		return adresse;
	}

	public void setAdresse(AdresseDOM adresse) {
		this.adresse = adresse;
	}

	public List<FotostudioDOM> getFotostudios() {
		return fotostudios;
	}

	public void setFotostudios(List<FotostudioDOM> fotostudios) {
		this.fotostudios = fotostudios;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public boolean is128bitLogin() {
		return is128BitLogin;
	}

	public void setIs128BitLogin(boolean is128bitLogin) {
		this.is128BitLogin = is128bitLogin;
	}

	public void setQrCodeMD5(String qrCodeMD5) {
		this.qrCodeMD5 = qrCodeMD5;
	}

	public String getQrCodeMD5() {
		return qrCodeMD5;
	}

	public void fillDTO(UserDTO user) {
		setUserId(user.getUserId());
		setName(user.getName());
		setEmail(user.getEmail());
		setPassword(user.getPassword());
		setQrcode(user.getQrcode());
		setQrCodeMD5(user.getQrcodeMD5());
		setMd5(user.getMd5());
		setIs128BitLogin(user.is128BitLogin());
	}
}