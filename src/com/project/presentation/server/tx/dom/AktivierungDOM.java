/*
 * Database scheme for categories 
 * Every topic needs a category (a.e. "FieldBuilder" is in the "Widget" category)
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dom;

import javax.persistence.*;
import java.util.Date;

/**
 * Tabellen Mapping
 * The Class CategoryDOM represents the database scheme for categories
 */
@Entity
@Table(/*schema = "showcase",*/ name = "Aktivierung")
public class AktivierungDOM {

	/**
	 * The category id
	 */
	@Id
	@GeneratedValue
	@Column(name = "Aktivierung_Id")
	private long aktivierungID;

	/**
	 * The category name
	 */
//	@Column(unique = true)

	//private long einstellungID;
	private Date datumVon;
	private Date datumBis;

	private long dauer;

	public long getAktivierungID() {
		return aktivierungID;
	}

	public void setAktivierungID(long aktivierungID) {
		this.aktivierungID = aktivierungID;
	}

	public Date getDatumVon() {
		return datumVon;
	}

	public void setDatumVon(Date datumVon) {
		this.datumVon = datumVon;
	}

	public Date getDatumBis() {
		return datumBis;
	}

	public void setDatumBis(Date datumBis) {
		this.datumBis = datumBis;
	}

	public long getDauer() {
		return dauer;
	}

	public void setDauer(long dauer) {
		this.dauer = dauer;
	}
}

//aktivierungID



