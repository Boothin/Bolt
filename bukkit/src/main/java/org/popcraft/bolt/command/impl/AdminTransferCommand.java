package org.popcraft.bolt.command.impl;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.popcraft.bolt.BoltPlugin;
import org.popcraft.bolt.command.Arguments;
import org.popcraft.bolt.command.BoltCommand;
import org.popcraft.bolt.data.Store;
import org.popcraft.bolt.lang.Translation;
import org.popcraft.bolt.util.Action;
import org.popcraft.bolt.util.BoltComponents;
import org.popcraft.bolt.util.BukkitAdapter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AdminTransferCommand extends BoltCommand {
    public AdminTransferCommand(BoltPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, Arguments arguments) {
        if (!(sender instanceof final Player player)) {
            BoltComponents.sendMessage(sender, Translation.COMMAND_PLAYER_ONLY);
            return;
        }
        if (arguments.remaining() < 1) {
            BoltComponents.sendMessage(sender, Translation.COMMAND_NOT_ENOUGH_ARGS);
            return;
        }
        final String owner = arguments.next();
        final String newOwner = arguments.next();
        final CompletableFuture<UUID> ownerUuidFuture = BukkitAdapter.findOrLookupPlayerUniqueId(owner);
        final CompletableFuture<UUID> newOwnerUuidFuture = BukkitAdapter.findOrLookupPlayerUniqueId(newOwner);
        if (newOwner != null) {
            CompletableFuture.allOf(ownerUuidFuture, newOwnerUuidFuture).thenRun(() -> {
                final UUID ownerUuid = ownerUuidFuture.join();
                final UUID newOwnerUuid = newOwnerUuidFuture.join();
                if (ownerUuid == null) {
                    BoltComponents.sendMessage(player, Translation.PLAYER_NOT_FOUND, Placeholder.unparsed("player", owner));
                } else if (newOwnerUuid == null) {
                    BoltComponents.sendMessage(player, Translation.PLAYER_NOT_FOUND, Placeholder.unparsed("player", newOwner));
                } else {
                    final Store store = plugin.getBolt().getStore();
                    store.loadBlockProtections().join().stream()
                            .filter(protection -> protection.getOwner().equals(ownerUuid))
                            .forEach(protection -> {
                                protection.setOwner(newOwnerUuid);
                                store.saveBlockProtection(protection);
                            });
                    store.loadEntityProtections().join().stream()
                            .filter(protection -> protection.getOwner().equals(ownerUuid))
                            .forEach(protection -> {
                                protection.setOwner(newOwnerUuid);
                                store.saveEntityProtection(protection);
                            });
                    BoltComponents.sendMessage(player, Translation.CLICK_TRANSFER_ALL, Placeholder.unparsed("old", owner), Placeholder.unparsed("new", newOwner));
                }
            });
        } else {
            ownerUuidFuture.thenAccept(uuid -> {
                if (uuid != null) {
                    plugin.player(player).setAction(new Action(Action.Type.TRANSFER, uuid.toString(), true));
                    BoltComponents.sendMessage(player, Translation.CLICK_TRANSFER);
                } else {
                    BoltComponents.sendMessage(player, Translation.PLAYER_NOT_FOUND, Placeholder.unparsed("player", owner));
                }
            });
        }

    }

    @Override
    public List<String> suggestions(Arguments arguments) {
        if (arguments.remaining() == 0) {
            return Collections.emptyList();
        }
        arguments.next();
        if (arguments.remaining() == 0) {
            return plugin.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return Collections.emptyList();
    }
}