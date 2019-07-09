package cc.zoyn.easyhub;

import cc.zoyn.easyhub.command.CommandHandler;
import cc.zoyn.easyhub.listener.*;
import cc.zoyn.easyhub.task.PlayerMoveCheckTask;
import cc.zoyn.easyhub.task.WorldTimeSetTask;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 主类
 *
 * @author Zoyn
 */
public class EasyHub extends JavaPlugin {

    @SuppressWarnings("unused")
    private static final String INFORMATION = "You do not have to decompile the plugin to get the source code, this plugin has push the source code to Github, URL: https://github.com/602723113/EasyHub";
    @SuppressWarnings("unused")
    private static final String INFORMATION_CN = "你不必反编译本插件来获取源码, 本插件已将源码托管到Github, URL: https://github.com/602723113/EasyHub";

    private static EasyHub instance;
    private List<String> noRainWorlds;
    private Map<String, TimeType> timeSetWorlds = Maps.newHashMap();
    private WorldTimeSetTask timeSetTask;
    private PlayerMoveCheckTask moveCheckTask;
    // 出生点相关
    private File spawnPointFile;
    private Location spawnPoint;

    @Override
    public void onEnable() {
        instance = this;

        // 配置读取
        saveDefaultConfig();
        spawnPointFile = new File(getDataFolder(), "spawnpoint.yml");
        saveResource("spawnpoint.yml", false);
        timeSetTask = new WorldTimeSetTask();
        moveCheckTask = new PlayerMoveCheckTask();
        loadConfig();

        // 事件注册
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlayListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CreatureListener(this), this);

        Bukkit.getPluginCommand("easyhub").setExecutor(new CommandHandler());
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

    public void loadConfig() {
        if (getConfig().getBoolean("weather.switch")) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §f正在加载锁住晴天...");
            // 开局变晴天
            Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getWorlds().forEach(world -> {
                if (world != null) {
                    if (noRainWorlds.contains(world.getName())) {
                        // 防止世界表中有地狱或者末地的情况
                        if (world.getWeatherDuration() != 0) {
                            world.setStorm(false);
                        }
                    }
                }
            }), 20L);
            Bukkit.getPluginManager().registerEvents(new WeatherListener(), this);
        }

        if (getConfig().getBoolean("time.switch")) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §f正在加载锁定时间...");
            timeSetTask.startTask();
        }

        if (getConfig().getBoolean("antiPluginDetect.switch")) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §f正在加载隐藏插件...");
            Bukkit.getPluginManager().registerEvents(new DetectPluginListener(this), this);
        }

        if (getConfig().getBoolean("voidReturn")) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §f正在加载虚空回照(掉落虚空自动返回出生点)...");
            moveCheckTask.startTask();
        }

        // 无雨读取
        noRainWorlds = getConfig().getStringList("weather.worlds");
        // 锁定时间读取
        getConfig().getStringList("time.worlds").forEach(s -> {
            if (s.contains(":")) {
                String[] data = s.split(":");
                String worldName = data[0];
                String timeType = data[1].toUpperCase();
                timeSetWorlds.put(worldName, TimeType.valueOf(timeType));
            }
        });

        // 上线地点读取
        if (spawnPointFile != null && spawnPointFile.exists()) {
            FileConfiguration spawnConfig = YamlConfiguration.loadConfiguration(spawnPointFile);
            String worldName = spawnConfig.getString("spawnPoint.world", "world");
            double x = spawnConfig.getDouble("spawnPoint.x", 0D);
            double y = spawnConfig.getDouble("spawnPoint.y", 60D);
            double z = spawnConfig.getDouble("spawnPoint.z", 0D);
            float yaw = (float) spawnConfig.getDouble("spawnPoint.yaw", 0D);
            float pitch = (float) spawnConfig.getDouble("spawnPoint.pitch", 0D);
            spawnPoint = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
    }

    public List<String> getNoRainWorlds() {
        return noRainWorlds;
    }

    public Map<String, TimeType> getTimeSetWorlds() {
        return timeSetWorlds;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void saveSpawnPoint() {
        FileConfiguration spawnConfig = YamlConfiguration.loadConfiguration(spawnPointFile);
        spawnConfig.set("spawnPoint.world", spawnPoint.getWorld().getName());
        spawnConfig.set("spawnPoint.x", spawnPoint.getX());
        spawnConfig.set("spawnPoint.y", spawnPoint.getY());
        spawnConfig.set("spawnPoint.z", spawnPoint.getZ());
        spawnConfig.set("spawnPoint.yaw", spawnPoint.getYaw());
        spawnConfig.set("spawnPoint.pitch", spawnPoint.getPitch());
        try {
            spawnConfig.save(spawnPointFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
