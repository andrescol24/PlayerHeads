package co.andrescol.playerheads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Class 
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
		if (args.length == 0 || !isSubCommand(args[0])) {
			message = (sender.hasPermission("playerheads.commandinfo") || isConsole) 
						? Lang.getInfo() : Lang.getMessageWithPrefix("ERROR_PERMISSION");
		} else if(args[0].equalsIgnoreCase(CMD_RELOAD)) {
			if (sender.hasPermission("playerheads.reload") || isConsole) {
				plugin.reload();
				message = Lang.getMessageWithPrefix("CONFIG_RELOADED");
			} else {
				message = Lang.getMessageWithPrefix("ERROR_PERMISSION");
			}
		} else if(args[0].equalsIgnoreCase(CMD_SPAWN)) {
			if(isConsole) {
				message = Lang.getMessageWithPrefix("ONLY_PLAYERS");
			} else {
				message = executeSpawnCommand((Player) sender, args);
			}
		}
		sender.sendMessage(message);
		return true;
	}
	
	private boolean isSubCommand(String subCommand) {
		return subCommand.equalsIgnoreCase(CMD_RELOAD) || subCommand.equalsIgnoreCase(CMD_SPAWN);
	}

	/**
	 * Execute subcommand spawn for players
	 * 
	 * @param sender sender that executed the command
	 * @param args   arguments
	 * @return message related with spawn command
	 */
	private String executeSpawnCommand(Player sender, String[] args) {

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
				ItemStack itemdded = Tools.addMobHead(
						parameter.getReciever(), parameter.getHeadName(), parameter.getAmount());
				
				if (itemdded != null) {
					String displayName = itemdded.getItemMeta().getDisplayName();
					return Lang.getMessageWithPrefix("HEAD_ADDED", displayName,
							parameter.getReciever().getName());
				} else {
					return Lang.getMessageWithPrefix("HEAD_NOT_ADDED",
							parameter.getReciever().getName());
				}
			} else {
				return "Player head does not suportted";
			}
		}
		return Lang.getMessageWithPrefix("ERROR_PERMISSION");

	}
	
	/**
	 * Class that define the spawn command parameters
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
