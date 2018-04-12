package org.shininet.bukkit.playerheads;

import java.io.File;
import java.util.logging.Level;
import org.andrescol.playerheads.SkullGenerator;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author meiskam
 */
public final class PlayerHeads extends JavaPlugin{

    private static PlayerHeads instance;
    private PlayerHeadsCommandExecutor commandExecutor;
    private PlayerHeadsListener listener;

    @Override
    public void onEnable() {
        instance = this;
        loadFiles();
        Lang.init(this);
        Config.init(this);
        SkullGenerator.init(this);
        listener = new PlayerHeadsListener(this);
        commandExecutor = new PlayerHeadsCommandExecutor(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("PlayerHeads").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        EntityDeathEvent.getHandlerList().unregister(listener);
        PlayerInteractEvent.getHandlerList().unregister(listener);
        BlockBreakEvent.getHandlerList().unregister(listener);
        Lang.disable();
        Config.disable();
        SkullGenerator.disable();
        instance = null;
    }
    
    public void reload(){
        reloadConfig();
        Lang.disable();
        Config.disable();
        Lang.init(this);
        Config.init(this);
    }
        
    public static void log(Level level, String msg){
        instance.getLogger().log(level, msg);
    }
    
    private void loadFiles(){
        getConfig().options().copyDefaults(true);
        File config = new File(getDataFolder(), "config.yml");
        File lang = new File(getDataFolder(), "lang.properties");
        File headsFile = new File(getDataFolder(), "heads.yml");
        try{
            if(!config.exists()){
                saveDefaultConfig();
            }
            if(!headsFile.exists()){
                saveResource("heads.yml", false);
            }
            if(!lang.exists())
                saveResource("lang.properties", false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
