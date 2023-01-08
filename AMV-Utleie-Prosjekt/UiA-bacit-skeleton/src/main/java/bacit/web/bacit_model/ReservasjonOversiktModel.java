package bacit.web.bacit_model;

import java.sql.Date;

public class ReservasjonOversiktModel {
    private int reservasjon_id;
    private int ansatt_id;
    private String fornavn;
    private String etternavn;
    private int enhet_id;
    private Date dato_laant;
    private Date dato_levert;
    private boolean betalt;
    private int totalpris;
    private String skademelding;
    private String betalingsmetode;
    private int dager_reservert;
    private String telefon;

    public ReservasjonOversiktModel(int r, int a, String f, String en, int e, Date d_laant, Date d_levert, boolean b,
                            int t, String s, String bm, int d_reservert, String tlf) {
        this.reservasjon_id = r;
        this.ansatt_id = a;
        this.fornavn = f;
        this.etternavn = en;
        this.enhet_id = e;
        this.dato_laant = d_laant;
        this.dato_levert = d_levert;
        this.betalt = b;
        this.totalpris = t;
        this.skademelding = s;
        this.betalingsmetode = bm;
        this.dager_reservert = d_reservert;
        this.telefon = tlf;
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

    public String getFornavn() {
        return this.fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return this.etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
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

    public String getTelefon() { return this.telefon; }

    public void setTelefon(String tlf) { this.telefon = tlf; }
}