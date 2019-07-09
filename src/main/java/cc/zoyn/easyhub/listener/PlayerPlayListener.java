package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
    private static boolean preventDropItem;
    private static boolean preventInteractItemFrame;
    private static boolean preventPlayerFoodLevelChange;
    // custom help
    private static boolean customHelp;
    private static List<String> customHelpCheckCommands;
    private static List<String> customHelpMessage;

    public PlayerPlayListener(EasyHub plugin) {
        instance = plugin;

        clearDeathMessage = instance.getConfig().getBoolean("clearDeathMessage");
        preventPickupItem = instance.getConfig().getBoolean("preventPickupItem");
        preventDropItem = instance.getConfig().getBoolean("preventDropItem");
        preventInteractItemFrame = instance.getConfig().getBoolean("preventInteractItemFrame");
        preventPlayerFoodLevelChange = instance.getConfig().getBoolean("preventPlayerFoodLevelChange");

        customHelp = instance.getConfig().getBoolean("customHelp.switch");
        customHelpCheckCommands = instance.getConfig().getStringList("customHelp.checkCommands");
        customHelpMessage = instance.getConfig().getStringList("customHelp.message")
                .stream()
                .map(s -> s.replace("&", "§"))
                .collect(Collectors.toList());

        if (clearDeathMessage) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f清空死亡信息已加载...");
        }
        if (preventPickupItem) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家拾取物品已加载...");
        }
        if (preventDropItem) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家丢弃物品已加载...");
        }
        if (preventInteractItemFrame) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家旋转展示框已加载...");
        }
        if (preventPlayerFoodLevelChange) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家饱食度减少已加载...");
        }
        if (customHelp) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f自定义帮助信息已加载...");
        }
    }

    // 防止玩家丢弃物品
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (preventDropItem && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    // 防止玩家饱食度变化
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (preventPlayerFoodLevelChange) {
            if (event.getFoodLevel() != 20) {
                event.setFoodLevel(20);
                return;
            }
            event.setCancelled(true);
        }
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
        preventPlayerFoodLevelChange = instance.getConfig().getBoolean("preventPlayerFoodLevelChange");

        customHelp = instance.getConfig().getBoolean("customHelp.switch");
        customHelpCheckCommands = instance.getConfig().getStringList("customHelp.checkCommands");
        customHelpMessage = instance.getConfig().getStringList("customHelp.message")
                .stream()
                .map(s -> s.replace("&", "§"))
                .collect(Collectors.toList());
    }
}
