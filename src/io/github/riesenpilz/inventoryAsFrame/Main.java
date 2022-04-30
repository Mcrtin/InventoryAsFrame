package io.github.riesenpilz.inventoryAsFrame;

import io.github.riesenpilz.inventoryAsFrame.listener.InventoryAsFrameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static JavaPlugin plugin;

	public void onEnable() {
		plugin = this;
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new InventoryAsFrameListener(), this);
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
