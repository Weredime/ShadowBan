package io.github.weredime.shadowban.listener;

import io.github.weredime.shadowban.ShadowBanCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinedListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player plr = event.getPlayer();
        if (ShadowBanCommand.banList.getBoolean("players." + plr.getUniqueId())) {
            plr.kickPlayer(ShadowBanCommand.banList.get("config.ban-message").toString());
        }
    }
}
