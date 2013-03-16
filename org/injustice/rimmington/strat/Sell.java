package org.injustice.rimmington.strat;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.rimmington.util.Variables.*;

public class Sell extends Node {

	@Override
	public boolean activate() {
		return Inventory.isFull() && Calculations.distanceTo(shopTile) <= 10
				&& guiDone && sell;
	}

	@Override
	public void execute() {
		NPC shopkeeper = NPCs.getNearest(keeper);
		if (shopkeeper != null) {
			if (shopkeeper.isOnScreen()) {
				if (Inventory.contains(logUsed)) {
					shopkeeper.interact("Trade");
					status = "Trading shopkeeper";
					sleep(1000, 2000);
					status = ("Selling logs");
					Inventory.getItem(log()).getWidgetChild()
							.interact("Sell 50");
					sleep(1000, 1500);
					Widgets.get(1265, 87).interact("Close");
				}

			} else {
				Camera.turnTo(shopkeeper);
				status = ("Turning to keeper");
			}

		}

	}

	int log() {
		if (autopilot) {
			if (Skills.getRealLevel(Skills.WOODCUTTING) < 15)
				logUsed = normalsID;
			else if (Skills.getRealLevel(Skills.WOODCUTTING) < 30
					&& Skills.getRealLevel(Skills.WOODCUTTING) > 15)
				logUsed = oaksID;
			else if (Skills.getRealLevel(Skills.WOODCUTTING) > 30)
				logUsed = willowsID;
		}
		return logUsed;
	}

}
