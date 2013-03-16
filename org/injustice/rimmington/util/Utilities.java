package org.injustice.rimmington.util;

import sk.general.TimedCondition;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import static org.injustice.rimmington.util.Variables.status;

public class Utilities {
	// LODESTONE METHOD BY ANDREW

	public static final WidgetChild MAGIC_TAB_ON_ABIL = new Widget(275)
			.getChild(33);
	public static final WidgetChild TELEPORT_TAB_ON_ABIL = new Widget(275)
			.getChild(47);
	public static final WidgetChild HOME_TELEPORT_ON_ABIL = new Widget(275)
			.getChild(18).getChild(155);
	public static final WidgetChild TELEPORTATION_INTERFACE = new Widget(1092)
			.getChild(8);

	public static final WidgetChild PORT_SARIM = new Widget(1092).getChild(48);

	public static final Area PORT_SARIM_ARRIVAL_AREA = new Area(new Tile[] {
			new Tile(3003, 3221, 0), new Tile(3017, 3221, 0),
			new Tile(3017, 3207, 0), new Tile(3003, 3207, 0) });

	public static boolean isPlayerTeleporting() {
		return Players.getLocal().getAnimation() == 16385;
	}

	// METHOD BY AION
	public static String format(long number, int precision) {
		String sign = number < 0 ? "-" : "";
		number = Math.abs(number);
		if (number < 1000) {
			return sign + number;
		}
		int exponent = (int) (Math.log(number) / Math.log(1000));
		return String.format("%s%." + precision + "f%s", sign,
				number / Math.pow(1000, exponent),
				"kmbtpe".charAt(exponent - 1));
	}

	public static boolean teleportTo(WidgetChild location, Area arrivalArea) {
		Tabs.ABILITY_BOOK.open();

		if (!isPlayerTeleporting()) {

			if (!TELEPORT_TAB_ON_ABIL.validate()) {
				status = "Opening magic tab.";
				Mouse.click(MAGIC_TAB_ON_ABIL.getCentralPoint(), true);
			} else {
				if (!HOME_TELEPORT_ON_ABIL.validate()) {
					status = "Opening tab in ability book.";
					Mouse.click(TELEPORT_TAB_ON_ABIL.getCentralPoint(), true);
				}
				if (!TELEPORTATION_INTERFACE.validate()) {
					status = "Teleport interface not visible, opening";
					Mouse.click(HOME_TELEPORT_ON_ABIL.getCentralPoint(), true);
				} else {
					if (!isPlayerTeleporting()) {
						status = "Clicking";
						Mouse.click(location.getCentralPoint(), true);
						Task.sleep(8000, 10000);
					}
				}
			}
		}

		return arrivalArea.contains(Players.getLocal());
	}

	public static boolean barIsExpanded() {
		return Widgets.get(640, 4).visible();
	}

	public static WidgetChild getExpandButton() {
		return Widgets.get(640, (barIsExpanded()) ? 30 : 3);
	}

	public static boolean setExpanded(final boolean expanded) {
		if (barIsExpanded() == expanded)
			return true;
		WidgetChild tc = getExpandButton();
		return tc.visible() && tc.click(true) && new TimedCondition(1500) {

			@Override
			public boolean isDone() {
				return barIsExpanded() == expanded;
			}
		}.waitStop();
	}

}
