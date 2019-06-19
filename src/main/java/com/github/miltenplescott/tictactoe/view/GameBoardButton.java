/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.view;

import com.github.miltenplescott.tictactoe.Colors;
import com.github.miltenplescott.tictactoe.model.Symbol;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author Milten Plescott
 */
public class GameBoardButton extends JButton {

	public GameBoardButton(String buttonName) {
		super(buttonName);
		this.setFont(new Font("Monospaced", Font.BOLD, 40));
		this.setBackground(Colors.GREY);
		this.setFocusable(false);
	}

	public void setSymbol(Symbol symbol) {
		if (symbol == Symbol.none) {
			this.setText(" ");
			this.setForeground(this.getBackground());
		}
		else {
			this.setText(symbol.toString());
			this.setForeground(Colors.BLACK);
		}
	}

	public Color getColor() {
		return this.getForeground();
	}

	public void setFontSize(int fontSize) {
		this.setFont(this.getFont().deriveFont(fontSize));
	}

}
