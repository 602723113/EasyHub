package cc.zoyn.easyhub.task;

import cc.zoyn.easyhub.EasyHub;
import cc.zoyn.easyhub.util.BasicUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 玩家移动检查任务
 *
 * @author Zoyn
 */
public class PlayerMoveCheckTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : BasicUtils.getOnlinePlayers()) {
            if (player == null || !player.isOnline()) {
                return;
            }
            Location location = player.getLocation();
            Block block = location.getBlock();

            // 虚空掉落检查
            if (block.getRelative(BlockFace.DOWN).getType().isSolid() || location.getY() <= 0.0D) {
                player.teleport(EasyHub.getInstance().getSpawnPoint());
            }

        }
    }

    public void startTask() {
        runTaskTimer(EasyHub.getInstance(), 10L, 20L);
    }
}
