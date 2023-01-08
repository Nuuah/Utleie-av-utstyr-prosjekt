package bacit.web.bacit_model;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnhetModel {

    private int enhetId;
    private int utstyrTypeId;
    private boolean aktivEnhet;

    public EnhetModel (int enhetId, PrintWriter out){

        try {
            Connection db = null;

            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            String query;
            query = "SELECT * FROM Utstyr_Enhet where Utstyr_Type_ID = "+enhetId;

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs1;
            rs1 = ps.executeQuery();


            if (rs1.next()) {
                this.enhetId = rs1.getInt("Utstyr_Enhet_ID");
                this.utstyrTypeId = rs1.getInt("Utstyr_Type_ID");
                this.aktivEnhet = rs1.getBoolean("Aktiv_Enhet");
            }
            db.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public EnhetModel(int enhetId, int utstyrTypeId, boolean aktivEnhet) {
        this.enhetId = enhetId;
        this.utstyrTypeId = utstyrTypeId;
        this.aktivEnhet = aktivEnhet;
    }

    public int getEnhetId() {
        return enhetId;
    }

    public void setEnhetId(int enhetId) {
        this.enhetId = enhetId;
    }

    public int getUtstyrTypeId() {
        return utstyrTypeId;
    }

    public void setUtstyrTypeId(int utstyrTypeId) {
        this.utstyrTypeId = utstyrTypeId;
    }

    public boolean isAktivEnhet() {
        return aktivEnhet;
    }

    public void setAktivEnhet(boolean aktivEnhet) {
        this.aktivEnhet = aktivEnhet;
    }

}
