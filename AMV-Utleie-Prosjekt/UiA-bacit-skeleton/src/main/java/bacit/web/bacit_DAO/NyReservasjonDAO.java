package bacit.web.bacit_DAO;

import bacit.web.bacit_model.NyReservasjonModel;
import bacit.web.bacit_model.ReservasjonOversiktModel;
import bacit.web.bacit_model.UtstyrModel;
import bacit.web.bacit_web.DBUtils;

import java.io.PrintWriter;
import java.sql.*;

import java.sql.Date;
import java.time.*;
import java.util.ArrayList;

public class NyReservasjonDAO {

    public void addReservasjon(NyReservasjonModel reservasjonAdd, PrintWriter out) throws Exception{
        Connection db = DBUtils.getINSTANCE().getConnection(out);

        String queryAddProduct = "insert into Reservasjon  (Ansatt_ID, Utstyr_Enhet_ID, Dato_Laant, Betalingsmetode, Dager_Reservert) " +
                "values (?,?,?,'',?)";
        PreparedStatement statement = db.prepareStatement(queryAddProduct);
        statement.setInt(1, reservasjonAdd.getAnsatt_id());
        statement.setInt(2, reservasjonAdd.getEnhet_id());
        statement.setDate(3, reservasjonAdd.getDato_Laant());
        statement.setInt(4, reservasjonAdd.getDager_reservert());

        statement.executeUpdate();
        db.close();

    }

    public int getTotalEnheterAvType(int utstyr_type_id, PrintWriter out) throws Exception {
        try {
                Connection db = null;
            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            String query;
            query = "SELECT count(UE.Utstyr_Type_ID) FROM Utstyr_Enhet UE join Utstyr_Type UT on UT.Utstyr_Type_ID = UE.Utstyr_Type_ID " +
                    "where UE.Utstyr_Type_ID = ? and Aktiv_Enhet = 1;";

            assert db != null;
            ps = db.prepareStatement(query);
            ps.setInt(1, utstyr_type_id);

            ResultSet rs;
            rs = ps.executeQuery();

            int antallEnheter = 0;
            while (rs.next()) {
                antallEnheter = rs.getInt(1);
            }
            db.close();

            return antallEnheter;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int enhetAvailableToRent(int utstyr_type_id, Date start, Date end, PrintWriter out) throws Exception {
        try {
            Connection db = null;
            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            String query;
            query = "select UE.Utstyr_Enhet_ID from Utstyr_Enhet UE\n" +
                    "where Utstyr_Type_ID = ? and\n" +
                    "      1 > any\n" +
                    "(select count(Reservasjon.Utstyr_Enhet_ID) from Reservasjon\n" +
                    "where Dato_Levert is null and Reservasjon.Utstyr_Enhet_ID = UE.Utstyr_Enhet_ID\n" +
                    "  and (Dato_Laant BETWEEN ? and ?\n" +
                    "  or (Dato_Laant + interval (Dager_Reservert-1) day) BETWEEN ? and ?)\n" +
                    "    );";

            assert db != null;
            ps = db.prepareStatement(query);
            ps.setInt(1, utstyr_type_id);
            ps.setDate(2, start);
            ps.setDate(3, end);
            ps.setDate(4, start);
            ps.setDate(5, end);

            ResultSet rs;
            rs = ps.executeQuery();

            int enhet_id = -1;
            while (rs.next()) {
                enhet_id = rs.getInt(1);
            }
            db.close();

            return enhet_id;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ArrayList<UtstyrModel> hentUtstyrType(int id, PrintWriter out) {
        try {
            // Kobler til database.
            Connection db = null;
            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Gjøre query
            // ? er der variablene skal puttes inn.
            String queryAnsattLogin = "select * from Utstyr_Type where Utstyr_Type_ID = ?";

            assert db != null;
            PreparedStatement statement = db.prepareStatement(queryAnsattLogin);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            ArrayList<UtstyrModel> utstyret = new ArrayList<>();
            while (rs.next()) {
                UtstyrModel utstyr = new UtstyrModel(
                        rs.getInt("Utstyr_Type_ID"),
                        rs.getInt("Utstyr_Kategori_ID"),
                        rs.getInt("Bilde_ID"),
                        rs.getString("Utstyr_Navn"),
                        rs.getString("Beskrivelse"),
                        rs.getInt("Antall"),
                        rs.getInt("Pris_Forste_Dag"),
                        rs.getInt("Pris"),
                        rs.getBoolean("Spesielle_Krav"),
                        rs.getBoolean("Aktiv_Type"),
                        rs.getInt("Max_Laanbare_Dager")
                );
                utstyret.add(utstyr);
            }
            db.close();

            return utstyret;

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public ArrayList<ReservasjonOversiktModel> hentReservasjoner(int id, PrintWriter out) {
        try {
            // Kobler til database.
            Connection db = null;
            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Gjøre query
            // ? er der variablene skal puttes inn.
            String hentReservasjoner = "SELECT Reservasjon_ID, R.Ansatt_ID, A.Fornavn, A.Etternavn, R.Utstyr_Enhet_ID, Dato_Laant, Dato_Levert, Betalt,\n" +
                    "       Totalpris, Skademelding, Betalingsmetode, Dager_Reservert, A.Telefon from Reservasjon R\n" +
                    "    inner join Ansatt A on R.Ansatt_ID = A.Ansatt_ID\n" +
                    "   inner join Utstyr_Enhet UE on UE.Utstyr_Enhet_ID = R.Utstyr_Enhet_ID\n" +
                    "    inner join Utstyr_Type UT on UT.Utstyr_Type_ID = UE.Utstyr_Type_ID\n" +
                    "    where Dato_Levert is null and UT.Utstyr_Type_ID = ?\n" +
                    "order by Dato_Laant";

            assert db != null;
            PreparedStatement statement = db.prepareStatement(hentReservasjoner);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            ArrayList<ReservasjonOversiktModel> reservasjoner = new ArrayList<>();
            while (rs.next()) {
                ReservasjonOversiktModel reservasjon = new ReservasjonOversiktModel(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getBoolean(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getInt(12),
                        rs.getString(13)
                );
                reservasjoner.add(reservasjon);
            }
            db.close();

            return reservasjoner;

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}