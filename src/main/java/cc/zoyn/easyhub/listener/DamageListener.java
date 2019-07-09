package cc.zoyn.easyhub.listener;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


/**
 * 该类用于监听玩家伤害别人或自己脚滑而造成伤害时所处理的东西
 *
 * @author Zoyn
 */
public class DamageListener implements Listener {

    private static EasyHub instance;
    private static boolean preventPlayerFallDamage;
    private static boolean preventPlayerHitOthers;
    private static boolean voidReturn;

    public DamageListener(EasyHub plugin) {
        instance = plugin;

        preventPlayerFallDamage = instance.getConfig().getBoolean("preventPlayerFallDamage");
        preventPlayerHitOthers = instance.getConfig().getBoolean("preventPlayerHitOthers");
        voidReturn = instance.getConfig().getBoolean("voidReturn");
        if (preventPlayerFallDamage) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家掉落伤害已加载...");
        }
        if (preventPlayerHitOthers) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f防止玩家PVP已加载...");
        }
        if (voidReturn) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f正在加载虚空回照(掉落虚空自动返回出生点)...");
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity target = event.getEntity();
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (target instanceof Player) {
            // 跌落
            if (preventPlayerFallDamage) {
                if (cause == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                }
            }
            // 玩家互锤
            if (preventPlayerHitOthers) {
                if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    event.setCancelled(true);
                }
            }
            // 虚空返照
            if (voidReturn) {
                Location location = target.getLocation();
                Block block = location.getBlock();

                // 虚空掉落检查
                if (!block.getRelative(BlockFace.DOWN).getType().isSolid() && location.getY() <= 0.0D) {
                    target.teleport(EasyHub.getInstance().getSpawnPoint());
                }
            }
        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        Entity player = event.getDamager();
        Entity target = event.getEntity();
        if (!(target instanceof Player) || !(player instanceof Player)) {
            return;
        }
        if (preventPlayerHitOthers) {
            event.setCancelled(true);
        }
    }

    public static void reloadConfig() {
        preventPlayerFallDamage = instance.getConfig().getBoolean("preventPlayerFallDamage");
        preventPlayerHitOthers = instance.getConfig().getBoolean("preventPlayerHitOthers");
        voidReturn = instance.getConfig().getBoolean("voidReturn");
    }
}
