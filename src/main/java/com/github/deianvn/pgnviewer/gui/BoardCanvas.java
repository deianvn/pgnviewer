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
package com.github.deianvn.pgnviewer.gui;

import com.github.deianvn.pgnviewer.core.Color;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.github.deianvn.pgnviewer.core.Bishop;
import com.github.deianvn.pgnviewer.core.GameManager;
import com.github.deianvn.pgnviewer.core.Images;
import com.github.deianvn.pgnviewer.core.King;
import com.github.deianvn.pgnviewer.core.Knight;
import com.github.deianvn.pgnviewer.core.MalformedPositionException;
import com.github.deianvn.pgnviewer.core.Pawn;
import com.github.deianvn.pgnviewer.core.Piece;
import com.github.deianvn.pgnviewer.core.Queen;
import com.github.deianvn.pgnviewer.core.Rook;

public class BoardCanvas {

	private final Canvas canvas;
	
	private GameManager gameManager;
	
	private Image tableBuffer;
	
	private boolean flip = false;

	public BoardCanvas(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	public BoardCanvas(Composite parent, int style) {
		canvas = new Canvas(parent, style);
		drawTable();
		canvas.addPaintListener(e -> draw(e.gc));
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void redraw() {
		canvas.redraw();
	}
	
	public void redrawFully() {
		drawTable();
		redraw();
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public boolean isFlip() {
		return flip;
	}

	public void setFlip(boolean flip) {
		this.flip = flip;
	}

	private void drawTable() {
		if (tableBuffer != null) {
			tableBuffer.dispose();
		}
		
		String white = "";
		String black = "";
		
		if (gameManager != null) {
			if (gameManager.getGame().containsTagKey("White")) {
				white += gameManager.getGame().getTag("White");
			} else {
				white += "Unknown";
			}
			
			if (gameManager.getGame().containsTagKey("Black")) {
				black += gameManager.getGame().getTag("Black");
			} else {
				black += "Unknown";
			}
			
			if (gameManager.getGame().containsTagKey("WhiteElo")) {
				white += " (" + gameManager.getGame().getTag("WhiteElo") + ")";
			} else {
				white += " (Unknown)";
			}
			
			if (gameManager.getGame().containsTagKey("BlackElo")) {
				black += " (" + gameManager.getGame().getTag("BlackElo") + ")";
			} else {
				black += " (Unknown)";
			}
		}
		
		tableBuffer = new Image(Display.getDefault(), 450, 510);
		GC g = new GC(tableBuffer);
		
		Point whiteTextExtent = null;
		Point blackTextExtent = null;
		Font font = new Font(canvas.getDisplay(), "Arial, Helvetsia, Tahoma", 10, SWT.BOLD);
		
		if (gameManager != null) {
			g.setFont(font);
			g.setTextAntialias(SWT.ON);
			whiteTextExtent = g.textExtent(white);
			blackTextExtent = g.textExtent(black);
		}
		
		if (flip) {
			g.drawImage(Images.BOARDB, 0, 30);
			
			if (gameManager != null) {
				g.drawString(white, (450 - whiteTextExtent.x) / 2, (30 - whiteTextExtent.y) / 2);
				g.drawString(black, (450 - blackTextExtent.x) / 2, 480 + (30 - blackTextExtent.y) / 2);
			}
		} else {
			g.drawImage(Images.BOARDW, 0, 30);
			
			if (gameManager != null) {
				g.drawString(black, (450 - blackTextExtent.x) / 2, (20 - blackTextExtent.y) / 2);
				g.drawString(white, (450 - whiteTextExtent.x) / 2, 480 + (20 - whiteTextExtent.y) / 2);
			}
		}
		
		font.dispose();
		g.dispose();
	}
	
	private void draw(GC gc) {
		Image buffer = new Image(Display.getDefault(), canvas.getBounds());
		GC g = new GC(buffer);
		g.drawImage(tableBuffer, 0, 0);
		
		if (gameManager != null) {
			if (flip) {
				for (int i = 0; i < 8; i++) {
					for (int j = 7; j >= 0; j--) {
						try {
							Piece piece = gameManager.getBoard().getPiece(j, i);
							
							if (piece != null) {
								g.drawImage(getIPieceImage(piece), 25 + (7 - j) * 50, 30 + 25 + i * 50);
							}
						} catch (MalformedPositionException e) {}
					}
				}
			} else {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						try {
							Piece piece = gameManager.getBoard().getPiece(j, i);
							
							if (piece != null) {
								g.drawImage(getIPieceImage(piece), 25 + j * 50, 30 + 25 + (7 - i) * 50);
							}
						} catch (MalformedPositionException e) {}
					}
				}
			}
		}
		
		g.dispose();
		gc.drawImage(buffer, 0, 0);
		buffer.dispose();
	}
	
	private Image getIPieceImage(Piece piece) {
		if (piece instanceof Pawn) {
			return piece.getColor() == Color.WHITE ? Images.WP : Images.BP;
		} else if (piece instanceof Knight) {
			return piece.getColor() == Color.WHITE ? Images.WN : Images.BN;
		} else if (piece instanceof Bishop) {
			return piece.getColor() == Color.WHITE ? Images.WB : Images.BB;
		} else if (piece instanceof Rook) {
			return piece.getColor() == Color.WHITE ? Images.WR : Images.BR;
		} else if (piece instanceof Queen) {
			return piece.getColor() == Color.WHITE ? Images.WQ : Images.BQ;
		} else if (piece instanceof King) {
			return piece.getColor() == Color.WHITE ? Images.WK : Images.BK;
		}
		
		return null;
	}
	
}
