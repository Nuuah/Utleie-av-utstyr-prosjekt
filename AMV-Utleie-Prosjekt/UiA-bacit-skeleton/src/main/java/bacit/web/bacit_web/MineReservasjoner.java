package bacit.web.bacit_web;

import bacit.web.bacit_DAO.MineReservasjonerDAO;
import bacit.web.bacit_model.MineReservasjonerModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "minereservasjoner", value = "/MineReservasjoner")
public class MineReservasjoner extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session != null) {
            // a session exists
            ArrayList<MineReservasjonerModel> reservasjoner = listReservasjon(out, session);
            out.println("<h1 style=\"color: #11165a\"> Mine Reservasjoner </h1>");
            out.println("<br>");
            out.println("<table>\n" +
                    "  <tr>\n" +
                    "    <th>ReservasjonID</th>\n" +
                    "    <th>Total Pris</th>\n" +
                    "    <th>Betalt</th>\n" +
                    "    <th>Dato Lånt</th>\n" +
                    "    <th>Dato Levert</th>\n" +
                    "    <th>Utstyr Navn</th>\n" +
                    "  </tr>");

            for (MineReservasjonerModel reservasjon : reservasjoner) {

                out.println("  <tr>\n" +
                        "    <td>" + reservasjon.getReservasjon_id() + "</td>\n" +
                        "    <td>" + reservasjon.getTotalpris() + "</td>\n" +
                        "    <td>" + reservasjon.isBetalt() + "</td>\n" +
                        "    <td>" + reservasjon.getDato_laant() + "</td>\n" +
                        "    <td>" + reservasjon.getDato_levert() + "</td>\n" +
                        "    <td>" + reservasjon.getUtstyr_navn() + "</td>\n" +
                        "  </tr>");


            }

            out.println("</table>");

        } else {
            response.sendRedirect("login");
        }


        writeHtmlStart(out, "Mine Reservasjoner");

        writeKanseller(out);
        writeLeverTilbake(out);
        out.println("<form action='Frontpage\'><input type='submit' value='Tilbake til Hovedsiden' /></form>");
        writeHtmlEnd(out);


    }

    private void writeHtmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");

        out.println("<style> table {font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 70%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                ".table { display:table; }\n" +
                ".table-row { display:table-row; }\n" +
                ".table-cell { display:table-cell; }" +
                "</style>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
    }


    private void writeHtmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }

    private ArrayList<MineReservasjonerModel> listReservasjon(PrintWriter out, HttpSession session) {

        Connection db = null;
        PreparedStatement ps;
        ArrayList<MineReservasjonerModel> reservasjon = new ArrayList<>();
        String telefon = (String) session.getAttribute("Telefon");

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String query =
                    "SELECT UT.Utstyr_Navn, Reservasjon_ID, Dato_Laant, Dato_Levert, Totalpris, Betalt " +
                            "FROM Ansatt A " +
                            "INNER JOIN Reservasjon R on A.Ansatt_ID = R.Ansatt_ID " +
                            "INNER JOIN Utstyr_Enhet UE on R.Utstyr_Enhet_ID = UE.Utstyr_Enhet_ID " +
                            "INNER JOIN Utstyr_Type UT on UE.Utstyr_Type_ID = UT.Utstyr_Type_ID " +
                            "WHERE A.Telefon = ? ";

            ps = db.prepareStatement(query);
            ps.setString(1, telefon);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                MineReservasjonerModel ansattReservasjon = new MineReservasjonerModel(
                        rs.getInt("Reservasjon_ID"),
                        rs.getInt("Totalpris"),
                        rs.getBoolean("Betalt"),
                        rs.getDate("Dato_Laant"),
                        rs.getDate("Dato_Levert"),
                        rs.getString("Utstyr_Navn")

                );
                reservasjon.add(ansattReservasjon);
            }


            return reservasjon;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        MineReservasjonerDAO slettReservasjon = new MineReservasjonerDAO();

        String reservasjon_id = request.getParameter("Reservasjon_ID");
        try {
            slettReservasjon.kansellerReservasjon(reservasjon_id, out);

            writeHtmlStart(out, "Mine Reservasjoner");
            HttpSession session = request.getSession(false);

            if (session != null) {
                // a session exists
                ArrayList<MineReservasjonerModel> reservasjoner = listReservasjon(out, session);
                out.println("<h1 style=\"color: #11165a\"> Mine Reservasjoner </h1>");
                out.println("<br>");
                out.println("<table>" +
                        "  <tr>" +
                        "    <th>Reservasjon ID</th>" +
                        "    <th>Total Pris</th>" +
                        "    <th>Betalt</th>" +
                        "    <th>Dato Lånt</th>" +
                        "    <th>Dato Levert</th>" +
                        "    <th>Utstyr Navn</th>" +
                        "  </tr>");

                if (reservasjoner != null) {
                    for (MineReservasjonerModel reservasjon : reservasjoner) {

                        out.println("  <tr>\n" +
                                "    <td>" + reservasjon.getReservasjon_id() + "</td>\n" +
                                "    <td>" + reservasjon.getTotalpris() + "</td>\n" +
                                "    <td>" + reservasjon.isBetalt() + "</td>\n" +
                                "    <td>" + reservasjon.getDato_laant() + "</td>\n" +
                                "    <td>" + reservasjon.getDato_levert() + "</td>\n" +
                                "    <td>" + reservasjon.getUtstyr_navn() + "</td>\n" +
                                "  </tr>");


                    }
                }

                out.println("</table>");

                out.println("<p style=\"color:#00FF00\";>Reservasjon med reservasjon ID " + request.getParameter("Reservasjon_ID") + ", er nå kansellert!</p>");


            } else {
                response.sendRedirect("login");
            }
            writeKanseller(out);
            writeHtmlEnd(out);

        } catch (Exception g) {
            g.printStackTrace();
        }

    }
        public void writeKanseller(PrintWriter out){
            out.println("<form action='MineReservasjoner' method='POST'>");
            out.println("<label for='Reservasjon_ID'>Velg reservasjon å kansellere, ved å skrive \"Reservasjon ID\":</label>");
            out.println("<input type='text' name='Reservasjon_ID'/>");
            out.println("<button type=\"submit\" style=\"float:none; background-color:#FF0000;border-color:#800000; color:#FFFFFF\">Kanseller </button>");
            out.println("</form>");
        }
        public void writeLeverTilbake(PrintWriter out){
            out.println("<form action='LeverTilbake' method='GET'>");
            out.println("<label for='reservasjon_id'>Velg reservasjon å levere tilbake, ved å skrive \"Reservasjon ID\":</label>");
            out.println("<input type='text' name='reservasjon_id'/>");
            out.println("<button type=\"submit\" style=\"float:none; background-color:#ffb300;border-color:#f2ad0c; color:#11165a\">Lever tilbake </button>");
            out.println("</form>");
        }


}


