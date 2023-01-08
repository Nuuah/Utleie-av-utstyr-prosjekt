package bacit.web.bacit_model;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class BildeModel {

    private int bildeId;
    private Blob bildeBlob;
    private String beskrivelse;

    public BildeModel (int bildeId, PrintWriter out){

        try {
            Connection db = null;

            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            String query;
            query = "SELECT * FROM Bilde where Bilde_ID = "+bildeId;

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs1;
            rs1 = ps.executeQuery();


            if (rs1.next()) {
                this.bildeId = rs1.getInt("Bilde_ID");
                this.bildeBlob = rs1.getBlob("Bilde_Blob");
                this.beskrivelse = rs1.getString("Beskrivelse");
            }
            db.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public BildeModel(int bildeId, Blob bildeBlob, String beskrivelse){
        this.bildeId = bildeId;
        this.bildeBlob = bildeBlob;
        this.beskrivelse = beskrivelse;

    }

    public int getBildeId() {
        return bildeId;
    }

    public void setBildeId(int bildeId) {
        this.bildeId = bildeId;
    }

    public Blob getBildeBlob() {
        return bildeBlob;
    }

    public void setBildeBlob(Blob bildeBlob) {
        this.bildeBlob = bildeBlob;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    // Trengs denne metoden?**************************************** (Daniel)
    public BildeModel getBilderByID (int bildeId, PrintWriter out) {



        try {
            Connection db = null;

            try {

                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            ArrayList <BildeModel> bilder = new ArrayList<>();

            String query;
            query = "SELECT * FROM Bilde where Bilde_ID = "+bildeId;

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs1;
            rs1 = ps.executeQuery();

            BildeModel bilde = new BildeModel(rs1.getInt("Bilde_ID"), rs1.getBlob("Bilde_Blob"),
                                rs1.getString("Beskrivelse"));

            return bilde;

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
