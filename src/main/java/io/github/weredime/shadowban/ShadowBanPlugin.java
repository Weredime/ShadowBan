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

import io.github.weredime.shadowban.listener.PlayerJoinedListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class ShadowBanPlugin extends JavaPlugin {
    private Logger logger = this.getLogger();
    @Override
    public void onEnable() {
        logger.info("ShadowBan is Copyright (c) Weredime 2022.");
        logger.info("This program comes with ABSOLUTELY NO WARRANTY;");
        logger.info("This is free software, and you are welcome to redistribute it under certain conditions - See https://github.com/Weredime/ShadowBan/blob/master/LICENSE");
        getServer().getPluginManager().registerEvents(new PlayerJoinedListener(), this);

        // Create data folder if not found
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        ShadowBanCommand.getOrCreateBanListFile();

        // Register commands
        getCommand("shadowban").setExecutor(new ShadowBanCommand());
        getCommand("shadowban").setTabCompleter(new ShadowBanTabCompleter());
    }
}
