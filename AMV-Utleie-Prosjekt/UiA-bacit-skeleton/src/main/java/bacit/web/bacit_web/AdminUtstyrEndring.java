package bacit.web.bacit_web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminUtstyrEndring", value = "/AdminUtstyrEndring")
public class AdminUtstyrEndring extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<p> Denne nettsiden eksisterer ikke enda :-)</p>");
        out.println("</body></html>");
    }
}

