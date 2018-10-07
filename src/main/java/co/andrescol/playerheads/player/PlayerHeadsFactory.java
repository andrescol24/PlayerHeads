package co.andrescol.playerheads.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import co.andrescol.playerheads.Lang;
import co.andrescol.playerheads.PlayerHeads;

/**
 * 
 * Factory class that allow get player's heads
 * @author xX_andrescol_Xx
 *
 */
public class PlayerHeadsFactory {

	private static PlayerHeadsFactory instance;
	private PlayerHeads plugin;
	
	private PlayerHeadsFactory(PlayerHeads plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Initializa the class
	 * @param plugin
	 */
	public static void init(PlayerHeads plugin) {
		if(instance == null) {
			instance = new PlayerHeadsFactory(plugin);
		}
	}
	
	/**
	 * Get a head player by the player's mane
	 * @param playerName player name
	 * @param amount amount
	 * @return ItemStack being the player's head
	 */
	public static ItemStack getHead(String playerName, int amount) {
		Player player = instance.plugin.getServer().getPlayer(playerName);
		if(player == null) {
			return null;
		}
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(player);
		meta.setDisplayName(Lang.getMessage("HEAD_PLAYER", playerName));
		skull.setItemMeta(meta);
		
		return skull;
	}
	
	/**
	 * Get a head player
	 * @param playerName player name
	 * @param amount amount
	 * @return ItemStack being the player's head
	 */
	public static ItemStack getHead(Player player, int amount) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(player);
		meta.setDisplayName(Lang.getMessage("HEAD_PLAYER", player.getName()));
		skull.setItemMeta(meta);
		
		return skull;
	}	
}
