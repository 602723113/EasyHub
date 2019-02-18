package cc.zoyn.easyhub;

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
