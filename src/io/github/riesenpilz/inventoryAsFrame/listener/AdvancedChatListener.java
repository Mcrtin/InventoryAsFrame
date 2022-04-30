package io.github.riesenpilz.inventoryAsFrame.listener;

import org.bukkit.entity.Player;

import io.github.riesenpilz.inventoryAsFrame.Frame;

public class AdvancedChatListener implements ChatListener {
	private final Frame frame;
	private final ChatListener chatListener;
	private final Player player;

	public AdvancedChatListener(Frame frame, Player player, ChatListener chatListener) {
		this.frame = frame;
		this.chatListener = chatListener;
		this.player = player;

		player.sendMessage(" ");
		player.sendMessage("§aWrite a filter, none, to reset the filter or §6cancel§a to cancel:");
		player.closeInventory();
	}

	@Override
	public void run(String message) {
		frame.removeChatListener();
		if (message.equals("cancel"))
			player.sendMessage("§aYou can now write in the chat again!");
		else
			chatListener.run(message);
	}

}
