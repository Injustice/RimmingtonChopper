package org.injustice.rimmington.strat;

import static org.injustice.rimmington.util.Variables.*;

import org.injustice.rimmington.util.Utilities;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Chop extends Node {

	@Override
	public boolean activate() {
		return !Inventory.isFull() && cuttingTile.isOnMap()
				&& Calculations.distanceTo(shopTile) > 3 && guiDone;
	}

	@Override
	public void execute() {
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

						treesBeingCut = oaks;
						cuttingTile = oakTile;
						logUsed = oaksID;
						price = 6;
					} else if (Skills.getRealLevel(Skills.WOODCUTTING) > 30) {
						cuttingTile = willowTile;
						SceneObject willow = SceneEntities.getNearest(willows);
						status = "Chopping willow";
						chop(willow);

						treesBeingCut = willows;
						cuttingTile = willowTile;
						logUsed = willowsID;
						price = 12;
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
		if (log != null) {
			if (log.isOnScreen()) {
				if (log.interact("Chop")) {
					status = "Chopping";
					sleep(1000, 1500);
					while (Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1)
						sleep(1000);
				}
			} else {
				status = "Turning to tree";
				Camera.turnTo(log);
			}
		} else
			sleep(250, 300);
	}
}
