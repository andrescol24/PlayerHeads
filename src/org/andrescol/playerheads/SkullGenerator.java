package org.andrescol.playerheads;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.shininet.bukkit.playerheads.PlayerHeads;

/**
 *
 * @author xX_andrescol_Xx
 */
public final class SkullGenerator {
    
    private static SkullGenerator instance; 
    private HashMap<String, ItemStack> skulls;  
    private final boolean avalaible;
    
    private SkullGenerator(File headsFile){
        skulls = new HashMap<>();
        avalaible = load(headsFile);
    }
    
    public static ItemStack getHead(String key){
        return instance.skulls.get(key);
    }
    
    public static boolean isAvalaible(){
        return instance.avalaible;
    }
    
    public static void init(PlayerHeads plugin){
        File headsFile = new File(plugin.getDataFolder(), "heads.yml");
        if(instance== null)instance = new SkullGenerator(headsFile);
    }
    
    public static void disable(){
        instance.skulls.clear();
        instance.skulls = null;
        instance = null;
    }
    
    private boolean load(File file){
        YamlConfiguration config = new YamlConfiguration();
        Set<String> keys;
        if(!file.exists())
            return false;
        try{
            config.load(file);
        }catch(IOException | InvalidConfigurationException e){
            PlayerHeads.log(Level.SEVERE, e.getMessage());
            return false;
        }
        keys = config.getKeys(false);
        keys.forEach((key) -> {
            ItemStack head = config.getItemStack(key);
            skulls.put(key, head);
        });
        PlayerHeads.log(Level.INFO, "Loaded heads from heads.yml");
        return true;
    }
}
