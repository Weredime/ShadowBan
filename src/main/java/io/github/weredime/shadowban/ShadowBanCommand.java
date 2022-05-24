/**
 * ShadowBan - Ban players secretly without letting them know.
 * Copyright (C) 2022 Weredime
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package io.github.weredime.shadowban;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class ShadowBanCommand implements CommandExecutor {
    public static final Plugin ShadowBan = Bukkit.getPluginManager().getPlugin("ShadowBan");
    public static final File banListFile = new File(ShadowBan.getDataFolder().getPath() + "/bans.yml");
    public static final YamlConfiguration banList = YamlConfiguration.loadConfiguration(banListFile);
    public static final String[] ALLOWED_ARGUMENTS = {"setmsg", "setmessage", "add", "remove", "list", "ban", "unban", "pardon"};
    public static final String CHAT_PREFIX = String.format("[%sShadowBan%s]", ChatColor.RED, ChatColor.RESET);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(String.format("%s: You do not have the required permission to use this command.", CHAT_PREFIX));
            return true;
        }
        if (args.length == 0) {
            return false;
        }
        String usage = args[0].toLowerCase();
        if (!Arrays.stream(ALLOWED_ARGUMENTS).anyMatch(usage::equals)) {
            return false;
        }
        getOrCreateBanListFile();

        if (usage.equals("add") || usage.equals("ban")) {
            if (args.length == 1) {
                sender.sendMessage(String.format("%s: Please specify a player!", CHAT_PREFIX));
                return false;
            }

            Player plr = Bukkit.getPlayerExact(args[1]);
            if (plr == null) {
                sender.sendMessage(String.format("%s: Invalid player!", CHAT_PREFIX));
            } else {
                banList.set("players." + plr.getUniqueId(), true);
                try {
                    banList.save(banListFile);
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
                if (plr.isOnline()) {
                    plr.kickPlayer(banList.get("config.ban-message").toString());
                }
                return true;
            }

        } else if (usage.equals("pardon") || usage.equals("unban")) {
            if (args.length == 1) {
                sender.sendMessage(String.format("%s: Please specify a player!", CHAT_PREFIX));
                return false;
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

            Boolean playerBanned = banList.getBoolean("players." + offlinePlayer.getUniqueId());
            if (playerBanned) {
                // Unban the player
                banList.set("players." + offlinePlayer.getUniqueId(), false);
                try {
                    banList.save(banListFile);
                } catch(IOException ex) {
                    ShadowBan.getLogger().severe("An error occured when trying to update the bans file:");
                    ex.printStackTrace();
                    sender.sendMessage(String.format("%s: Internal exception occured when trying to save the bans file. Ask your server admin to look at the logs.", CHAT_PREFIX));
                    return true;
                }
                sender.sendMessage(String.format("%s: Successfully pardoned %s!", CHAT_PREFIX, offlinePlayer.getName()));
            } else {
                sender.sendMessage(String.format("%s: The player %s is not banned!", CHAT_PREFIX, offlinePlayer.getName()));
            }

            return true;
        } else if (usage.equals("list")) {
            Object[] bans = banList.getConfigurationSection("players").getKeys(false).toArray();

            if (bans.length == 0) {
                sender.sendMessage(String.format("%s: You haven't banned anybody yet..", CHAT_PREFIX));
            }
            sender.sendMessage(String.format("%s: There %s %d player%s banned", CHAT_PREFIX, bans.length == 1 ? "is" : "are", bans.length, bans.length == 1 ? "" : "s"));
            for (Object uuid : bans) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid.toString()));
                sender.sendMessage(player.getName());
            }
            return true;
         } else if (usage.equals("setmessage") || usage.equals("setmsg")) {
            banList.set("config.ban-message", args[2]);

            try {
                banList.save(banListFile);
            } catch(IOException ex) {
                ShadowBan.getLogger().severe("An error occured when saving the config file:");
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static void getOrCreateBanListFile() {
        if (!banListFile.exists()) {
            try {
                banListFile.createNewFile();
                banList.createSection("players");
                banList.createSection("config");
                banList.set("config.ban-message", ShadowBanMessages.CONNECTION_RESET);
            } catch(IOException ex) {
                ShadowBan.getLogger().severe("An error occured when creating the bans.yml file. Please manually create one - Stack trace:");
                ex.printStackTrace();
            }
        }
    }
}
