package cc.zoyn.easyhub.command.subcommand;

import cc.zoyn.easyhub.EasyHub;
import cc.zoyn.easyhub.command.SubCommand;
import cc.zoyn.easyhub.listener.*;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        EasyHub.getInstance().reloadConfig();

        EasyHub.getInstance().loadConfig();
        PlayerJoinListener.reloadConfig();
        PlayerPlayListener.reloadConfig();
        DetectPluginListener.reloadConfig();
        DamageListener.reloadConfig();
        ExplosionListener.reloadConfig();
        BlockListener.reloadConfig();

        sender.sendMessage("§6[§eEasyHub§6] §a重新加载成功!");
    }
}
