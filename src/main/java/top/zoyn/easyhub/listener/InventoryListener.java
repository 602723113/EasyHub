package top.zoyn.easyhub.listener;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.zoyn.easyhub.EasyHub;
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
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getClickedInventory() instanceof PlayerInventory) {
            if (preventMoveItem) {
                event.setCancelled(true);
            }
        }
//        if (event.getClickedInventory().getTitle().equalsIgnoreCase("container.crafting") || event.getInventory().getTitle().equalsIgnoreCase("container.inventory")) {
//            if (preventMoveItem) {
//                event.setCancelled(true);
//            }
//        }
    }

}
