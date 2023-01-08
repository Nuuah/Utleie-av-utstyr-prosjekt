package bacit.web.bacit_model;


import java.sql.Date;

public class LeverTilbakeModel {
    private String skademelding;
    private String betalingsmetode;
    private Boolean betalt;
    private int reservasjon_id;
    private Date dato_levert;
    private int totalpris;

    public LeverTilbakeModel(String skademelding, String betalingsmetode, Boolean betalt, int reservasjon_id, java.sql.Date dato_levert, int totalpris){
        this.skademelding = skademelding;
        this.betalingsmetode = betalingsmetode;
        this.betalt = betalt;
        this.reservasjon_id = reservasjon_id;
        this.dato_levert = dato_levert;
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

    public Boolean getBetalt() {
        return betalt;
    }

    public void setBetalt(Boolean betalt) {
        this.betalt = betalt;
    }

    public int getReservasjon_id() {
        return reservasjon_id;
    }

    public void setReservasjon_id(int reservasjon_id) {
        this.reservasjon_id = reservasjon_id;
    }

    public Date getDato_levert() {
        return dato_levert;
    }

    public void setDato_levert(Date dato_levert) {
        this.dato_levert = dato_levert;
    }

    public int getTotalpris() {
        return totalpris;
    }

    public void setTotalpris(int totalpris) {
        this.totalpris = totalpris;
    }
}
