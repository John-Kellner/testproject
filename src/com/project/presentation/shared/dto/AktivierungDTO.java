package com.project.presentation.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dietmar on 02.07.2015.
 */
public class AktivierungDTO implements Serializable{
    private long aktivierungID;

    private Date DatumVon;
    private Date DatumBis;
    private long dauer;

    public Date getDatumVon() {
        return DatumVon;
    }

    public void setDatumVon(Date datumVon) {
        DatumVon = datumVon;
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(long dauer) {
        this.dauer = dauer;
    }

    public long getAktivierungsID() {
        return aktivierungID;
    }

    public long getAktivierungID() {
        return aktivierungID;
    }

    public void setAktivierungID(long aktivierungID) {
        this.aktivierungID = aktivierungID;
    }

    public Date getDatumBis() {
        return DatumBis;
    }

    public void setDatumBis(Date datumBis) {
        DatumBis = datumBis;
    }
}
