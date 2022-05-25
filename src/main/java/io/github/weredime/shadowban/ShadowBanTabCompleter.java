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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShadowBanTabCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> list = new ArrayList<>();

        if (cmd.getName().equalsIgnoreCase("shadowban") || cmd.getName().equalsIgnoreCase("sban")) {
            if (args.length == 0 || (args.length == 1 && args[0] == "")) {
                for (String arg : ShadowBanCommand.ALLOWED_ARGUMENTS) {
                    list.add(arg);
                }
                Collections.sort(list);

            } else if (args.length == 1 || (args.length == 2 && args[1] == "")) {
                String usage = args[0].toLowerCase();
                if (usage.equals("ban") || usage.equals("add") || usage.equals("unban") || usage.equals("pardon")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        list.add(player.getName());
                    }
                } else if (usage.equals("setmessage") || usage.equals("setmsg")) {
                    for (Object str : ShadowBanMessages.getMessages().keySet().toArray()) {
                        list.add(str.toString() + " (" + ShadowBanMessages.getMessages().get(str.toString()) + ")");
                    }
                }
            }
        }
        return list;
    }
}
