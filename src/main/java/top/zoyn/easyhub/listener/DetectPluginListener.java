package top.zoyn.easyhub.listener;

import top.zoyn.easyhub.EasyHub;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

/**
 * 该类用于监听玩家探测服务器插件时所处理的东西
 *
 * @author Zoyn
 */
public class DetectPluginListener implements Listener {

    private static List<String> checkPluginsCommands;
    private static String message;
    private static EasyHub instance;

    /**
     * 在构造时予以初始化
     */
    public DetectPluginListener(EasyHub plugin) {
        instance = plugin;

        checkPluginsCommands = instance.getConfig().getStringList("antiPluginDetect.checkCommands");
        message = instance.getConfig().getString("antiPluginDetect.message").replaceAll("&", "§");
    }

    @EventHandler
    public void onPluginsCommands(PlayerCommandPreprocessEvent event) {
        checkPluginsCommands.forEach(s -> {
            if (event.getMessage().contains(s)) {
                event.getPlayer().sendMessage(message);
                event.setCancelled(true);
            }
        });
    }

    public static void reloadConfig() {
        checkPluginsCommands = instance.getConfig().getStringList("antiPluginDetect.checkCommands");
        message = instance.getConfig().getString("antiPluginDetect.message").replaceAll("&", "§");
    }

}
