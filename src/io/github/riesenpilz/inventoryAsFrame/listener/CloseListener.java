package io.github.riesenpilz.inventoryAsFrame.listener;

import org.bukkit.event.inventory.InventoryCloseEvent;

@FunctionalInterface
public interface CloseListener {
	void run(InventoryCloseEvent e);
}
