package ch.gabzdev.gabzapi_test.mysql;
//
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfo {
    private static Map<Player, PlayerInfo> playerInfo = new HashMap<Player, PlayerInfo>();
    private Player player;
    private PlayerData playerData;

    public PlayerInfo(Player player) {
        this.player = player;
        this.playerData = new PlayerData(player.getUniqueId());
        playerInfo.put(player, this);
    }

    public static PlayerInfo getInfosPlayer(Player player) {
        return playerInfo.get(player);
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Float getCoinsNumber() {
        return playerData.getCoins();
    }

    public void addCoins(float amount) {
        playerData.addCoins(amount);
    }

    public void removeCoins(float amount) {
        playerData.removeCoins(amount);
    }
}
