package bacit.web.bacit_DAO;

import bacit.web.bacit_model.ReservasjonModel;
import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class ReservasjonDAO {

    public ArrayList<ReservasjonModel> listAktiveReservasjoner(PrintWriter out) throws Exception {
        try{
            Connection db = null;

            try{
                db = DBUtils.getINSTANCE().getConnection(out);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            PreparedStatement ps;

            ArrayList<ReservasjonModel> laantUtstyr = new ArrayList<>();
            String query;
            query = "select Reservasjon_ID, Ansatt.Ansatt_ID, Reservasjon.Utstyr_Enhet_ID, Utstyr_Type.Utstyr_Type_ID, Utstyr_Navn, Fornavn, Etternavn, Telefon, Dato_Laant, Dager_Reservert\n" +
                    "                                              from Reservasjon\n" +
                    "                                                       join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID\n" +
                    "                                                       join Utstyr_Enhet on Reservasjon.Utstyr_Enhet_ID = Utstyr_Enhet.Utstyr_Enhet_ID\n" +
                    "                                                       join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID\n" +
                    "                                              where Reservasjon.Dato_Levert is null\n" +
                    "                                                and DATEDIFF(CURRENT_DATE, Dato_Laant) >= 0";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                ReservasjonModel model = new ReservasjonModel(rs.getInt("Reservasjon_ID"),
                        rs.getInt("Ansatt_ID"),
                        rs.getInt("Utstyr_Enhet_ID"),
                        rs.getDate("Dato_Laant"),
                        null,
                        false,
                        -1,
                        null,
                        null,
                        rs.getInt("Dager_Reservert"));
                laantUtstyr.add(model);

            }
            db.close();
            return laantUtstyr;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<ReservasjonModel> listManglendeBetalinger (PrintWriter out) throws Exception {
        try{
            Connection db = null;

            try{
                db = DBUtils.getINSTANCE().getConnection(out);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            PreparedStatement ps;

            ArrayList<ReservasjonModel> manglendeBetaling = new ArrayList<>();
            String query;
            query = "select Reservasjon_ID, Ansatt.Ansatt_ID, Ansatt.Fornavn, Ansatt.Etternavn, Ansatt.Telefon, Ansatt.Fagforening, Utstyr_Enhet.Utstyr_Enhet_ID, Utstyr_Type.Utstyr_Type_ID, Utstyr_Type.Utstyr_Navn, Utstyr_Type.Pris, Dato_Laant, Dato_Levert, Totalpris, Betalt, Betalingsmetode\n" +
                    "from Reservasjon inner join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID\n" +
                    "join Utstyr_Enhet on Reservasjon.Utstyr_Enhet_ID = Utstyr_Enhet.Utstyr_Enhet_ID\n" +
                    "join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID where Dato_Levert is not null and Betalt is false and Fagforening is false";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                ReservasjonModel reservasjonModel = new ReservasjonModel(rs.getInt("Reservasjon_ID"),
                        rs.getInt("Ansatt_ID"),
                        rs.getInt("Utstyr_Enhet_ID"),
                        rs.getDate("Dato_Laant"),
                        rs.getDate("Dato_Levert"),
                        rs.getBoolean("Betalt"),
                        rs.getInt("Totalpris"),
                        null,
                        rs.getString("Betalingsmetode"),
                        -1);
                manglendeBetaling.add(reservasjonModel);

            }
            db.close();
            return manglendeBetaling;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public ArrayList<ReservasjonModel> listTidligereReservasjoner(PrintWriter out) throws Exception {
        try{
            Connection db = null;

            try{
                db = DBUtils.getINSTANCE().getConnection(out);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            PreparedStatement ps;

            ArrayList<ReservasjonModel> tidligereReservasjoner = new ArrayList<>();
            String query;
            query = "select Reservasjon_ID, Ansatt.Ansatt_ID, Reservasjon.Utstyr_Enhet_ID, Utstyr_Type.Utstyr_Type_ID, Utstyr_Navn, Ansatt.Fornavn, Ansatt.Etternavn, Ansatt.Telefon, Betalt, Totalpris, Skademelding, Fagforening, Dato_Laant, Dato_Levert\n" +
                    " from Reservasjon inner join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID\n" +
                    "join Utstyr_Enhet on Reservasjon.Utstyr_Enhet_ID = Utstyr_Enhet.Utstyr_Enhet_ID\n" +
                    "join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID\n" +
                    "where Dato_Laant between adddate(current_date(),-90) and current_date order by Ansatt.Fornavn ASC";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                ReservasjonModel model = new ReservasjonModel(rs.getInt("Reservasjon_ID"),
                        rs.getInt("Ansatt_ID"),
                        rs.getInt("Utstyr_Enhet_ID"),
                        rs.getDate("Dato_Laant"),
                        rs.getDate("Dato_Levert"),
                        rs.getBoolean("Betalt"),
                        rs.getInt("Totalpris"),
                        rs.getString("Skademelding"),
                        null,
                        -1);
                        tidligereReservasjoner.add(model);

            }
            db.close();
            return tidligereReservasjoner;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<ReservasjonModel> listFramtidigeReservasjoner(PrintWriter out) throws Exception {
        try{
            Connection db = null;

            try{
                db = DBUtils.getINSTANCE().getConnection(out);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            PreparedStatement ps;

            ArrayList<ReservasjonModel> framtidigeReservasjoner = new ArrayList<>();
            String query;
            query = "select Reservasjon_ID, Ansatt.Ansatt_ID, Reservasjon.Utstyr_Enhet_ID, Utstyr_Type.Utstyr_Type_ID, Utstyr_Navn, Ansatt.Fornavn, Ansatt.Etternavn, Ansatt.Telefon, Dato_Laant, Dager_Reservert\n" +
                    " from Reservasjon inner join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID\n" +
                    "join Utstyr_Enhet on Reservasjon.Utstyr_Enhet_ID = Utstyr_Enhet.Utstyr_Enhet_ID\n" +
                    "join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID\n" +
                    "where DATEDIFF(CURRENT_DATE, Dato_Laant) < 0 and DATEDIFF(CURRENT_DATE, Dato_Laant) >= -180 order by Ansatt.Fornavn ASC";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                ReservasjonModel model = new ReservasjonModel(rs.getInt("Reservasjon_ID"),
                        rs.getInt("Ansatt_ID"),
                        rs.getInt("Utstyr_Enhet_ID"),
                        rs.getDate("Dato_Laant"),
                        null,
                        false,
                        -1,
                        null,
                        null,
                        rs.getInt("Dager_Reservert"));
                framtidigeReservasjoner.add(model);

            }
            db.close();
            return framtidigeReservasjoner;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<ReservasjonModel> listForPeriode (Date nyDatoFra, Date nyDatoTil, PrintWriter out) throws Exception {

        try{
            Connection db = null;

            try{
                db = DBUtils.getINSTANCE().getConnection(out);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ArrayList<ReservasjonModel> reservasjonerForPeriode = new ArrayList<>();
            String query;
            query = "select Reservasjon_ID, Ansatt.Ansatt_ID, Reservasjon.Utstyr_Enhet_ID, Utstyr_Type.Utstyr_Type_ID, Utstyr_Navn, Ansatt.Fornavn, Ansatt.Etternavn, Ansatt.Telefon, Betalt, Totalpris, Skademelding, Fagforening, Dato_Laant, Dato_Levert, Dager_Reservert\n" +
                    "from Reservasjon inner join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID\n" +
                    "join Utstyr_Enhet on Reservasjon.Utstyr_Enhet_ID = Utstyr_Enhet.Utstyr_Enhet_ID\n" +
                    "join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID\n" +
                    "where Dato_Laant BETWEEN ? and ? order by Ansatt.Fornavn ASC;";



            assert db != null;
            PreparedStatement ps = db.prepareStatement(query);

            ps.setDate(1, nyDatoFra);
            ps.setDate(2, nyDatoTil);

            ResultSet rs;
            rs = ps.executeQuery();


            while (rs.next()) {
                ReservasjonModel model = new ReservasjonModel(rs.getInt("Reservasjon_ID"),
                        rs.getInt("Ansatt_ID"),
                        rs.getInt("Utstyr_Enhet_ID"),
                        rs.getDate("Dato_Laant"),
                        rs.getDate("Dato_Levert"),
                        rs.getBoolean("Betalt"),
                        rs.getInt("Totalpris"),
                        rs.getString("Skademelding"),
                        null,
                        rs.getInt("Dager_Reservert"));
                reservasjonerForPeriode.add(model);

            }
            db.close();
            return reservasjonerForPeriode;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}







