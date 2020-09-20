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
public class CastleEvent implements Event {
	
	private Board board;
	
	private Castle castle;
	
	private Color color;
	
	/**
	 * @param board
	 * @param castle
	 * @param color
	 */
	public CastleEvent(Board board, Castle castle, Color color) {
		if (board == null || castle == null || color == null) {
			throw new NullPointerException("Arguments cannot be null");
		}
		
		this.board = board;
		this.castle = castle;
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see com.codethesis.jpgnviewer.core.Event#processForeward()
	 */
	@Override
	public void processForeward() {
		
		if (color == Color.WHITE) {
			if (castle == Castle.kingSide) {
				try {
					board.move(4, 0, 6, 0);
					board.move(7, 0, 5, 0);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			} else {
				try {
					board.move(4, 0, 2, 0);
					board.move(0, 0, 3, 0);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (castle == Castle.kingSide) {
				try {
					board.move(4, 7, 6, 7);
					board.move(7, 7, 5, 7);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			} else {
				try {
					board.move(4, 7, 2, 7);
					board.move(0, 7, 3, 7);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.codethesis.jpgnviewer.core.Event#processBackwards()
	 */
	@Override
	public void processBackwards() {
		
		if (color == Color.BLACK) {
			if (castle == Castle.kingSide) {
				try {
					board.move(6, 0, 4, 0);
					board.move(5, 0, 7, 0);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			} else {
				try {
					board.move(2, 0, 4, 0);
					board.move(3, 0, 0, 0);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (castle == Castle.kingSide) {
				try {
					board.move(6, 7, 4, 7);
					board.move(5, 7, 7, 7);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			} else {
				try {
					board.move(2, 7, 4, 7);
					board.move(3, 7, 0, 7);
				} catch (MalformedPositionException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
