package io.github.riesenpilz.inventoryAsFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.riesenpilz.inventoryAsFrame.listener.ClickListener;

public class Button extends ItemStack {
	private ClickListener clickListener;

	public Button(Material material, String name) {
		super(material);
		setName(name);

	}

	public Button(Material material) {
		super(material);
	}

	public Button(ItemStack itemStack) {
		super(itemStack);
	}

	public void setClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public ClickListener getClickListener() {
		return clickListener;
	}

	public PersistentDataContainer getPDC() {
		return getItemMeta().getPersistentDataContainer();
	}

	public void setName(String name) {
		ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(name);
		setItemMeta(itemMeta);
	}

	public String getName() {
		return getItemMeta().getDisplayName();
	}

//------------------------LORE---------------------------------//
	public void setLore(String... lore) {
		List<String> list = new ArrayList<>();
		Collections.addAll(list, lore);
		setLore(list);
	}

	public List<String> getLore() {
		ItemMeta itemMeta = getItemMeta();
		return itemMeta.getLore() == null ? new ArrayList<>() : itemMeta.getLore();
	}

	public void setLore(List<String> lore) {
		ItemMeta itemMeta = getItemMeta();
		itemMeta.setLore(lore);
		setItemMeta(itemMeta);
	}

	public void addLore(String text) {
		List<String> lore = getLore();
		lore.add(text);
		setLore(lore);
	}

	public void setLore(int i, String lore) {
		List<String> lore2 = getLore();
		if (lore2.size() < i + 1)
			for (int j = lore2.size() - 1; j < i; j++)
				lore2.add(" ");

		lore2.set(i, lore);
		setLore(lore2);
	}

	public void removeLore() {
		getItemMeta().setLore(null);
	}

	public <T, Z> void set(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
		getPDC().set(key, type, value);
	}

	public <T, Z> boolean has(NamespacedKey key, PersistentDataType<T, Z> type) {
		return getPDC().has(key, type);
	}

	public <T, Z> Z get(NamespacedKey key, PersistentDataType<T, Z> type) {
		return getPDC().get(key, type);
	}

	public <T, Z> Z getOrDefault(NamespacedKey key, PersistentDataType<T, Z> type, Z defaultValue) {
		return getPDC().getOrDefault(key, type, defaultValue);
	}

	public Set<NamespacedKey> getKeys() {
		return getPDC().getKeys();
	}

	public void remove(NamespacedKey key) {
		getPDC().remove(key);
	}

	public boolean isEmpty() {
		return getPDC().isEmpty();
	}
}
