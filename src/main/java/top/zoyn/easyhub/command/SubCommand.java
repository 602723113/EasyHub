package top.zoyn.easyhub.command;

import org.bukkit.command.CommandSender;

/**
 * 副指令接口
 *
 * @author Zoyn
 */
public interface SubCommand {

    void execute(CommandSender sender, String[] args);

}
