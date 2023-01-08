package bacit.web.bacit_model;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtstyrModel {

    private int id;
    private int utstyrKategoriId;
    private int bildeId;
    private String utstyrNavn;
    private String beskrivelse;
    private int antall;
    private int prisForsteDag;
    private int pris;
    private boolean spesielleKrav;
    private boolean aktivType;
    private int maxLaanbareDager;


    public UtstyrModel(int utstyrTypeId, PrintWriter out) {

        try {
            Connection db = null;

            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            String query;
            query = "SELECT * FROM Utstyr_Type where Utstyr_Type_ID = " + utstyrTypeId;

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs1;
            rs1 = ps.executeQuery();


            if (rs1.next()) {
                this.id = rs1.getInt("Utstyr_Type_ID");
                this.utstyrKategoriId = rs1.getInt("Utstyr_Kategori_ID");
                this.bildeId = rs1.getInt("Bilde_ID");
                this.utstyrNavn = rs1.getString("Utstyr_Navn");
                this.beskrivelse = rs1.getString("Utstyr_Type_ID");
                this.antall = rs1.getInt("Antall");
                this.prisForsteDag = rs1.getInt("Pris_Forste_Dag");
                this.pris = rs1.getInt("Pris");
                this.spesielleKrav = rs1.getBoolean("Spesielle_Krav");
                this.aktivType = rs1.getBoolean("Aktiv_Type");
                this.maxLaanbareDager = rs1.getInt("Max_Laanbare_Dager");
            }
            db.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public UtstyrModel(int id, int utstyrKategoriId, int bildeId, String utstyrNavn, String beskrivelse,
                       int antall, int prisForsteDag, int pris, boolean spesielleKrav, boolean aktivType, int maxLaanbareDager) {
        this.id = id;
        this.utstyrKategoriId = utstyrKategoriId;
        this.bildeId = bildeId;
        this.utstyrNavn = utstyrNavn;
        this.beskrivelse = beskrivelse;
        this.antall = antall;
        this.prisForsteDag = prisForsteDag;
        this.pris = pris;
        this.spesielleKrav = spesielleKrav;
        this.aktivType = aktivType;
        this.maxLaanbareDager = maxLaanbareDager;
    }


    public UtstyrModel (String utstyrNavn, Integer pris, Integer bildeId, String beskrivelse, Boolean spesielleKrav, Integer maxLaanbareDager, Integer antall) {
        this.utstyrNavn = utstyrNavn;
        this.pris = pris;
        this.bildeId = bildeId;
        this.beskrivelse = beskrivelse;
        this.spesielleKrav = spesielleKrav;
        this.maxLaanbareDager = maxLaanbareDager;
        this.antall = antall;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtstyrKategoriId() {
        return utstyrKategoriId;
    }

    public void setUtstyrKategoriId(int utstyrKategoriId) {
        this.utstyrKategoriId = utstyrKategoriId;
    }

    public int getBildeId() {
        return bildeId;
    }

    public void setBilde(int bilde) {
        this.bildeId = bilde;
    }

    public String getUtstyrNavn() {
        return utstyrNavn;
    }

    public void setUtstyrNavn(String utstyrNavn) {
        this.utstyrNavn = utstyrNavn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

    public int getPrisForsteDag() { return prisForsteDag; }

    public void setPrisForsteDag(int prisForsteDag) { this.prisForsteDag = prisForsteDag; }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public boolean isSpesielleKrav() {
        return spesielleKrav;
    }

    public void setSpesielleKrav(boolean spesielleKrav) {
        this.spesielleKrav = spesielleKrav;
    }

    public boolean isAktivType() {
        return aktivType;
    }

    public void setAktivType(boolean aktivType) {
        this.aktivType = aktivType;
    }

    public int getMaxLaanbareDager() {
        return maxLaanbareDager;
    }

    public void setMaxLaanbareDager(int maxLaanbareDager) {
        this.maxLaanbareDager = maxLaanbareDager;
    }
}
