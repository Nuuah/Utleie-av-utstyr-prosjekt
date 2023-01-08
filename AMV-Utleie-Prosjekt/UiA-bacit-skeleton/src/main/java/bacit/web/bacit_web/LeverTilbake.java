package bacit.web.bacit_web;

import bacit.web.bacit_DAO.LeverTilbakeDAO;
import bacit.web.bacit_model.LeverTilbakeModel;
import bacit.web.bacit_model.ReservasjonModel;
import bacit.web.bacit_model.UtstyrModel;
import bacit.web.bacit_utility.htmlUtility;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Logger;

@WebServlet(name = "LeverTilbake", value = "/LeverTilbake")
public class LeverTilbake extends HttpServlet {

    Logger logger = Logger.getLogger(FileUploadServlet.class.getName());
    LeverTilbakeDAO dao = new LeverTilbakeDAO();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        htmlUtility html = new htmlUtility();
        html.htmlStart(out, "Lever Tilbake");

        int reservasjon_id =  Integer.parseInt(request.getParameter("reservasjon_id"));
        ReservasjonModel reservasjonModel = new ReservasjonModel(reservasjon_id);

        try {
            writeProduktInfo(out, reservasjonModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            writeLeverTilbakeForm(out, reservasjonModel);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        html.htmlEnd(out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String Skademelding = request.getParameter("Skademelding");
            String Betalingsmetode = request.getParameter("Betalingsmetode");
            Boolean Betalt = Boolean.parseBoolean(request.getParameter("Betalt"));
            Integer Reservasjon_id = Integer.parseInt(request.getParameter("reservasjon_id"));
            int totalpris = Integer.parseInt(request.getParameter("totalpris"));
            java.sql.Date Dato_Levert = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            LeverTilbakeModel leverTilbakeModel = new LeverTilbakeModel(
                    Skademelding, Betalingsmetode, Betalt, Reservasjon_id, Dato_Levert, totalpris
            );

            dao.addLeverTilbake(leverTilbakeModel, out);
            response.sendRedirect("kvittering?reservasjon_id=" + Reservasjon_id);

        } catch (Exception ex) {
            out.println(ex);
        }

    }

    private void writeProduktInfo(PrintWriter out, ReservasjonModel reservasjonModel) throws Exception {
        String utstyrNavn = dao.hentProduktNavn(reservasjonModel, out);
        String dato_laant = dao.hentDato_Laant(reservasjonModel, out);
        int totalpris = dao.hentTotalPris(reservasjonModel, out);

        out.println("<p>Utstyr: " + utstyrNavn + "</p>");
        out.println("<p>Dato lånt: " + dato_laant + "</p>");
        out.println("<p>Totalpris:" + totalpris + "</p>");
    }


    private void writeLeverTilbakeForm (PrintWriter out,ReservasjonModel reservasjonModel) throws SQLException, ClassNotFoundException {
        int totalpris = dao.hentTotalPris(reservasjonModel, out);
        out.println("<form action=\"LeverTilbake\" method=\"POST\">\n");

        out.println("<div class=\"container\">\n");
        out.println("<div>");
        out.println("<label for=\"Skademelding\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Skademelding</b></label>");
        out.println("<input type=\"text\" placeholder=\"Skriv her om utstyr er blitt skadet\" name=\"Skademelding\" >\n");
        out.println("</div>");
        out.println("<div>");
        out.println("<label for=\"Betalingsmetode\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Vipps</b></label>");
        out.println("<input type=\"radio\" name =\"Betalingsmetode\" value = \"Vipps\">\n");
        out.println("</div>");

        out.println("<div>");
        out.println("<label for=\"Betalingsmetode\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Kontant</b></label>");
        out.println("<input type=\"radio\" name =\"Betalingsmetode\" value = \"Kontant\"required>\n");
        out.println("</div>");

        out.println("<div>");
        out.println("<label for=\"Betalt\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Jeg har betalt</b></label>");
        out.println("<input type=\"checkbox\" name=\"Betalt\"value = \"1\" required>");
        out.println("</div>");
        out.println("<br>");
        out.println("<br>");

        out.println("<input name='reservasjon_id' type='hidden' value='" + reservasjonModel.getReservasjon_id() + "'>");
        out.println("<input name='totalpris' type='hidden' value='" + totalpris + "'>");

        out.println("<br><button type=\"submit\" button style=\"font-size:30px;background-color:#ffb300;width:30%;border-color:#f2ad0c; color:#11165a\">Lever</button>\n");
        out.println("</div>\n");
        out.println("</form>\n");

    }

}


