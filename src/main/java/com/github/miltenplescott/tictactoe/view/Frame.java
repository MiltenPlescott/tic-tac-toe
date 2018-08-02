/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

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
		this.setBackground(Colors.GREY);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addGuiComponents();
		this.setVisible(true);
	}

	private void addGuiComponents() {
		this.add(GameBoard.getInstance());
		this.add(Controls.getInstance());
	}

	public static Frame getInstance() {
		return FrameHolder.INSTANCE;
	}

	private static class FrameHolder {

		private static final Frame INSTANCE = new Frame();
	}
}
