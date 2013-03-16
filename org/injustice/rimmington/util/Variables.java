package org.injustice.rimmington.util;

import java.awt.Point;
import java.awt.Rectangle;

import org.powerbot.core.script.util.Timer;
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.wrappers.Tile;

public class Variables {
	public static Point p;
	public static Rectangle close = new Rectangle(8, 398, 503, 125);
	public static Rectangle open = new Rectangle(8, 398, 503, 125);

	/*
	 * Trees IDs
	 */
	public static int[] willows = { 38616, 38627 };
	public static int[] normals = { 38760, 38782, 38783, 38785, 38787, 38788 };
	public static int[] oaks = { 38731, 38732 };
	public static int[] yews = { 38755, };
	public static int[] treesBeingCut = normals;

	/*
	 * Inventory IDs
	 */
	public static int normalsID = 1511;
	public static int oaksID = 1521;
	public static int willowsID = 1519;
	public static int yewsID = 1515;
	public static int logUsed = normalsID;

	/*
	 * Tiles
	 */

	public static Tile shopTile = new Tile(2948, 3217, 0);
	public static Tile willowTile = new Tile(2974, 3196, 0);
	public static Tile depositTile = new Tile(3047, 3236, 0);
	public static Tile oakTile = new Tile(2983, 3205, 0);
	public static Tile yewTile = new Tile(2938, 3232, 0);
	public static Tile normalTile = new Tile(2948, 3239, 0);
	public static Tile cuttingTile = normalTile;

	/*
	 * Paint
	 */

	public static String chopping = "Normal";
	public static int startExp, startLevel, price, startCount;
	public static int logsCut, logsChopped;
	public static long startTime;
	public static final double version = 3.0;
	public static String timeTNL;
	public static Timer runTime = new Timer(0);
	public static String status;
	public static float timeHours;
	public static boolean antiban, showPaint;

	/*
	 * GUI
	 */

	public static boolean hide = false;
	public static boolean guiDone = false;
	public static boolean autopilot = false;
	public static boolean drop = false;
	public static boolean sell = false;
	public static boolean actionbarDrop = false;
	
	/*
	 * Other
	 */

	public static int keeper = 530;
}
