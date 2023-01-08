package bacit.web.bacit_web;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;

@WebServlet(name = "AdminServlet", value = "/Admin")
public class AdminServlet extends HttpServlet {
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
        out.println("<html><body>");
        out.println("<a href='Admin_Ansatt'><div> <i> </i> <p> Ansatt </p> </div></a>");

        out.println("<a href='Admin_Utstyr'><div> <i> </i> <p> Utstyr </p> </div></a>");

        out.println("<a href='Admin_Rapporter'><div> <i> </i> <p> Rapporter </p> </div></a>");
        out.println("</body></html>");

    }
    
}