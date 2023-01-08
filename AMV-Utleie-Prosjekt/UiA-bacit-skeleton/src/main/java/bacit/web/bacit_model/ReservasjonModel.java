package bacit.web.bacit_model;

import java.sql.Date;

public class ReservasjonModel {

    private int reservasjon_id;
    private int ansatt_id;
    private int enhet_id;
    private java.sql.Date dato_laant;
    private java.sql.Date dato_levert;
    private boolean betalt;
    private int totalpris;
    private String skademelding;
    private String betalingsmetode;
    private int dager_reservert;

    public ReservasjonModel (int reservasjon_id){
        this.reservasjon_id = reservasjon_id;
    }
    public ReservasjonModel(int reservasjon_id, int ansatt_id, int enhet_id, java.sql.Date dato_laant, java.sql.Date dato_levert, boolean betalt, int totalpris, String skademelding, String betalingsmetode, int dager_reservert) {
        this.reservasjon_id = reservasjon_id;
        this.ansatt_id = ansatt_id;
        this.enhet_id = enhet_id;
        this.dato_laant = dato_laant;
        this.dato_levert = dato_levert;
        this.betalt = betalt;
        this.totalpris = totalpris;
        this.skademelding = skademelding;
        this.betalingsmetode = betalingsmetode;
        this.dager_reservert = dager_reservert;
    }


    public int getReservasjon_id() {
        return reservasjon_id;
    }

    public void setReservasjon_id(int reservasjon_id) {
        this.reservasjon_id = reservasjon_id;
    }

    public int getAnsatt_id() {
        return ansatt_id;
    }

    public void setAnsatt_id(int ansatt_id) {
        this.ansatt_id = ansatt_id;
    }

    public int getEnhet_id() {
        return enhet_id;
    }

    public void setEnhet_id(int enhet_id) {
        this.enhet_id = enhet_id;
    }

    public Date getDato_laant() {
        return dato_laant;
    }

    public void setDato_laant(Date dato_laant) {
        this.dato_laant = dato_laant;
    }

    public Date getDato_levert() {
        return dato_levert;
    }

    public void setDato_levert(Date dato_levert) {
        this.dato_levert = dato_levert;
    }

    public boolean getBetalt() {
        return betalt;
    }

    public void setBetalt(boolean betalt) {
        this.betalt = betalt;
    }

    public int getTotalpris() {
        return totalpris;
    }

    public void setTotalpris(int totalpris) {
        this.totalpris = totalpris;
    }

    public String getSkademelding() {
        return skademelding;
    }

    public void setSkademelding(String skademelding) {
        this.skademelding = skademelding;
    }

    public String getBetalingsmetode() {
        return betalingsmetode;
    }

    public void setBetalingsmetode(String betalingsmetode) {
        this.betalingsmetode = betalingsmetode;
    }

    public int getDager_reservert() {
        return dager_reservert;
    }

    public void setDager_reservert(int dager_reservert) {
        this.dager_reservert = dager_reservert;
    }

}

