package bacit.web.bacit_DAO;

import bacit.web.bacit_model.ReservasjonModel;
import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KvitteringDAO {

    public String hentBetalingsmetode (PrintWriter out, ReservasjonModel reservasjonModel) throws SQLException, ClassNotFoundException {
        Connection db = DBUtils.getINSTANCE().getConnection(out);

        String queryHentBetalingsmetode = "select Betalingsmetode from Reservasjon where Reservasjon_ID = ?";
        PreparedStatement statement = db.prepareStatement(queryHentBetalingsmetode);
        statement.setInt(1,reservasjonModel.getReservasjon_id());
        ResultSet rs = statement.executeQuery();

        String Betalingsmetode = "";
        while (rs.next()) {
            Betalingsmetode = rs.getString("Betalingsmetode");
        }
        db.close();
        return Betalingsmetode;
    }
    public String hentSkademelding (PrintWriter out, ReservasjonModel reservasjonModel) throws SQLException, ClassNotFoundException {
        Connection db = DBUtils.getINSTANCE().getConnection(out);

        String queryHentSkademelding = "select Skademelding from Reservasjon where Reservasjon_ID = ?";
        PreparedStatement statement = db.prepareStatement(queryHentSkademelding);
        statement.setInt(1,reservasjonModel.getReservasjon_id());
        ResultSet rs = statement.executeQuery();

        String Skademelding = "";
        while (rs.next()) {
            Skademelding = rs.getString("Skademelding");
        }
        db.close();
        return Skademelding;
    }
}