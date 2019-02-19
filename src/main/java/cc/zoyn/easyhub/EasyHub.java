package cc.zoyn.easyhub;

import cc.zoyn.easyhub.listener.WeatherListener;
import cc.zoyn.easyhub.task.WorldTimeSetTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

/**
 * 主类
 *
 * @author Zoyn
 */
public class EasyHub extends JavaPlugin {

    private static EasyHub instance;
    private List<String> noRainWorlds;
    private Map<String, TimeType> timeSetWorlds;
    private WorldTimeSetTask timeSetTask;

    @Override
    public void onEnable() {
        instance = this;

        // 配置读取
        saveDefaultConfig();
        loadConfig();
        timeSetTask = new WorldTimeSetTask();

        if (getConfig().getBoolean("weather.switch")) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §a模块 §f锁住晴天 §a已加载!");
            // 开局变晴天
            Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getWorlds().forEach(world -> {
                if (noRainWorlds.contains(world.getName())) {
                    // 防止世界表中有地狱或者末地的情况
                    if (world.getWeatherDuration() != 0) {
                        world.setStorm(false);
                    }
                }
            }), 20L);
            Bukkit.getPluginManager().registerEvents(new WeatherListener(), this);
        }

        if (getConfig().getBoolean("time.switch")) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §a模块 §f锁定时间 §a已加载!");
            timeSetTask.startTask();
        }

        Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §a已加载!");
    }

    /**
     * 得到EasyHub实例
     *
     * @return {@link EasyHub}
     */
    public static EasyHub getInstance() {
        return instance;
    }

    private void loadConfig() {
        // 无雨读取
        noRainWorlds = getConfig().getStringList("weather.worlds");
        // 锁定时间读取
        getConfig().getStringList("time.worlds").forEach(s -> {
            if (s.contains(":")) {
                String[] data = s.split(":");
                String worldName = data[0];
                String timeType = data[1];
                timeSetWorlds.put(worldName, TimeType.valueOf(timeType));
            }
        });
    }

    public List<String> getNoRainWorlds() {
        return noRainWorlds;
    }

    public Map<String, TimeType> getTimeSetWorlds() {
        return timeSetWorlds;
    }

}
