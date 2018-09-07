/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe;

import com.github.miltenplescott.tictactoe.model.Symbol;
import com.github.miltenplescott.tictactoe.model.Difficulty;
import com.github.miltenplescott.tictactoe.controller.GameBoardController;
import com.github.miltenplescott.tictactoe.model.Model;
import com.github.miltenplescott.tictactoe.view.View;
import javax.swing.SwingUtilities;

public class Main {

	/*


	another cmd line argument for difficulty:  random, easy, impossible
	random is... well... random
	easy will only try to block 2 in a row, otherwise it's random
	impossible: user will be able to tie at best


	 */
	private static Symbol userSymbol;
	private static Difficulty difficulty;

	public String getGreeting() {
		return "Hello world.";
	}

	// to suppressing SpotBugs warning
	private static void newInstanceNotUsedWarning(View view) {
	}

	// to suppressing SpotBugs warning
	private static void newInstanceNotUsedWarning(GameBoardController gbc) {
	}

	public static void main(String[] args) {

		userSymbol = Symbol.X;
		difficulty = Difficulty.impossible;
		if (args.length != 0) {
			parseArgs(args);
		}

		System.out.println(new Main().getGreeting());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				View view = View.getInstance();
				newInstanceNotUsedWarning(view);
				Model model = new Model(userSymbol, difficulty);
				GameBoardController gbc = new GameBoardController(model, view);
				newInstanceNotUsedWarning(gbc);
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
					Symbol sym = parseSymbol(args[i]);
					if (sym != null) {
						userSymbol = sym;
						i++;
					}
					else {
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
					Difficulty dif = parseDifficulty(args[i]);
					if (dif != null) {
						difficulty = dif;
						i++;
					}
					else {
						error = true;
						System.err.println("Difficulty argument is not recognized.");
					}
				}
				else {
					error = true;
					System.err.println("Difficulty argument is missing.");
				}
			}
		}

		if (error) {
			System.err.println("Usage: java -jar tic-tac-toe.jar [-s|--user-symbol <symbol>] [-d|--difficulty <difficulty name|number>]");
			System.err.println("Available symbols: X, O");
			System.err.println("Available difficulty: random (1), easy (2), impossible (3)");
		}
		System.out.println("Starting with following settings: " + userSymbol + ", " + difficulty);

	}

	private static Symbol parseSymbol(String str) {
		switch (str) {
			case "x":
			case "X":
				return Symbol.X;
			case "o":
			case "O":
			case "0":
				return Symbol.O;
			default:
				return null;
		}
	}

	private static Difficulty parseDifficulty(String str) {
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
				return null;
		}
	}

}
