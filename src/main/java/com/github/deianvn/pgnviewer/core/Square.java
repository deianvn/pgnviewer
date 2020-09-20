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
public class Square {

	private Color color;
	
	private Piece piece;
	
	private Piece enpassantPiece;
	
	/**
	 * @param color
	 */
	Square(Color color) {
		super();
		this.color = color;
	}

	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * 
	 */
	public void removePiece() {
		setPiece(null);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the enpassantPiece
	 */
	public Piece getEnpassantPiece() {
		return enpassantPiece;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEnpassant() {
		return enpassantPiece != null;
	}

	/**
	 * @param enpassantPiece the enpassantPiece to set
	 */
	public void setEnpassant(Piece enpassantPiece) {
		this.enpassantPiece = enpassantPiece;
	}
	
	/**
	 * 
	 */
	public void clearEnpassant() {
		enpassantPiece = null;
	}
	
}
