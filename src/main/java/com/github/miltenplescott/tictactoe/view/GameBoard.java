/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.view;

import com.github.miltenplescott.tictactoe.model.Model;
import com.github.miltenplescott.tictactoe.model.Symbol;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Milten Plescott
 */
public class GameBoard extends JPanel {

	private GameBoardButton[] buttons = new GameBoardButton[Model.BOARD_SIZE];
	private final transient PropertyChangeListener propertyChangeListener;
	private static final String PROPERTY_NAME = "gameBoard";

	public GameBoard() {
		this.setLayout(new GridBagLayout());

		for (int i = 0; i < Model.BOARD_SIZE; i++) {
			buttons[i] = new GameBoardButton(" ");
			this.add(buttons[i], gridBagConstraintsWrapper(i));
		}

		propertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String property = evt.getPropertyName();
				if (evt instanceof IndexedPropertyChangeEvent) {
					int index = ((IndexedPropertyChangeEvent) evt).getIndex();
					if (property.equals(PROPERTY_NAME)) {
						buttons[index].setSymbol((Symbol) evt.getNewValue());
					}
				}
			}
		};
	}

	private static GridBagConstraints gridBagConstraintsWrapper(int i) {
		int row = (i / 3) % 3;
		int col = i % 3;
		return new GridBagConstraints(col, row, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
	}

	public void buttonFlash(int buttonIndex, Color flashColor, long duration) {
		GameBoardButton button = getButton(buttonIndex);
		Color originalColor = button.getForeground();
		button.setForeground(flashColor);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(duration);
				}
				catch (InterruptedException e) {
					//
				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						button.setForeground(originalColor);
					}
				});
			}
		}).start();
	}

	public GameBoardButton getButton(int i) {
		try {
			return buttons[i];
		}
		catch (IndexOutOfBoundsException | NullPointerException e) {
			return null;
		}
	}

	public void setActionListener(ActionListener listener) {
		for (GameBoardButton button : buttons) {
			button.addActionListener(listener);
		}
	}

	public PropertyChangeListener getPropertyChangeListener() {
		return propertyChangeListener;
	}

	public String getPropertyName() {
		return PROPERTY_NAME;
	}

}
