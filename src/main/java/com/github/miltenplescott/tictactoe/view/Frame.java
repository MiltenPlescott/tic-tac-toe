/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.view;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JFrame;
import com.github.miltenplescott.tictactoe.Colors;
import com.github.miltenplescott.tictactoe.model.Difficulty;
import com.github.miltenplescott.tictactoe.model.GameState;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Milten Plescott
 */
public class Frame extends JFrame {

	private GameBoard gameBoard;
	private ToolBar toolbar;
	// taskbar excluded
	private final int screenWidth;
	private final int screenHeight;
	private final int screenShorterDimension;
	private final int screenShorterDimensionDivisibleBy9;
	private int widthExcludingGameBoard;
	private int heightExcludingGameBoard;

	private final transient PropertyChangeListener propertyChangeListener;
	private static final String PROPERTY_NAME = "gameState";
	private Difficulty difficulty;

	public Frame() {
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

		propertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(PROPERTY_NAME)) {
					String gameResult = null;
					switch ((GameState) evt.getNewValue()) {
						case tie:
							gameResult = "Game ended in a tie.";
							break;
						case winnerUser:
							if (Frame.this.difficulty == Difficulty.impossible) {
								gameResult = "<html>User won the game, despite the 'impossible' game difficulty."
									+ "<br>"
									+ "<br> Please, report this bug at milten.plescott@gmx.com or create a new issue at https://github.com/MiltenPlescott/tic-tac-toe/issues/new"
									+ "<br> with a list of your moves.";
							}
							else {
								gameResult = "User won the game!";
							}
							break;
						case winnerAi:
							gameResult = "AI won the game!";
							break;
						case inProgress:
							throw new AssertionError("Should not happen - property should not get fired.");
					}
					JOptionPane.showMessageDialog(Frame.this, gameResult, "Game Over", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(gameResult);
				}
			}
		};
	}

	public void addGuiComponents() {
		toolbar = new ToolBar(this);
		add(toolbar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		gameBoard = new GameBoard();
		add(gameBoard, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

	public void calculateDimensions() {
		// border width 6px
		// border height 31px
		// toolbar height 47px

		widthExcludingGameBoard = getWidth() - getContentPane().getSize().width;
		heightExcludingGameBoard = getHeight() - getContentPane().getHeight() + toolbar.getHeight();
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
		toolbar.setSliderRange(3 * minButtonSize, maxGameBoardSize, value);
		toolbar.addSliderListener(); // adds the listener and enables the slider;
	}

	protected void changeFrameSize(int newGameBoardSize) {
		int width = newGameBoardSize + widthExcludingGameBoard;
		int height = newGameBoardSize + heightExcludingGameBoard;

		if (toolbar.getSlider().getMaximum() == newGameBoardSize) {
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
	}

	public int getScreenShorterDimension() {
		return screenShorterDimension;
	}

	public int getScreenShorterDimensionDivisibleBy9() {
		return screenShorterDimensionDivisibleBy9;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public PropertyChangeListener getPropertyChangeListener() {
		return propertyChangeListener;
	}

	public String getPropertyName() {
		return PROPERTY_NAME;
	}

	protected void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

}
