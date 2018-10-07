package co.andrescol.playerheads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import co.andrescol.playerheads.mobs.MobHeadsFactory;
import co.andrescol.playerheads.player.PlayerHeadsFactory;

/**
 * Class
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
		if (args.length == 0 || !isSubCommand(args[0])) {
			message = (sender.hasPermission("playerheads.commandinfo") || isConsole) ? Lang.getInfo()
					: Lang.getMessageWithPrefix("ERROR_PERMISSION");
		} else if (args[0].equalsIgnoreCase(CMD_RELOAD)) {
			if (sender.hasPermission("playerheads.reload") || isConsole) {
				plugin.reload();
				message = Lang.getMessageWithPrefix("CONFIG_RELOADED");
			} else {
				message = Lang.getMessageWithPrefix("ERROR_PERMISSION");
			}
		} else if (args[0].equalsIgnoreCase(CMD_SPAWN)) {
			if (isConsole) {
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
		ItemStack itemToAdd = null;
		Player receiver;
		try {
			// Validate if exists a receiver
			if (parameter.getReciever() == null) {
				return Lang.getMessageWithPrefix("PLAYER_NOT_FOUND", parameter.getReceiverName());
			}
			// Validate permission needed
			if (!parameter.isSenderHasPermission()) {
				return Lang.getMessageWithPrefix("ERROR_PERMISSION");
			}
			// Get the first empty element in the receiver inventory
			receiver = parameter.getReciever();
			PlayerInventory inventory = receiver.getInventory();
			int firstEmpty = inventory.firstEmpty();

			// Check if the inventory is full
			if (firstEmpty == -1) {
				return Lang.getMessageWithPrefix("FULL_INVENTORY", receiver.getName());
			}

			// Get the head 
			if (parameter.isSelfSpawn()) {
				itemToAdd = PlayerHeadsFactory.getHead(sender, parameter.getAmount());
			} else {
				if (parameter.isMobHeadSpawn()) {
					itemToAdd = MobHeadsFactory.getHead(parameter.getHeadName(), parameter.getAmount());
					
				} else {
					itemToAdd = PlayerHeadsFactory.getHead(parameter.getHeadName(), parameter.getAmount());
				}
			}

			// Validate and add the head
			if (itemToAdd != null) {
				inventory.addItem(itemToAdd);
				String displayName = itemToAdd.getItemMeta().getDisplayName();
				if (parameter.getReciever() == sender) {
					return Lang.getMessageWithPrefix("HEAD_ADDED_OWN_INVENTORY", displayName);
				}
				return Lang.getMessageWithPrefix("HEAD_ADDED", displayName, parameter.getReciever().getName());
			} else {
				if (parameter.isMobHeadSpawn()) {
					return Lang.getMessageWithPrefix("MOB_HEAD_NOT_FOUND", parameter.getHeadName());
				}
				return Lang.getMessageWithPrefix("PLAYER_HEAD_NOT_FOUND", parameter.getHeadName());
			}
		} catch (Exception e) {
			return Lang.getMessageWithPrefix("CHECK_YOUR_COMMAND");
		}
	}

	/**
	 * Class that define the spawn command parameters
	 */
	private class SpawnParameter {

		private String headName;
		private boolean selfSpawn;
		private int amount;
		private Player reciever;
		private boolean mobHeadSpawn;
		private String receiverName;
		private boolean senderHasPermission;

		/**
		 * Constructor that initialize the instance properties based on command sender
		 * and arguments
		 * 
		 * @param sender player sender
		 * @param args   arguments
		 */
		public SpawnParameter(Player sender, String[] args) {

			switch (args.length) {
			case 1:
				this.headName = sender.getName();
				this.reciever = sender;
				this.amount = 1;
				break;
			case 2:
				this.headName = args[1];
				this.reciever = sender;
				this.amount = 1;
				break;
			case 3:
			case 4:
			default:
				this.headName = args[1];
				this.receiverName = args[2];
				this.reciever = plugin.getServer().getPlayer(receiverName);
				this.amount = (args.length == 4) ? Integer.parseInt(args[3]) : 1;
				break;
			}
			if (this.reciever == sender && this.headName.equals(sender.getName())) {
				this.selfSpawn = true;
			} else if (this.headName.startsWith("#")) {
				this.mobHeadSpawn = true;
			}
			this.senderHasPermission = verifySenderPermission(sender);
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

		public boolean isMobHeadSpawn() {
			return mobHeadSpawn;
		}

		public String getReceiverName() {
			return receiverName;
		}

		public boolean isSenderHasPermission() {
			return senderHasPermission;
		}

		private boolean verifySenderPermission(Player sender) {
			if (this.selfSpawn) {
				return sender.hasPermission("playerheads.spawn.own");
			}
			if (sender != this.reciever) {
				return sender.hasPermission("playerheads.spawn.forother");
			} else {
				return sender.hasPermission("playerheads.spawn");
			}
		}

	}
}
