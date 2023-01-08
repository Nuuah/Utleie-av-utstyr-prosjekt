package bacit.web.bacit_web;

import bacit.web.bacit_DAO.NyReservasjonDAO;
import bacit.web.bacit_model.*;
import bacit.web.bacit_utility.htmlUtility;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "reservasjonsside", value = "/reservasjonsside")
public class Reservasjonside extends HttpServlet {

    NyReservasjonDAO dao = new NyReservasjonDAO();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        htmlUtility html = new htmlUtility();

        String request_id = request.getParameter("id");
        int utstyr_type_id = Integer.parseInt(request_id);

        int ansatt_id = 1; //TODO Ansatt id til verdi spesifisert i session.

        ArrayList<UtstyrModel> utstyret = dao.hentUtstyrType(utstyr_type_id, out);

        html.setPopup(true);
        html.setBootstrap(true);
        // Writing the html site;

        html.htmlStart(out, "Reserver3456");
        MenyBar.printMenyBar(out);

        assert utstyret != null;
        int laanbare_dager = -1;

        ListIterator<UtstyrModel> utstyr = utstyret.listIterator();
        while(utstyr.hasNext()) {
            UtstyrModel u = utstyr.next();
            BildeModel b = new BildeModel(u.getBildeId(), out);
            reservasjonHtml(out, u, b);
            laanbare_dager = u.getMaxLaanbareDager();
        }
        ArrayList<ReservasjonOversiktModel> reservasjoner = dao.hentReservasjoner(utstyr_type_id, out);

