package bacit.web.bacit_web;


import bacit.web.bacit_DAO.LoginDAO;
import bacit.web.bacit_model.LoginModel;

import java.net.URLEncoder;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.http.HttpSession;

import static bacit.web.bacit_utility.PasswordEncrypt.encrypt;

@WebServlet(name = "LoginSide", value = "/login")
public class LoginPage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String phoneNumber = request.getParameter("phoneNumber");
        String password = encrypt(request.getParameter("password"));

        LoginModel loginModel = new LoginModel(phoneNumber, password);
        LoginDAO loginDAO = new LoginDAO();
        try {
            if (loginDAO.checkLogin(loginModel, response, out)) {
                HttpSession session = request.getSession();
                session.setAttribute("Telefon", phoneNumber);

                response.sendRedirect("Frontpage");
            } else {
                response.sendRedirect("index.jsp?errorMessage=" + URLEncoder.encode("Telefonummer eller passord er feil. Prøv igjen", "UTF-8"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}













