package bacit.web.bacit_model;

import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KategoriModel {

     private int utstyrKategoriId;
     private String kategoriNavn;

    public KategoriModel(int utstyrKategoriId, String kategoriNavn) {
        this.utstyrKategoriId = utstyrKategoriId;
        this.kategoriNavn = kategoriNavn;
    }

    public KategoriModel(int utstyrKategoriId, PrintWriter out) {
        try {
                Connection db = null;

                try {
                    db = DBUtils.getINSTANCE().getConnection(out);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                PreparedStatement ps;

                String query;
                query = "SELECT * FROM Utstyr_Kategori where Utstyr_Kategori_ID = "+utstyrKategoriId;

                assert db != null;
                ps = db.prepareStatement(query);
                ResultSet rs2;
                rs2 = ps.executeQuery();


                if (rs2.next()) {
                    this.utstyrKategoriId = rs2.getInt("Utstyr_Kategori_ID");
                    this.kategoriNavn = rs2.getString("Kategori_Navn");
                }
            db.close();
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }

    }

    public int getUtstyrKategoriId() {
        return utstyrKategoriId;
    }

    public void setUtstyrKategoriId(int utstyrKategoriId) {
        this.utstyrKategoriId = utstyrKategoriId;
    }

    public String getKategoriNavn() {
        return kategoriNavn;
    }

    public void setKategoriNavn(String kategoriNavn) {
        this.kategoriNavn = kategoriNavn;
    }

}
