/**
 * 
 */
package com.github.deianvn.pgnviewer.test;

import com.github.deianvn.pgnviewer.core.Color;
import com.github.deianvn.pgnviewer.core.GameManager;

import com.github.deianvn.pgnviewer.core.Board;
import com.github.deianvn.pgnviewer.core.MalformedPositionException;
import com.github.deianvn.pgnviewer.core.Piece;

/**
 *
 * @author Deyan Rizov
 *
 */
public class TestGameManager {

	/**
		if (args.length == 0) {
			System.out.println("Usage:");
			System.out.println("\tpgn_path");
		}
		
		File file = new File(args[0]);
		
		if (!file.exists()) {
			System.out.println("File does not exist!");
			return;
		}
		
		PGNSource source = new PGNSource(file);
		Iterator<PGNGame> i = source.listGames().iterator();
		
		while (i.hasNext()) {
			PGNGame game = i.next();
			GameManager manager = new GameManager(game);
			char ch;
			
			while ((ch = (char)System.in.read()) != 'q') {
				switch (ch) {
				case '.' :
					manager.next();
					printBoard(manager);
					break;
				case ',' :
					manager.back();
					printBoard(manager);
					break;
				}
			}
		}

	}
	*/

	private static void printBoard(GameManager manager) {
		Board board = manager.getBoard();
		System.out.println(manager.getGame().getMove(manager.getPosition() - 1).getFullMove());
		
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				Piece piece = null;
				
				try {
					piece = board.getPiece(j, i);
				} catch (MalformedPositionException e) {}
				
				if (piece != null) {
					if (piece.getColor() == Color.BLACK) {
						System.out.print(piece);
					} else {
						System.out.print(piece);
					}
				} else {
					System.out.print("_");
				}
			}
			
			System.out.println();
		}
		
		System.out.println();
	}

}
