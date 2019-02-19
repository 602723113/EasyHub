package cc.zoyn.easyhub.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class DetectPluginListener implements Listener {

    private static List<String> checkPluginsCommands;
    private static String message;

    /**
     * 在构造时予以初始化
     */
    public DetectPluginListener(Plugin plugin) {
        checkPluginsCommands = plugin.getConfig().getStringList("antiPluginDetect.checkCommands");
        message = plugin.getConfig().getString("antiPluginDetect.message").replaceAll("&", "§");
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

}
