package top.zoyn.easyhub.listener;

import top.zoyn.easyhub.EasyHub;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Map;

/**
 * 玩家上线时所做的一些东西
 *
 * @author Zoyn
 */
public class PlayerJoinLeaveListener implements Listener {

    private static EasyHub instance;
    private static boolean clearInventory;
    private static boolean healPlayer;
    private static boolean forceSpawnPoint;

    private static boolean customJoinLeaveMessage;
    // 对应关系 权限节点 -> 一串信息
    private static final Map<String, List<String>> joinMessage = Maps.newHashMap();
    private static final Map<String, List<String>> leaveMessage = Maps.newHashMap();

    public PlayerJoinLeaveListener(EasyHub plugin) {
        instance = plugin;

        clearInventory = instance.getConfig().getBoolean("clearInventory");
        healPlayer = instance.getConfig().getBoolean("healPlayer");
        forceSpawnPoint = instance.getConfig().getBoolean("forceSpawnPoint");
        customJoinLeaveMessage = instance.getConfig().getBoolean("customJoinLeaveMessage.enable");

        if (clearInventory) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f开局清空背包已加载...");
        }
        if (healPlayer) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f开局治疗玩家已加载...");
        }
        if (forceSpawnPoint) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f开局强行出生点已加载...");
        }
        if (customJoinLeaveMessage) {
            instance.getServer().getConsoleSender().sendMessage("§6[§eEasyHub§6] §f自定义加入离开服务器信息已加载...");
            ConfigurationSection joinSection = instance.getConfig().getConfigurationSection("customJoinLeaveMessage.join");
            joinSection.getKeys(false).forEach(s -> {
                if (joinSection.getString(s + ".permission") != null) {
                    joinMessage.put(joinSection.getString(s + ".permission"), joinSection.getStringList(s + ".message"));
                }
            });
            joinMessage.put("op", joinSection.getStringList("op"));
            joinMessage.put("default", joinSection.getStringList("default"));


            ConfigurationSection leaveSection = instance.getConfig().getConfigurationSection("customJoinLeaveMessage.leave");
            leaveSection.getKeys(false).forEach(s -> {
                if (leaveSection.getString(s + ".permission") != null) {
                    leaveMessage.put(leaveSection.getString(s + ".permission"), leaveSection.getStringList(s + ".message"));
                }
            });
            leaveMessage.put("op", leaveSection.getStringList("op"));
            leaveMessage.put("default", leaveSection.getStringList("default"));
        }


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
        if (customJoinLeaveMessage) {
            event.setJoinMessage(null);
            for (Map.Entry<String, List<String>> entry : joinMessage.entrySet()) {
                if (player.isOp()) {
                    joinMessage.get("op").forEach(s -> Bukkit.broadcastMessage(s.replace("&", "§")
                            .replace("%player%", player.getName())));
                    return;
                }
                if (player.hasPermission(entry.getKey())) {
                    entry.getValue().forEach(s -> Bukkit.broadcastMessage(s.replace("&", "§")
                            .replace("%player%", player.getName())));
                    return;
                }
            }
            joinMessage.get("default").forEach(s -> Bukkit.broadcastMessage(s.replace("&", "§")
                    .replace("%player%", player.getName())));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (customJoinLeaveMessage) {
            event.setQuitMessage(null);
            for (Map.Entry<String, List<String>> entry : leaveMessage.entrySet()) {
                if (player.isOp()) {
                    leaveMessage.get("op").forEach(s -> Bukkit.broadcastMessage(s.replace("&", "§")
                            .replace("%player%", player.getName())));
                    return;
                }
                if (player.hasPermission(entry.getKey())) {
                    entry.getValue().forEach(s -> Bukkit.broadcastMessage(s.replace("&", "§")
                            .replace("%player%", player.getName())));
                    return;
                }
            }
            leaveMessage.get("default").forEach(s -> Bukkit.broadcastMessage(s.replace("&", "§")
                    .replace("%player%", player.getName())));
        }
    }

    public static void reloadConfig() {
        clearInventory = instance.getConfig().getBoolean("clearInventory");
        healPlayer = instance.getConfig().getBoolean("healPlayer");
        forceSpawnPoint = instance.getConfig().getBoolean("forceSpawnPoint");
        customJoinLeaveMessage = instance.getConfig().getBoolean("customJoinLeaveMessage.enable");

        if (customJoinLeaveMessage) {
            joinMessage.clear();
            leaveMessage.clear();

            ConfigurationSection joinSection = instance.getConfig().getConfigurationSection("customJoinLeaveMessage.join");
            joinSection.getKeys(false).forEach(s -> {
                if (joinSection.getString(s + ".permission") != null) {
                    joinMessage.put(joinSection.getString(s + ".permission"), joinSection.getStringList(s + ".message"));
                }
            });
            joinMessage.put("op", joinSection.getStringList("op"));
            joinMessage.put("default", joinSection.getStringList("default"));


            ConfigurationSection leaveSection = instance.getConfig().getConfigurationSection("customJoinLeaveMessage.leave");
            leaveSection.getKeys(false).forEach(s -> {
                if (leaveSection.getString(s + ".permission") != null) {
                    leaveMessage.put(leaveSection.getString(s + ".permission"), leaveSection.getStringList(s + ".message"));
                }
            });
            leaveMessage.put("op", leaveSection.getStringList("op"));
            leaveMessage.put("default", leaveSection.getStringList("default"));
        }
    }
}