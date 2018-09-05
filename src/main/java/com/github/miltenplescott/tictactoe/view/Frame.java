/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JFrame;
import com.github.miltenplescott.tictactoe.Colors;

/**
 *
 * @author Milten Plescott
 */
public class Frame extends JFrame {

	// taskbar excluded
	private final int screenWidth;
	private final int screenHeight;
	private final int screenShorterDimension;
	private final int screenShorterDimensionDivisibleBy9;
	private int widthExcludingGameBoard;
	private int heightExcludingGameBoard;

	private Frame() {
		super("Tic Tac Toe");
		setBackground(Colors.GREY);
		setResizable(false);

		/*
		 *  horizontal: 3x100 + 6 border = 306
		 *  vertical: 3x100 + 47 toolbar + 31 border = 378
		 */
		setSize(306, 378); // gets changed after in sliderSetup()

		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		screenShorterDimension = screenHeight < screenWidth ? screenHeight : screenWidth;
		screenShorterDimensionDivisibleBy9 = screenShorterDimension - (screenShorterDimension % 9);

		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
	}

	public void addGuiComponents() {
		add(ToolBar.getInstance(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(GameBoard.getInstance(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

	public void calculateDimensions() {
		System.out.println("frame size: \t\t" + getWidth() + " x " + getHeight());
		System.out.println("frame content pane: \t" + getContentPane().getSize().width + " x " + getContentPane().getSize().height);
		System.out.println("toolbar: \t\t" + ToolBar.getInstance().getWidth() + " x " + ToolBar.getInstance().getHeight());
		System.out.println("middle button: \t\t" + GameBoard.getInstance().getButtons().get(1).get(1).getWidth() + " x " + GameBoard.getInstance().getButtons().get(1).get(1).getHeight());
		// border width 6px
		// border height 31px
		// toolbar height 47px

		widthExcludingGameBoard = getWidth() - getContentPane().getSize().width;
		heightExcludingGameBoard = getHeight() - getContentPane().getHeight() + ToolBar.getInstance().getHeight();
	}

	public void sliderSetup() {
		int minButtonSize = 51; // so 3*51 is divisible by 9
		int maxGameBoardSize;

		if ((widthExcludingGameBoard / heightExcludingGameBoard) < (screenWidth / screenHeight)) {
			maxGameBoardSize = screenHeight - heightExcludingGameBoard;
		}
		else {
			maxGameBoardSize = screenWidth - widthExcludingGameBoard;
		}

		maxGameBoardSize = maxGameBoardSize - (maxGameBoardSize % 9);

		int value = (3 * minButtonSize + maxGameBoardSize) / 2 - ((3 * minButtonSize + maxGameBoardSize) / 2) % 9;
		setSize(value + widthExcludingGameBoard, value + heightExcludingGameBoard);
		ToolBar.getInstance().setSliderRange(3 * minButtonSize, maxGameBoardSize, value);
		ToolBar.getInstance().addSliderListener(); // adds the listener and enables the slider;
	}

	protected void changeFrameSize(int newGameBoardSize) {
		int width = newGameBoardSize + widthExcludingGameBoard;
		int height = newGameBoardSize + heightExcludingGameBoard;

		if (ToolBar.getInstance().getSlider().getMaximum() == newGameBoardSize) {
			if (screenWidth > screenHeight) {
				setBounds(getX(), 0, width, height);
			}
			else {
				setBounds(0, getY(), width, height);
			}
		}
		else {
			setSize(width, height);
		}

		System.out.println("middle button: " + GameBoard.getInstance().getButton(1, 1).getWidth() + " x " + GameBoard.getInstance().getButton(1, 1).getHeight());
	}

	public int getScreenShorterDimension() {
		return screenShorterDimension;
	}

	public int getScreenShorterDimensionDivisibleBy9() {
		return screenShorterDimensionDivisibleBy9;
	}

	public static Frame getInstance() {
		return FrameHolder.INSTANCE;
	}

	private static class FrameHolder {

		private static final Frame INSTANCE = new Frame();
	}
}
