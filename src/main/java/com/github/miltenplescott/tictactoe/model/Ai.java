/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Milten Plescott
 */
public class Ai {

	private static final Random RNG = new Random(System.currentTimeMillis());
	private static final int CENTER = 4;
	private static final int[] CORNERS = {0, 2, 6, 8};
	private static final int[] CENTERS_OF_SIDES = {1, 3, 5, 7};

	private static Difficulty difficulty;
	private static Symbol userSymbol;
	private static Symbol aiSymbol;

	private static int userMoves;
	private static int[] firstTwoMoves;
	private static List<Integer> freeToMoveCells;
	private static List<Map<Integer, Symbol>> userWinningPatterns; // all possible user winning triplets
	private static List<Map<Integer, Symbol>> aiWinningPatterns; // all possible ai winning triplets

	protected static void init(Difficulty difficulty, Symbol userSymbol, Symbol aiSymbol) {
		Ai.difficulty = difficulty;
		Ai.userSymbol = userSymbol;
		Ai.aiSymbol = aiSymbol;
	}

	protected static void reset() {
		userMoves = 0;
		firstTwoMoves = new int[2];
		freeToMoveCells = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
		userWinningPatterns = setDefaultPatterns();
		aiWinningPatterns = setDefaultPatterns();
	}

	private static List<Map<Integer, Symbol>> setDefaultPatterns() {
		List<Map<Integer, Symbol>> patterns = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			patterns.add(new HashMap<>(3));
		}

		/*
		i = 0, 1, 2
		k = 3
		patterns:
			rows:
				0 * k + i -> 012
				1 * k + i -> 345
				2 * k + i -> 678
			cols:
				i * k + 0 -> 036
				i * k + 1 -> 147
				i * k + 2 -> 258
			diag:
				i * k + i -> 048
				(i + 1) * (k - 1) -> 246
		 */
		int boardSide = 3;
		int patternIndex = 0;
		for (int i = 0; i < boardSide; i++) {
			for (int j = 0; j < boardSide; j++) { // horizontal patterns
				patterns.get(patternIndex).put(i * boardSide + j, Symbol.none);
			}
			patternIndex++;
			for (int j = 0; j < boardSide; j++) { // vertical patterns
				patterns.get(patternIndex).put(j * boardSide + i, Symbol.none);
			}
			patternIndex++;
		}
		for (int i = 0; i < boardSide; i++) { // main diagonal pattern
			patterns.get(patternIndex).put(i * boardSide + i, Symbol.none);
		}
		patternIndex++;
		for (int i = 0; i < boardSide; i++) { // antidiagonal pattern
			patterns.get(patternIndex).put((i + 1) * (boardSide - 1), Symbol.none);
		}

