package org.injustice.rimmington.strat;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.map.Path;

import static org.injustice.rimmington.util.Variables.*;

public class WalkShop extends Node {

	@Override
	public boolean activate() {
		return Inventory.isFull() && !shopTile.isOnScreen() && guiDone && sell;
	}

	@Override
	public void execute() {
		Path path = Walking.findPath(shopTile);
		path.traverse();
		status = ("Walking to shop");
	}
}
