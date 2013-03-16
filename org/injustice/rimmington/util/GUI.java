package org.injustice.rimmington.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.rimmington.util.Variables.*;

public class GUI extends JFrame {

	public GUI() {
		super("GUI");
		setResizable(false);
		setTitle("GUI");
		initComponents();
		getContentPane().setLayout(null);
		
		JLabel lblWhatToDo = new JLabel("What to do");
		lblWhatToDo.setBounds(6, 61, 138, 14);
		getContentPane().add(lblWhatToDo);
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Sell", "Drop", "Actionbar drop"}));
		comboBox.setBounds(6, 86, 139, 22);
		getContentPane().add(comboBox);
		setSize(155, 180);
	}

	private void initComponents() {
		logsToUse.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Normal", "Oak", "Willow", "Autopilot" }));
		logsToUse.setBounds(6, 28, 139, 22);
		getContentPane().add(logsToUse);

		JLabel label_1 = new JLabel("Logs to cut");
		label_1.setBounds(6, 11, 138, 14);
		getContentPane().add(label_1);

		JButton button = new JButton("Start");
		button.setBounds(6, 120, 139, 23);
		getContentPane().add(button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}

			private void jButton1ActionPerformed(ActionEvent evt) {
				if (logsToUse.getSelectedItem() != null) {
					index = (logsToUse.getSelectedIndex());
					status = "GUI complete";
					status = "In setup";
					String choice = logsToUse.getSelectedItem().toString();
					if (choice == "Normal") {
						treesBeingCut = normals;
						logUsed = normalsID;
						cuttingTile = normalTile;
						chopping = "Normal";
						price = 1;
					} else if (choice == "Oak") {
						treesBeingCut = oaks;
						logUsed = oaksID;
						cuttingTile = oakTile;
						chopping = "Oak";
						price = 6;
					} else if (choice == "Willow") {
						treesBeingCut = willows;
						logUsed = willowsID;
						cuttingTile = willowTile;
						chopping = "Willow";
						price = 12;
					} else if (choice == "Autopilot") {
						autopilot = true;
						if (Skills.getRealLevel(Skills.WOODCUTTING) < 15) {
							treesBeingCut = normals;
							cuttingTile = normalTile;
							logUsed = normalsID;
							price = 1;
						} else if (Skills.getRealLevel(Skills.WOODCUTTING) < 30
								&& Skills.getRealLevel(Skills.WOODCUTTING) > 15) {
							treesBeingCut = oaks;
							cuttingTile = oakTile;
							logUsed = oaksID;
							price = 6;
						} else if (Skills.getRealLevel(Skills.WOODCUTTING) > 30) {
							treesBeingCut = willows;
							cuttingTile = willowTile;
							logUsed = willowsID;
							price = 12;
						}
					}
				}
				if (comboBox.getSelectedItem() != null) {
					String choice = comboBox.getSelectedItem().toString();
					if (choice == "Drop") {
						actionbarDrop = false;
						drop = true;
						sell = false;
						price = 0;
					} else if (choice == "Sell") {
						actionbarDrop = false;
						drop = false;
						sell = true;
						price = 0;
					} else if (choice == "Actionbar drop") {
						actionbarDrop = true;
						drop = false;
						sell = false;
						price = 0;
					}
				}
				status = "Gui complete";
				guiDone = true;
				dispose();
			}
		});


	}
	JComboBox<String> comboBox = new JComboBox<String>();
	JComboBox<String> logsToUse = new JComboBox<String>();
	public static int index;
	final JLabel jLabel2 = new JLabel("Logs to cut");
	final JButton jButton1 = new JButton("Start");
}
