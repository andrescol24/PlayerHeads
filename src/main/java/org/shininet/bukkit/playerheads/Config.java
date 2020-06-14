package org.shininet.bukkit.playerheads;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;


public class Config {
    
    private static Config instance;
    private Map<String, Object> config;
    public static final int defaultStackSize = 1;
    
    private Config(PlayerHeads plugin){
        config = new HashMap<>();
        loadConfiguration(plugin);
    }
    
    public static String getValue(String key){
        Object value = instance.config.get(key);
        return value.toString();
    }
    
    public static double getDouble(String key){
        Object value = instance.config.get(key);
        return (double)value;
    }
    
    public static int getInt(String key){
        Object value = instance.config.get(key);
        return (int)value;
    }
    
    public static boolean getBoolean(String key){
        Object value = instance.config.get(key);
        return (boolean)value;
    }
    
    private void loadConfiguration(PlayerHeads plugin){
        
        FileConfiguration configFile = plugin.getConfig();
        config.put("pkonly", configFile.getBoolean("pkonly"));
        config.put("droprate", configFile.getDouble("droprate"));
        config.put("lootingrate", configFile.getDouble("lootingrate"));
        config.put("mobpkonly", configFile.getBoolean("mobpkonly"));
        config.put("creeperdroprate", configFile.getDouble("creeperdroprate"));
        config.put("zombiedroprate", configFile.getDouble("zombiedroprate"));
        config.put("skeletondroprate", configFile.getDouble("skeletondroprate"));
        config.put("witherskeletondroprate", configFile.getDouble("witherskeletondroprate"));
        config.put("enderdragondroprate", configFile.getDouble("enderdragondroprate"));
        for (CustomSkullType customSkullType : CustomSkullType.values()) {
            String custonSkullTypeName = customSkullType.name().replace("_", "").toLowerCase() + "droprate";
            config.put(custonSkullTypeName, configFile.getDouble(custonSkullTypeName));
        }
        config.put("broadcast", configFile.getBoolean("broadcast"));
        config.put("broadcastrange", configFile.getInt("broadcastrange"));
        config.put("antideathchest", configFile.getBoolean("antideathchest"));
        config.put("dropboringplayerheads", configFile.getBoolean("dropboringplayerheads"));
    }
      
    public static void init(PlayerHeads plugin){
        if(instance == null) instance = new Config(plugin);
    }
    
    public static void disable(){
        instance.config.clear();
        instance.config = null;
        instance = null;
    }
}
