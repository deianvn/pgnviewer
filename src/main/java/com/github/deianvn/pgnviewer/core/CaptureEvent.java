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

/**
 *
 * @author Deyan Rizov
 *
 */
public class CaptureEvent implements Event {
	
	private Position position;
	
	private Board board;
	
	private Piece piece;

	/**
	 * @param position
	 * @param board
	 */
	public CaptureEvent(Position position, Board board) {
		if (position == null || board == null) {
			throw new NullPointerException("Arguments cannot be null");
		}
		
		this.position = position;
		this.board = board;
	}

	/* (non-Javadoc)
	 * @see com.codethesis.jpgnviewer.core.Event#processForeward()
	 */
	@Override
	public void processForeward() {
		try {
			piece = board.getPiece(position);
			board.capture(piece);
		} catch (MalformedPositionException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.codethesis.jpgnviewer.core.Event#processBackwards()
	 */
	@Override
	public void processBackwards() {
		
		board.release(piece, position);

	}

}
