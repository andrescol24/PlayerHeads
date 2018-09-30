package org.shininet.bukkit.playerheads;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import co.andrescol.playerheads.MobHeadsFactory;

/**
 * maintainer by xX_andrescol_Xx
 * Main plugin class that start all features
 * @author meiskam
 */
public final class PlayerHeads extends JavaPlugin{

    private PlayerHeadsCommandExecutor commandExecutor;
    private PlayerHeadsListener listener;

    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        saveReourceFiles();
        Lang.init(this);
        Config.init(this);
        MobHeadsFactory.init(this);
        listener = new PlayerHeadsListener(this);
        commandExecutor = new PlayerHeadsCommandExecutor(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("PlayerHeads").setExecutor(commandExecutor);
    }

    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {
        EntityDeathEvent.getHandlerList().unregister(listener);
        PlayerInteractEvent.getHandlerList().unregister(listener);
        BlockBreakEvent.getHandlerList().unregister(listener);
        Lang.disable();
        Config.disable();
        MobHeadsFactory.disable();
    }
    
    /**
     * Reload all plugin data
     */
    public void reload(){
    	// Disables
        Lang.disable();
        Config.disable();
        MobHeadsFactory.disable();
        
        // Enables
        saveReourceFiles();
        reloadConfig();
        Lang.init(this);
        Config.init(this);
        MobHeadsFactory.init(this);
    }
    
    /**
     * Save files on plugin server folder
     */
    private void saveReourceFiles(){
        getConfig().options().copyDefaults(true);
        File config = new File(getDataFolder(), "config.yml");
        File lang = new File(getDataFolder(), "lang.properties");
        try{
            if(!config.exists()){
                saveDefaultConfig();
            }
            if(!lang.exists())
                saveResource("lang.properties", false);
        }catch(Exception e){
            this.getLogger().log(Level.SEVERE, "Could not be saved plugin files", e);
        }
    }
}
