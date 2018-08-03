/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

/**
 *
 * @author Milten Plescott
 */
public class ToolBar extends JToolBar {

	private ToolBar() {
		super("toolbar", JToolBar.HORIZONTAL);
		setFloatable(false);
		add(new JButton("Left aligned button"));
		add(Box.createHorizontalGlue());
		add(new JLabel("Window size: "));
		add(new JButton("+"));
		add(new JButton("-"));
	}

	public static ToolBar getInstance() {
		return ToolBarHolder.INSTANCE;
	}

	private static class ToolBarHolder {

		private static final ToolBar INSTANCE = new ToolBar();
	}
}
