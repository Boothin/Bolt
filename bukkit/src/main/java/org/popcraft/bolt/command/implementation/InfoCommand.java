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

public class InfoCommand extends BoltCommand {
    public InfoCommand(BoltPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, Arguments arguments) {
        if (sender instanceof final Player player) {
            plugin.getBolt().getPlayerMeta(player.getUniqueId()).addAction(Action.INFO);
            BoltComponents.sendMessage(player, Translation.CLICK_BLOCK_INFO);
        } else {
            BoltComponents.sendMessage(sender, Translation.COMMAND_PLAYER_ONLY);
        }
    }

    @Override
    public List<String> suggestions() {
        return Collections.emptyList();
    }
}
