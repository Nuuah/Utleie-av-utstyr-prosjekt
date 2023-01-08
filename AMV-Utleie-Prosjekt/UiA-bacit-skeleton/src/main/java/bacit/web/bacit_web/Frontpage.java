package bacit.web.bacit_web;

import bacit.web.bacit_model.KategoriModel;
import bacit.web.bacit_model.UtstyrModel;
import bacit.web.bacit_model.BildeModel;
import bacit.web.bacit_DAO.FrontpageDAO;



import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ListIterator;


@WebServlet(name = "frontpage", value = "/Frontpage")
public class Frontpage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        // Hente data fra databasen.

        FrontpageDAO DAOlist = new FrontpageDAO();

        writeStartHtml(out, "Hovedside");

        ArrayList<UtstyrModel> utstyr = new ArrayList<>();

        try {
            utstyr = DAOlist.listUtstyr(out, request);
        } catch (Exception e) {
            e.printStackTrace();
        }



        MenyBar.printMenyBar(out);

        writeSearchBar(out);



        // Legge data inn i html.
        assert utstyr != null;


        ListIterator<UtstyrModel> iteratoren = utstyr.listIterator();
        out.println("<table>\n" +
                "  <tr>\n" +
                "    <th>Bilde</th>\n" +
                "    <th>Navn</th>\n" +
                "    <th>Kategori</th>\n" +
                "    <th>Beskrivelse</th>\n" +
                "    <th>Reservere</th>\n" +
                "  </tr>");
        while (iteratoren.hasNext()) {
            UtstyrModel utstyrType = iteratoren.next();
            try {
                writeUtstyrTypeHtml(out, utstyrType, new BildeModel(utstyrType.getBildeId(), out), new KategoriModel(utstyrType.getUtstyrKategoriId(), out));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        out.println("</table>");
        out.println("</body></html>");


        writeHtmlEnd(out);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        FrontpageDAO DAOget = new FrontpageDAO();

        String utstyrNavn = request.getParameter("Utstyr_Navn");

        try {
            UtstyrModel model = DAOget.getUtstyr(utstyrNavn, out);

            writeStartHtml(out, "Hovedside");

            writeSearchBar(out);

            writeUtstyrTypeHtml(out, model, new BildeModel(model.getBildeId(), out), new KategoriModel(model.getUtstyrKategoriId(), out));

            writeHtmlEnd(out);


                } catch (SQLException throwables) {
                      throwables.printStackTrace();
                }
    }

    private void writeStartHtml(PrintWriter out, String tittel) {
        out.println("<html>");
        out.println("<head>");

        out.println("<style>" +
                "body {margin: 0;}\n" +
                " table {font-family: arial, sans-serif;\n" +
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

    private void writeUtstyrTypeHtml(PrintWriter out, UtstyrModel utstyrType, BildeModel bildeModel, KategoriModel kategoriModel) throws SQLException {


        String bildeHtml;

        if (bildeModel.getBildeBlob() == null) {
            bildeHtml = "Ingen bilde";
        } else {
            Blob blob = bildeModel.getBildeBlob();
            int blobLength = (int) blob.length();


            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            String bilde = Base64.getEncoder().encodeToString(blobAsBytes);
            bildeHtml = "<img src=\"data:image/PNG;base64," + bilde + "\" width=\"50\" height=\"60\">";
        }


        String navn = utstyrType.getUtstyrNavn();


        String kategoriNavn = kategoriModel.getKategoriNavn();

        int utstyrId = utstyrType.getId();



        out.println("<tr><td>" + bildeHtml + "</td>\n" +
                "    <td>" + navn + "</td>\n" +
                "    <td>" + kategoriNavn + "</td>\n" +
                "    <td>" + utstyrType.getBeskrivelse() + "</td>\n");
        out.println("<td><a href=\"reservasjonsside?id=" + utstyrId + "\">Reserver</a></td></tr>\n");
    }

    public void writeSearchBar (PrintWriter out){
        out.println("<form action='Frontpage' method='POST'>");
        out.println("  <label for='Utstyr_Navn'>Søk etter utstyr:</label>");
        out.println("  <input type='text' name='Utstyr_Navn'/>");
        out.println("  <input type='submit' value='Søk' />");
        out.println("</form>");
    }
}