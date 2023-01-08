
    package bacit.web.bacit_web;

import java.io.PrintWriter;

    public class MenyBar {
        static void printMenyBar(PrintWriter out) {
            out.println("<style>" +
                    "#MenyBar li { float: left; }\n" +
                    "#MenyBar { list-style-type: none; margin: 0; padding: 0; overflow: hidden; background-color: #ffb300; }\n" +
                    "#MenyBar li a { display: block; color: #11165a; text-align: center; text-decoration: none; padding: 14px 16px; }\n" +
                    "#MenyBar li a:hover { color: ghostwhite; }" +
                    "</style>");

            out.println("<ul id=\"MenyBar\">\n" +
                    "    <li><a href=\"#\">Om Velferden</a></li>\n" +
                    "    <li><a href=\"MineReservasjoner\">Mine Reservasjoner</a></li>\n" +
                    "    <li><a href=\"MineReservasjoner\">Returner</a></li>\n" +
                    "    <li><a href=\"Admin\">Administrator</a></li>\n" +
                    "    <li><a href=\"index.jsp\">Logg ut</a></li>" +
                    "</ul><br><br>");
        }
    }

