package bacit.web.bacit_web;


import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminANyAnsatt", value = "/AdminNyAnsatt")
public class AdminNyAnsatt extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<p> Denne nettsiden eksisterer ikke enda :-)</p>");
        out.println("</body></html>");
    }
}