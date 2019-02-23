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
        sender.sendMessage("=== [EasyHub] ===");
        sender.sendMessage("§e/eh setspawn §7设置出生点");
    }
}
