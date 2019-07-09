package cc.zoyn.easyhub.command.subcommand;

import cc.zoyn.easyhub.command.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        sender.sendMessage("§e§l§m一一一一§r §7[ §6EasyHub §7] §e§l§m一一一一");
        sender.sendMessage("§b/eh §7查看帮助");
        sender.sendMessage("§b/eh setspawn §7设置出生点");
        sender.sendMessage("§b/eh reload §7重载插件");
    }
}
