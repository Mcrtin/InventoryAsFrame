package io.github.riesenpilz.inventoryAsFrame;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import io.github.riesenpilz.inventoryAsFrame.listener.ChatListener;
import io.github.riesenpilz.inventoryAsFrame.listener.ClickListener;
import io.github.riesenpilz.inventoryAsFrame.listener.CloseListener;
import lombok.Data;

@Data
public class Frame {
	public static final HashMap<Player, Frame> frames = new HashMap<>();
	private final Button[] buttons;
	private ChatListener chatListener;
	private ClickListener clickListener;
	private CloseListener closeListener;

	private final String name;
	private Inventory inventory;

	public Frame(String name, Button[] buttons) {
		if (buttons.length % 9 != 0)
			throw new IllegalArgumentException("buttons must be divisible by 9");
		this.buttons = buttons;
		this.name = name;
		setInventory();
	}

	public Frame(String name, Button button, int size) {
		if (size % 9 != 0)
			throw new NullPointerException("size must be divisible by 9");
		this.name = name;
		Button[] buttons = new Button[size];
		for (int i = 0; i < size; i++)
			buttons[i] = button;
		this.buttons = buttons;
		setInventory();
	}

	public void openInventory(Player player) {
		try {
			player.openInventory(getInventory());
			frames.put(player, this);
		} catch (Exception e) {
			Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
				player.openInventory(getInventory());
				frames.put(player, this);
			});
		}
	}

	public Button getButton(int index) {
		return buttons[index];
	}

	public void setButton(int index, Button button) {
		buttons[index] = button;
		updateButton(index);
	}

	public Button getButton(int row, int column) {
		return buttons[(row - 1) * 9 + column - 1];
	}

	public void setButton(int row, int column, Button button) {
		buttons[(row - 1) * 9 + column - 1] = button;
		updateButton((row - 1) * 9 + column - 1);
	}

	public void updateButton(int slot) {
		inventory.setItem(slot, getButton(slot));
	}

	public void updateButton(int row, int column) {
		inventory.setItem((row - 1) * 9 + column - 1, getButton(row, column));
	}

	public void removeChatListener() {
		setChatListener(null);
	}

	public void updateInventory() {
		for (int i = 0; i < buttons.length; i++)
			inventory.setItem(i, buttons[i]);
	}

	private void setInventory() {
		inventory = Bukkit.createInventory(null, buttons.length, name);
		updateInventory();
	}

}