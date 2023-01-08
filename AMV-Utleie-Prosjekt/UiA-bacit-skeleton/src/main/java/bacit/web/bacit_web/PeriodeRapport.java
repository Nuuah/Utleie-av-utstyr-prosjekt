package bacit.web.bacit_web;

import bacit.web.bacit_DAO.ReservasjonDAO;
import bacit.web.bacit_model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ListIterator;


@WebServlet(name = "PeriodeRapport", value = "/PeriodeRapport")
public class PeriodeRapport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // hente data fra databasen
        ReservasjonDAO DAOlist = new ReservasjonDAO();

        ArrayList<ReservasjonModel> reservasjonerForPeriode = new ArrayList<>();

        writeStartHtml(out, "PeriodeRapport");

        out.println("<form action='PeriodeRapport' method='POST'>");
        out.println("  <label for='Dato_Laant'>Dato fra:</label>");
        out.println("  <input type='date' placeholder=\"Skriv fra dato \" name='fraDato'/>");
        out.println("  <label for='Dato_Laant'>Dato til:</label>");
        out.println("  <input type='date' placeholder=\"Skriv til dato\" name='tilDato'/>");
        out.println("  <input type='submit' value='Søk' />");
        out.println("</form>");

        try {
            reservasjonerForPeriode = DAOlist.listTidligereReservasjoner(out);
        } catch (Exception e) {
            e.printStackTrace();
        }


        assert reservasjonerForPeriode != null;

        out.println("<table>\n" +
                "  <tr>\n" +
                "    <th>Utstyr</th>\n" +
                "    <th>Fornavn</th>\n" +
                "    <th>Etternavn</th>\n" +
                "    <th>Telefonnummer</th>\n" +
                "    <th>Dato lånt</th>\n" +
                "    <th>Dato levert tilbake</th>\n" +
                "    <th>Totalpris</th>\n" +
                "    <th>Betalt</th>\n" +
                "    <th>Fagforening</th>\n" +
                "  </tr>");

        ListIterator<ReservasjonModel> iteratoren = reservasjonerForPeriode.listIterator();

        while (iteratoren.hasNext()) {
            ReservasjonModel model = iteratoren.next();
            try {
                writeReservasjonTypeHtml(out, model, new AnsattModel(model.getAnsatt_id(), out));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        out.println("</table>");

        writeHtmlEnd(out);

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        ReservasjonDAO DAOlist = new ReservasjonDAO();

            Date nyDatoFra = null;
            Date nyDatoTil = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String fraDato = request.getParameter("fraDato");
        try {
            nyDatoFra = new java.sql.Date(format.parse(fraDato).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String tilDato = request.getParameter("tilDato");
        try {
            nyDatoTil = new java.sql.Date(format.parse(tilDato).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert nyDatoFra != null;
        assert nyDatoTil != null;

        try {
            ArrayList<ReservasjonModel> reservasjonerForPeriode = DAOlist.listForPeriode(nyDatoFra, nyDatoTil, out);


            writeStartHtml(out, "PeriodeRapport");

            out.println("<form action='PeriodeRapport' method='POST'>");
            out.println("  <label for='Dato_Laant'>Dato fra:</label>");
            out.println("  <input type='date' placeholder=\"Skriv fra dato \" name='fraDato'/>");
            out.println("  <label for='Dato_Laant'>Dato til:</label>");
            out.println("  <input type='date' placeholder=\"Skriv til dato\" name='tilDato'/>");
            out.println("  <input type='submit' value='Søk' />");
            out.println("</form>");


            ListIterator<ReservasjonModel> iteratoren = reservasjonerForPeriode.listIterator();

            out.println("<table>\n" +
                    "  <tr>\n" +
                    "    <th>Utstyr</th>\n" +
                    "    <th>Fornavn</th>\n" +
                    "    <th>Etternavn</th>\n" +
                    "    <th>Telefonnummer</th>\n" +
                    "    <th>Dato lånt</th>\n" +
                    "    <th>Dato levert tilbake</th>\n" +
                    "    <th>Totalpris</th>\n" +
                    "    <th>Betalt</th>\n" +
                    "    <th>Fagforening</th>\n" +
                    "  </tr>");

            while (iteratoren.hasNext()) {
                ReservasjonModel model = iteratoren.next();
                try {
                    writeReservasjonTypeHtml(out, model, new AnsattModel(model.getAnsatt_id(), out));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            out.println("</table>");

            writeHtmlEnd(out);

            } catch (Exception e) {
                e.printStackTrace();
            }

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
        Date datoLevert = model.getDato_levert();
        int totalpris = model.getTotalpris();
        boolean betalt = model.getBetalt();
        Boolean fagforening = ansattModel.isFagforening();

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


        out.println("<tr><td>" + utstyrNavn + "</td>" +
                "    <td>" + fornavn + "</td>\n" +
                "    <td>" + etternavn + "</td>\n" +
                "    <td>" + telefon + "</td>\n" +
                "    <td>" + datoLaant + "</td>\n" +
                "    <td>" + datoLevert + "</td>\n" +
                "    <td>" + totalpris + "</td>\n" +
                "    <td>" + betaltString + "</td>\n" +
                "    <td>" + fagforeningString + "</td>\n</tr>");

    }
}
