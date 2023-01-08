package bacit.web.bacit_model;

import java.sql.Date;

public class NyReservasjonModel {

    private int ansatt_id;
    private int enhet_id;
    private Date dato_laant;
    private int dager_reservert;

    public NyReservasjonModel(int a, int e, Date dl, int dr) {
        this.ansatt_id = a;
        this.enhet_id = e;
        this.dato_laant = dl;
        this.dager_reservert = dr;
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

    public Date getDato_Laant() {
        return dato_laant;
    }

    public void setDato_Laant(Date dato_laant) {
        this.dato_laant = dato_laant;
    }

    public int getDager_reservert() {
        return dager_reservert;
    }

    public void setDager_reservert(int dager_reservert) {
        this.dager_reservert = dager_reservert;
    }


}