        assert reservasjoner != null;
        ArrayList<String> disableddates = null;
        try {
            disableddates = disabledDates(reservasjoner, utstyr_type_id, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert disableddates != null;
        kalendar(out, disableddates);

        reserver(out, ansatt_id, utstyr_type_id, laanbare_dager, disableddates);

        tabellLaanere(out, reservasjoner);

        html.htmlEnd(out);

    }

    private void reservasjonHtml(PrintWriter out, UtstyrModel utstyr, BildeModel bilde) {
        String navn = utstyr.getUtstyrNavn();
        int bildeId = utstyr.getBildeId();
        String beskrivelse = utstyr.getBeskrivelse();
        int pris = utstyr.getPris();
        boolean krav = utstyr.isSpesielleKrav();
        int laanbare_dager = utstyr.getMaxLaanbareDager();

        String bildeHtml;
        if (bilde == null){
            bildeHtml="Ingen bilde";
        }
        else {
            try {
                Blob blob = bilde.getBildeBlob();
                int blobLength = (int) blob.length();


                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                String bildeSrc = Base64.getEncoder().encodeToString(blobAsBytes);
                bildeHtml = "<img src=\"data:image/PNG;base64," + bildeSrc + "\" width=\"100\" height=\"120\">";
            }
            catch(Exception e) {
                bildeHtml = "Ingen bilde";
            }
        }

        out.println("<h1>" + navn + "</h1>");
        out.println(bildeHtml);
        out.println("<p>Pris per dag etter første dagen: " + pris + "</p>");
        out.println("<p>"+beskrivelse+"</p>");
        if (krav) {
            out.println("<p>Dette utstyret har spesielle krav for å brukes.</p>");
        }
        out.println("<p>Maks antall dager det er mulig å låne dette utstyret: " + laanbare_dager + "</p>");

    }

    private void kalendar(PrintWriter out, ArrayList<String> disabled) {
        StringBuilder disabledStr = new StringBuilder("[");
        for (int i = 0; i < disabled.size(); i++) {
            disabledStr.append("'").append(disabled.get(i)).append("', ");
        }
        disabledStr.append("]");

        out.println("<div class='container'>");
            out.println("<div class='col-md-8' >");
                out.println("<div class='form-group'>");
                    out.println("<div class='input-group date' id='datetimepicker6'>\n" +
                            "            <input type='text' class=\"form-control\" id='datepicker_start' />\n" +
                            "            <span class=\"input-group-addon\">\n" +
                            "            <span class=\"glyphicon glyphicon-calendar\"></span>\n" +
                            "            </span>\n" +
                            "         </div>");
                out.println("</div>");
            out.println("</div>");
            out.println("<div class='col-md-8'  >"); //style=" width:30%; "
                out.println("<div class='form-group'>");
                    out.println("<div class='input-group date' id='datetimepicker7'>\n" +
                            "         <input type='text' class=\"form-control\" id='datepicker_end' />\n" +
                            "         <span class=\"input-group-addon\">\n" +
                            "            <span class=\"glyphicon glyphicon-calendar\"></span>\n" +
                            "         </span>\n" +
                            "    </div>");
                out.println("</div>");
            out.println("</div>");
        out.println("</div>");
        out.println("<script type='text/javascript'>");
            out.println("$(function () {");
                out.println("$('#datetimepicker6').datetimepicker({");
                    out.println("format:'L',");
                    out.println("disabledDates: " + disabledStr + ",");
                out.println("});");
                out.println("$('#datetimepicker7').datetimepicker({");
                    out.println("useCurrent: false, //Important! See issue #1075");
                    out.println("format:'L',");
                    out.println("disabledDates: " + disabledStr + ",");
                out.println("});");
                out.println("$('#datetimepicker6').on('dp.change', function (e) {");
                    out.println("$('#datetimepicker7').data('DateTimePicker').minDate(e.date);");
                out.println("});");
                out.println("$('#datetimepicker7').on('dp.change', function (e) {");
                    out.println("$('#datetimepicker6').data('DateTimePicker').maxDate(e.date);");
                out.println("});");
            out.println("});");
        out.println("</script>");

    }

     private void reserver(PrintWriter out, int ansatt_id, int utstyr_type_id, int laanbare_dager, ArrayList<String> disableddates) {
        out.println("<button id=\"reserver_knapp\" class=\"open-button\" onclick=\"openForm()\">Reserver</button>\n");
        out.println("<div class=\"form-popup\" id=\"myForm\">");

        out.println("<h2>Reserver</h2>");
        out.println("<p id='feil_datoer' style='color: red;'>Du har valgt en ugyldig reservasjonsperiode. Ha totalt " + laanbare_dager + " dager.</p>");
        out.println("<div id='riktig_datoer'><p>Ønsker du å leie fra <span id='fra_dato'></span> til <span id='til_dato'></span>.</p></div>");

        out.println("<form method=\"post\" action=\"reservasjonsside\" class=\"form-container\">");
        // *********' SKJema som skal komme i popup når riktig input data. ********
        out.println("<input type='hidden' name='ansatt_id' value='" + ansatt_id + "'>");
        out.println("<input type='hidden' name='utstyr_type_id' value='" + utstyr_type_id + "'>");
        out.println("<input type='hidden' name='dato_laant' id='dato_laant'>");
        out.println("<input type='hidden' name='dato_levere' id='dato_levere'>");
        out.println("<input id='gjennomfor_reservasjon' type='submit' value='Reserver'>");
        out.println("<button type=\"button\" class=\"btn cancel\" onclick=\"closeForm()\">Close</button>");

        out.println("</form>");
        out.println("</div>");

        StringBuilder disabledStr = new StringBuilder("[");
        for (int i = 0; i < disableddates.size(); i++) {
            disabledStr.append("'").append(disableddates.get(i)).append("', ");
        }
        disabledStr.append("]");

        out.println("<script>function openForm() {" +
                "  document.getElementById(\"myForm\").style.display = \"block\";" +
                "}" +
                "" +
                "function closeForm() {" +
                "  document.getElementById(\"myForm\").style.display = \"none\";" +
                "}" +
                "" +
                "   document.getElementById('reserver_knapp').addEventListener('click', setInput);" +
                "   function setInput() {" +
                "       let datepicker_start = document.getElementById('datepicker_start');" +
                "       let datepicker_end = document.getElementById('datepicker_end');" +
                "       let input_dato_laant = document.getElementById('dato_laant');" +
                "       let input_dato_levere = document.getElementById('dato_levere');" +
                "       input_dato_laant.value = datepicker_start.value;" +
                "       input_dato_levere.value = datepicker_end.value;" +
                "" +
                "       if (datediff(parseDate(input_dato_laant.value), parseDate(input_dato_levere.value)) > " + (laanbare_dager-1) + " || datoPaValgteDato(" + disabledStr + ", input_dato_laant.value, input_dato_levere.value)) {" +
                "           document.getElementById('feil_datoer').style.display = 'block';" +
                "           document.getElementById('riktig_datoer').style.display = 'none';" +
                "           document.getElementById('gjennomfor_reservasjon').style.display = 'none';" +
                "       }" +
                "       else {" +
                "           document.getElementById('feil_datoer').style.display = 'none';" +
                "" +
                "           document.getElementById('fra_dato').innerHTML = input_dato_laant.value;" +
                "           document.getElementById('til_dato').innerHTML = input_dato_levere.value;" +
                "           document.getElementById('riktig_datoer').style.display = 'block';" +
                "           document.getElementById('gjennomfor_reservasjon').style.display = 'block';" +
                "       }" +
                "   }" +
                "   function parseDate(str) {" +
                "       var mdy = str.split('/');" +
                "       console.log(mdy[2], mdy[0]-1, mdy[1]);" +
                "       return new Date(mdy[2], mdy[0]-1, mdy[1]);" +
                "   }" +
                "" +
                "   function datediff(first, second) {\n" +
                "       // Take the difference between the dates and divide by milliseconds per day." +
                "       // Round to nearest whole number to deal with DST.\n" +
                "       console.log(Math.round((second-first)/(1000*60*60*24)));" +
                "       return Math.round((second-first)/(1000*60*60*24));" +
                "   }" +
                "" +
                "   function datoPaValgteDato(disableddays, first, second) {" +
                "       var dateFrom = first;" +
                "       var dateTo = second;" +
                "" +
                "       var d1 = dateFrom.split(\"/\");" +
                "       var d2 = dateTo.split(\"/\");" +
                "" +
                "       let isBetween = false;" +
                "       for (let i = 0; i < disableddays.length;i++) {" +
                "           var c = disableddays[i].split(\"-\");" +
                "           var from  = new Date(d1[2], d1[0], parseInt(d1[1])-1);" +
                "           var to    = new Date(d2[2], d2[0], parseInt(d2[1])-1);" +
                "           var check = new Date(c[2], c[0], parseInt(c[1])-1);" +
                "console.log(disableddays[i], c);" +
                "           console.log('from', from, 'to', to, 'check', check);" +
                "           if (check > from && check < to) {" +
                "               isBetween = true;" +
                "               break;" +
                "           }" +
                "       }" +
                "       return isBetween;" +
                "   }" +
                "</script>");
    }

    private void tabellLaanere(PrintWriter out, ArrayList<ReservasjonOversiktModel> reservasjoner) {
        out.println("<table>" +
                "       <tr>" +
                "           <th>Fornavn</th>" +
                "           <th>Etternavn</th>" +
                "           <th>Telefonnummer</th>" +
                "           <th>Fra dato</th>" +
                "           <th>Til dato</th>" +
                "       </tr>");

        for (int i = 0; i < reservasjoner.size(); i++) {
            ReservasjonOversiktModel r = reservasjoner.get(i);

            java.sql.Date dato_laant = r.getDato_laant();
            LocalDate dato_leveres = dato_laant.toLocalDate().plusDays(r.getDager_reservert());

            out.println("<tr>" +
                    "       <td>" + r.getFornavn() + "</td>" +
                    "       <td>" + r.getEtternavn() + "</td>" +
                    "       <td>" + r.getTelefon() + "</td>" +
                    "       <td>" + dato_laant + "</td>" +
                    "       <td>" + dato_leveres + "</td>" +
                    "   </tr>");
        }

        out.println("</table>");
    }

    private ArrayList<String> disabledDates(ArrayList<ReservasjonOversiktModel> reservasjoner, int utstyr_type_id, PrintWriter out) throws Exception {
        ArrayList<String> disableddates = new ArrayList<>();
        ArrayList<String> alldates = new ArrayList<>();

        // Antall enheter det finnes av spesifikk type.
        int antallEnheter = dao.getTotalEnheterAvType(utstyr_type_id, out);

        // Alle dager hvor utstyr er på utlån.

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy");

        for (int i = 0; i < reservasjoner.size(); i++) {
            ReservasjonOversiktModel r = reservasjoner.get(i);
            Date date = r.getDato_laant();

            String strDate = format.format(date);
            alldates.add(strDate);

            Calendar cal = Calendar.getInstance();
            int dagerReservert = r.getDager_reservert();
            for (int j = 1; j < dagerReservert; j++) {
                cal.setTime(date);
                cal.add(Calendar.DATE, 1);
                date = (Date) cal.getTime();
                strDate = format.format(date);
                alldates.add(strDate);
            }
        }

        // Finner antall ganger utstyr er lånt på samme dato.
        HashMap<String, Integer> occurences = new HashMap<String, Integer>();
        for (int i = 0; i < alldates.size(); i++) {
            String dato = alldates.get(i);

            if (occurences.containsKey(dato)) {
                occurences.put(dato, (occurences.get(dato) + 1));
            }
            else {
                occurences.put(dato, 1);
            }
        }

        // Hvis utstyret er lånt like mange ganger som det er enheter, er datoen disabled.
        disableddates = getKeysByValue(occurences, antallEnheter);

        return disableddates;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            int Ansatt_ID = Integer.parseInt(request.getParameter("ansatt_id"));
            int type_id = Integer.parseInt(request.getParameter("utstyr_type_id"));
            String dato_laant_str = formatToyyyyMMdd(request.getParameter("dato_laant"));
            String dato_levere_str = formatToyyyyMMdd(request.getParameter("dato_levere"));

            java.sql.Date dato_laant = java.sql.Date.valueOf(dato_laant_str);
            java.sql.Date dato_levere = java.sql.Date.valueOf(LocalDate.parse(dato_levere_str));

            int dager_reservert = (int) getDateDiff(dato_laant, dato_levere);

            int enhet_id = dao.enhetAvailableToRent(type_id, dato_laant, dato_levere, out);

            NyReservasjonModel reservasjonModel = new NyReservasjonModel(
                    Ansatt_ID, enhet_id, dato_laant, dager_reservert
            );

            dao.addReservasjon(reservasjonModel, out);

            writeReservationConfirmation(out, dato_laant_str, dato_levere_str);

        }
        catch (Exception ex){
            out.println(ex);
        }
    }

    private void writeReservationConfirmation(PrintWriter out, String dato_laant, String dato_levere) {
        htmlUtility html = new htmlUtility();

        html.htmlStart(out, "Reservert utstyr");

        out.println("<p>Du har suksessfullt reservert utstyret</p>");
        out.println("<p>Fra " + dato_laant + "</p>");
        out.println("<p>Til " + dato_levere + "</p>");
        out.println("<a href='Frontpage'>Gå tilbake til hovedsiden</a>");

        html.htmlEnd(out);
    }

    private static ArrayList<String> getKeysByValue(Map<String, Integer> map, int value) {
        ArrayList<String> datoer = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                datoer.add(entry.getKey());
            }
        }
        return datoer;
    }

    private static String formatToyyyyMMdd(String MMddyyyy) {
        String[] splitDate = MMddyyyy.split("/");
        return (splitDate[2] + "-" + splitDate[0] + "-" + splitDate[1]);
    }

    private static long getDateDiff(Date date1, Date date2) {
        TimeUnit timeUnit = TimeUnit.DAYS;
        long diffInMillies = date2.getTime() - date1.getTime();
        return (timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS) + 1);
    }

}
