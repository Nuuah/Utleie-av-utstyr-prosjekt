package bacit.web.bacit_web;

import bacit.web.bacit_DAO.ReservasjonDAO;
import bacit.web.bacit_model.AnsattModel;
import bacit.web.bacit_model.EnhetModel;
import bacit.web.bacit_model.UtstyrModel;
import bacit.web.bacit_model.ReservasjonModel;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "RapportLaant_utstyr", value = "/RapportLaant_utstyr")
public class RapportLaant_utstyr extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Hente data fra databasen.

        ReservasjonDAO DAOlist = new ReservasjonDAO();

        ArrayList<ReservasjonModel> laantUtstyr = new ArrayList<>();


        out.println("<html><body>");
        out.println("<p> Rapportsiden </p>");
        out.println("<p> Oversikt over aktive reservasjoner og utgåtte reservasjoner hvor utstyr ikke er registrert levert tilbake </p>");
        out.println("</body></html>");

        writeStartHtml(out, "Laant_utstyr");


        out.println("<form action='Admin_Rapporter\'><input type='submit' value='Tilbake til rapporter' /></form>");


        try {
            laantUtstyr = DAOlist.listAktiveReservasjoner(out);
        } catch (Exception e) {
            e.printStackTrace();
        }


        assert laantUtstyr != null;

        out.println("<table>\n" +
                "  <tr>\n" +
                "    <th>Utstyr</th>\n" +
                "    <th>Fornavn</th>\n" +
                "    <th>Etternavn</th>\n" +
                "    <th>Telefonnummer</th>\n" +
                "    <th>Dato lånt</th>\n" +
                "    <th>Antall dager reservert</th>\n" +
                "  </tr>");

        ListIterator<ReservasjonModel> iteratoren = laantUtstyr.listIterator();

        while (iteratoren.hasNext()) {
            ReservasjonModel model = iteratoren.next();
            try {
                writeReservasjonTypeHtml(out, model, new AnsattModel(model.getAnsatt_id(), out));
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

    private void writeReservasjonTypeHtml(PrintWriter out, ReservasjonModel model, AnsattModel ansattModel) throws SQLException, IOException {

        int enhetId = model.getEnhet_id();
        EnhetModel enhetModel = new EnhetModel(enhetId, out);
        int utstyrTypeId = enhetModel.getUtstyrTypeId();
        UtstyrModel utstyrModel = new UtstyrModel(utstyrTypeId, out);

        String utstyrNavn = utstyrModel.getUtstyrNavn();
        String fornavn = ansattModel.getFornavn();
        String etternavn = ansattModel.getEtternavn();
        String telefon = ansattModel.getTelefon();
        Date datoLaant = model.getDato_laant();
        int dagerReservert = model.getDager_reservert();


        out.println("<tr><td>" + utstyrNavn + "</td>" +
                "    <td>" + fornavn + "</td>\n" +
                "    <td>" + etternavn + "</td>\n" +
                "     <td>" + telefon + "</td>\n" +
                "    <td>" + datoLaant + "</td>\n" +
                "    <td>" + dagerReservert + "</td>\n</tr>");

    }
}

