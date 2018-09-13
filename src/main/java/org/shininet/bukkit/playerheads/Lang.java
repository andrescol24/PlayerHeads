package org.shininet.bukkit.playerheads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;


/**
 * Maintainer by xX_andrescol_Xx
 * Static class that manages the language configuration for messaging
 * @author meiskam
 */
public class Lang {

    private static Properties properties;
    private Lang() {}
    
    /**
     * gets a string from properties field
     * @param key property's key
     * @return property's value
     */
    public static String getString(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Initializes the Lang class 
     * @param plugin
     */
    public static void init(PlayerHeads plugin) {
        properties = new Properties();
        File langFile = new File(plugin.getDataFolder(), "lang.properties");
        
        try (InputStream input = new FileInputStream(langFile)){
            properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        }catch(IOException e){
            plugin.getLogger().log(Level.SEVERE, "The lang could not be started", e);
        }
    }
    
    /**
     * Clear all language data
     */
    public static void disable(){
        properties.clear();
        properties = null;
    }
}
