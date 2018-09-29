package org.shininet.bukkit.playerheads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.ChatColor;


/**
 * Static class that manages the language configuration for messaging
 * @author xX_andrescol_Xx
 */
public final class Lang {

    private static Properties properties;
    private static String PREFIX;
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
            String prefix = properties.getProperty("PREFIX");
            PREFIX = ChatColor.translateAlternateColorCodes('&', prefix);
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
    
    /**
     * Get the info plugin command
     * @return Info plugin command
     */
    public static String getInfo() {
    	String title = getString("COMMAND_INFO_TITLE");
    	String spawn = getMessage("COMMAND_INFO_SPAWN", 
    			getString("OPT_HEADNAME_OPTIONAL"),
    			getString("OPT_RECEIVER_OPTIONAL"),
    			getString("OPT_AMOUNT_OPTIONAL"));
    	String reload = getString("COMMAND_INFO_RELOAD");
    	StringBuilder builder = new StringBuilder();
    	builder.append("\n").append(title).append("\n").append(spawn).append("\n").append(reload);
    	return ChatColor.translateAlternateColorCodes('&', builder.toString());
    }
    
    /**
     * Gets message replacing the string format %#% by values given and adding color
     * @param key key of message
     * @param replacement values to replace
     * @return message
     */
    public static String getMessage(String key, String... replacement) {
        String output = getString(key);
        for (int i = 0; i < replacement.length; i++) {
            output = output.replace("%" + (i + 1) + "%", replacement[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', output);
    }
    
    /**
     * Gets message replacing the string format %#% by values given and adding color and the plugin 
     * prefix
     * @param key key of message
     * @param replacement values to replace
     * @return message
     */
    public static String getMessageWithPrefix(String key, String... replacement) {
    	String message = getMessage(key, replacement);
    	return PREFIX.concat(message);
    }
}
