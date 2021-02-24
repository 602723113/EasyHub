package top.zoyn.easyhub.listener;

import top.zoyn.easyhub.EasyHub;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * 该类主要用于监听生物相关所监控的东西
 *
 * @author Zoyn
 */
public class CreatureListener implements Listener {

    private static EasyHub instance;
    private static boolean preventSpawnMob;

    public CreatureListener(EasyHub plugin) {
        instance = plugin;

        preventSpawnMob = instance.getConfig().getBoolean("preventSpawnMob");
        if (preventSpawnMob) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止怪物生成已加载...");
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            return;
        }
        if (event.getEntity() instanceof Monster) {
            if (preventSpawnMob) {
//                event.getEntity().getWorld().setDifficulty(Difficulty.PEACEFUL);
                event.setCancelled(true);
            }
        }
    }

    public static void reloadConfig() {
        preventSpawnMob = instance.getConfig().getBoolean("preventSpawnMob");
    }

}
