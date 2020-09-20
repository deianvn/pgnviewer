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

import java.util.ArrayList;
import java.util.List;

public class Board {

  private Square[][] board;

  private List<Piece> whiteCaptured;

  private List<Piece> blackCaptured;

  private Square enpassantSquare;

  public Board() {
    board = new Square[8][8];
    whiteCaptured = new ArrayList<>();
    blackCaptured = new ArrayList<>();
    updateDefaultBoard();
    updateDefaultStartPosition();
  }

  public boolean removePiece(Piece piece) {
    Position position = getPiecePosition(piece);

    return removePiece(position);
  }

  public boolean removePiece(Position position) {
    try {
      getSquare(position).setPiece(null);

      return true;
    } catch (MalformedPositionException e) {
    }

    return false;
  }

  public boolean capture(Piece piece) {
    Position position = getPiecePosition(piece);

    if (position != null) {
      board[position.gethPosition()][position.getvPosition()]
          .setPiece(null);

      if (piece.getColor() == Color.WHITE) {
        whiteCaptured.add(piece);
      } else if (piece.getColor() == Color.BLACK) {
        blackCaptured.add(piece);
      }

      return true;
    }

    return false;
  }

  public boolean capture(Position position) {
    Piece piece = null;

    try {
      piece = getPiece(position);
    } catch (MalformedPositionException e) {
    }

    if (piece != null) {

      board[position.gethPosition()][position.getvPosition()]
          .setPiece(null);

      if (piece.getColor() == Color.WHITE) {
        whiteCaptured.add(piece);
      } else if (piece.getColor() == Color.BLACK) {
        blackCaptured.add(piece);
      }

      return true;
    }

    return false;
  }

  public boolean release(Piece piece, Position position) {

    boolean captured = switch (piece.getColor()) {
      case WHITE -> whiteCaptured.remove(piece);
      case BLACK -> blackCaptured.remove(piece);
    };

    if (captured) {
      try {
        getSquare(position).setPiece(piece);

        return true;
      } catch (MalformedPositionException e) {
      }
    }

    return false;
  }

  public boolean move(int fromhPosition, int fromvPosition, int tohPosition,
      int tovPosition, boolean capture) throws MalformedPositionException {
    if (fromhPosition < 0 || fromhPosition > 7 || fromvPosition < 0
        || fromvPosition > 7 || tohPosition < 0 || tohPosition > 7
        || tovPosition < 0 || tovPosition > 7) {
      throw new MalformedPositionException("[" + fromhPosition + " : "
          + fromvPosition + "][" + tohPosition + " : " + tovPosition
          + "]");
    }

    Piece piece = getPiece(fromhPosition, fromvPosition);

    if (piece != null) {
      if (capture) {
        capture(getPiece(tohPosition, tovPosition));
      }

      board[tohPosition][tovPosition].setPiece(piece);
      board[fromhPosition][fromvPosition].removePiece();

      boolean enpassantSet = false;

      if (piece instanceof Pawn && tovPosition - fromvPosition == 2) {
        try {
          Piece pawn = getPiece(tohPosition - 1, tovPosition);

          if (pawn instanceof Pawn
              && pawn.getColor() != piece.getColor()) {
            enpassantSquare = board[tohPosition][tovPosition - 1];
            enpassantSet = true;
          }
        } catch (MalformedPositionException e) {
        }

        try {
          Piece pawn = getPiece(tohPosition + 1, tovPosition);

          if (pawn instanceof Pawn
              && pawn.getColor() != piece.getColor()) {
            enpassantSquare = board[tohPosition][tovPosition - 1];
            enpassantSet = true;
          }
        } catch (MalformedPositionException e) {
        }
      }

      if (!enpassantSet) {
        enpassantSquare = null;
      }

      return true;
    }

    return false;
  }

  public boolean move(int fromhPosition, int fromvPosition, int tohPosition,
      int tovPosition) throws MalformedPositionException {
    return move(fromhPosition, fromvPosition, tohPosition, tovPosition,
        false);
  }

  public boolean move(Position fromPosition, Position toPosition)
      throws MalformedPositionException {
    return move(fromPosition.gethPosition(), fromPosition.getvPosition(),
        toPosition.gethPosition(), toPosition.getvPosition(), false);
  }

  public boolean move(Position fromPosition, Position toPosition,
      boolean capture) throws MalformedPositionException {
    return move(fromPosition.gethPosition(), fromPosition.getvPosition(),
        toPosition.gethPosition(), toPosition.getvPosition(), capture);
  }

  public boolean move(Piece piece, Position position)
      throws MalformedPositionException {
    Position fromPosition = getPiecePosition(piece);

    if (fromPosition != null) {
      return move(fromPosition.gethPosition(),
          fromPosition.getvPosition(), position.gethPosition(),
          position.getvPosition(), false);
    }

    return false;
  }

