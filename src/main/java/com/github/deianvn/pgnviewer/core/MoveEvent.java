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

public class MoveEvent implements Event {

	private final Square fromSquare;
	
	private final Square toSquare;


	public MoveEvent(Square fromSquare, Square toSquare) {
		if (fromSquare == null || toSquare == null) {
			throw new NullPointerException("Arguments cannot be null");
		}
		
		this.fromSquare = fromSquare;
		this.toSquare = toSquare;
	}

	@Override
	public void processForeward() {
		toSquare.setPiece(fromSquare.getPiece());
		fromSquare.removePiece();
	}

	@Override
	public void processBackwards() {
		fromSquare.setPiece(toSquare.getPiece());
		toSquare.removePiece();
	}

}
