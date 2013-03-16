package org.injustice.rimmington.strat;

import sk.action.ActionBar;
import sk.action.ActionSlotType;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;

import static org.injustice.rimmington.util.Variables.*;

public class ActionDrop extends Node {

	@Override
	public boolean activate() {
		return guiDone && actionbarDrop && Inventory.contains(logUsed);
	}

	@Override
	public void execute() {
		Item[] items = Inventory.getItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return i.getId() == logUsed;
			}
		});
		if (items != null) {
			if (ActionBar.getSlotType(0) == ActionSlotType.ITEM) {
				if (ActionBar.isExpanded()) {
					status = "Checking actionbar";
					if (ActionBar.isReadyForInteract()) {
						for (int i = 0; i <= 28; i++) {
							status = "Dropping " + chopping;
							ActionBar.interactSlot(0, "Drop");
						}
						sleep(200, 300);
					} else {
						ActionBar.makeReadyForInteract();
						status = "Readying actionbar";
					}
				} else {
					ActionBar.setExpanded(true);
					status = "Readying actionbar";
				}

			} else {
				ActionBar.dragToSlot(items[0], 0);
				status = "Dragging to slot";
			}
		}
	}
}
