package org.injustice.rimmington.strat;

import static org.injustice.rimmington.util.Variables.*;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;

public class Drop extends Node {

	@Override
	public boolean activate() {
		return Inventory.isFull() && drop && guiDone;
	}

	@Override
	public void execute() {
		Item[] items = Inventory.getItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return i.getId() == logUsed;
			}
		});

		for (final Item item : items) {
			item.getWidgetChild().interact("Drop");
		}
	}
}
