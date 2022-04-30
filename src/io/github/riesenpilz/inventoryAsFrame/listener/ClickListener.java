package io.github.riesenpilz.inventoryAsFrame.listener;

import io.github.riesenpilz.inventoryAsFrame.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
@FunctionalInterface
public interface ClickListener {
	void run(InventoryClickEvent e, Button button);
}
