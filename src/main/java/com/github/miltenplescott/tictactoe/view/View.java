/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

/**
 *
 * @author Milten Plescott
 */
public class View {

	private View() {
		Frame frame = Frame.getInstance();
		frame.addGuiComponents();
		frame.setVisible(true);
		frame.calculateDimensions();
		frame.sliderSetup();
	}

	public static View getInstance() {
		return ViewHolder.INSTANCE;
	}

	private static class ViewHolder {

		private static final View INSTANCE = new View();
	}
}
