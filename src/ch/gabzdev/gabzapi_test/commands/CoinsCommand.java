package ch.gabzdev.gabzapi_test.commands;
//
import ch.gabzdev.gabzapi_test.mysql.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand implements CommandExecutor {
    private final long MAX_COINS = 5000000;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerInfo playerInfo = new PlayerInfo(player);

        if (args.length == 0) {
            player.sendMessage("§eVous avez actuellement §6§l" + playerInfo.getCoinsNumber() + " Coins§e.");
            return true;
        }

        //coins add <montant> <joueur>
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("add")) {

                if (args.length == 3) {
                    String playerName = args[2];
                    Player target = Bukkit.getPlayer(playerName);
                    if (target != null) {
                        String amountString = args[1].replace(",", ".");
                        long amount = 0;
                        try {
                            amount = Integer.parseInt(amountString);
                        } catch (NumberFormatException e) {
                            player.sendMessage("§cErreur: Veuillez saisir un montant valide.");
                            return true;
                        }

                        if (amount < 1 || amount > MAX_COINS) {
                            player.sendMessage("§cErreur: Le montant doit être compris entre 1 et " + MAX_COINS + ".");
                            return true;
                        }

                        if (amount > MAX_COINS - playerInfo.getCoinsNumber()) {
                            player.sendMessage("§cErreur: Vous ne pouvez pas ajouter plus de §6§l" + (MAX_COINS - playerInfo.getCoinsNumber()) + " Coins §cen raison de la limite maximale de §6§l" + MAX_COINS + " Coins§c.");
                        }

                        PlayerInfo targetInfo = new PlayerInfo(target);
                        targetInfo.addCoins(amount);
                        player.sendMessage("§eVous avez give §6§l" + amount + " Coins §eà " + target.getName() + " !");
                    } else {
                        player.sendMessage("§cErreur: Le joueur " + playerName + " n'est pas en ligne.");
                    }
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    String playerName = args[2];
                    Player target = Bukkit.getPlayer(playerName);
                    if (target != null) {
                        String amountString = args[1].replace(",", ".");
                        long amount = 0;
                        try {
                            amount = Integer.parseInt(amountString);
                        } catch (NumberFormatException e) {
                            player.sendMessage("§cErreur: Veuillez saisir un montant valide.");
                            return true;
                        }

                        if (amount < 1 || amount > MAX_COINS) {
                            player.sendMessage("§cErreur: Le montant doit être compris entre 1 et " + MAX_COINS + ".");
                            return true;
                        }

                        if (amount > playerInfo.getCoinsNumber()) {
                            player.sendMessage("§cErreur: Le joueur n'a pas assez de coins !");
                            return true;
                        }

                        PlayerInfo targetInfo = new PlayerInfo(target);
                        targetInfo.removeCoins(amount);
                        player.sendMessage("§eVous avez retiré §6§l" + amount + " Coins §eà " + target.getName() + " !");
                    } else {
                        player.sendMessage("§cErreur: Le joueur " + playerName + " n'est pas en ligne.");
                    }
                }
            }
        }
        return false;
    }
}