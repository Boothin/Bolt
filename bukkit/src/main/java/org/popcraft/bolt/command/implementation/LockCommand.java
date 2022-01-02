package org.popcraft.bolt.command.implementation;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.popcraft.bolt.BoltPlugin;
import org.popcraft.bolt.command.Arguments;
import org.popcraft.bolt.command.BoltCommand;
import org.popcraft.bolt.util.Action;
import org.popcraft.bolt.util.BoltComponents;
import org.popcraft.bolt.util.lang.Translation;

import java.util.Collections;
import java.util.List;

public class LockCommand extends BoltCommand {
    public LockCommand(BoltPlugin plugin) {
        super(plugin);
    }

    public void execute(CommandSender sender, Arguments arguments) {
        if (sender instanceof final Player player) {
            plugin.getBolt().getBoltPlayer(player.getUniqueId()).addAction(Action.LOCK_BLOCK);
            BoltComponents.sendMessage(player, Translation.CLICK_BLOCK);
        }
    }

    @Override
    public List<String> suggestions() {
        return Collections.emptyList();
    }
}
