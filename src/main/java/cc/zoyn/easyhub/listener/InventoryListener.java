package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private static EasyHub instance;
    private static boolean preventMoveItem;

    public InventoryListener(EasyHub plugin) {
        instance = plugin;

        preventMoveItem = instance.getConfig().getBoolean("preventMoveItem");
        if (preventMoveItem) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家移动物品已加载...");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked().isOp()) {
            return;
        }
        if (event.getClickedInventory().getTitle().equalsIgnoreCase("container.crafting") || event.getInventory().getTitle().equalsIgnoreCase("container.inventory")) {
            if (preventMoveItem) {
                event.setCancelled(true);
            }
        }
    }

}
