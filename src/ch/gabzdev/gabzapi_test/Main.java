package ch.gabzdev.gabzapi_test;
//
import ch.gabzdev.gabzapi_test.commands.CoinsCommand;
import ch.gabzdev.gabzapi_test.listeners.PlayerJoinListener;
import ch.gabzdev.gabzapi_test.mysql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main INSTANCE;
    public DatabaseManager database;

    @Override
    public void onEnable() {
        INSTANCE = this;
        database = new DatabaseManager("jdbc:mysql://", "localhost", "mc", "root", "");
        database.connexion();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);

        this.getCommand("coins").setExecutor(new CoinsCommand());
    }

    @Override
    public void onDisable() {
        database.deconnexion();
    }
}
