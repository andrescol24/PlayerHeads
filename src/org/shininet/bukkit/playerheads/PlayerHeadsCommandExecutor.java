/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.shininet.bukkit.playerheads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * @author meiskam
 */

public class PlayerHeadsCommandExecutor implements CommandExecutor, TabCompleter {

    private final PlayerHeads plugin;

    public PlayerHeadsCommandExecutor(PlayerHeads plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("PlayerHeads")) {
            return false;
        }
        if (args.length == 0) {
            Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("SUBCOMMANDS") + Lang.getString("COLON_SPACE") + Lang.getString("CMD_CONFIG") + Lang.getString("COMMA_SPACE") + Lang.getString("CMD_SPAWN"));
            return true;
        }
        if (args[0].equalsIgnoreCase(Tools.formatStrip(Lang.getString("CMD_CONFIG")))) {
            if (args.length == 1) {
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_CONFIG") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("SUBCOMMANDS") + Lang.getString("COLON_SPACE") + Lang.getString("CMD_RELOAD"));
                return true;
            }
            if (args[1].equalsIgnoreCase(Tools.formatStrip(Lang.getString("CMD_RELOAD")))) {
                if (!sender.hasPermission("playerheads.config.reload")) {
                    Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_CONFIG") + Lang.getString("COLON") + Lang.getString("CMD_RELOAD") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("ERROR_PERMISSION"));
                    return true;
                }
                plugin.reload();
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_CONFIG") + Lang.getString("COLON") + Lang.getString("CMD_RELOAD") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("CONFIG_RELOADED"));
                return true;
            } else {
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_CONFIG") + Lang.getString("COLON") + Lang.getString("CMD_UNKNOWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("ERROR_INVALID_SUBCOMMAND"));
                return true;
            }
        } else if (args[0].equalsIgnoreCase(Tools.formatStrip(Lang.getString("CMD_SPAWN")))) {
            String skullOwner;
            boolean haspermission;
            Player reciever = null;
            int quantity = Config.defaultStackSize;
            boolean isConsoleSender = !(sender instanceof Player);

            if (isConsoleSender) {
                if ((args.length != 3) && (args.length != 4)) {
                    Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_SPAWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("SYNTAX") + Lang.getString("COLON_SPACE") + label + Lang.getString("SPACE") + Lang.getString("CMD_SPAWN") + Lang.getString("SPACE") + Lang.getString("OPT_HEADNAME_REQUIRED") + Lang.getString("SPACE") + Lang.getString("OPT_RECEIVER_REQUIRED") + Lang.getString("SPACE") + Lang.getString("OPT_AMOUNT_OPTIONAL"));
                    return true;
                }
            } else {
                reciever = (Player) sender;
            }
            if ((args.length == 1) || ((args.length == 2) && !isConsoleSender && ((Player) sender).getName().equalsIgnoreCase(args[1]))) {
                skullOwner = ((Player) sender).getName();
                haspermission = sender.hasPermission("playerheads.spawn.own");
            } else if ((args.length == 2) && !isConsoleSender) {
                skullOwner = args[1];
                haspermission = sender.hasPermission("playerheads.spawn");
            } else if ((args.length == 3) || (args.length == 4)) {
                if (args.length == 4) {
                    try {
                        quantity = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                    }
                }
                if ((reciever = plugin.getServer().getPlayer(args[2])) == null) {
                    Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_SPAWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("ERROR_NOT_ONLINE"), args[2]);
                    return true;
                }
                skullOwner = args[1];
                if (reciever.equals(sender)) {
                    haspermission = sender.hasPermission("playerheads.spawn");
                } else {
                    haspermission = sender.hasPermission("playerheads.spawn.forother");
                }
            } else {
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_SPAWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("SYNTAX") + Lang.getString("COLON_SPACE") + label + Lang.getString("SPACE") + Lang.getString("CMD_SPAWN") + Lang.getString("SPACE") + Lang.getString("OPT_HEADNAME_OPTIONAL") + Lang.getString("SPACE") + Lang.getString("OPT_RECEIVER_OPTIONAL") + Lang.getString("SPACE") + Lang.getString("OPT_AMOUNT_OPTIONAL"));
                return true;
            }
            if (!haspermission) {
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_SPAWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("ERROR_PERMISSION"));
                return true;
            }
            if (Tools.addHead(reciever, skullOwner, quantity)) {
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_SPAWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("SPAWNED_HEAD"), skullOwner);
            } else {
                Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_SPAWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("ERROR_INV_FULL"));
            }
            return true;
        } else {
            Tools.formatMsg(sender, Lang.getString("BRACKET_LEFT") + label + Lang.getString("COLON") + Lang.getString("CMD_UNKNOWN") + Lang.getString("BRACKET_RIGHT") + Lang.getString("SPACE") + Lang.getString("ERROR_INVALID_SUBCOMMAND"));
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("PlayerHeads")) {
            return null;
        }

        ArrayList<String> completions = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].toLowerCase();
        }

        final String cmd_config = Tools.formatStrip(Lang.getString("CMD_CONFIG"));
        final String cmd_reload = Tools.formatStrip(Lang.getString("CMD_RELOAD"));
        final String cmd_spawn = Tools.formatStrip(Lang.getString("CMD_SPAWN"));

        if (args.length == 1) {
            if (cmd_config.startsWith(args[0])) {
                completions.add(cmd_config);
            }
            if (cmd_spawn.startsWith(args[0])) {
                completions.add(cmd_spawn);
            }
            return sort(completions);
        }
        if (args[0].equals(cmd_config)) {
            if (args.length == 2) {
                if (cmd_reload.startsWith(args[1])) {
                    completions.add(cmd_reload);
                }
                return sort(completions);
            }
            return completions;
        } else if (args[0].equals(cmd_spawn)) {
            if (args.length > 3) {
                return completions;
            }
        }
        return null;
    }

    public List<String> sort(List<String> completions) {
        Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
        return completions;
    }

}
