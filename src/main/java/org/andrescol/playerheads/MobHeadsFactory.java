package org.andrescol.playerheads;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.shininet.bukkit.playerheads.PlayerHeads;

/**
 * Class that saves all mobs heads loaded from heads.yml
 * @author xX_andrescol_Xx
 * @since 13/09/2018
 */
public final class MobHeadsFactory {
    
    private static MobHeadsFactory instance; 
    private HashMap<String, ItemStack> heads;
    
    /**
     * Constructor
     * @param headsFile yml file containing all mobs heads
     * @param plugin plugin
     */
    private MobHeadsFactory(File headsFile, PlayerHeads plugin){
        heads = new HashMap<>();
        load(headsFile, plugin);
    }
    
    /**
     * Get a head as ItemStack
     * @param key key of head, is the CustomMobHead enum name
     * @return ItemStack
     */
    public static ItemStack getHead(String key){
        return instance.heads.get(key);
    }
      
    /**
     * Initialize the instance of class
     * @param plugin plugin
     */
    public static void init(PlayerHeads plugin){
        File headsFile = new File(plugin.getDataFolder(), "heads.yml");
        if(instance == null) {
        	instance = new MobHeadsFactory(headsFile, plugin);
        }
    }
    
    /**
     * Clear all data off class
     */
    public static void disable(){
        instance.heads.clear();
        instance.heads = null;
        instance = null;
    }
    
    /**
     * Loads heads from heads.yml
     * @param file file
     * @param plugin
     */
    private void load(File file, PlayerHeads plugin){
        YamlConfiguration headsFile = new YamlConfiguration();
        Logger log = plugin.getLogger();
        Set<String> keys;
        if(!file.exists()) {
        	log.log(Level.SEVERE, "The heads.yml could not be found. This plugin is going to be disabled");
        	Bukkit.getPluginManager().disablePlugin(plugin);
        	return;
        }
        
        try{
            headsFile.load(file);
        }catch(IOException | InvalidConfigurationException e){
        	log.log(Level.SEVERE, "Could not load heads.yml because there has an error", e);
        	return;
        }
        keys = headsFile.getKeys(false);
        keys.forEach( key -> {
            ItemStack head = headsFile.getItemStack(key);
            heads.put(key, head);
        });
        log.log(Level.FINE, "Loaded heads from heads.yml");
    }
}
