package bacit.web.bacit_web;

import bacit.web.bacit_DAO.ReservasjonDAO;
import bacit.web.bacit_model.AnsattModel;
import bacit.web.bacit_model.EnhetModel;
import bacit.web.bacit_model.ReservasjonModel;
import bacit.web.bacit_model.UtstyrModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

@WebServlet(name = "RapportBetaling", value = "/RapportBetaling")
public class RapportBetaling extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // hente data fra databasen
            ReservasjonDAO DAOlist = new ReservasjonDAO();

            ArrayList<ReservasjonModel> manglendeBetaling = new ArrayList<>();


            out.println("<html><body>");
            out.println("<p> Rapportsiden </p>");
            out.println("<p> Oversikt over manglende betalinger for lånt utstyr som er levert tilbake </p>");
            out.println("</body></html>");

            writeStartHtml(out, "RapportBetaling");


            out.println("<form action='Admin_Rapporter\'><input type='submit' value='Tilbake til rapporter' /></form>");


            try {
                manglendeBetaling = DAOlist.listManglendeBetalinger(out);
            } catch (Exception e) {
                e.printStackTrace();
            }


            assert manglendeBetaling != null;

            out.println("<table>\n" +
                    "  <tr>\n" +
                    "    <th>Fornavn</th>\n" +
                    "    <th>Etternavn</th>\n" +
                    "    <th>Telefonnummer</th>\n" +
                    "    <th>Fagforening</th>\n" +
                    "    <th>Utstyr</th>\n" +
                    "    <th>Dato lånt</th>\n" +
                    "    <th>Dato levert tilbake</th>\n" +
                    "    <th>Pris per dag</th>\n" +
                    "    <th>Totalpris</th>\n" +
                    "    <th>Betalt</th>\n" +
                    "    <th>Valgt betalingsmetode</th>\n" +
                    "  </tr>");

            ListIterator<ReservasjonModel> iteratoren = manglendeBetaling.listIterator();

            while (iteratoren.hasNext()) {
                ReservasjonModel model = iteratoren.next();
                try {
                    writeBetalingerTypeHtml(out, model, new AnsattModel(model.getAnsatt_id(), out));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            out.println("</table>");
            out.println("</body></html>");

            writeHtmlEnd(out);

        }

        private void writeStartHtml(PrintWriter out, String tittel) {

            out.println("<html>");
            out.println("<head>");

            out.println("<style> table {font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
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
            out.println("<title>" + tittel + "</title>");
            out.println("</head>");
            out.println("<body>");
        }

        private void writeHtmlEnd(PrintWriter out) {
            out.println("</body>");
            out.println("</html>");

        }

        private void writeBetalingerTypeHtml(PrintWriter out, ReservasjonModel model, AnsattModel ansattModel) throws SQLException, IOException {

            int enhetId = model.getEnhet_id();
            EnhetModel enhetModel = new EnhetModel(enhetId, out);
            int utstyrTypeId = enhetModel.getUtstyrTypeId();
            UtstyrModel utstyrModel = new UtstyrModel(utstyrTypeId, out);

            String fornavn = ansattModel.getFornavn();
            String etternavn = ansattModel.getEtternavn();
            String telefon = ansattModel.getTelefon();
            Boolean fagforening = ansattModel.isFagforening();
            String utstyrNavn = utstyrModel.getUtstyrNavn();
            java.sql.Date datoLaant = model.getDato_laant();
            java.sql.Date datoLevert = model.getDato_levert();
            int pris = utstyrModel.getPris();
            int totalpris = model.getTotalpris();
            boolean betalt = model.getBetalt();
            String betalingsmetode = model.getBetalingsmetode();

            String fagforeningString;
            if (fagforening){
                fagforeningString="JA";
            }
            else {
                fagforeningString="NEI";
            }

            String betaltString;
            if (betalt){
                betaltString="JA";
            }
            else {
                betaltString="NEI";
            }

            out.println("<tr><td>" + fornavn + "</td>" +
                    "    <td>" + etternavn + "</td>\n" +
                    "    <td>" + telefon + "</td>\n" +
                    "    <td>" + fagforeningString + "</td>\n" +
                    "    <td>" + utstyrNavn + "</td>\n" +
                    "    <td>" + datoLaant + "</td>\n" +
                    "    <td>" + datoLevert + "</td>\n" +
                    "    <td>" + pris + "</td>\n" +
                    "    <td>" + totalpris + "</td>\n" +
                    "    <td>" + betaltString + "</td>\n" +
                    "    <td>" + betalingsmetode + "</td>\n</tr>");

    }
}