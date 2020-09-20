/*
 * This file is part of JPGNViewer.
 *
 * JPGNViewer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPGNViewer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JPGNViewer.  If not, see <http://www.gnu.org/licenses/>. 
 */
package com.github.deianvn.pgnviewer.core;

import com.github.deianvn.pgnparse.PGNGame;
import com.github.deianvn.pgnparse.PGNMove;
import java.util.Iterator;

/**
 *
 * @author Deyan Rizov
 *
 */
public class GameManager {

	private PGNGame game;
	
	private Board board;
	
	private EventLine eventLine;

	/**
	 * @param game
	 * @throws MalformedPositionException 
	 * @throws EventGroupNotAvailableException 
	 */
	public GameManager(PGNGame game) throws EventGroupNotAvailableException, MalformedPositionException {
		
		if (game == null) {
			throw new NullPointerException("Argument cannot be null");
		}
		
		this.game = game;
		board = new Board();
		eventLine = new EventLine();
		
		initEventLine();
	}
	
	/**
	 * @return the game
	 */
	public PGNGame getGame() {
		return game;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPosition() {
		return eventLine.getPosition();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean next() {
		return eventLine.processNext();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean back() {
		return eventLine.processBack();
	}
	
	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean gotoMove(int move) {
		if (move < 0 || move >= eventLine.getSize() || eventLine.isEmpty()) {
			return false;
		}
		
		if (move == eventLine.getPosition()) {
			return true;
		}
		
		int direction = move - eventLine.getPosition();
		
		while (move != eventLine.getPosition()) {
			if (direction > 0) {
				eventLine.processNext();
			} else {
				eventLine.processBack();
			}
		}
		
		return true;
		
	}

	/**
	 * 
	 * @throws EventGroupNotAvailableException
	 * @throws MalformedPositionException
	 */
	private void initEventLine() throws EventGroupNotAvailableException, MalformedPositionException {
		Iterator<PGNMove> i = game.getMovesIterator();
		
		while (i.hasNext()) {
			PGNMove move = i.next();
			
			if (move.isEndGameMarked()) {
				continue;
			}
			
			eventLine.createEventGroup();
			
			if (move.isCastle()) {
				Castle castle;
				
				if (move.isKingSideCastle()) {
					castle = Castle.kingSide;
				} else {
					castle = Castle.queenSide;
				}
				
				eventLine.addEvent(new CastleEvent(board, castle, Color.fromString(move.getColor())));
				
				continue;
			}
			
			if (move.isCaptured()) {
				if (move.isEnpassantCapture()) {
					Position position = new Position(move.getEnpassantPieceSquare());
					eventLine.addEvent(new CaptureEvent(position, board));
				} else {
					Position position = new Position(move.getToSquare());
					eventLine.addEvent(new CaptureEvent(position, board));
				}
			}
			
			eventLine.addEvent(new MoveEvent(board.getSquare(move.getFromSquare()), board.getSquare(move.getToSquare())));
			
			if (move.isPromoted()) {
				eventLine.addEvent(new PromotionEvent(board.getSquare(move.getToSquare()), PieceFactory.createPiece(move.getPromotion(), Color.fromString(move.getColor()))));
			}
			
		}
	}
	
}
