package cc.zoyn.easyhub;

import cc.zoyn.easyhub.listener.WeatherListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 主类
 *
 * @author Zoyn
 */
public class EasyHub extends JavaPlugin {

    private static EasyHub instance;

    public EasyHub() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §a已加载!");

        // 开局变晴天
        Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getWorlds().forEach(world -> {
            // 防止世界表中有地狱或者末地的情况
            if (world.getWeatherDuration() != 0) {
                world.setStorm(false);
            }
        }), 3 * 20L);

//        Bukkit.getPluginManager().registerEvents(new WeatherListener(), this);
    }

    /**
     * 得到EasyHub实例
     *
     * @return {@link EasyHub}
     */
    public static EasyHub getInstance() {
        return instance;
    }
}
