package org.injustice.rimmington.strat;

import static org.injustice.rimmington.util.Variables.*;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class WalkTrees extends Node {

	@Override
	public boolean activate() {
		return !Inventory.isFull() && !cuttingTile.isOnMap()
				&& Players.getLocal().getLocation().distance(cuttingTile) < 60
				&& Players.getLocal().getAnimation() == -1 && guiDone;
	}

	@Override
	public void execute() {
		if (autopilot) {
			if (Skills.getRealLevel(Skills.WOODCUTTING) < 15) {
				doPath(normalTile);
			} else if (Skills.getRealLevel(Skills.WOODCUTTING) < 30
					&& Skills.getRealLevel(Skills.WOODCUTTING) > 15) {
				doPath(oakTile);
			} else if (Skills.getRealLevel(Skills.WOODCUTTING) > 30) {
				doPath(willowTile);
			}
		} 
		else if (cuttingTile == normalTile) 
			doPath(normalTile);
		else if (cuttingTile == oakTile)
			doPath(oakTile);
		else
			doPath(willowTile);
		
	}
	
	void doPath(Tile tile) {
		Path path = Walking.findPath(tile);
		path.traverse();
		status = "Walking to tree";
	}

}
