package org.shininet.bukkit.playerheads;

import java.util.HashMap;
import java.util.Map;

import org.andrescol.playerheads.enums.CustomMobHead;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * 
 * Maintainer by xX_andrescol_Xx
 * Static class that manages the configuration
 * @author meiskam
 */
public class Config {
    
    private static Map<String, Object> configuration;
    
    private Config(){}
    
    /**
     * Get Value as string from loaded configuration
     * @param key key of value
     * @return String
     */
    public static String getValue(String key){
        Object value = configuration.get(key);
        return value.toString();
    }
    
    /**
     * Get value as double from loaded configuration
     * @param key key of value
     * @return Double
     */
    public static double getDouble(String key){
        Object value = configuration.get(key);
        return (double)value;
    }
    
    /**
     * Get value as integer from loaded configuration
     * @param key key of value
     * @return Integer
     */
    public static int getInt(String key){
        Object value = configuration.get(key);
        return (int)value;
    }
    
    /**
     * Get value as boolean from loaded configuration
     * @param key
     * @return
     */
    public static boolean getBoolean(String key){
        Object value = configuration.get(key);
        return (boolean)value;
    }
    
    /**
     * Load configuration from config.yml
     * @param plugin
     */
    private static void loadConfiguration(PlayerHeads plugin){
        
        FileConfiguration configFile = plugin.getConfig();
        configuration.put("pkonly", configFile.getBoolean("pkonly"));
        configuration.put("droprate", configFile.getDouble("droprate"));
        configuration.put("lootingrate", configFile.getDouble("lootingrate"));
        configuration.put("mobpkonly", configFile.getBoolean("mobpkonly"));
        configuration.put("creeperdroprate", configFile.getDouble("creeperdroprate"));
        configuration.put("zombiedroprate", configFile.getDouble("zombiedroprate"));
        configuration.put("skeletondroprate", configFile.getDouble("skeletondroprate"));
        configuration.put("witherskeletondroprate", configFile.getDouble("witherskeletondroprate"));
        configuration.put("enderdragondroprate", configFile.getDouble("enderdragondroprate"));
        for (CustomMobHead customSkullType : CustomMobHead.values()) {
            String custonSkullTypeName = customSkullType.name().replace("_", "").toLowerCase() + "droprate";
            configuration.put(custonSkullTypeName, configFile.getDouble(custonSkullTypeName));
        }
        configuration.put("broadcast", configFile.getBoolean("broadcast"));
        configuration.put("broadcastrange", configFile.getInt("broadcastrange"));
        configuration.put("antideathchest", configFile.getBoolean("antideathchest"));
        configuration.put("dropboringplayerheads", configFile.getBoolean("dropboringplayerheads"));
    }
     
    /**
     * Initializes plugin configuration
     * @param plugin
     */
    public static void init(PlayerHeads plugin){
    	configuration = new HashMap<>();
        loadConfiguration(plugin);
    }
    
    /**
     * Clear all configuration data loaded
     */
    public static void disable(){
        configuration.clear();
        configuration = null;
    }
}
