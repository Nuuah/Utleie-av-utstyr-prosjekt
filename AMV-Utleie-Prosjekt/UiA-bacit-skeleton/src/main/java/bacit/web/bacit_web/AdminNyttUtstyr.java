package bacit.web.bacit_web;

import bacit.web.bacit_DAO.UtstyrDAO;
import bacit.web.bacit_model.UtstyrModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet(name = "AdminNyttUtstyr", value = "/AdminNyttUtstyr")
public class AdminNyttUtstyr extends HttpServlet {

    Logger logger = Logger.getLogger(FileUploadServlet.class.getName());


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        writeHtmlStart(out, "AdminNyttUtstyr");
        writeProduktAddForm(out);
        writeHtmlEnd(out);
    }

    private void writeHtmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    try {

        String Utstyr_navn = request.getParameter("Utstyr_navn");
        Integer Pris = Integer.parseInt(request.getParameter("Pris"));
        Integer Bilde_ID = Integer.parseInt(request.getParameter("Bilde_ID"));
        String Beskrivelse = request.getParameter("Beskrivelse");
        Boolean Spesielle_Krav = request.getParameter("Spesielle_Krav") != null;
        Integer Max_Laanbare_Dager = Integer.parseInt(request.getParameter("Max_Laanbare_Dager"));
        Integer Antall = Integer.parseInt(request.getParameter("Antall"));



        UtstyrModel utstyrModel = new UtstyrModel(
                Utstyr_navn, Pris, Bilde_ID, Beskrivelse, Spesielle_Krav, Max_Laanbare_Dager, Antall

        );

        UtstyrDAO dao = new UtstyrDAO();

        dao.addUtstyr(utstyrModel, out);

        logger.info("Received file with name: "+utstyrModel.getUtstyrNavn());


    }
    catch (Exception ex){
        logger.severe(ex.getMessage());

        out.println(ex);
    }

    }


    private void writeProduktAddForm (PrintWriter out){
        out.println("<form action=\"AdminNyttUtstyr\" method=\"post\">\n");

        out.println("<div class=\"container\">\n");
            out.println("<div>");
                out.println("<br><label for=\"Utstyr_navn\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Utstyr_navn</b></label>\n");
                out.println("<input type=\"text\" placeholder=\"Skriv inn navn på produkt\" name=\"Utstyr_navn\" required>\n");
                out.println("<br>");
            out.println("</div>");
            out.println("<div>");
                out.println("<label for=\"Pris\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Pris</b></label>");
                out.println("<input type=\"number\" placeholder=\"Skriv inn pris for produkt\" name=\"Pris\" required>\n");
            out.println("</div>");
            out.println("<div>");
                out.println("<label for=\"Bilde_ID\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Bilde_ID</b></label>");
                out.println("<input type=\"number\" placeholder=\"Skriv inn pris for produkt\" name=\"Bilde_ID\" required>\n");
            out.println("</div>");
            out.println("<div>");
                out.println("<label for=\"Beskrivelse\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Beskrivelse</b></label>");
                out.println("<input type=\"text\" placeholder=\"Skriv inn pris for produkt\" name=\"Beskrivelse\" required>\n");
            out.println("</div>");

            out.println("<div>");
                out.println("<label for=\"Spesielle_Krav\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Spesielle Krav</b></label>");
                out.println("<input type=\"checkbox\" name=\"Spesielle_Krav\"value = \"1\">");
            out.println("</div>");

            out.println("<div>");
                out.println("<label for=\"Max_Laanbare_Dager\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Max_Laanbare_Dager</b></label>");
                out.println("<input type=\"number\" placeholder=\"Skriv inn pris for produkt\" name=\"Max_Laanbare_Dager\" required>\n");
            out.println("</div>");
            out.println("<div>");
                out.println("<label for=\"Antall\"><b style=\"font-family:verdana;font-size:14px;color:#11165a;\">Antall</b></label>");
                out.println("<input type=\"number\" placeholder=\"Skriv inn pris for produkt\" name=\"Antall\" required>\n");
        out.println("</div>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br><button type=\"submit\" button style=\"font-size:30px;background-color:#ffb300;width:30%;border-color:#f2ad0c; color:#11165a\">Add produkt</button>\n");
        out.println("</div>\n");
        out.println("</form>\n");

    }

    private void writeHtmlEnd(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }
}