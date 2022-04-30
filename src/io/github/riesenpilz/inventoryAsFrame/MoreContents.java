package io.github.riesenpilz.inventoryAsFrame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.persistence.PersistentDataType;

import io.github.riesenpilz.inventoryAsFrame.listener.AdvancedChatListener;
import lombok.Data;

@Data
public class MoreContents {
	public static final NamespacedKey LORE = new NamespacedKey(Main.getPlugin(), "moreContentsLore");
	private ArrayList<Button> moreContents;
	private int[] slots;
	private int page;
	private int row;
	private final Frame frame;

	public MoreContents(int[] slots, ArrayList<Button> buttons, Frame frame) {
		this.frame = frame;
		this.slots = slots;
		this.moreContents = buttons;
		setPage(1);
	}

	public MoreContents(Frame frame) {
		this.frame = frame;
	}

	public void setPage(int page) {
		double d = ((double) moreContents.size()) / ((double) slots.length);
		int maxPages = (moreContents.size() / slots.length);
		if (d - maxPages != 0)
			maxPages++;
		for (int i = 0; i < 6; i++) {
			if (page < 1)
				page += maxPages;
			else if (page > maxPages)
				page -= maxPages;
		}

		for (int i = 0; i < slots.length; i++) {
			int index = slots[i];
			if (i + (page - 1) * slots.length >= moreContents.size())
				frame.setButton(index, new Button(Material.AIR));
			else
				frame.setButton(index, moreContents.get(i + (page - 1) * slots.length));
		}
		this.page = page;
		frame.getButton(row, 5).setName("Page " + getPage());
	}

	public void swapPages(int i) {
		setPage(getPage() + i);
	}

	public void addMoreContents(Button button) {
		moreContents.add(button);
	}

	public void addScroll(int row) {
		addScroll(row, moreContents);
	}

	private void addScroll(int row, ArrayList<Button> buttons) {
		this.row = row;
		Button page = new Button(Material.GRAY_STAINED_GLASS_PANE, "§rPage " + getPage());
		page.setClickListener((e, b) -> {
			Player player = (Player) e.getWhoClicked();
			new AdvancedChatListener(frame, player, (message -> {
				MoreContents newMoreContents = new MoreContents(frame);
				if (message.equals("none")) {
					newMoreContents.setMoreContents(buttons);
					newMoreContents.setSlots(slots);
					newMoreContents.setPage(1);
					newMoreContents.addScroll(1, buttons);
					frame.getButton(1, 5).removeLore();
				} else {
					ArrayList<Button> buttons2 = new ArrayList<>();
					for (Button button : buttons)
						if (button.getName().contains(message))
							buttons2.add(button);
					newMoreContents.setMoreContents(buttons2);
					newMoreContents.setSlots(slots);
					newMoreContents.setPage(1);
					newMoreContents.addScroll(1, buttons);
					frame.getButton(1, 5).setLore("�rfilter: " + message);
				}
				frame.openInventory(player);
			}));
		});

		Button back1 = new Button(Material.GREEN_STAINED_GLASS_PANE, "§r§a<");
		Button forwart1 = new Button(Material.GREEN_STAINED_GLASS_PANE, "§r§a>");
		Button back2 = new Button(Material.GREEN_STAINED_GLASS_PANE, "§r§a<<");
		Button forwart2 = new Button(Material.GREEN_STAINED_GLASS_PANE, "§r§a>>");
		Button back3 = new Button(Material.GREEN_STAINED_GLASS_PANE, "§r§a<<<");
		Button forwart3 = new Button(Material.GREEN_STAINED_GLASS_PANE, "§r§a>>>");

		back1.setClickListener((e, b) -> swapPages(-1));
		forwart1.setClickListener((e, b) -> swapPages(1));
		back2.setClickListener((e, b) -> swapPages(-5));
		forwart2.setClickListener((e, b) -> swapPages(5));
		back3.setClickListener((e, b) -> setPage(1));
		forwart3.setClickListener((e, b) -> setPage(getMaxPages()));

		frame.setButton(row, 5, page);
		frame.setButton(row, 4, back1);
		frame.setButton(row, 6, forwart1);
		frame.setButton(row, 3, back2);
		frame.setButton(row, 7, forwart2);
		frame.setButton(row, 2, back3);
		frame.setButton(row, 8, forwart3);
	}

	private int getMaxPages() {
		return getMoreContents().size() / getSlots().length + 1;
	}

	public void addLore() {
		List<String> loreList = new ArrayList<>();
		for (Button button : moreContents)
			if (button.has(LORE, PersistentDataType.STRING))
				loreList.add(button.get(LORE, PersistentDataType.STRING));

		loreList.add("§aKlicke zum ändern");
		for (Button button : moreContents) {
			List<String> lore = new ArrayList<>(loreList);
			for (int i = 0; i < lore.size(); i++) {
				final boolean equals = lore.get(i).equals(button.get(LORE, PersistentDataType.STRING));
				lore.set(i, equals ? "§r§l" + lore.get(i) : "§r" + lore.get(i));
			}
			button.setLore(lore);
		}
		update();
	}

	public void addScrollButton(int page, int slot) {
		for (Button button : moreContents)
			button.setClickListener((e, b) -> swapPages(e.getClick().equals(ClickType.RIGHT) ? -1 : 1));
		int[] slots = { slot };
		setSlots(slots);
		setPage(page);
		addLore();
	}

	public void update() {
		swapPages(0);
	}
	
	public static int[] getSlots(int start, int end) {
		int[] slots = new int[end-start+1];
		for (int i = start; i <= end; i++)
			slots[i] = i;
		return slots;
	}
}
