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
public class PromotionEvent implements Event {

	private Square square;
	
	private Piece piece;
	
	private Pawn pawn;

	/**
	 * @param square
	 * @param piece
	 */
	public PromotionEvent(Square square, Piece piece) {
		if (square == null || piece == null) {
			throw new NullPointerException("Arguments cannot be null");
		}
		
		this.square = square;
		this.piece = piece;
	}

	/* (non-Javadoc)
	 * @see com.codethesis.jpgnviewer.core.Event#processForeward()
	 */
	@Override
	public void processForeward() throws ProcessException {
		try {
			pawn = (Pawn)square.getPiece();
		} catch (ClassCastException e) {
			throw new ProcessException(e);
		}
		
		square.setPiece(piece);

	}

	/* (non-Javadoc)
	 * @see com.codethesis.jpgnviewer.core.Event#processBackwards()
	 */
	@Override
	public void processBackwards() throws ProcessException {
		if (pawn == null) {
			throw new ProcessException(new NullPointerException("Pawn not found"));
		}
		
		square.setPiece(pawn);
	}

}
