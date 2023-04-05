package ch.gabzdev.gabzapi_test.utils;
//
import ch.gabzdev.gabzapi_test.mysql.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServersCommon {
    public String table = "servers";
    private String displayName;

    public ServersCommon(String displayName) {
        this.displayName = displayName;
    }

    public void loadServer(String address, int port) {
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnexion().prepareStatement("SELECT status FROM " + table + "WHERE server_name = ?");
            preparedStatement.setString(1, displayName);

            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                    preparedStatement.close();
                    preparedStatement = DatabaseManager.getConnexion().prepareStatement("INSERT INTO " + table + " (status, port, ip, name)");
                    preparedStatement.setInt(1, 1);
                    preparedStatement.setInt(2, port);
                    preparedStatement.setString(3, address);
                    preparedStatement.setString(4, displayName);
                    preparedStatement.execute();
                    preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(int status) {
        try {

            PreparedStatement preparedStatement = DatabaseManager.getConnexion().prepareStatement("UPDATE " + table + " SET status = ? WHERE server_name = ?");
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, displayName);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
