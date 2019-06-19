/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe;

import com.github.miltenplescott.tictactoe.model.Symbol;
import com.github.miltenplescott.tictactoe.model.Difficulty;
import com.github.miltenplescott.tictactoe.controller.GameBoardController;
import com.github.miltenplescott.tictactoe.model.Model;
import com.github.miltenplescott.tictactoe.view.View;
import javax.swing.SwingUtilities;

public class Main {

	private static Symbol userSymbol;
	private static Difficulty difficulty;
	private static Boolean userStarts;

	public static void main(String[] args) {

		userSymbol = Symbol.X; // default
		difficulty = Difficulty.impossible; // default
		userStarts = true; // default

		if (args.length != 0) {
			parseArgs(args);
			if (userStarts) {
				System.out.println("Starting with following settings: " + userSymbol + ", " + difficulty + ", user goes first");
			}
			else {
				System.out.println("Starting with following settings: " + userSymbol + ", " + difficulty + ", computer goes first");
			}
		}
		else {
			System.out.println("Starting with default settings: " + userSymbol + ", " + difficulty + ", user goes first");
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Model model = new Model(userSymbol, difficulty, userStarts);
				View view = new View(model);
				GameBoardController.initGameBoardController(model, view);
				model.startNewGame();
			}
		});
	}

	private static void parseArgs(String[] args) {
		int i = 0;
		boolean error = false;

		while (i < args.length && args[i].startsWith("-")) {
			String arg = args[i];
			i++;
			if (arg.equals("-s") || arg.equals("--user-symbol")) {
				if (i < args.length) {
					try {
						userSymbol = parseSymbol(args[i]);
						i++;
					}
					catch (IllegalArgumentException e) {
						error = true;
						System.err.println("Symbol argument is not recognized.");
					}
				}
				else {
					error = true;
					System.err.println("Symbol argument is missing.");
				}
			}
			else if (arg.equals("-d") || arg.equals("--difficulty")) {
				if (i < args.length) {
					try {
						difficulty = parseDifficulty(args[i]);
						i++;
					}
					catch (IllegalArgumentException e) {
						error = true;
						System.err.println("Difficulty argument is not recognized.");
					}
				}
				else {
					error = true;
					System.err.println("Difficulty argument is missing.");
				}
			}
			else if (arg.equals("-u") || arg.equals("--user-starts")) {
				if (i < args.length) {
					try {
						userStarts = parseUserStarts(args[i]);
						i++;
					}
					catch (IllegalArgumentException e) {
						error = true;
						System.err.println("User starts argument is not recognized.");
					}
				}
				else {
					error = true;
					System.err.println("User starts argument is missing.");
				}
			}
		}

		if (error) {
			System.err.println("Usage: java -jar tic-tac-toe.jar "
				+ "[-s|--user-symbol <symbol>] "
				+ "[-d|--difficulty <difficulty name|number>] "
				+ "[-u|--user-starts <boolean>]");
			System.err.println("Available symbols: X, O");
			System.err.println("Available difficulty: random (1), easy (2), impossible (3)");
			System.err.println("Available boolean: true (1), false (0)");
		}
	}

	private static Symbol parseSymbol(String str) throws IllegalArgumentException {
		switch (str) {
			case "x":
			case "X":
				return Symbol.X;
			case "o":
			case "O":
			case "0":
				return Symbol.O;
			default:
				throw new IllegalArgumentException();
		}
	}

	private static Difficulty parseDifficulty(String str) throws IllegalArgumentException {
		switch (str.toLowerCase()) {
			case "1":
			case "random":
				return Difficulty.random;
			case "2":
			case "easy":
				return Difficulty.easy;
			case "3":
			case "impossible":
				return Difficulty.impossible;
			default:
				throw new IllegalArgumentException();
		}
	}

	private static Boolean parseUserStarts(String str) throws IllegalArgumentException {
		switch (str.toLowerCase()) {
			case "1":
			case "true":
				return true;
			case "0":
			case "false":
				return false;
			default:
				throw new IllegalArgumentException();
		}
	}

}
