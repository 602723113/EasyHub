package cc.zoyn.easyhub.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Zoyn
 */
public class BasicUtils {

    /**
     * 防止意外构建
     */
    private BasicUtils() {
    }


    /**
     * 获取在线玩家
     *
     * @return {@link List}
     */
    public static List<Player> getOnlinePlayers() {
        List<Player> players = Lists.newArrayList();
        Bukkit.getWorlds().forEach(world -> players.addAll(world.getPlayers()));
        return players;
    }

}
