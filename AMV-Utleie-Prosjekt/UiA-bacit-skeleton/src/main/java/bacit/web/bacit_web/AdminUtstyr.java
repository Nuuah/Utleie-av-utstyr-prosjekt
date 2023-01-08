package bacit.web.bacit_web;

import bacit.web.bacit_DAO.UtstyrDAO;
import bacit.web.bacit_model.BildeModel;
import bacit.web.bacit_model.KategoriModel;
import bacit.web.bacit_model.UtstyrModel;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ListIterator;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Admin_Utstyr", value = "/Admin_Utstyr")
public class AdminUtstyr extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String username = "finnes ikke";
        HttpSession session = request.getSession(false);
        if (session != null) {
            // a session exists
            username = (String) session.getAttribute("abc");
        }
        else {
            response.sendRedirect("login");
        }

        PrintWriter out = response.getWriter();

        UtstyrDAO DAOlist = new UtstyrDAO();


        writeStartHtml(out, "RegistrerNyttUtstyr");
        out.println("<html><body>");
        out.println("<p>Utstyr til utleie i AMV Velferden</p>");
        out.println("<a href='AdminNyttUtstyr'><div> <i> </i> <p> <button> Legg til utstyr </button> </p> </div></a>");
        out.println("</table>");
        out.println("</body></html>");


        ArrayList<UtstyrModel> utstyr = new ArrayList<>();

        try {
            utstyr = DAOlist.listutstyrDAO(out, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert utstyr != null;


        ListIterator<UtstyrModel> iteratoren = utstyr.listIterator();
        out.println("<table>\n" +
                "  <tr>\n" +
                "    <th>Bilde</th>\n" +
                "    <th>Navn</th>\n" +
                "    <th>Kategori</th>\n" +
                "    <th>Beskrivelse</th>\n" +
                "    <th>Endre</th>\n" +
                "  </tr>");
        while (iteratoren.hasNext()) {
            UtstyrModel utstyrType = iteratoren.next();
            try {
                writeUtstyrTypeHtml(out, utstyrType, new BildeModel(utstyrType.getBildeId(), out), new KategoriModel(utstyrType.getUtstyrKategoriId(), out));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


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
        out.println("<td><a href=\"AdminUtstyrEndring?id=" + utstyrId + "\">Endre</a></td></tr>\n");
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
}