package bacit.web.bacit_model;
import java.util.Date;

public class MineReservasjonerModel {

    private int reservasjon_id;
    private int totalpris;
    private boolean betalt;
    private Date dato_laant;
    private Date dato_levert;
    private String utstyr_navn;


    public MineReservasjonerModel(int reservasjon_id, int totalpris, boolean betalt, Date dato_laant, Date dato_levert, String utstyr_navn) {
        this.reservasjon_id = reservasjon_id;
        this.totalpris = totalpris;
        this.betalt = betalt;
        this.dato_laant = dato_laant;
        this.dato_levert = dato_levert;
        this.utstyr_navn = utstyr_navn;
    }


    


    public int getReservasjon_id() {
        return reservasjon_id;
    }

    public void setReservasjon_id(int reservasjon_id) {
        this.reservasjon_id = reservasjon_id;
    }

    public int getTotalpris() {
        return totalpris;
    }

    public void setTotalpris(int totalpris) {
        this.totalpris = totalpris;
    }

    public boolean isBetalt() {
        return betalt;
    }

    public void setBetalt(boolean betalt) {
        this.betalt = betalt;
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

    public String getUtstyr_navn() {
        return utstyr_navn;
    }

    public void setUtstyr_navn(String utstyr_navn) {
        this.utstyr_navn = utstyr_navn;
    }

}






