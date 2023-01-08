package bacit.web.bacit_DAO;

import bacit.web.bacit_model.UtstyrModel;
import bacit.web.bacit_web.DBUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FrontpageDAO {

    public ArrayList<UtstyrModel> listUtstyr(PrintWriter out, HttpServletRequest request) throws Exception {
        try {
            Connection db = DBUtils.getINSTANCE().getConnection(out);
            PreparedStatement ps;

            ArrayList<UtstyrModel> utstyr = new ArrayList<>();

            String query;
            query = "SELECT * FROM Utstyr_Type inner join Utstyr_Kategori on Utstyr_Type.Utstyr_Kategori_ID = Utstyr_Kategori.Utstyr_Kategori_ID\n" +
                    "                          inner join Bilde on Utstyr_Type.Bilde_ID = Bilde.Bilde_ID\n" +
                    "                          order by Utstyr_Type.Utstyr_Kategori_ID;";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {

                UtstyrModel utstyrType = new UtstyrModel(rs.getInt("Utstyr_Type_ID"),
                        rs.getInt("Utstyr_Kategori_Id"),
                        rs.getInt("Bilde_ID"),
                        rs.getString("Utstyr_Navn"),
                        rs.getString("Beskrivelse"),
                        rs.getInt("Antall"),
                        rs.getInt("Pris_Forste_Dag"),
                        rs.getInt("Pris"),
                        rs.getBoolean("Spesielle_Krav"),
                        rs.getBoolean("Aktiv_Type"),
                        rs.getInt("Max_Laanbare_Dager"));
                utstyr.add(utstyrType);
            }
            db.close();
            return utstyr;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public UtstyrModel getUtstyr(String utstyrNavn, PrintWriter out) {
        try {
            Connection db = DBUtils.getINSTANCE().getConnection(out);

            String query3 = "select * from Utstyr_Type where Utstyr_Navn = ?";
            PreparedStatement statement = db.prepareStatement(query3);
            statement.setString(1, utstyrNavn);
            ResultSet rs = statement.executeQuery();
            UtstyrModel model = null;


            while (rs.next()) {
                model = new UtstyrModel(rs.getInt("Utstyr_Type_ID"),
                        rs.getInt("Utstyr_Kategori_ID"),
                        rs.getInt("Bilde_ID"),
                        rs.getString("Utstyr_Navn"),
                        rs.getString("Beskrivelse"),
                        rs.getInt("Antall"),
                        rs.getInt("Pris_Forste_Dag"),
                        rs.getInt("Pris"),
                        rs.getBoolean("Spesielle_Krav"),
                        rs.getBoolean("Aktiv_Type"),
                        rs.getInt("Max_Laanbare_Dager"));
            }
            return model;
        } catch (Exception g) {
            g.printStackTrace(out);
        }
        return null;
    }

}