		return patterns;
	}

	// when ai starts the game ai moves in the center
	protected static int aiMove() {
		int aiMove = Ai.CENTER;
		freeToMoveCells.remove((Integer) aiMove);
		fillMoveInPatterns(aiWinningPatterns, aiMove);
		deleteBlockedPatterns(userWinningPatterns, aiMove);
		return aiMove;
	}

	// returns 0-8
	protected static int aiMove(int userMove) {
		if (freeToMoveCells.contains(userMove)) {
			freeToMoveCells.remove((Integer) userMove);
			fillMoveInPatterns(userWinningPatterns, userMove);
			deleteBlockedPatterns(aiWinningPatterns, userMove);
			userMoves++;
		}
		else {
			throw new IllegalArgumentException("Requested user move does not seem to be valid.");
		}
		Integer aiMove = null;

		switch (freeToMoveCells.size()) {
			case 0:
				throw new UnsupportedOperationException("AI is out of moves");
			case 1:
				return freeToMoveCells.remove(0); // don't need to deleteBlockedPatterns() since it's the last one
			default:
				switch (Ai.difficulty) {
					case random:
						aiMove = aiRandom();
						break;
					case easy:
						aiMove = aiEasy();
						break;
					case impossible:
						aiMove = aiImpossible(userMove);
						break;
				}
				break;
		}

		if (aiMove == null) {
			throw new AssertionError();
		}
		freeToMoveCells.remove((Integer) aiMove);
		fillMoveInPatterns(aiWinningPatterns, aiMove);
		fillMoveInPatterns(aiWinningPatterns, aiMove);
		deleteBlockedPatterns(userWinningPatterns, aiMove);
		return aiMove;
	}

	private static int aiRandom() {
		return freeToMoveCells.get(RNG.nextInt(freeToMoveCells.size()));
	}

	private static int aiEasy() {
		Integer aiMove;

		if (userMoves == 1) {
			aiMove = aiRandom();
		}
		else { // win > block > random
			aiMove = getTheWin();
			if (aiMove == null) {
				aiMove = blockTheWin();
				if (aiMove == null) {
					aiMove = aiRandom();
				}
			}
		}
		if (freeToMoveCells.contains(aiMove) == false) {
			throw new AssertionError("Should not happen.");
		}
		return aiMove;
	}

	/*
	priorities
	1 - get the win
	2 - block the win
	3 - get the center
	4 - freetomove.size == 6 AND user got opposite corners -> go to side
    5 - go to random corner
	6 - go to a random
	 */
	private static int aiImpossible(int userMove) {
		switch (userMoves) {
			case 1:
				firstTwoMoves[0] = userMove;
				break;
			case 2:
				firstTwoMoves[1] = userMove;
				break;
			default:
				break;
		}

		Integer aiMove = getTheWin();
		if (aiMove == null) {
			aiMove = blockTheWin();
			if (aiMove == null) {
				aiMove = Ai.CENTER;
				if (!freeToMoveCells.contains(aiMove)) {
					// 6 cells free to move -> 3 cells taken
					// 3 cells taken and AI is on the move -> user started the game and moved twice, ai moved once
					// userStartedOppositeCorners -> there is a user-ai-user diagonal
					if (freeToMoveCells.size() == 6 && userStartedOppositeCorners()) {
						aiMove = getRandomSide();
					}
					else {
						aiMove = getRandomCorner();
					}
				}
			}
		}
		if (aiMove == null) {
			aiMove = aiRandom();
		}
		if (freeToMoveCells.contains(aiMove) == false) {
			throw new AssertionError("Should not happen.");
		}
		return aiMove;
	}

	private static void fillMoveInPatterns(List<Map<Integer, Symbol>> patterns, int moveIndex) {
		Symbol sym;

		if (patterns == userWinningPatterns) {
			sym = userSymbol;
		}
		else if (patterns == aiWinningPatterns) {
			sym = aiSymbol;
		}
		else {
			throw new AssertionError("Should not happen.");
		}

		for (Map<Integer, Symbol> map : patterns) {
			if (map.containsKey(moveIndex)) {
				map.put(moveIndex, sym);
			}
		}
	}

	// diagonally opposite
	private static boolean userStartedOppositeCorners() {
		boolean bool = false;
		Integer oppositeCorner = getOppositeCorner(firstTwoMoves[1]);
		if (oppositeCorner != null) {
			if (firstTwoMoves[0] == oppositeCorner) {
				return true;
			}
		}
		return bool;
	}

	// diagonally opposite
	private static Integer getOppositeCorner(int corner) {
		switch (corner) {
			case 0:
				return 8;
			case 2:
				return 6;
			case 6:
				return 2;
			case 8:
				return 0;
		}
		return null;
	}

	// returns null if no center of side is empty
	private static Integer getRandomSide() {
		List<Integer> emptySides = new ArrayList<>();
		for (int i : Ai.CENTERS_OF_SIDES) {
			if (freeToMoveCells.contains(i)) {
				emptySides.add(i);
			}
		}
		if (emptySides.size() > 0) {
			return emptySides.get(RNG.nextInt(emptySides.size()));
		}
		return null;
	}

	// returns null if no corner is empty
	private static Integer getRandomCorner() {
		List<Integer> emptyCorner = new ArrayList<>();
		for (int i : Ai.CORNERS) {
			if (freeToMoveCells.contains(i)) {
				emptyCorner.add(i);
			}
		}
		if (emptyCorner.size() > 0) {
			return emptyCorner.get(RNG.nextInt(emptyCorner.size()));
		}
		return null;
	}

	// returns null if there are no 2 ai symbols and 1 empty spot in a row/col/diag
	private static Integer getTheWin() {
		for (Map<Integer, Symbol> map : aiWinningPatterns) {
			int countAiSymbols = 0;
			boolean hasEmpty = false;
			Integer indexOfEmpty = null;
			for (Map.Entry<Integer, Symbol> entry : map.entrySet()) {
				if (entry.getValue() == aiSymbol) {
					countAiSymbols++;
				}
				else if (entry.getValue() == userSymbol) {
					break;
				}
				else {
					hasEmpty = true;
					indexOfEmpty = entry.getKey();
				}
			}
			if (countAiSymbols == 2 && hasEmpty) {
				return indexOfEmpty;
			}
		}
		return null;
	}

	// returns null if there are no 2 user symbols and 1 empty spot in a row/col/diag
	private static Integer blockTheWin() {
		for (Map<Integer, Symbol> map : userWinningPatterns) {
			int countUserSymbols = 0;
			boolean hasEmpty = false;
			Integer indexOfEmpty = null;
			for (Map.Entry<Integer, Symbol> entry : map.entrySet()) {
				if (entry.getValue() == userSymbol) {
					countUserSymbols++;
				}
				else if (entry.getValue() == aiSymbol) {
					break;
				}
				else {
					hasEmpty = true;
					indexOfEmpty = entry.getKey();
				}
			}
			if (countUserSymbols == 2 && hasEmpty) {
				return indexOfEmpty;
			}
		}
		return null;
	}

	private static void deleteBlockedPatterns(List<Map<Integer, Symbol>> patterns, int move) {
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).containsKey(move)) {
				patterns.remove(patterns.get(i));
				i--;
			}
		}
	}

}
