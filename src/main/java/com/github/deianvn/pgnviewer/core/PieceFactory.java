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

import com.github.deianvn.pgnparse.PGNParser;

/**
 * 
 *
 * @author Deyan Rizov
 *
 */
public class PieceFactory {

	public static Piece createPiece(String piece, Color color) {
		if (piece == null) {
			throw new NullPointerException("Argument cannot be null");
		}
		
		if (piece.equals(PGNParser.PAWN)) {
			return new Pawn(color);
		} else if (piece.equals(PGNParser.KNIGHT)) {
			return new Knight(color);
		}  else if (piece.equals(PGNParser.BISHOP)) {
			return new Bishop(color);
		}  else if (piece.equals(PGNParser.ROOK)) {
			return new Rook(color);
		}  else if (piece.equals(PGNParser.QUEEN)) {
			return new Queen(color);
		}  else if (piece.equals(PGNParser.KING)) {
			return new King(color);
		} 
		
		return null;
	}
	
}
