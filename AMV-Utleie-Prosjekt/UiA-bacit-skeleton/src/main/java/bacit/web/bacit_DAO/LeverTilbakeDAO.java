package bacit.web.bacit_DAO;

import bacit.web.bacit_model.LeverTilbakeModel;
import bacit.web.bacit_model.ReservasjonModel;
import bacit.web.bacit_web.DBUtils;
import bacit.web.bacit_model.UtstyrModel;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class LeverTilbakeDAO {

    public String hentProduktNavn (ReservasjonModel reservasjonModel, PrintWriter out) throws Exception {
        Connection db = DBUtils.getINSTANCE().getConnection(out);


        String queryShowProduct = "select Utstyr_Navn from Utstyr_Type join Utstyr_Enhet UE on Utstyr_Type.Utstyr_Type_ID = UE.Utstyr_Type_ID join Reservasjon R on UE.Utstyr_Enhet_ID = R.Utstyr_Enhet_ID where Reservasjon_ID = ?";
        PreparedStatement statement = db.prepareStatement(queryShowProduct);
        statement.setInt(1, reservasjonModel.getReservasjon_id());
        ResultSet rs = statement.executeQuery();

        String utstyr = "";
        while (rs.next()) {
            utstyr = rs.getString("Utstyr_Navn");
        }

        db.close();
        return utstyr;
    }

    public String hentDato_Laant(ReservasjonModel reservasjonModel, PrintWriter out) throws SQLException, ClassNotFoundException {
        Connection db = DBUtils.getINSTANCE().getConnection(out);

        String queryShowProduct = "select Dato_Laant from Reservasjon where Reservasjon_ID = ?";
        PreparedStatement statement = db.prepareStatement(queryShowProduct);
        statement.setInt(1, reservasjonModel.getReservasjon_id());
        ResultSet rs = statement.executeQuery();

        java.sql.Date dato_laant = null;
        while (rs.next()) {
            dato_laant = rs.getDate("Dato_Laant");
        }

        String dato = dato_laant.toString();

        db.close();
        return dato;
    }

    public int hentTotalPris(ReservasjonModel reservasjonModel, PrintWriter out) throws SQLException, ClassNotFoundException {
        Connection db = DBUtils.getINSTANCE().getConnection(out);

        String queryTotalPris = "select Dato_Laant from Reservasjon where Reservasjon_ID = ?";
        PreparedStatement statement = db.prepareStatement(queryTotalPris);
        statement.setInt(1, reservasjonModel.getReservasjon_id());
        ResultSet rs = statement.executeQuery();

        java.sql.Date dato_laant = null;
        while (rs.next()) {
            dato_laant = rs.getDate("Dato_Laant");

        }

        assert dato_laant != null;
        LocalDateTime date1 = dato_laant.toLocalDate().atStartOfDay();
        LocalDateTime date2 = LocalDateTime.now();
        int daysBetween = (int) Duration.between(date1, date2).toDays();

        String queryHentPris = "select Pris, Pris_Forste_Dag from Utstyr_Type UT\n" +
                "join Utstyr_Enhet UE on UT.Utstyr_Type_ID = UE.Utstyr_Type_ID\n" +
                "join Reservasjon R on UE.Utstyr_Enhet_ID = R.Utstyr_Enhet_ID\n" +
                "where Reservasjon_ID = ?";
        PreparedStatement statement2 = db.prepareStatement(queryHentPris);
        statement2.setInt(1,reservasjonModel.getReservasjon_id() );
        ResultSet rs2 = statement2.executeQuery();


        int pris = -1;
        int prisForste = -1;
        int totalpris = 0;
        while (rs2.next()) {
            pris = rs2.getInt("Pris");
            prisForste = rs2.getInt("Pris_Forste_Dag");
        }

        if (pris > -1){
            totalpris = totalpris + prisForste;
            totalpris = totalpris + pris * daysBetween;

        }
        else {
            out.println("Her var det en feil gitt");
        }

        db.close();

        return totalpris;


    }

    public void addLeverTilbake(LeverTilbakeModel leverTilbakeModel, PrintWriter out) throws Exception{
        Connection db = DBUtils.getINSTANCE().getConnection(out);


        String queryAddLevering = "update Reservasjon set Skademelding = ?, Betalingsmetode = ?, Betalt = ?, Dato_Levert = ?, Totalpris = ? " +
                "where Reservasjon_ID = ?";
        PreparedStatement statement = db.prepareStatement(queryAddLevering);
        statement.setString(1, leverTilbakeModel.getSkademelding());
        statement.setString(2, leverTilbakeModel.getBetalingsmetode());
        statement.setBoolean(3,leverTilbakeModel.getBetalt());
        statement.setDate(4, leverTilbakeModel.getDato_levert());
        statement.setInt(5, leverTilbakeModel.getTotalpris());
        statement.setInt(6, leverTilbakeModel.getReservasjon_id());

        statement.executeUpdate();
        db.close();

    }
}