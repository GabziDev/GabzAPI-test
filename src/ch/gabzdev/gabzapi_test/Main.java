package ch.gabzdev.gabzapi_test;
//
import ch.gabzdev.gabzapi_test.commands.CoinsCommand;
import ch.gabzdev.gabzapi_test.listeners.PlayerJoinListener;
import ch.gabzdev.gabzapi_test.mysql.DatabaseManager;
import ch.gabzdev.gabzapi_test.utils.ServersCommon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main INSTANCE;
    public DatabaseManager database;
    public ServersCommon serverCommon;

    @Override
    public void onEnable() {
        INSTANCE = this;
        database = new DatabaseManager("jdbc:mysql://", "localhost", "mc", "root", "");
        database.connexion();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);

        this.getCommand("coins").setExecutor(new CoinsCommand());

        serverCommon = new ServersCommon(Bukkit.getServerName());
        serverCommon.loadServer(Bukkit.getIp(), Bukkit.getPort());
        serverCommon.setStatus(1);
    }

    @Override
    public void onDisable() {
        serverCommon.setStatus(0);
        database.deconnexion();
    }
}
