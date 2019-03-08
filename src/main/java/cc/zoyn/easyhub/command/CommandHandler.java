package cc.zoyn.easyhub.command;

import cc.zoyn.easyhub.command.subcommand.HelpCommand;
import cc.zoyn.easyhub.command.subcommand.ReloadCommand;
import cc.zoyn.easyhub.command.subcommand.SetSpawnCommand;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private static Map<String, SubCommand> subCommandMap = Maps.newHashMap();

    public CommandHandler() {
        registerCommand("setspawn", new SetSpawnCommand());
        registerCommand("help", new HelpCommand());
        registerCommand("reload", new ReloadCommand());
    }

    private static void registerCommand(String subCommandName, SubCommand subCommand) {
        if (subCommandMap.containsKey(subCommandName)) {
            Bukkit.getConsoleSender().sendMessage("§6[§eEasyHub§6] §f发现重复注册指令!");
        }
        subCommandMap.put(subCommandName, subCommand);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            subCommandMap.get("help").execute(sender, args);
            return true;
        }
        if (!subCommandMap.containsKey(args[0])) {
            sender.sendMessage("§6[§eEasyHub§6] §c未知指令!");
            return true;
        }

        // args[0] 意为子命令
        SubCommand subCommand = subCommandMap.get(args[0]);
        subCommand.execute(sender, args);
        return true;
    }
}
