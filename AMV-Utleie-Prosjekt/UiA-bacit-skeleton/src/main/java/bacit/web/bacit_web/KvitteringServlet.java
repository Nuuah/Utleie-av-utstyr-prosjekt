package bacit.web.bacit_web;

import bacit.web.bacit_DAO.KvitteringDAO;
import bacit.web.bacit_DAO.LeverTilbakeDAO;
import bacit.web.bacit_model.ReservasjonModel;
import bacit.web.bacit_utility.htmlUtility;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(name = "kvittering", value = "/kvittering")
public class KvitteringServlet extends HttpServlet {

    LeverTilbakeDAO dao = new LeverTilbakeDAO();
    KvitteringDAO kvitteringDAO = new KvitteringDAO();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        htmlUtility html = new htmlUtility();
        html.htmlStart(out, "Kvittering");


        int reservasjon_id = Integer.parseInt(request.getParameter("reservasjon_id"));
        ReservasjonModel reservasjonModel = new ReservasjonModel(reservasjon_id);

        try {
            String betalingsmetode = kvitteringDAO.hentBetalingsmetode(out, reservasjonModel );
            if (betalingsmetode.equals("Vipps")) {
                try {
                    writeKvitteringVipps(out, reservasjonModel);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
            else  {
                try {
                    writeKvitteringKontant(out, reservasjonModel);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }



        writeGetBackToFrontpage(out);

        html.htmlEnd(out);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("Frontpage");

    }
    private void writeKvitteringKontant (PrintWriter out, ReservasjonModel reservasjonModel) throws SQLException, ClassNotFoundException {
        int totalpris = dao.hentTotalPris(reservasjonModel, out);
        String skademelding = kvitteringDAO.hentSkademelding(out, reservasjonModel);
        if (skademelding.equals("")) {
            out.println("<p>Her er kvitteringen for din innlevering:</p> \n" +
                    "<p>Betal med kontant i kassen</p>\n" +
                    "<p>Totalpris du må betale blir: " + totalpris + "kr</p>");
        }
        else {

            out.println("<p>Her er kvitteringen for din innlevering:</p> \n" +
                    "<p>Betal med kontant i kassen</p>\n" +
                    "<p>Totalpris du må betale blir: " + totalpris + "kr</p>\n" +
                    "<p>Dette er din eventuelle skademelding: \n" + skademelding + "</p>");
        }
    }

    private void writeKvitteringVipps (PrintWriter out, ReservasjonModel reservasjonModel) throws SQLException, ClassNotFoundException {
        int totalpris = dao.hentTotalPris(reservasjonModel, out);
        String skademelding = kvitteringDAO.hentSkademelding(out, reservasjonModel);

        if (skademelding.equals("")) {
            out.println("<p>Her er kvitteringen for din innlevering:</p> \n" +
                    "<p>Vipps dette nummere: 46417396</p>\n" +
                    "<p>Totalpris du må betale blir: " + totalpris + "kr</p>");
        }
        else {

            out.println("<p>Her er kvitteringen for din innlevering:</p> \n" +
                    "<p>Vipps dette nummere: 46417396</p>\n" +
                    "<p>Totalpris du må betale blir: " + totalpris + "kr</p>\n" +
                    "<p>Dette er din eventuelle skademelding: \n" + skademelding + "</p>");
        }
    }

    private void writeGetBackToFrontpage (PrintWriter out){
        out.println("<form action=\"kvittering\" method=\"post\">\n");

        out.println("<div class=\"container\">\n");
        out.println("<div>");
        out.println("<br><button type=\"submit\" button style=\"font-size:30px;background-color:#ffb300;width:30%;border-color:#f2ad0c; color:#11165a\">Tilbake til hovudsiden</button>\n");
        out.println("</div>\n");
        out.println("</form>\n");
    }
}
