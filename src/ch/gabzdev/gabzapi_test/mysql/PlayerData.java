package ch.gabzdev.gabzapi_test.mysql;
//
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public void addCoins(long amount) {
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnexion().prepareStatement("UPDATE players SET coins = coins + ? WHERE uuid_player = ?");
            preparedStatement.setLong(1, amount);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCoins(long amount) {
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnexion().prepareStatement("UPDATE players SET coins = coins - ? WHERE uuid_player = ?");
            preparedStatement.setLong(1, amount);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long getCoins() {
        try {

            PreparedStatement preparedStatement = DatabaseManager.getConnexion().prepareStatement("SELECT coins FROM players WHERE uuid_player = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet rs = preparedStatement.executeQuery();
            long coins = 0;

            while (rs.next()) {
                coins = rs.getLong("coins");
            }
            preparedStatement.close();

            return coins;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
