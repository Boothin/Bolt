package org.popcraft.bolt.command.impl;

import net.kyori.adventure.text.minimessage.Template;
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

import static org.popcraft.bolt.util.lang.Translator.translate;

public class UnlockCommand extends BoltCommand {
    public UnlockCommand(BoltPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, Arguments arguments) {
        if (sender instanceof final Player player) {
            plugin.playerMeta(player).setAction(Action.UNLOCK);
            BoltComponents.sendMessage(player, Translation.CLICK_ACTION, Template.of("action", translate(Translation.UNLOCK)));
        } else {
            BoltComponents.sendMessage(sender, Translation.COMMAND_PLAYER_ONLY);
        }
    }

    @Override
    public List<String> suggestions(Arguments arguments) {
        return Collections.emptyList();
    }
}
