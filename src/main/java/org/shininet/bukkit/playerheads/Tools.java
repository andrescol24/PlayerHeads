package org.shininet.bukkit.playerheads;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import co.andrescol.playerheads.MobHeadsFactory;

/**
 * @author xX_andrescol_Xx
 */
public abstract class Tools {

	private Tools() {}
	
	/**
	 * Add MobHead to Player's inventory
	 * @param player player
	 * @param headSpawnName head spawn name
	 * @param quantity amount
	 * @return An ItemStack that was added to the player's inventory
	 */
    public static ItemStack addMobHead(Player player, String headSpawnName, int quantity) {
        PlayerInventory inv = player.getInventory();
        int firstEmpty = inv.firstEmpty();
        if (firstEmpty == -1) {
            return null;
        } else {
        	ItemStack head = MobHeadsFactory.getHead(headSpawnName, quantity);
            inv.setItem(firstEmpty, head);
            return head;
        }
    }
}
