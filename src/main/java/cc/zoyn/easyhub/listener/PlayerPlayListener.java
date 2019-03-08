package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.List;
import java.util.stream.Collectors;

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
    // custom help
    private static boolean customHelp;
    private static List<String> customHelpCheckCommands;
    private static List<String> customHelpMessage;

    public PlayerPlayListener(EasyHub plugin) {
        instance = plugin;

        clearDeathMessage = instance.getConfig().getBoolean("clearDeathMessage");
        preventPickupItem = instance.getConfig().getBoolean("preventPickupItem");
        preventInteractItemFrame = instance.getConfig().getBoolean("preventInteractItemFrame");

        customHelp = instance.getConfig().getBoolean("customHelp.switch");
        customHelpCheckCommands = instance.getConfig().getStringList("customHelp.checkCommands");
        customHelpMessage = instance.getConfig().getStringList("customHelp.message")
                .stream()
                .map(s -> s.replace("&", "§"))
                .collect(Collectors.toList());

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

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (customHelp && !event.getPlayer().isOp()) {
            System.out.println();
            if (customHelpCheckCommands.contains(event.getMessage())) {
                customHelpMessage.forEach(event.getPlayer()::sendMessage);
                event.setCancelled(true);
            }
        }
    }

    public static void reloadConfig() {
        clearDeathMessage = instance.getConfig().getBoolean("clearDeathMessage");
        preventPickupItem = instance.getConfig().getBoolean("preventPickupItem");
        preventInteractItemFrame = instance.getConfig().getBoolean("preventInteractItemFrame");

        customHelp = instance.getConfig().getBoolean("customHelp.switch");
        customHelpCheckCommands = instance.getConfig().getStringList("customHelp.checkCommands");
        customHelpMessage = instance.getConfig().getStringList("customHelp.message")
                .stream()
                .map(s -> s.replace("&", "§"))
                .collect(Collectors.toList());
    }
}
