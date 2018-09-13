package org.shininet.bukkit.playerheads;

import java.util.Set;
import org.andrescol.playerheads.MobHeadsFactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author meiskam
 */
public abstract class Tools {

    public static boolean addHead(Player player, String skullOwner) {
        return addHead(player, skullOwner, Config.defaultStackSize);
    }

    public static boolean addHead(Player player, String skullOwner, int quantity) {
        PlayerInventory inv = player.getInventory();
        int firstEmpty = inv.firstEmpty();
        if (firstEmpty == -1) {
            return false;
        } else {
            inv.setItem(firstEmpty, Tools.Skull(skullOwner, quantity));
            return true;
        }
    }

    public static ItemStack Skull(String skullOwner) {
        return Skull(skullOwner, Config.defaultStackSize);
    }

    public static ItemStack Skull(String skullOwner, int quantity) {
        String skullOwnerLC = skullOwner.toLowerCase();

        for (CustomMobHead skullType : CustomMobHead.values()) {
            if (skullOwnerLC.equals(skullType.getSpawnName().toLowerCase())) {
                return Skull(skullType, quantity);
            }
        }

        if (skullOwnerLC.equals(Lang.getString("HEAD_SPAWN_CREEPER"))) {
            return Skull(SkullType.CREEPER, quantity);
        } else if (skullOwnerLC.equals(Lang.getString("HEAD_SPAWN_ZOMBIE"))) {
            return Skull(SkullType.ZOMBIE, quantity);
        } else if (skullOwnerLC.equals(Lang.getString("HEAD_SPAWN_SKELETON"))) {
            return Skull(SkullType.SKELETON, quantity);
        } else if (skullOwnerLC.equals(Lang.getString("HEAD_SPAWN_WITHER_SKELETON"))) {
            return Skull(SkullType.WITHER, quantity);
        } else if (skullOwnerLC.equals(Lang.getString("HEAD_SPAWN_ENDER_DRAGON"))) {
            return Skull(SkullType.DRAGON, quantity);
        } else {
            return Skull(skullOwner, null, quantity);
        }
    }

    public static ItemStack Skull(String skullOwner, String displayName) {
        return Skull(skullOwner, displayName, Config.defaultStackSize);
    }

    public static ItemStack Skull(String skullOwner, String displayName, int quantity) {
        
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, quantity, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        boolean shouldSet = false;
        if ((skullOwner != null) && (!skullOwner.equals(""))) {
        	skullMeta.setOwner(skullOwner);
            shouldSet = true;
        }
        if (displayName != null) {
            skullMeta.setDisplayName(ChatColor.RESET + displayName);
            shouldSet = true;
        }
        if (shouldSet) {
            skull.setItemMeta(skullMeta);
        }
        return skull;
    }

    public static ItemStack Skull(CustomMobHead type) {
        return Skull(type, Config.defaultStackSize);
    }

    public static ItemStack Skull(CustomMobHead type, int quantity) {
        if(MobHeadsFactory.isAvalaible()){
            ItemStack skull = MobHeadsFactory.getHead(type.name());
            skull.setAmount(quantity);
            SkullMeta meta = (SkullMeta)skull.getItemMeta();
            meta.setDisplayName(type.getDisplayName());
            skull.setItemMeta(meta);
            return skull;
        }
        return Skull(type.getOwner(), type.getDisplayName(), quantity);
    }

    public static ItemStack Skull(SkullType type) {
        return Skull(type, Config.defaultStackSize);
    }

    public static ItemStack Skull(SkullType type, int quantity) {
        return new ItemStack(Material.SKULL_ITEM, quantity, (short) type.ordinal());
    }

    public static String format(String text, String... replacement) {
        String output = text;
        for (int i = 0; i < replacement.length; i++) {
            output = output.replace("%" + (i + 1) + "%", replacement[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', output);
    }

    public static void formatMsg(CommandSender player, String text, String... replacement) {
        player.sendMessage(format(text, replacement));
    }

    public static String formatStrip(String text, String... replacement) {
        return ChatColor.stripColor(format(text, replacement));
    }

}
