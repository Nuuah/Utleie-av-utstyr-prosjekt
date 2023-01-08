package bacit.web.bacit_web;

import bacit.web.bacit_DAO.AnsattDAO;
import bacit.web.bacit_DAO.FrontpageDAO;
import bacit.web.bacit_model.AnsattModel;
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

@WebServlet(name = "AdminAnsatt", value = "/Admin_Ansatt")
public class AdminAnsatt extends HttpServlet {


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

        // Hente data fra databasen.

        AnsattDAO DAOlist = new AnsattDAO();

        ArrayList<AnsattModel> ansatte = new ArrayList<>();


        out.println("<html><body>");
        out.println("<p> AMV Ansatte </p>");
        out.println("</body></html>");

        writeStartHtml(out, "AdminAnsatt");


        out.println("<form action='Admin_Ansatt' method='POST'>");
        out.println("  <label for='Ansatt_Navn'>Finn ansatt:</label>");
        out.println("  <input type='text' placeholder=\"Skriv inn fornavn\" name='Fornavn'/>");
        out.println("  <input type='text' placeholder=\"Skriv inn etternavn\" name='Etternavn'/>");
        out.println("  <input type='submit' value='Søk' />");
        out.println("</form>");



        out.println("<form action='AdminNyAnsatt'><input type='submit' value='Registrer ny ansatt' /></form>");

        try {
            ansatte = DAOlist.listAnsattDAO(out);
        } catch (Exception e) {
            e.printStackTrace();
        }


        assert ansatte != null;

        out.println("<table>\n" +
                "  <tr>\n" +
                "    <th>Fornavn</th>\n" +
                "    <th>Etternavn</th>\n" +
                "    <th>Telefonnummer</th>\n" +
                "  </tr>");

        ListIterator<AnsattModel> iteratoren = ansatte.listIterator();
        while (iteratoren.hasNext()) {
            AnsattModel model = iteratoren.next();
            try {
                writeAnsattTypeHtml(out, model);
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
        AnsattDAO DAOget = new AnsattDAO();

        String fornavn = request.getParameter("Fornavn");
        String etternavn = request.getParameter("Etternavn");


        try {
            AnsattModel model = DAOget.getAnsattDAO(fornavn, etternavn, out);

            writeStartHtml(out, "AdminAnsatt");

            out.println("<form action='Admin_Ansatt' method='POST'>");
            out.println("  <label for='Ansatt_Navn'>Finn ansatt:</label>");
            out.println("  <input type='text' placeholder=\"Skriv inn fornavn\" name='Fornavn'/>");
            out.println("  <input type='text' placeholder=\"Skriv inn etternavn\" name='Etternavn'/>");
            out.println("  <input type='submit' value='Søk' />");
            out.println("</form>");
            out.println("</body></html>");



            writeAnsattTypeHtml(out, model);

            writeHtmlEnd(out);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    private void writeAnsattTypeHtml(PrintWriter out, AnsattModel model) throws SQLException, IOException {

        int ansattId = model.getId();

        String fornavn = model.getFornavn();
        String etternavn = model.getEtternavn();
        String telefon = model.getTelefon();



        out.println("<tr><td>" + fornavn + "</td>\n" +
                "    <td>" + etternavn + "</td>\n" +
                "    <td>" + telefon + "</td>\n");
        out.println("<td><a href=\"AdminAnsattEndring?id=" + ansattId + "\">Endre</a></td></tr>\n");
    }
}