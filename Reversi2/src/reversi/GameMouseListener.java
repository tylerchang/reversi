package reversi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameMouseListener implements MouseListener {
	
	static boolean blackTurn = true;
	static boolean invalidMove = false;
	static boolean gameOver = false;
	static String winner;
	boolean blackHasLegalMovesLeft = true;
	boolean whiteHasLegalMovesLeft = true;
	
	
	Board board;
	
	public GameMouseListener(Board b) {
		board = b;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		boolean legalMove = false;
		int x = e.getX();
		int y = e.getY();
		
		Square square = board.getSquare(x, y);
		legalMove = board.legalMove(x,y);
		
		if(!legalMove){
			invalidMove = true;
			board.repaint();
		}
		
		//black cell
		if(square!=null && legalMove && blackTurn) {
			
			//flipping
			board.update(x,y);
			//changing color of clicked
			square.circleColor="black";
			//repaint
			board.repaint();
			invalidMove = false;
			
			//check if game is over
			gameOver = isGameOver();
			
			if(gameOver) {
				winner = board.getWinner();
			}
			
			blackTurn = !blackTurn;
			
			
			//Check if white has legalmove left, if so
			whiteHasLegalMovesLeft = board.hasLegalMovesLeft();
						
			if(whiteHasLegalMovesLeft) {
				
//				Scanner scanner = new Scanner(System.in);
//				scanner.next();
//				scanner.close();
				moveWhite();
				
				
			}else {
				blackTurn = !blackTurn;
			}

		}
		
	}
	
	public boolean isGameOver() {
		
		boolean blackAlive = true;
		boolean whiteAlive = true;
		
		if(blackTurn) {
			//if black has moves left
			blackAlive = board.hasLegalMovesLeft();
			
			blackTurn = !blackTurn;
			//if white has moves left
			whiteAlive = board.hasLegalMovesLeft();
			
			blackTurn = !blackTurn;
		}else {
			//if black has moves left
			whiteAlive = board.hasLegalMovesLeft();
			
			blackTurn = !blackTurn;
			//if white has moves left
			blackAlive = board.hasLegalMovesLeft();
			
			blackTurn = !blackTurn;
		}
		
		if(!blackAlive && !whiteAlive) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void moveWhite() {
		
		//pick a move
		ArrayList<int[]> moves = board.getLegalMoves();
		
		int move = (int)(Math.random()*moves.size());
		
		int [] coordinates = moves.get(move);
		
		int i = coordinates[0];
		int j = coordinates[1];
		
		System.out.println("White move placed on: " + i + " , " + j);
		
		
		board.updateTop(i,j);
		board.updateBottom(i,j);
		board.updateRight(i,j);
		board.updateLeft(i,j);
		board.updateTopLeftDiagonal(i,j);
		board.updateTopRightDiagonal(i,j);
		board.updateBottomLeftDiagonal(i,j);
		board.updateBottomRightDiagonal(i,j);
		
		Square sq = board.getSquareWithIndices(i, j);
		
		sq.circleColor = "white";
		board.repaint();
		invalidMove = false;
		
		//check if game is over
		gameOver = isGameOver();
		
		if(gameOver) {
			winner = board.getWinner();
		}
		
		blackTurn = !blackTurn;
		
		blackHasLegalMovesLeft = board.hasLegalMovesLeft();
		
		if(!blackHasLegalMovesLeft) {
			blackTurn = !blackTurn;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
