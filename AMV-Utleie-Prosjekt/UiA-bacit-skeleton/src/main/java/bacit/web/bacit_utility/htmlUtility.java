package bacit.web.bacit_utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class htmlUtility {

    private boolean bootstrap = false;
    private boolean popup = false;

    public void htmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");

        if (bootstrap) {
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css\">");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js\"></script>");
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js\"></script>");
        }
        if (popup) {
            writePopupStyling(out);
        }
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body>");

    }

    public void htmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }

    public void setBootstrap(boolean activate) {
        this.bootstrap = activate;
    }

    public void setPopup(boolean activate) {
        this.popup = activate;
    }

    public void writePopupStyling (PrintWriter out){
        out.println(
                "<style>\n" +
                "{box-sizing: border-box;}\n" +
                "\n" +
                ".open-button {\n" +
                "  background-color: #555;\n" +
                "  color: white;\n" +
                "  padding: 16px 20px;\n" +
                "  border: none;\n" +
                "  cursor: pointer;\n" +
                "  opacity: 0.8;\n" +
                "  position: fixed;\n" +
                "  bottom: 23px;\n" +
                "  right: 28px;\n" +
                "  width: 280px;\n" +
                "}\n" +
                "\n" +
                ".form-popup {\n" +
                "  display: none;\n" +
                "  position: fixed;\n" +
                "  bottom: 0;\n" +
                "  right: 15px;\n" +
                "  border: 3px solid #f1f1f1;\n" +
                "  z-index: 9;\n" +
                "}\n" +
                "\n" +
                ".form-container {\n" +
                "  max-width: 300px;\n" +
                "  padding: 10px;\n" +
                "  background-color: white;\n" +
                "}\n" +
                "\n" +
                ".form-container .btn {\n" +
                "  background-color: #04AA6D;\n" +
                "  color: white;\n" +
                "  padding: 16px 20px;\n" +
                "  border: none;\n" +
                "  cursor: pointer;\n" +
                "  width: 100%;\n" +
                "  margin-bottom:10px;\n" +
                "  opacity: 0.8;\n" +
                "}\n" +
                "\n" +
                ".form-container .cancel {\n" +
                "  background-color: red;\n" +
                "}\n" +
                "\n" +
                ".form-container .btn:hover, .open-button:hover {\n" +
                "  opacity: 1;\n" +
                "}\n" +
                "</style");

    }

}

