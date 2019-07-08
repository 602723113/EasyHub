package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 该类用于监听玩家对方块进行操作时所处理的东西
 *
 * @author Zoyn
 */
public class BlockListener implements Listener {

    private static EasyHub instance;
    private static boolean preventPlayerBreakBlock;
    private static boolean preventPlayerPlaceBlock;

    public BlockListener(EasyHub plugin) {
        instance = plugin;

        preventPlayerBreakBlock = instance.getConfig().getBoolean("preventPlayerBreakBlock");
        preventPlayerPlaceBlock = instance.getConfig().getBoolean("preventPlayerPlaceBlock");
    }

    // 防止破坏
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (preventPlayerBreakBlock && !player.isOp()) {
            event.setCancelled(true);
        }
    }

    // 防止放置
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (preventPlayerPlaceBlock && !player.isOp()) {
            event.setCancelled(true);
        }
    }

    public static void reloadConfig() {
        preventPlayerBreakBlock = instance.getConfig().getBoolean("preventPlayerBreakBlock");
        preventPlayerPlaceBlock = instance.getConfig().getBoolean("preventPlayerPlaceBlock");
    }


}
