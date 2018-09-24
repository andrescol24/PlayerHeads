package org.shininet.bukkit.playerheads;

import org.andrescol.playerheads.MobHeadsFactory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author meiskam
 */
public abstract class Tools {

	/**
	 * Add MobHead to Player's inventory
	 * @param player player
	 * @param headSpawnName head spawn name
	 * @param quantity amount
	 * @return true if the item was added or false if else
	 */
    public static boolean addMobHead(Player player, String headSpawnName, int quantity) {
        PlayerInventory inv = player.getInventory();
        int firstEmpty = inv.firstEmpty();
        if (firstEmpty == -1) {
            return false;
        } else {
            inv.setItem(firstEmpty, MobHeadsFactory.getHead(headSpawnName, quantity));
            return true;
        }
    }

    /**
     * Gets message replacing the string format %#% by values given and adding color
     * @param text text to convert in message
     * @param replacement values to replace
     * @return message
     */
    public static String getMessage(String text, String... replacement) {
        String output = text;
        for (int i = 0; i < replacement.length; i++) {
            output = output.replace("%" + (i + 1) + "%", replacement[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', output);
    }
}
