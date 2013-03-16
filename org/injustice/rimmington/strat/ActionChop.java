package org.injustice.rimmington.strat;

import sk.action.ActionBar;
import sk.action.ActionSlotType;
import org.injustice.rimmington.util.Utilities;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.rimmington.util.Variables.*;

public class ActionChop extends Node {

	@Override
	public boolean activate() {
		return Inventory.contains(logUsed) && guiDone
				&& Calculations.distanceTo(shopTile) > 3
				&& cuttingTile.isOnMap();
	}

	@Override
	public void execute() {
		status = "here";
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
						// for (int i = 0; i <= 28; i++) {
						status = "ADropping " + chopping;
						ActionBar.interactSlot(0, "Drop");
						// }
						sleep(500, 800);
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

		SceneObject tree = SceneEntities.getNearest(treesBeingCut);
		if (Players.getLocal().getAnimation() == -1) {
			if (!Players.getLocal().isMoving()) {
				if (autopilot) {
					if (Skills.getRealLevel(Skills.WOODCUTTING) < 15) {
						cuttingTile = normalTile;
						SceneObject normal = SceneEntities.getNearest(normals);
						status = "Chopping normal";
						chop(normal);
						// in case of level up I must constantly check this
						treesBeingCut = normals;
						cuttingTile = normalTile;
						logUsed = normalsID;
						price = 1;
					} else if (Skills.getRealLevel(Skills.WOODCUTTING) < 30
							&& Skills.getRealLevel(Skills.WOODCUTTING) > 15) {
						cuttingTile = oakTile;
						SceneObject oak = SceneEntities.getNearest(oaks);
						status = "Chopping oak";
						chop(oak);
					} else if (Skills.getRealLevel(Skills.WOODCUTTING) > 30) {
						cuttingTile = willowTile;
						SceneObject willow = SceneEntities.getNearest(willows);
						status = "Chopping willow";
						chop(willow);
					}
				} else
					chop(tree);
			} else
				sleep(250, 300);
			status = "Chopping, waiting";

			if (Utilities.barIsExpanded() && !actionbarDrop) {
				Utilities.setExpanded(false);
			}
		}

	}

	void chop(SceneObject log) {
		Item[] items = Inventory.getItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return i.getId() == logUsed;
			}
		});
		if (log != null) {
			if (log.isOnScreen()) {
				if (log.interact("Chop")) {
					status = "Chopping";
					sleep(1500, 2000);
					while ((Players.getLocal().isMoving() || Players.getLocal()
							.getAnimation() != -1) && items == null) {
						sleep(250);
						status = "Chopping tree";
					}

				}
			} else {
				status = "Turning to tree";
				Camera.turnTo(log);
			}
		} else
			sleep(250, 300);
	}
}
