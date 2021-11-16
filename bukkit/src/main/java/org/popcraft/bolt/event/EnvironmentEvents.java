package org.popcraft.bolt.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.popcraft.bolt.Bolt;
import org.popcraft.bolt.data.util.BlockLocation;

public class EnvironmentEvents implements Listener {
    private final Bolt bolt;

    public EnvironmentEvents(final Bolt bolt) {
        this.bolt = bolt;
    }

    @EventHandler
    public void onStructureGrow(final StructureGrowEvent e) {
        for (final BlockState blockState : e.getBlocks()) {
            final BlockLocation location = new BlockLocation(blockState.getWorld().getName(), blockState.getX(), blockState.getY(), blockState.getZ());
            if (bolt.getStore().loadBlockProtection(location).isPresent()) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onEntityChangeBlock(final EntityChangeBlockEvent e) {
        final Block block = e.getBlock();
        final BlockLocation location = new BlockLocation(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
        if (bolt.getStore().loadBlockProtection(location).isPresent()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockMultiPlace(final BlockMultiPlaceEvent e) {
        for (final BlockState blockState : e.getReplacedBlockStates()) {
            final BlockLocation location = new BlockLocation(blockState.getWorld().getName(), blockState.getX(), blockState.getY(), blockState.getZ());
            if (bolt.getStore().loadBlockProtection(location).isPresent()) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockFromTo(final BlockFromToEvent e) {
        final Block block = e.getToBlock();
        final BlockLocation location = new BlockLocation(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
        if (bolt.getStore().loadBlockProtection(location).isPresent()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(final BlockExplodeEvent e) {
        for (final Block block : e.blockList()) {
            final BlockLocation location = new BlockLocation(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
            if (bolt.getStore().loadBlockProtection(location).isPresent()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPistonRetract(final BlockPistonRetractEvent e) {
        // TODO: Block event
    }

    @EventHandler
    public void onBlockPistonExtend(final BlockPistonExtendEvent e) {
        // TODO: Block event
    }

    @EventHandler
    public void onHangingBreak(final HangingBreakEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onEntityBreakDoor(final EntityBreakDoorEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onHangingBreakByEntity(final HangingBreakByEntityEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onVehicleDestroy(final VehicleDestroyEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
        // TODO: Entity event
    }

    @EventHandler
    public void onInventoryMoveItem(final InventoryMoveItemEvent e) {
        // TODO: Inventory event
    }
}