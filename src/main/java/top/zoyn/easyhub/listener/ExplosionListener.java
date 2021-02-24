package top.zoyn.easyhub.listener;

import top.zoyn.easyhub.EasyHub;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

/**
 * 该类用于监听服务器内实体或方块爆炸时所处理的东西
 *
 * @author Zoyn
 */
public class ExplosionListener implements Listener {

    private static EasyHub instance;
    private static boolean antiExplosion;

    public ExplosionListener(EasyHub plugin) {
        instance = plugin;

        antiExplosion = instance.getConfig().getBoolean("antiExplosion");
        if (antiExplosion) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止爆炸已加载...");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onExplode(EntityExplodeEvent event) {
        if (antiExplosion) {
            List<Block> blocks = event.blockList();
            blocks.clear();
        }
    }

    public static void reloadConfig() {
        antiExplosion = instance.getConfig().getBoolean("antiExplosion");
    }

}
