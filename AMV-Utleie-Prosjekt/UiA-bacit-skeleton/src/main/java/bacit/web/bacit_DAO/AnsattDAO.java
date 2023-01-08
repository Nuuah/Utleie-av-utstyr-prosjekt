package bacit.web.bacit_DAO;

import bacit.web.bacit_model.AnsattModel;
import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnsattDAO {

    public ArrayList<AnsattModel> listAnsattDAO(PrintWriter out) throws Exception {
        try{
            Connection db = null;

            try{
                 db = DBUtils.getINSTANCE().getConnection(out);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            PreparedStatement ps;

            ArrayList<AnsattModel> ansatte = new ArrayList<>();
            String query;
            query = "SELECT * FROM Ansatt";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                AnsattModel model = new AnsattModel(rs.getInt("Ansatt_ID"),
                        rs.getString("Fornavn"),
                        rs.getString("Etternavn"),
                        rs.getString("Telefon"),
                        rs.getString("Epost"),
                        rs.getString("Passord"),
                        rs.getBoolean("Aktiv_ansatt"),
                        rs.getBoolean("Admin"),
                        rs.getBoolean("Fagforening"),
                        rs.getBoolean("Regler_for_utleie"));
                ansatte.add(model);

            }
            db.close();
            return ansatte;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public AnsattModel getAnsattDAO(String Fornavn, String Etternavn, PrintWriter out){
        try {
            Connection db = DBUtils.getINSTANCE().getConnection(out);

            String query = "select * from Ansatt where Fornavn = ? or Etternavn = ?";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setString(1, Fornavn);
            statement.setString(2, Etternavn);

            ResultSet rs = statement.executeQuery();
            AnsattModel model = null;


            while (rs.next()) {
                model = new AnsattModel(rs.getInt("Ansatt_ID"),
                        rs.getString("Fornavn"),
                        rs.getString("Etternavn"),
                        rs.getString("Telefon"),
                        rs.getString("Epost"),
                        rs.getString("Passord"),
                        rs.getBoolean("Aktiv_ansatt"),
                        rs.getBoolean("Admin"),
                        rs.getBoolean("Fagforening"),
                        rs.getBoolean("Regler_for_utleie"));
            }

            return model;

        } catch (Exception g){
            g.printStackTrace(out);
        }
        return  null;
    }

}








