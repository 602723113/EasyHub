package top.zoyn.easyhub.command.subcommand;

import org.bukkit.command.CommandSender;
import top.zoyn.easyhub.EasyHub;
import top.zoyn.easyhub.command.SubCommand;
import top.zoyn.easyhub.listener.*;

public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        EasyHub.getInstance().reloadConfig();

        EasyHub.getInstance().loadConfig();
        BlockListener.reloadConfig();
        CreatureListener.reloadConfig();
        DamageListener.reloadConfig();
        DetectPluginListener.reloadConfig();
        ExplosionListener.reloadConfig();
        PlayerJoinLeaveListener.reloadConfig();
        PlayerPlayListener.reloadConfig();

        sender.sendMessage("§6[§eEasyHub§6] §a重新加载成功!");
    }
}
