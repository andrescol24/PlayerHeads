package org.shininet.bukkit.playerheads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * Maintainer by xX_andrescol_Xx
 * 
 * @author meiskam
 */
public class PlayerHeadsCommandExecutor implements CommandExecutor, TabCompleter {

	private static final String CMD_RELOAD = "reload";
	private static final String CMD_SPAWN = "spawn";

	private final PlayerHeads plugin;

	/**
	 * Constructor
	 * 
	 * @param plugin
	 */
	public PlayerHeadsCommandExecutor(PlayerHeads plugin) {
		this.plugin = plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.
	 * CommandSender, org.bukkit.command.Command, java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String message = null;
		if (args.length == 0 && sender.hasPermission("playerheads.commandinfo")) {
			message = Tools.getMessage(Lang.getString("COMMAND_INFO"));
			
		} else {
			switch (args[0]) {
			case CMD_RELOAD:
				if (sender.hasPermission("playerheads.reload")) {
					plugin.reload();
					message = Tools.getMessage(Lang.getString("CONFIG_RELOADED"));
				} else {
					message = Tools.getMessage(Lang.getString("ERROR_PERMISSION"));
				}
				break;
			case CMD_SPAWN:
				message = executeSpawn(sender, args);
				break;
			default:
			}	
		}
		sender.sendMessage(message);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("PlayerHeads")) {
			return null;
		}

		ArrayList<String> completions = new ArrayList<>();

		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].toLowerCase();
		}

		final String cmd_config = Tools.formatStrip(Lang.getString("CMD_CONFIG"));
		final String cmd_reload = Tools.formatStrip(Lang.getString("CMD_RELOAD"));
		final String cmd_spawn = Tools.formatStrip(Lang.getString("CMD_SPAWN"));

		if (args.length == 1) {
			if (cmd_config.startsWith(args[0])) {
				completions.add(cmd_config);
			}
			if (cmd_spawn.startsWith(args[0])) {
				completions.add(cmd_spawn);
			}
			return sort(completions);
		}
		if (args[0].equals(cmd_config)) {
			if (args.length == 2) {
				if (cmd_reload.startsWith(args[1])) {
					completions.add(cmd_reload);
				}
				return sort(completions);
			}
			return completions;
		} else if (args[0].equals(cmd_spawn)) {
			if (args.length > 3) {
				return completions;
			}
		}
		return null;
	}
	
	/**
	 * Execute subcommand spawn
	 * @param sender sender that executed the command
	 * @param args arguments
	 * @return message related with spawn command
	 */
	private String executeSpawn(CommandSender sender, String[] args) {
		String skullOwner;
        boolean haspermission;
        Player reciever = null;
        
        if(sender instanceof Player) {
        	
        } else {
        	
        }

		return null;
	}

	public List<String> sort(List<String> completions) {
		Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
		return completions;
	}

}
