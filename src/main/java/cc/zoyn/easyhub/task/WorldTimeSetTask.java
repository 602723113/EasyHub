package cc.zoyn.easyhub.task;

import cc.zoyn.easyhub.EasyHub;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 世界锁时间任务
 *
 * @author Zoyn
 */
public class WorldTimeSetTask extends BukkitRunnable {

    private boolean start = false;

    @Override
    public void run() {
        EasyHub.getInstance().getTimeSetWorlds().forEach((s, timeType) -> {
            World world = Bukkit.getWorld(s);
            if (world != null) {
                world.setTime(timeType.toTick());
            }
        });
    }

    public void startTask() {
        runTaskTimer(EasyHub.getInstance(), 10L, 10 * 20L);
        start = true;
    }

    public boolean isStart() {
        return start;
    }
}
