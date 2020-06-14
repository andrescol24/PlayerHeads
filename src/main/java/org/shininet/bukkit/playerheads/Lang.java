/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.shininet.bukkit.playerheads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author meiskam
 */

public class Lang {

    private static Properties properties;
    private Lang() {}
    
    public static String getString(String key) {
        return properties.getProperty(key);
    }

    @SuppressWarnings("null")
    public static void init(PlayerHeads plugin) {
        properties = new Properties();
        InputStream input = null;
        File langFile;
        try{
            langFile = new File(plugin.getDataFolder(), "lang.properties");
            input = new FileInputStream(langFile);
            properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                input.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void disable(){
        properties.clear();
        properties = null;
    }
}
