package co.andrescol.playerheads.mobs;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import co.andrescol.playerheads.PlayerHeads;

/**
 * Class that saves all mobs heads loaded from heads.yml
 * 
 * @author xX_andrescol_Xx
 * @since 13/09/2018
 */
public final class MobHeadsFactory {

	private static MobHeadsFactory instance;
	private HashMap<String, ItemStack> heads;

	/**
	 * Constructor
	 * 
	 * @param headsFile yml file containing all mobs heads
	 * @param plugin    plugin
	 */
	private MobHeadsFactory(PlayerHeads plugin) {
		heads = new HashMap<>();
		this.load(plugin);
	}

	/**
	 * Loads heads from heads.yml
	 * 
	 * @param plugin
	 */
	private void load(PlayerHeads plugin) {
		Logger logger = plugin.getLogger();
		YamlConfiguration headsFile = new YamlConfiguration();
		try {
			File file = new File(plugin.getDataFolder(), "heads.yml");
			headsFile.load(file);
			
			for(CustomMobHead mobHead : CustomMobHead.values()) {
				ItemStack head = headsFile.getItemStack(mobHead.name());
				SkullMeta meta = (SkullMeta) head.getItemMeta();
				meta.setDisplayName(mobHead.getDisplayName());
				head.setItemMeta(meta);
				
				heads.put(mobHead.getOwner(), head);
				heads.put(mobHead.getSpawnCommand(), head);
			}
			
			for(MobHead mobHead: MobHead.values()) {
				Material material = mobHead.getMeterial();
				ItemStack item = new ItemStack(material);
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				meta.setDisplayName(mobHead.getDisplayName());
				item.setItemMeta(meta);
				heads.put(mobHead.getSpawnCommand(), item);
			}
			logger.log(Level.FINE, "Loaded heads from heads.yml");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not load heads.yml because there is an error", e);
		}
	}

	/**
	 * Get Head by spawn or owner name and set the amount and display name
	 * configured in lang.properties to the ItemStack
	 * 
	 * @param identifier spawn name or owner name
	 * @param quantity  amount
	 * @return ItemStack or null if it was not found
	 */
	public static ItemStack getHead(String identifier, int quantity) {
		ItemStack skull = instance.heads.get(identifier);
		if(skull == null) return null;
		skull.setAmount(quantity);
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

}
