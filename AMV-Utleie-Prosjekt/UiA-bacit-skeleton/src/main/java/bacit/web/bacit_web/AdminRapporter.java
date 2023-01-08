package bacit.web.bacit_web;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Admin_Rapporter", value = "/Admin_Rapporter")
public class AdminRapporter extends HttpServlet {
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

        out.println("<form action='Frontpage\'><input type='submit' value='Tilbake til Hovedsiden' /></form>");

        out.println("<html><body>");
        out.println("<a href='RapportLaant_utstyr'><div> <i> </i> <p> Oversikt over utstyr utlånt per i dag </p> </div></a>");

        out.println("<a href='ReservasjonPerAnsatt'><div> <i> </i> <p> Oversikt over reservasjoner per ansatt siste tre måneder\n" +
                "\n </p> </div></a>");
        out.println("<a href='ReservasjonPerAnsatt'><div> <i> </i> <p> Oversikt over reservasjoner per ansatt for neste seks måneder\n" +
                "\n </p> </div></a>");
        out.println("<a href='RapportBetaling'><div> <i> </i> <p> Oversikt over betalinger som ikke er fullført </p> </div></a>");

        out.println("<a href='PeriodeRapport'><div> <i> </i> <p> Finn reservasjon i perioden </p> </div></a>");
        out.println("</body></html>");

    }
}
