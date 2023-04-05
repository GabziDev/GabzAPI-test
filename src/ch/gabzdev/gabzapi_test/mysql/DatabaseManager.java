package ch.gabzdev.gabzapi_test.mysql;
//
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    private String urlBase;
    private String host;
    private String database;
    private String userName;
    private String password;
    private static Connection connection;

    public DatabaseManager(String urlBase, String host, String database, String userName, String password) {
        this.urlBase = urlBase;
        this.host = host;
        this.database = database;
        this.userName = userName;
        this.password = password;
    }

    public static Connection getConnexion() {
        return connection;
    }



    public void createAccount(UUID uuid) {
        if (!hasAccount(uuid)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid_player, pseudo_player, coins) VALUES (?, ?, ?)");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, Bukkit.getPlayer(uuid).getName());
                preparedStatement.setFloat(3, 100F);
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasAccount(UUID uuid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT coins FROM players WHERE uuid_player = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    * Méthode qui permet d'effectuer la connexion à la base de donnée quand le plugin s'allume.
    * */

    public void connexion() {
        if (!isOnline()) {
            try {
                connection = DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.userName, this.password);
                System.out.println("§c[DatabaseManager] Succesfully connected.");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Méthode qui permet d'effectuer la déconnexion à la base de donnée quand le plugin s'éteint.
     * */
    public void deconnexion() {
        if (isOnline()) {
            try {
                connection.close();
                System.out.println("§c[DatabaseManager] Succesfully disconnected.");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isOnline() {
        try {
            if ((connection == null) || (connection.isClosed())) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}