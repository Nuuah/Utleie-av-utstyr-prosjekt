package bacit.web.bacit_model;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnsattModel {

    private int id;
    private String fornavn;
    private String etternavn;
    private String telefon;
    private String epost;
    private String passord;
    private boolean aktiv_ansatt;
    private boolean admin;
    private boolean fagforening;
    private boolean reglerForUtleie;

    public AnsattModel (int ansattId, PrintWriter out){

        try {
            Connection db = null;

            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            String query;
            query = "SELECT * FROM Ansatt where Ansatt_ID = "+ansattId;

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs1;
            rs1 = ps.executeQuery();


            if (rs1.next()) {
                this.id = rs1.getInt("Ansatt_ID");
                this.fornavn = rs1.getString("Fornavn");
                this.etternavn = rs1.getString("Etternavn");
                this.telefon = rs1.getString("Telefon");
                this.epost = rs1.getString("Epost");
                this.passord = rs1.getString("Passord");
                this.aktiv_ansatt = rs1.getBoolean("Aktiv_ansatt");
                this.admin = rs1.getBoolean("Admin");
                this.fagforening = rs1.getBoolean("Fagforening");
                this.reglerForUtleie = rs1.getBoolean("Regler_for_utleie");
            }
            db.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public AnsattModel(int id, String fornavn, String etternavn, String telefon, String epost, String passord, boolean aktiv_ansatt, boolean admin, boolean fagforening, boolean reglerForUtleie) {

        this.id = id;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.telefon = telefon;
        this.epost = epost;
        this.passord = passord;
        this.aktiv_ansatt = aktiv_ansatt;
        this.admin = admin;
        this.fagforening = fagforening;
        this.reglerForUtleie = reglerForUtleie;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public boolean isAktiv_ansatt() {
        return aktiv_ansatt;
    }

    public void setAktiv_ansatt(boolean aktiv_ansatt) {
        this.aktiv_ansatt = aktiv_ansatt;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isFagforening() {
        return fagforening;
    }

    public void setFagforening(boolean fagforening) {
        this.fagforening = fagforening;
    }

    public boolean isReglerForUtleie() {
        return reglerForUtleie;
    }

    public void setReglerForUtleie(boolean reglerForUtleie) {
        this.reglerForUtleie = reglerForUtleie;
    }
}


