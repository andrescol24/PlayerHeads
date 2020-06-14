package org.shininet.bukkit.playerheads;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.shininet.bukkit.playerheads.events.FakeBlockBreakEvent;
import org.shininet.bukkit.playerheads.events.MobDropHeadEvent;
import org.shininet.bukkit.playerheads.events.PlayerDropHeadEvent;


/**
 * @author meiskam
 */

public class PlayerHeadsListener implements Listener {

    private final Random prng = new Random();
    private final PlayerHeads plugin;

    public PlayerHeadsListener(PlayerHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    @SuppressWarnings({ "static-access", "deprecation" })
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        double lootingrate = 1;

        if (killer != null) {
            ItemStack weapon = killer.getInventory().getItemInMainHand();
            if (weapon != null) {
                lootingrate = 1 + (Config.getDouble("lootingrate") * weapon.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS));
            }
        }
        
        EntityType entityType = event.getEntityType();
        LivingEntity entity = event.getEntity();
        switch (entityType) {
            case PLAYER:
                Double dropchance = prng.nextDouble();
                Player player = (Player) event.getEntity();
                if ((dropchance >= Config.getDouble("droprate") * lootingrate) && ((killer == null) || !killer.hasPermission("playerheads.alwaysbehead"))) {
                    return;
                }   if (!player.hasPermission("playerheads.canlosehead")) {
                    return;
                }   if (Config.getBoolean("pkonly") && ((killer == null) || (killer == player) || !killer.hasPermission("playerheads.canbehead"))) {
                    return;
                }   String skullOwner;
                if (Config.getBoolean("dropboringplayerheads")) {
                    skullOwner = "";
                } else {
                    skullOwner = player.getName();
                }
                ItemStack drop = Tools.Skull(skullOwner);
                PlayerDropHeadEvent dropHeadEvent = new PlayerDropHeadEvent(player, drop);
                plugin.getServer().getPluginManager().callEvent(dropHeadEvent);
                if (dropHeadEvent.isCancelled()) {
                    return;
                }   if (Config.getBoolean("antideathchest")) {
                    Location location = player.getLocation();
                    location.getWorld().dropItemNaturally(location, drop);
                } else {
                    event.getDrops().add(drop);
                }   if (Config.getBoolean("broadcast")) {
                    String message;
                    if (killer == null) {
                        message = Tools.format(Lang.getString("BEHEAD_GENERIC"), player.getDisplayName() + ChatColor.RESET);
                    } else if (killer == player) {
                        message = Tools.format(Lang.getString("BEHEAD_SELF"), player.getDisplayName() + ChatColor.RESET);
                    } else {
                        message = Tools.format(Lang.getString("BEHEAD_OTHER"), player.getDisplayName() + ChatColor.RESET, killer.getDisplayName() + ChatColor.RESET);
                    }
                    
                    int broadcastRange = Config.getInt("broadcastrange");
                    if (broadcastRange > 0) {
                        broadcastRange *= broadcastRange;
                        Location location = player.getLocation();
                        List<Player> players = player.getWorld().getPlayers();
                        
                        for (Player loopPlayer : players) {
                            if (location.distanceSquared(loopPlayer.getLocation()) <= broadcastRange) {
                                player.sendMessage(message);
                            }
                        }
                    } else {
                        plugin.getServer().broadcastMessage(message);
                    }
                }   break;
            case CREEPER:
                EntityDeathHelper(event, SkullType.CREEPER, Config.getDouble("creeperdroprate") * lootingrate);
                break;
            case ZOMBIE:
                EntityDeathHelper(event, SkullType.ZOMBIE, Config.getDouble("zombiedroprate") * lootingrate);
                break;
            case SKELETON:
                EntityDeathHelper(event, SkullType.SKELETON, Config.getDouble("skeletondroprate") * lootingrate);
                break;
            case WITHER_SKELETON:
                for (Iterator<ItemStack> it = event.getDrops().iterator(); it.hasNext();) {
                    if (it.next().getType() == Material.SKULL_ITEM) {
                        it.remove();
                    }
                }   EntityDeathHelper(event, SkullType.WITHER, Config.getDouble("witherskeletondroprate") * lootingrate);
                break;
            case ENDER_DRAGON:
                EntityDeathHelper(event, SkullType.DRAGON, Config.getDouble("enderdragondroprate") * lootingrate);
                break;
            default:
                try {
                    String entityName = entityType.name();
                    if(entityType == EntityType.PARROT){
                        Parrot parrot = (Parrot)entity;
                        switch(parrot.getVariant()){
                            case RED: 
                                entityName += "_RED";
                                break;
                            case BLUE: 
                                entityName += "_BLUE";
                                break;
                            case GRAY: 
                                entityName += "_GRAY";
                                break;
                            case CYAN: 
                                entityName += "_CYAN";
                                break;
                            case GREEN:
                                entityName += "_GREEN";
                                break;
                        }
                    }
                    CustomSkullType customSkullType = CustomSkullType.valueOf(entityName);
                    EntityDeathHelper(event, customSkullType, Config.getDouble(customSkullType.name().replace("_", "").toLowerCase() + "droprate") * lootingrate);
                } catch (IllegalArgumentException e) {
                }   break;
        }
    }

    public void EntityDeathHelper(EntityDeathEvent event, Enum<?> type, Double droprate) {
        Double dropchance = prng.nextDouble();
        Player killer = event.getEntity().getKiller();

        if ((dropchance >= droprate) && ((killer == null) || !killer.hasPermission("playerheads.alwaysbeheadmob"))) {
            return;
        }
        if (Config.getBoolean("mobpkonly") && ((killer == null) || !killer.hasPermission("playerheads.canbeheadmob"))) {
            return;
        }

        ItemStack drop;

        if (type instanceof SkullType) {
            drop = Tools.Skull((SkullType) type);
        } else if (type instanceof CustomSkullType) {
            drop = Tools.Skull((CustomSkullType) type);
        } else {
            return;
        }

        MobDropHeadEvent dropHeadEvent = new MobDropHeadEvent(event.getEntity(), drop);
        plugin.getServer().getPluginManager().callEvent(dropHeadEvent);

        if (dropHeadEvent.isCancelled()) {
            return;
        }

        if (Config.getBoolean("antideathchest")) {
            Location location = event.getEntity().getLocation();
            location.getWorld().dropItemNaturally(location, drop);
        } else {
            event.getDrops().add(drop);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.OFF_HAND){
            return;
        }
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if (block != null && block.getType() == Material.SKULL) {
                Skull skullState = (Skull) block.getState();
                if (player.hasPermission("playerheads.clickinfo")) {
                    switch (skullState.getSkullType()) {
                    case PLAYER:
                        if (skullState.hasOwner()) {
                            String owner = skullState.getOwner();
                            CustomSkullType skullType = CustomSkullType.get(owner);
                            if(skullType == null){
                                owner = skullState.getOwningPlayer().getUniqueId().toString();
                                skullType = CustomSkullType.get(owner);
                            } if (skullType != null) {
                                Tools.formatMsg(player, Lang.getString("CLICKINFO2"), skullType.getDisplayName());
                                if (!owner.equals(skullType.getOwner())) {
                                    skullState.setOwner(skullType.getOwner());
                                    skullState.update();
                                }
                            } else {
                                Tools.formatMsg(player, Lang.getString("CLICKINFO"), skullState.getOwner());
                            }
                        } else {
                            Tools.formatMsg(player, Lang.getString("CLICKINFO2"), Lang.getString("HEAD"));
                        }
                        break;
                    case CREEPER:
                        Tools.formatMsg(player, Lang.getString("CLICKINFO2"), Tools.format(Lang.getString("HEAD_CREEPER")));
                        break;
                    case SKELETON:
                        Tools.formatMsg(player, Lang.getString("CLICKINFO2"), Tools.format(Lang.getString("HEAD_SKELETON")));
                        break;
                    case WITHER:
                        Tools.formatMsg(player, Lang.getString("CLICKINFO2"), Tools.format(Lang.getString("HEAD_WITHER")));
                        break;
                    case ZOMBIE:
                        Tools.formatMsg(player, Lang.getString("CLICKINFO2"), Tools.format(Lang.getString("HEAD_ZOMBIE")));
                        break;
                    case DRAGON:
                        Tools.formatMsg(player, Lang.getString("CLICKINFO2"), Tools.format(Lang.getString("HEAD_ENDER_DRAGON")));
                        break;
                    }
                } else if ((skullState.getSkullType() == SkullType.PLAYER) && (skullState.hasOwner())) {
                    String owner = skullState.getOwner();
                    CustomSkullType skullType = CustomSkullType.get(owner);
                    
                    if(skullType == null){
                        owner = skullState.getOwningPlayer().getUniqueId().toString();
                        skullType = CustomSkullType.get(owner);
                    } 
                    if ((skullType != null) && (!owner.equals(skullType.getOwner()))) {
                        skullState.setOwner(skullType.getOwner());
                        skullState.update();
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event instanceof FakeBlockBreakEvent) {
            return;
        }
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if ((player.getGameMode() != GameMode.CREATIVE) && (block.getType() == Material.SKULL)) {
            Skull skull = (Skull) block.getState();
            
            if (skull.hasOwner()) {
                CustomSkullType skullType = CustomSkullType.get(skull.getOwner());
                if (skullType == null)
                    skullType = CustomSkullType.get(skull.getOwningPlayer().getUniqueId().toString());
                
                if (skullType != null) {

                    plugin.getServer().getPluginManager().callEvent(new PlayerAnimationEvent(player));
                    plugin.getServer().getPluginManager().callEvent(new BlockDamageEvent(player, block, player.getItemInHand(), true));

                    FakeBlockBreakEvent fakebreak = new FakeBlockBreakEvent(block, player);
                    plugin.getServer().getPluginManager().callEvent(fakebreak);

                    if (fakebreak.isCancelled()) {
                        event.setCancelled(true);
                    } else {
                        Location location = block.getLocation();
                        ItemStack item = Tools.Skull(skullType);

                        event.setCancelled(true);
                        block.setType(Material.AIR);
                        location.getWorld().dropItemNaturally(location, item);
                    }
                }
            }
        }
    }
}
