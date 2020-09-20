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
 *
 * @author Deyan Rizov
 *
 */
public class Position {
	
	private static final String POS_REG = "[a-h][1-8]";

	private int hPosition;
	
	private int vPosition;
	
	/**
	 * 
	 */
	public Position() {
		hPosition = 0;
		vPosition = 0;
	}
	
	/**
	 * 
	 * @param position
	 * @throws MalformedPositionException 
	 */
	public Position(String position) throws MalformedPositionException {
		if (position == null) {
			throw new NullPointerException("Position is null");
		}
		
		if (position.matches(POS_REG)) {
			hPosition = position.charAt(0) - 'a';
			vPosition = Integer.parseInt(position.charAt(1) + "") - 1;
		} else {
			throw new MalformedPositionException(position);
		}
	}

	/**
	 * @param hPosition
	 * @param vPosition
	 * @throws MalformedPositionException 
	 */
	Position(int hPosition, int vPosition) throws MalformedPositionException {
		if (hPosition < 0 || hPosition > 7 || vPosition < 0 || vPosition > 7) {
			throw new MalformedPositionException("[" + hPosition + " : " + vPosition + "]");
		}
		
		this.hPosition = hPosition;
		this.vPosition = vPosition;
	}

	/**
	 * @return the hPosition
	 */
	public int gethPosition() {
		return hPosition;
	}

	/**
	 * @param hPosition the hPosition to set
	 */
	public void sethPosition(int hPosition) {
		this.hPosition = hPosition;
	}

	/**
	 * @return the vPosition
	 */
	public int getvPosition() {
		return vPosition;
	}

	/**
	 * @param vPosition the vPosition to set
	 */
	public void setvPosition(int vPosition) {
		this.vPosition = vPosition;
	}
	
}
