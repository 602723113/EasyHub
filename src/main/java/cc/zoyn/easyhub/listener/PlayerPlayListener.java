package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * 该类主要用于监听玩家在大厅游玩时所监控的东西
 *
 * @author Zoyn
 */
public class PlayerPlayListener implements Listener {

    private static EasyHub instance;
    private static boolean clearDeathMessage;
    private static boolean preventPickupItem;
    private static boolean preventInteractItemFrame;

    public PlayerPlayListener(EasyHub plugin) {
        instance = plugin;

        clearDeathMessage = instance.getConfig().getBoolean("clearDeathMessage");
        preventPickupItem = instance.getConfig().getBoolean("preventPickupItem");
        preventInteractItemFrame = instance.getConfig().getBoolean("preventInteractItemFrame");
    }

    // 防止玩家旋转展示框
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (preventInteractItemFrame && !event.getPlayer().isOp()) {
            if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
                event.setCancelled(true);
            }
        }
    }

    // 清除玩家死亡信息
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (clearDeathMessage) {
            event.setDeathMessage(null);
        }
    }

    // 防止玩家拾取物品
    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent event) {
        if (preventPickupItem && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    // TODO: 要把自定义help给修复了
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help")) {

            e.setCancelled(true);
        } else {
            return;
        }
    }

    public static void reloadConfig() {
        clearDeathMessage = instance.getConfig().getBoolean("clearDeathMessage");
        preventPickupItem = instance.getConfig().getBoolean("preventPickupItem");
        preventInteractItemFrame = instance.getConfig().getBoolean("preventInteractItemFrame");
    }
}
