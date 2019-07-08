package cc.zoyn.easyhub.command.subcommand;

import cc.zoyn.easyhub.EasyHub;
import cc.zoyn.easyhub.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c你必须是一名玩家才能使用该指令!");
            return;
        }
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        Player player = (Player) sender;
        EasyHub.getInstance().setSpawnPoint(player.getLocation());
        EasyHub.getInstance().saveSpawnPoint();
        player.sendMessage("§6[§eEasyHub§6] §a出生点设置成功!");
    }
}
