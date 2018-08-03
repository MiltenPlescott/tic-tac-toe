/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import shc.Colors;

/**
 *
 * @author Milten Plescott
 */
public class Frame extends JFrame {

	private Frame() {
		// system tray icon?
		super("Tic Tac Toe");
		setBackground(Colors.GREY);
		setSize(500, 400); // frame.getContentPane().getSize() gotta be a square at all times
		//this.setLocationRelativeTo(null);
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		addGuiComponents();
		//this.pack();
		setVisible(true);
	}

	private void addGuiComponents() {
		add(ToolBar.getInstance(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(GameBoard.getInstance(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

//	@Override
//	public Dimension getMinimumSize() {
//		return this.getPreferredSize();
//	}
//
//	@Override
//	public Dimension getMaximumSize() {
//		return this.getPreferredSize();
//	}
//
//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension(100, 100);
//	}
	public static Frame getInstance() {
		return FrameHolder.INSTANCE;
	}

	private static class FrameHolder {

		private static final Frame INSTANCE = new Frame();
	}
}
