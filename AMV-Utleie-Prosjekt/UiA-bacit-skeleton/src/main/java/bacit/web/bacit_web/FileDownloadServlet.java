package bacit.web.bacit_web;

import bacit.web.bacit_DAO.FileDAO;
import bacit.web.bacit_model.FileModel;
import bacit.web.bacit_utility.htmlUtility;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Logger;

@WebServlet(name = "fileDownload", value = "/fileDownload")
public class FileDownloadServlet extends HttpServlet {

    Logger logger = Logger.getLogger(String.valueOf(FileUploadServlet.class));

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        htmlUtility html = new htmlUtility();
        html.htmlStart(out, "Download a file");

        ArrayList<Integer> indexes = getPDFs(out);

        assert indexes != null;
        ListIterator<Integer> iteratoren = indexes.listIterator();
        while(iteratoren.hasNext()) {
            Integer index = iteratoren.next();
            out.println("<p>Pdf " + index + "</p>");
            out.println("<form method=\"post\" action=\"fileDownload\">");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + index + "\">");
            out.println("<input type=\"submit\" value=\"Last ned pdf\">");
            out.println("</form>");
            out.println("<br>");
        }


        html.htmlEnd(out);    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stringId = getQueryStringParameter(request,"id");
        int id = Integer.parseInt(stringId);
        PrintWriter out = response.getWriter();
        try{
            FileModel fileModel =  getFile(id, out);
            writeFileResult(response,fileModel);
        }
        catch(Exception ex)
        {
            logger.severe(ex.getMessage());
        }

    }

    private ArrayList<Integer> getPDFs (PrintWriter out) {
        try {
            Connection db = null;

            try {
                db = DBUtils.getINSTANCE().getConnection(out);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement ps;

            ArrayList <Integer> indexes = new ArrayList<>();
            String query;
            query = "SELECT * FROM Files";

            assert db != null;
            ps = db.prepareStatement(query);
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                indexes.add(rs.getInt("Id"));
            }

            return indexes;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

     protected FileModel getFile(int id, PrintWriter out) throws Exception
     {
        return new FileDAO().getFile(id, out);
    }

    protected String getQueryStringParameter(HttpServletRequest request, String parameter)
    {
        return request.getParameter(parameter);
    }

    protected void writeFileResult(HttpServletResponse response, FileModel model) throws IOException
    {
        response.setContentType(model.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename="+model.getName());
        OutputStream outStream = response.getOutputStream();
        outStream.write(model.getContents());
    }
}