  public boolean move(Piece piece, Position position, boolean capture)
      throws MalformedPositionException {
    Position fromPosition = getPiecePosition(piece);

    if (fromPosition != null) {
      return move(fromPosition.gethPosition(),
          fromPosition.getvPosition(), position.gethPosition(),
          position.getvPosition(), capture);
    }

    return false;
  }

  public boolean move(Piece piece, int hPosition, int vPosition,
      boolean capture) throws MalformedPositionException {
    Position fromPosition = getPiecePosition(piece);

    if (fromPosition != null) {
      return move(fromPosition.gethPosition(),
          fromPosition.getvPosition(), hPosition, vPosition, capture);
    }

    return false;
  }

  public boolean move(Piece piece, int hPosition, int vPosition)
      throws MalformedPositionException {
    Position fromPosition = getPiecePosition(piece);

    if (fromPosition != null) {
      return move(fromPosition.gethPosition(),
          fromPosition.getvPosition(), hPosition, vPosition, false);
    }

    return false;
  }

  public Position getPiecePosition(Piece piece) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j].getPiece() == piece) {
          try {
            return new Position(i, j);
          } catch (MalformedPositionException e) {
            e.printStackTrace();
          }
        }
      }
    }

    return null;
  }

  public Piece getPiece(int hPosition, int vPosition)
      throws MalformedPositionException {
    if (hPosition < 0 || hPosition > 7 || vPosition < 0 || vPosition > 7) {
      throw new MalformedPositionException("[" + hPosition + " : "
          + vPosition + "]");
    }

    return board[hPosition][vPosition].getPiece();
  }

  public Piece getPiece(Position position) throws MalformedPositionException {
    return getPiece(position.gethPosition(), position.getvPosition());
  }

  public Piece getPiece(String position) throws MalformedPositionException {
    return getPiece(new Position(position));
  }

  public Square getSquare(int hPosition, int vPosition)
      throws MalformedPositionException {
    if (hPosition < 0 || hPosition > 7 || vPosition < 0 || vPosition > 7) {
      throw new MalformedPositionException("[" + hPosition + " : "
          + vPosition + "]");
    }

    return board[hPosition][vPosition];
  }

  public Square getSquare(Position position) throws MalformedPositionException {
    return getSquare(position.gethPosition(), position.getvPosition());
  }

  public Square getSquare(String position) throws MalformedPositionException {
    return getSquare(new Position(position));
  }

  public boolean isEnpassantAvailable() {
    return enpassantSquare != null;
  }

  public Square getEnpassantSquare() {
    return enpassantSquare;
  }

  private void updateDefaultBoard() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((i + j) % 2 == 0) {
          board[i][j] = new Square(Color.BLACK);
        } else {
          board[i][j] = new Square(Color.WHITE);
        }
      }
    }
  }

  /**
   *
   */
  private void updateDefaultStartPosition() throws IndexOutOfBoundsException {
    board[0][0].setPiece(new Rook(Color.WHITE));
    board[1][0].setPiece(new Knight(Color.WHITE));
    board[2][0].setPiece(new Bishop(Color.WHITE));
    board[3][0].setPiece(new Queen(Color.WHITE));
    board[4][0].setPiece(new King(Color.WHITE));
    board[5][0].setPiece(new Bishop(Color.WHITE));
    board[6][0].setPiece(new Knight(Color.WHITE));
    board[7][0].setPiece(new Rook(Color.WHITE));
    board[0][1].setPiece(new Pawn(Color.WHITE));
    board[1][1].setPiece(new Pawn(Color.WHITE));
    board[2][1].setPiece(new Pawn(Color.WHITE));
    board[3][1].setPiece(new Pawn(Color.WHITE));
    board[4][1].setPiece(new Pawn(Color.WHITE));
    board[5][1].setPiece(new Pawn(Color.WHITE));
    board[6][1].setPiece(new Pawn(Color.WHITE));
    board[7][1].setPiece(new Pawn(Color.WHITE));

    board[0][7].setPiece(new Rook(Color.BLACK));
    board[1][7].setPiece(new Knight(Color.BLACK));
    board[2][7].setPiece(new Bishop(Color.BLACK));
    board[3][7].setPiece(new Queen(Color.BLACK));
    board[4][7].setPiece(new King(Color.BLACK));
    board[5][7].setPiece(new Bishop(Color.BLACK));
    board[6][7].setPiece(new Knight(Color.BLACK));
    board[7][7].setPiece(new Rook(Color.BLACK));
    board[0][6].setPiece(new Pawn(Color.BLACK));
    board[1][6].setPiece(new Pawn(Color.BLACK));
    board[2][6].setPiece(new Pawn(Color.BLACK));
    board[3][6].setPiece(new Pawn(Color.BLACK));
    board[4][6].setPiece(new Pawn(Color.BLACK));
    board[5][6].setPiece(new Pawn(Color.BLACK));
    board[6][6].setPiece(new Pawn(Color.BLACK));
    board[7][6].setPiece(new Pawn(Color.BLACK));
  }

}
