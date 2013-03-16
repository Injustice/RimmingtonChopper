package org.injustice.rimmington.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.util.Time;

import static org.injustice.rimmington.util.Utilities.format;
import static org.injustice.rimmington.util.Variables.*;

public class Paint { 
	static SkillData sd = new SkillData();
	public static void onRepaint(Graphics g1) {
		if (!hide) {
			Graphics2D g = (Graphics2D) g1;
			int expGained = Skills.getExperience(Skills.WOODCUTTING) - startExp;
			int level = Skills.getRealLevel(Skills.WOODCUTTING);
			int expToLevel = Skills.getExperienceToLevel(Skills.WOODCUTTING,
					level + 1);
			int levelsGained = Skills.getLevel(Skills.WOODCUTTING) - startLevel;
			int expHour = (int) (expGained * 3600000d / runTime.getElapsed());
			timeTNL = Time.format(sd.timeToLevel(SkillData.Rate.HOUR, 8));
			int gpMade = logsCut * price;
			int gpHour = (int) (gpMade * 3600000D / (System.currentTimeMillis() - startTime));
			int logsHour = expHour / 67;

			Color c = new Color(255, 255, 255);

			Font f1 = new Font("Impact", 0, 25);
			Font f2 = new Font("Impact", 0, 20);
			Font f3 = new Font("Impact", 0, 15);

			g.setRenderingHints(new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF));
			g.setColor(new Color(48, 35, 191));
			g.fillRoundRect(8, 398, 503, 125, 5, 5);
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0, 0, 0));
			g.drawRoundRect(8, 398, 503, 125, 5, 5);

			g.setColor(c);
			g.setFont(f1);
			g.drawString("Injustice's Rimmington Chopper v" + version, 80, 430);
			g.setFont(f2);
			g.drawString("Money made: " + format(gpMade, 1), 15, 450);
			g.drawString("Money/hour: " + format(gpHour, 1), 15, 470);
			g.drawString("Level: " + Skills.getLevel(Skills.WOODCUTTING)
					+ " (+" + levelsGained + ")", 360, 450);
			g.drawString("Exp gained: " + format(expGained, 2), 175, 450);
			g.drawString("Exp/hour: " + format(expHour, 2), 175, 470);
			g.drawString("Exp to level: " + format(expToLevel, 2), 360, 490);
			g.drawString("Logs cut: " + logsCut, 15, 490);
			g.drawString("Logs/hour: " + logsHour, 175, 490);
			g.drawString("Time TNL: " + timeTNL, 360, 470);
			g.setFont(f3);
			g.drawString("Runtime: " + runTime.toElapsedString(), 15, 515);
			g.drawString("Status: " + status, 175, 515);
//			g.drawString("Version: " + version, 435, 515);
			if (guiDone)
				g.drawString("(" + checkDoing() + checkAutopilot() + ")", 360, 515);
			drawMouse(g1);
		}
	}

	Point p;

	private static void drawMouse(Graphics g1) {
		((Graphics2D) g1).setRenderingHints(new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		Point p = Mouse.getLocation();
		Color[] gradient = new Color[] { new Color(255, 0, 0),
				new Color(255, 0, 255), new Color(0, 0, 255),
				new Color(0, 255, 255), new Color(0, 255, 0),
				new Color(255, 255, 0), new Color(255, 0, 0) };
		g1.setColor(gradient[0]);
		g1.drawLine(p.x - 2000, p.y, p.x + 2000, p.y);
		g1.drawLine(p.x, p.y - 2000, p.x, p.y + 2000);
		for (int r = gradient.length - 1; r > 0; r--) {
			int steps = 200 / ((gradient.length - 1) * 2);
			for (int i = steps; i > 0; i--) {
				float ratio = (float) i / (float) steps;
				int red = (int) (gradient[r].getRed() * ratio + gradient[r - 1]
						.getRed() * (1 - ratio));
				int green = (int) (gradient[r].getGreen() * ratio + gradient[r - 1]
						.getGreen() * (1 - ratio));
				int blue = (int) (gradient[r].getBlue() * ratio + gradient[r - 1]
						.getBlue() * (1 - ratio));
				Color stepColor = new Color(red, green, blue);
				g1.setColor(stepColor);
				g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
						+ ((i * 5) + (100 * r)), p.y);
				g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
						+ ((i * 5) + (100 * r)));
			}
		}
	}
	
	static String checkAutopilot() {
		if (autopilot) 
			return "Autopilot";
		else if (treesBeingCut == normals) 
			return "Logs";
		else if (treesBeingCut == oaks)
			return "Oaks";
		else
			return "Willows";
	}
	
	static String checkDoing() {
		if (drop && !sell)
			return "Dropping ";
		else if (actionbarDrop)
			return "Actiondropping ";
		else
			return "Selling ";
	}
}

