package io.github.riesenpilz.inventoryAsFrame.listener;

import io.github.riesenpilz.inventoryAsFrame.Button;
import io.github.riesenpilz.inventoryAsFrame.Frame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InventoryAsFrameListener implements org.bukkit.event.Listener {
	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent e) {
		if ((e instanceof InventoryCreativeEvent))
			return;

		final Player player = (Player) e.getWhoClicked();
		final Frame frame = Frame.frames.get(player);

		if (frame == null || !frame.getInventory().equals(e.getView().getTopInventory()))
			return;
		if (frame.getClickListener() != null)
			frame.getClickListener().run(e, null);
		final boolean isShiftClicked = e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY);
		final boolean isTopInventoryClicked = e.getView().getTopInventory().equals(e.getClickedInventory());

		if (e.getClickedInventory() != null && (isShiftClicked || isTopInventoryClicked))
			e.setCancelled(true);

		if (!isTopInventoryClicked)
			return;

		final Button button = frame.getButton(e.getSlot());
		if (button.getClickListener() != null)
			button.getClickListener().run(e, button);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Frame frame = Frame.frames.get(e.getPlayer());
		if (frame == null || frame.getChatListener() == null)
			return;

		frame.getChatListener().run(e.getMessage());
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerCloseInv(InventoryCloseEvent e) {
		Frame frame = Frame.frames.get((Player) e.getPlayer());
		if (frame == null || frame.getCloseListener() == null)
			return;

		frame.getCloseListener().run(e);
	}
}
