package bacit.web.bacit_DAO;

import bacit.web.bacit_model.MineReservasjonerModel;
import bacit.web.bacit_web.DBUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MineReservasjonerDAO {

    public void kansellerReservasjon(String reservasjon_id, PrintWriter out){
        try {
            Connection db = DBUtils.getINSTANCE().getConnection(out);


            String queryDeleteReservasjon = "delete from Reservasjon where Reservasjon_ID = ?";
            PreparedStatement statement = db.prepareStatement(queryDeleteReservasjon);

            statement.setString(1, reservasjon_id);
            ResultSet rs = statement.executeQuery();

        } catch (Exception g) {
            g.printStackTrace(out);
        }
    }
}
