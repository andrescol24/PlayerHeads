package org.andrescol.playerheads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.andrescol.playerheads.enums.CustomMobHead;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shininet.bukkit.playerheads.Lang;
import org.shininet.bukkit.playerheads.PlayerHeads;

/**
 * Class that saves all mobs heads loaded from heads.yml
 * 
 * @author xX_andrescol_Xx
 * @since 13/09/2018
 */
public final class MobHeadsFactory {

	private static MobHeadsFactory instance;
	private HashMap<String, ItemStack> heads;
	private static Logger logger;

	/**
	 * Constructor
	 * 
	 * @param headsFile yml file containing all mobs heads
	 * @param plugin    plugin
	 */
	private MobHeadsFactory(PlayerHeads plugin) {
		heads = new HashMap<>();
		logger = plugin.getLogger();
		this.load(plugin);
		
	}

	/**
	 * Get Head by spawn name and set the amount to the ItemStack
	 * 
	 * @param spawnName spawn name defined in lang.properties
	 * @param quantity  amount
	 * @return ItemStack or null if it was not found
	 */
	public static ItemStack getHead(String spawnName, int quantity) {
		CustomMobHead customHead = CustomMobHead.getBySpawnName(spawnName);
		if (customHead != null) {
			return getCustomMobHead(customHead, quantity);
		}
		return getMobHead(spawnName, quantity);
	}

	/**
	 * Get a mob head defined in Minecraft game
	 * 
	 * @param spawnName spawn name defined in lang.properties
	 * @param quantity  amount
	 * @return ItemStack or null if the spawnName is incorrect
	 */
	private static ItemStack getMobHead(String spawnName, int quantity) {

		ItemStack itemStack;
		String displayName;
		ItemMeta itemMeta;
		if (spawnName.equals(Lang.getString("HEAD_SPAWN_CREEPER"))) {
			itemStack = new ItemStack(Material.CREEPER_HEAD, quantity);
			displayName = Lang.getString("HEAD_CREEPER");
		} else if (spawnName.equals(Lang.getString("HEAD_SPAWN_ZOMBIE"))) {
			itemStack = new ItemStack(Material.ZOMBIE_HEAD, quantity);
			displayName = Lang.getString("HEAD_ZOMBIE");
		} else if (spawnName.equals(Lang.getString("HEAD_SPAWN_SKELETON"))) {
			itemStack = new ItemStack(Material.SKELETON_SKULL, quantity);
			displayName = Lang.getString("HEAD_SKELETON");
		} else if (spawnName.equals(Lang.getString("HEAD_SPAWN_WITHER_SKELETON"))) {
			itemStack = new ItemStack(Material.WITHER_SKELETON_SKULL, quantity);
			displayName = Lang.getString("HEAD_WITHER_SKELETON");
		} else if (spawnName.equals(Lang.getString("HEAD_SPAWN_ENDER_DRAGON"))) {
			itemStack = new ItemStack(Material.DRAGON_HEAD, quantity);
			displayName = Lang.getString("HEAD_ENDER_DRAGON");
		} else {
			return null;
		}
		itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		return itemStack;
	}

	/**
	 * Get a custom mob head
	 * 
	 * @param type     type of mob head
	 * @param quantity amount
	 * @return ItemStack. It does not return null
	 */
	private static ItemStack getCustomMobHead(CustomMobHead type, int quantity) {
		ItemStack skull = instance.heads.get(type.name());
		skull.setAmount(quantity);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setDisplayName(type.getDisplayName());
		skull.setItemMeta(meta);
		return skull;
	}

	/**
	 * Initialize the instance of class
	 * 
	 * @param plugin plugin
	 */
	public static void init(PlayerHeads plugin) {
		if (instance == null) {
			instance = new MobHeadsFactory(plugin);
		}
	}

	/**
	 * Clear all data off class
	 */
	public static void disable() {
		instance.heads.clear();
		instance.heads = null;
		instance = null;
	}

	/**
	 * Loads heads from heads.yml
	 * 
	 * @param plugin
	 */
	private void load(PlayerHeads plugin) {
		YamlConfiguration headsFile = new YamlConfiguration();
		Logger log = plugin.getLogger();
		Set<String> keys;
		try (InputStream inputFile = MobHeadsFactory.class.getResourceAsStream("/heads.yml");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));) {
			
			headsFile.load(reader);
			keys = headsFile.getKeys(false);
			keys.forEach(key -> {
				ItemStack head = headsFile.getItemStack(key);
				heads.put(key, head);
			});
			log.log(Level.FINE, "Loaded heads from heads.yml");
		} catch (IOException | InvalidConfigurationException e) {
			log.log(Level.SEVERE, "Could not load heads.yml because there has an error", e);
		}
	}
}
