package org.injustice.rimmington;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import org.injustice.rimmington.strat.ActionChop;
import org.injustice.rimmington.strat.ActionDrop;
import org.injustice.rimmington.strat.Chop;
import org.injustice.rimmington.strat.Drop;
import org.injustice.rimmington.strat.Sell;
import org.injustice.rimmington.strat.WalkShop;
import org.injustice.rimmington.strat.WalkTrees;
import org.injustice.rimmington.util.GUI;
import org.injustice.rimmington.util.Paint;
import org.injustice.rimmington.util.Utilities;

import static org.injustice.rimmington.util.Variables.*;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "Injustice" }, description = "Chops willows and sells them in Rimmington", name = "Rimmington Chopper", version = version)
public class RimmingtonChopper extends ActiveScript implements PaintListener,
		MouseListener, MessageListener {

	private final List<Node> jobsCollection = Collections
			.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

	public final void provide(final Node... jobs) {
		for (final Node job : jobs) {
			if (!jobsCollection.contains(job)) {
				jobsCollection.add(job);
			}
		}
		jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection
				.size()]));
	}

	public final void submit(final Job job) {
		getContainer().submit(job);
	}

	public void onStart() {
		provide(new ActionChop(), new WalkShop(), new Sell(), new WalkTrees(),
				new Drop(), new Chop());
		startExp = Skills.getExperience(Skills.WOODCUTTING);
		startLevel = Skills.getLevel(Skills.WOODCUTTING);
		startTime = System.currentTimeMillis();
		startCount = 28 - Inventory.getCount();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public int loop() {
		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		}
		return Random.nextInt(10, 50);
	}

	@Override
	public void onRepaint(Graphics g) {
		Paint.onRepaint(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		p = e.getPoint();
		if (close.contains(p) && !hide) {
			hide = true;
		} else if (open.contains(p) && hide) {
			hide = false;
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(MessageEvent m) {
		String s = m.getMessage();
		if (s.contains("You get some"))
			logsCut++;

	}

}