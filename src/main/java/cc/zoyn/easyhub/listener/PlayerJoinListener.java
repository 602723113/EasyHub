package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 玩家上线时所做的一些东西
 *
 * @author Zoyn
 */
public class PlayerJoinListener implements Listener {

    private static EasyHub instance;
    private static boolean clearInventory;
    private static boolean healPlayer;
    private static boolean forceSpawnPoint;

    public PlayerJoinListener(EasyHub plugin) {
        instance = plugin;

        clearInventory = instance.getConfig().getBoolean("clearInventory");
        healPlayer = instance.getConfig().getBoolean("healPlayer");
        forceSpawnPoint = instance.getConfig().getBoolean("forceSpawnPoint");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            // 清理背包, op不会清理背包
            if (clearInventory && !player.isOp()) {
                player.getInventory().clear();
            }
            // 治疗玩家
            if (healPlayer) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
            }
            // 传送至出生点
            if (forceSpawnPoint) {
                player.teleport(instance.getSpawnPoint());
            }
        }, 3 * 20L);
    }

    public static void reloadConfig() {
        clearInventory = instance.getConfig().getBoolean("clearInventory");
        healPlayer = instance.getConfig().getBoolean("healPlayer");
        forceSpawnPoint = instance.getConfig().getBoolean("forceSpawnPoint");
    }

}
