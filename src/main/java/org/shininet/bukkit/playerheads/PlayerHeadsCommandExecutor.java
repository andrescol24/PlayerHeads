package org.shininet.bukkit.playerheads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author xX_andrescol_Xx
 */
public class PlayerHeadsCommandExecutor implements CommandExecutor {

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
		boolean isConsole = !(sender instanceof Player);
		String message = null;
		if (args.length == 0) {
			if (sender.hasPermission("playerheads.commandinfo") || isConsole) {
				message = Lang.getInfo();
			} else {
				message = Lang.getMessageWithPrefix("ERROR_PERMISSION");
			}
		} else {
			switch (args[0].toLowerCase()) {
			case CMD_RELOAD:
				if (sender.hasPermission("playerheads.reload") || isConsole) {
					plugin.reload();
					message = Lang.getMessageWithPrefix("CONFIG_RELOADED");
				} else {
					message = Lang.getMessageWithPrefix("ERROR_PERMISSION");
				}
				break;
			case CMD_SPAWN:
				if (sender instanceof Player) {
					message = playerSpawnCommand((Player) sender, args);
				} else {
					message = consoleSpawnCommand(args);
				}
				break;
			default:
				if (sender.hasPermission("playerheads.commandinfo") || isConsole) {
					message = Lang.getInfo();
				} else {
					message = Lang.getMessageWithPrefix("ERROR_PERMISSION");
				}
			}
		}
		sender.sendMessage(message);
		return true;
	}

	/**
	 * Execute subcommand spawn for players
	 * 
	 * @param sender sender that executed the command
	 * @param args   arguments
	 * @return message related with spawn command
	 */
	private String playerSpawnCommand(Player sender, String[] args) {

		SpawnParameter parameter = new SpawnParameter(sender, args);
		if (parameter.isSelfSpawn()) {
			if (sender.hasPermission("playerheads.spawn.own")) {
				return "Player head does not suported";
			} else {
				return Lang.getMessageWithPrefix("ERROR_PERMISSION");
			}

		} else if ((parameter.getReciever() == sender && sender.hasPermission("playerheads.spawn"))
				|| parameter.getReciever().hasPermission("playerheads.spawn.forother")) {
			if (parameter.getHeadName().startsWith("#")) {
				boolean added = Tools.addMobHead(
						parameter.getReciever(), parameter.getHeadName(), parameter.getAmount());
				
				if (added) {
					return Lang.getMessageWithPrefix("HEAD_ADDED", parameter.getReciever().getName());
				} else {
					return Lang.getMessageWithPrefix("HEAD_NOT_ADDED");
				}
			} else {
				return "Player head does not suportted";
			}
		}
		return Lang.getMessageWithPrefix("ERROR_PERMISSION");

	}
	
	/**
	 * Execute subcommand spawn for console
	 * 
	 * @param args arguments
	 * @return message related with spawn command
	 */
	private String consoleSpawnCommand(String[] args) {

		Player reciever = null;
		// it needs receiver and head arguments
		if (args.length < 3) {
			return Lang.getMessageWithPrefix("ERROR_CONSOLE_SPAWN", Lang.getString("OPT_HEADNAME_REQUIRED"),
					Lang.getString("OPT_RECEIVER_REQUIRED"), Lang.getString("OPT_AMOUNT_OPTIONAL"));
		} else {
			reciever = plugin.getServer().getPlayer(args[2]);
			if (reciever == null) {
				return Lang.getMessageWithPrefix("ERROR_NOT_ONLINE", args[2]);
			}
			int quantity = (args.length == 4) ? Integer.parseInt(args[3]) : 1;
			String headName = args[1];
			if (headName.startsWith("#")) {
				boolean added = Tools.addMobHead(reciever, headName, quantity);
				if (added) {
					return Lang.getMessageWithPrefix("HEAD_ADDED", headName, reciever.getName());
				} else {
					return Lang.getMessageWithPrefix("HEAD_NOT_ADDED", headName, reciever.getName());
				}
			} else {
				return "Player head does not suportted";
			}
		}
	}
	
	/**
	 * Class that define the spawn command parameters
	 * @author AndresFernando
	 *
	 */
	private class SpawnParameter {
		
		private String headName;
		private boolean selfSpawn;
		private int amount;
		private Player reciever;
		
		/**
		 * Constructor that initialize the instance properties based on command sender and arguments
		 * @param sender player sender
		 * @param args arguments
		 */
		public SpawnParameter(Player sender, String[] args) {
			
			Player recieverAux = null;
			boolean selfSpawnAux = false;
			int amountAux = 1;
			String headNameAux = null;
			
			switch (args.length) {
			case 1:
				selfSpawnAux = true;
				break;
			case 2:
				headNameAux = args[1];
				if (headNameAux.equalsIgnoreCase(sender.getName()))
					selfSpawnAux = true;
				else
					recieverAux = sender;
				break;
			case 3:
			case 4:
				headNameAux = args[1];
				String recieverName = args[2];
				recieverAux = (recieverName.equalsIgnoreCase(sender.getName())) ? sender
						: plugin.getServer().getPlayer(recieverName);
				amountAux = (args.length == 4) ? Integer.parseInt(args[3]) : 1;
				break;
			default:
			}
			this.reciever = recieverAux;
			this.selfSpawn = selfSpawnAux;
			this.headName = headNameAux;
			this.amount = amountAux;
			
		}
		
		public String getHeadName() {
			return headName;
		}
		public boolean isSelfSpawn() {
			return selfSpawn;
		}
		public int getAmount() {
			return amount;
		}
		public Player getReciever() {
			return reciever;
		}		
	}
}
