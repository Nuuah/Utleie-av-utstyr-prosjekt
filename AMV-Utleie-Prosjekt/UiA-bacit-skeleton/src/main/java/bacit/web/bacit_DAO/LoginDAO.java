package bacit.web.bacit_DAO;

import bacit.web.bacit_model.LoginModel;
import bacit.web.bacit_web.DBUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {


    public boolean checkLogin (LoginModel loginModel, HttpServletResponse response, PrintWriter out) throws SQLException, IOException, ClassNotFoundException  {
        Connection db = DBUtils.getINSTANCE().getConnection(out);
        String queryAnsattLogin = "select * from Ansatt where Telefon = ? and Passord = ?";

        PreparedStatement statement = db.prepareStatement(queryAnsattLogin);
        statement.setString(1, loginModel.getTelefonnummer());
        statement.setString(2, loginModel.getPassord());
        ResultSet rs = statement.executeQuery();


        // Sjekker om kombinasjonen av telefonnummer og passord finnes 1 gang. Gjør det det er kombinasjonen riktig.
        int finnes = 0;
        while (rs.next()) {
            finnes++;
        }

        // Om passord og tlf. nummer riktig sendes brukeren til fremsiden. Hvis ikke får brukeren feilmelding.
        if (finnes == 1) {
            return true;
        }
        else {
            return false;
        }

    }

}

