package co.andrescol.playerheads;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import co.andrescol.playerheads.mobs.CustomMobHead;

/**
 * 
 * Static class that manages the configuration
 * @author xX_andrescol_Xx
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
        configuration.put("droprate", configFile.getDouble("droprate"));
        configuration.put("lootingrate", configFile.getDouble("lootingrate"));
        configuration.put("language", configFile.getString("language")); 
        configuration.put("creeperdroprate", configFile.getDouble("creeperdroprate"));
        configuration.put("zombiedroprate", configFile.getDouble("zombiedroprate"));
        configuration.put("skeletondroprate", configFile.getDouble("skeletondroprate"));
        configuration.put("witherskeletondroprate", configFile.getDouble("witherskeletondroprate"));
        configuration.put("enderdragondroprate", configFile.getDouble("enderdragondroprate"));
        for (CustomMobHead customSkullType : CustomMobHead.values()) {
            String custonSkullTypeName = customSkullType.name().replace("_", "").toLowerCase() + "droprate";
            configuration.put(custonSkullTypeName, configFile.getDouble(custonSkullTypeName));
        }
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
