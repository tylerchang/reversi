package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel{
	
	//2D Array of squares
	public Square[][] squares;
	
	public Board() {
		//Setting up the board
		this.setPreferredSize(new Dimension(800,900));
		
		
		//Size of square
		int squareWidth = 100;
		int squareHeight = 100;
		
		squares = new Square[8][8];
		
		for(int i=0; i<8; i++) {
			
			for(int j=0; j<8; j++) {
				
				squares[i][j] = new Square(j*squareWidth,i*squareHeight,squareWidth,squareHeight);
				
				if((i==3 && j==4) || (i==4 && j==3)){
					squares[i][j].circleColor = "black";
				}
			
				else if((i==4 && j==4) || (i==3 && j==3)) {
					squares[i][j].circleColor = "white";
				}
				
			}	
		}
			
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
		g2.setColor(Color.RED);
		
		if(GameMouseListener.gameOver) {
			if(GameMouseListener.winner.equals("Tie!")) {
				g2.drawString("Game Over! " + GameMouseListener.winner, 325, 850);

			}else {
				g2.drawString("Game Over! " + GameMouseListener.winner + " won!", 325, 850);
			}
		}
		
		//Invalid Move?
		if(GameMouseListener.invalidMove && !GameMouseListener.gameOver) {
			g2.drawString("Invalid Move!", 325, 850);
		}
		
		g2.setColor(Color.BLACK);
		
			
		//Drawing the grid
	
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				squares[i][j].draw(g2);
			}
		}
		
		
	
		
	}
	
	public Square getSquare(int x, int y) {
		for(int i =0; i < 8; i++) {
			for(int j = 0; j<8; j++) {
				Square sq = squares[i][j];
				if(sq.contains(x,y)) {
					return sq;
				}
			}
		}
		return null;
	}
	
	public Square getSquareWithIndices(int i, int j) {
		return squares[i][j];
	}
		
	public void update(int x,int y){
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Square sq = squares[i][j];
				if(sq.contains(x,y)) {
					
					updateTop(i,j);
					updateBottom(i,j);
					updateRight(i,j);
					updateLeft(i,j);
					updateTopLeftDiagonal(i,j);
					updateTopRightDiagonal(i,j);
					updateBottomLeftDiagonal(i,j);
					updateBottomRightDiagonal(i,j);
					
				}
			}
		}
	}
	
	public String getWinner() {
		
		int blackCounter = 0;
		int whiteCounter = 0;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(squares[i][j].circleColor.equals("black")) {
					blackCounter++;
				}else {
					whiteCounter++;
				}
			}
		}
		
		if(blackCounter > whiteCounter) {
			return "Black";
		}else if (whiteCounter > blackCounter) {
			return "White";
		}else {
			return "Tie!";
		}
	}
	
	public boolean legalMove(int x, int y) {
		
		ArrayList <Boolean> conditions = new ArrayList <Boolean> (0);
		
		for(int i =0; i < 8; i++) {
			for(int j = 0; j<8; j++) {
				
				Square sq = squares[i][j];
				if(sq.contains(x,y)) {
					
					//Check top
					conditions.add(checkTop(i,j));
					
					//Check bottom
					conditions.add(checkBottom(i,j));
					
					//Check left
					conditions.add(checkLeft(i,j));
					
					//Check right 
					conditions.add(checkRight(i,j));
					
					//Check top-left diagonal
					conditions.add(checkTopLeftDiagonal(i,j));
					
					//Check top-right diagonal FIXED
					conditions.add(checkTopRightDiagonal(i,j));
					
					//Check bottom-left diagonal FIXED
					conditions.add(checkBottomLeftDiagonal(i,j));
					
					//Check bottom-right diagonal FIXED
					conditions.add(checkBottomRightDiagonal(i,j));
				}
			}
		}
		
		if(conditions.contains(true)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public boolean hasLegalMovesLeft() {
		
		ArrayList <Boolean> conditions = new ArrayList <Boolean> (0);

			for(int i = 0; i<8; i++) {
				for(int j = 0; j<8; j++) {
					conditions.add(checkTop(i,j));
					conditions.add(checkBottom(i,j));
					conditions.add(checkRight(i,j));
					conditions.add(checkLeft(i,j));
					conditions.add(checkTopLeftDiagonal(i,j));
					conditions.add(checkTopRightDiagonal(i,j));
					conditions.add(checkBottomLeftDiagonal(i,j));
					conditions.add(checkBottomRightDiagonal(i,j));
					
				}
			}
	
		if(conditions.contains(true)) {
			return true;
		}else {
			return false;
		}
	}
	
	public ArrayList getLegalMoves() {
		
		ArrayList <int[]> legalMoveSets = new ArrayList<int[]> (0);
		
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				ArrayList <Boolean> conditions = new ArrayList <Boolean> (0);
				conditions.add(checkTop(i,j));
				conditions.add(checkBottom(i,j));
				conditions.add(checkRight(i,j));
				conditions.add(checkLeft(i,j));
				conditions.add(checkTopLeftDiagonal(i,j));
				conditions.add(checkTopRightDiagonal(i,j));
				conditions.add(checkBottomLeftDiagonal(i,j));
				conditions.add(checkBottomRightDiagonal(i,j));
				
				if(conditions.contains(true)) {
					int [] ar = new int []{i,j};
					legalMoveSets.add(ar);
				}
				
			}
		}
		
		return legalMoveSets;
	}
	
	public boolean checkTop(int i, int j) {
		
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		 if(i==0 || i==1) {
			 return false;
			 
		 }
		 //Checking that the current position is blank, and the position one space above is not the current color or a blank space
		 else if(squares[i][j].circleColor.equals("") && !squares[i-1][j].circleColor.equals(currentColor) && !squares[i-1][j].circleColor.equals("")){
			 
			 for(int i2=i-1; i2>=0; i2--) {
				 
				if(squares[i2][j].circleColor.equals("")) {
						return false;
				}
				if(squares[i2][j].circleColor.equals(currentColor)) {
					return true;
				}
			 } 
		 }
		 
		 return false;
	}
	public boolean checkBottom(int i, int j) {
			
			String currentColor;
			
			if(GameMouseListener.blackTurn) {
				currentColor = "black";
			}else {
				currentColor = "white";
			}
			
			 if(i==7 || i==6) {
				 return false;
				 
			 }

			 else if(squares[i][j].circleColor.equals("") && !squares[i+1][j].circleColor.equals(currentColor) && !squares[i+1][j].circleColor.equals("")){
				 
				 for(int i2=i+1; i2<=7; i2++) {
					if(squares[i2][j].circleColor.equals("")) {
						return false;
					}
					if(squares[i2][j].circleColor.equals(currentColor)) {
						return true;
					}
				 } 
			 }
			 
			 return false;
		}
	public boolean checkRight(int i, int j) {
		
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		 if(j==7 || j==6) {
			 return false;
			 
		 }

		 else if(squares[i][j].circleColor.equals("") && !squares[i][j+1].circleColor.equals(currentColor) && !squares[i][j+1].circleColor.equals("")){
			 
			 for(int j2=j+1; j2<=7; j2++) {
				
				if(squares[i][j2].circleColor.equals("")) {
						return false;
				}
				 
				if(squares[i][j2].circleColor.equals(currentColor)) {
					return true;
				}
			 } 
		 }
		 
		 return false;
	}
	public boolean checkLeft(int i, int j) {
			
			String currentColor;
			
			
			if(GameMouseListener.blackTurn) {
				currentColor = "black";
			}else {
				currentColor = "white";
			}
			
			 if(j==0 || j==1) {
				 
				 return false;

			 }

			 else if(squares[i][j].circleColor.equals("") && !squares[i][j-1].circleColor.equals(currentColor) && !squares[i][j-1].circleColor.equals("")){
				 
				 
				 for(int j2=j-1; j2>=0; j2--) {
					
					if(squares[i][j2].circleColor.equals("")) {
						return false;
					}
					 
					if(squares[i][j2].circleColor.equals(currentColor)) {
						
						return true;
					}
				 } 
			 }
			 
			 return false;
		}
	public boolean checkTopLeftDiagonal(int i, int j) {

		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		 if((i==0 || i==1) || (j==0 || j==1)) {
			 return false;
		 }

		 else if(squares[i][j].circleColor.equals("") && !squares[i-1][j-1].circleColor.equals(currentColor) && !squares[i-1][j-1].circleColor.equals("")){
			 
			 int i2 = i-1;
			 int j2 = j-1;
			 
			 while(j2 >= 0 && i2 >=0) {
				 
				if(squares[i2][j2].circleColor.equals("")) {
						return false;
				}
				 
				 if(squares[i2][j2].circleColor.equals(currentColor)) {
					 return true;
				 }				 
				 i2--;
				 j2--;
				 
			 }
		 }
	return false;
	}
	public boolean checkTopRightDiagonal(int i, int j) {
			
			String currentColor;
			
			if(GameMouseListener.blackTurn) {
				currentColor = "black";
			}else {
				currentColor = "white";
			}
			
			 if((i==0 || i==1) || (j==6 || j==7)) {
				 return false;
			 }

			 else if(squares[i][j].circleColor.equals("") && !squares[i-1][j+1].circleColor.equals(currentColor) && !squares[i-1][j+1].circleColor.equals("")){
				 
				 int i2 = i-1;
				 int j2 = j+1;
				 
				 while(j2 <= 7 && i2 >=0) {
					 
					if(squares[i2][j2].circleColor.equals("")) {
						return false;
					}
					 					 
					 if(squares[i2][j2].circleColor.equals(currentColor)) {
						 return true;
					 }				 
					 i2--;
					 j2++;
					 
				 }
			 }
		return false;
	}
	public boolean checkBottomLeftDiagonal(int i, int j) {
			
			String currentColor;
			
			if(GameMouseListener.blackTurn) {
				currentColor = "black";
			}else {
				currentColor = "white";
			}
			
			 if((i==6 || i==7) || (j==0 || j==1)) {
				 return false;
			 }

			 else if(squares[i][j].circleColor.equals("") && !squares[i+1][j-1].circleColor.equals(currentColor) && !squares[i+1][j-1].circleColor.equals("")){
				 
				 int i2 = i+1;
				 int j2 = j-1;
				 
				 while(j2 >= 0 && i2 <= 7) {
					 
					 if(squares[i2][j2].circleColor.equals("")) {
							return false;
					 }
					 					 
					 if(squares[i2][j2].circleColor.equals(currentColor)) {
						 return true;
					 }				 
					 i2++;
					 j2--;
					 
				 }
			 }
		return false;
	}
	public boolean checkBottomRightDiagonal(int i, int j) {
		
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		 if((i==6 || i==7) || (j==6 || j==7)) {
			 return false;
		 }

		 else if(squares[i][j].circleColor.equals("") && !squares[i+1][j+1].circleColor.equals(currentColor) && !squares[i+1][j+1].circleColor.equals("")){
			 
			 int i2 = i+1;
			 int j2 = j+1;
			 
			 while(j2 <= 7 && i2 <= 7) {
				 
				if(squares[i2][j2].circleColor.equals("")) {
						return false;
				}
				 				 
				 if(squares[i2][j2].circleColor.equals(currentColor)) {
					 return true;
				 }				 
				 i2++;
				 j2++;
				 
			 }
		 }
	return false;
}
	
	public void updateTop(int i, int j) {
		
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkTop(i,j)) {
			for(int i2=i-1; i2>=0; i2--) {
				
				if(squares[i2][j].circleColor.equals(currentColor)){
					break;
				}
				
				if(!squares[i2][j].circleColor.equals("")) {
					if(currentColor.equals("black")) {
						squares[i2][j].circleColor = "black";
					}else {
						squares[i2][j].circleColor = "white";
					}
					
				}
			 } 
		}		
		 
	}
	public void updateBottom(int i, int j) {
			
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkBottom(i,j)) {
		 for(int i2=i+1; i2<=7; i2++) {
				
				if(squares[i2][j].circleColor.equals(currentColor)){
					break;
				}
				
				if(!squares[i2][j].circleColor.equals("")) {
					if(currentColor.equals("black")) {
						squares[i2][j].circleColor = "black";
					}else {
						squares[i2][j].circleColor = "white";
					}
					
				}
			 } 
		}
			 
		
	}
	public void updateRight(int i, int j) {
		
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkRight(i,j)) {
			for(int j2=j+1; j2<=7; j2++) {
				
				if(squares[i][j2].circleColor.equals(currentColor)){
					break;
				}
				
				if(!squares[i][j2].circleColor.equals("")) {
					if(currentColor.equals("black")) {
						squares[i][j2].circleColor = "black";
					}else {
						squares[i][j2].circleColor = "white";
					}
					
				}
			} 
		}
			 
		 
	}
	public void updateLeft(int i, int j) {
		
		
		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		
		if(checkLeft(i,j)) {
	
			for(int j2=j-1; j2>=0; j2--) {
				
				if(squares[i][j2].circleColor.equals(currentColor)){
					break;
				}
				
				
				if(currentColor.equals("black")) {
					squares[i][j2].circleColor = "black";
				}else {
					squares[i][j2].circleColor = "white";
				}
					
				
			} 
		}
			 
		 
	}
	public void updateTopLeftDiagonal(int i, int j) {

		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkTopLeftDiagonal(i,j)) {
			int i2 = i-1;
			 int j2 = j-1;
			 
			 while(j2 >= 0 && i2 >=0) {
				 
				 if(squares[i2][j2].circleColor.equals(currentColor)) {
					 break;
				 }
				
						
				 if(currentColor.equals("black")) {
						squares[i2][j2].circleColor = "black";
				 }else{
						squares[i2][j2].circleColor = "white";
				 }	
										 
				 i2--;
				 j2--;
				 
			 }
		}
			 
		 
		 
	}
	public void updateTopRightDiagonal(int i, int j) {

		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkTopRightDiagonal(i,j)) {
			
			int i2 = i-1;
			 int j2 = j+1;
			 
			 while(j2 <= 7 && i2 >=0) {
				 
				 if(squares[i2][j2].circleColor.equals(currentColor)) {
					 break;
				 }
				 
						
				 if(currentColor.equals("black")) {
						squares[i2][j2].circleColor = "black";
				 }else{
						squares[i2][j2].circleColor = "white";
				 }
					
								 
				 i2--;
				 j2++;
				 
			 }
		}
			 
		 
	}
	public void updateBottomLeftDiagonal(int i, int j) {
			

		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkBottomLeftDiagonal(i,j)) {
			
			int i2 = i+1;
			 int j2 = j-1;
			 
			 while(j2 >= 0 && i2 <= 7) {
				 
				 if(squares[i2][j2].circleColor.equals(currentColor)) {
					 break;
				 }
				 
						
				 if(currentColor.equals("black")) {
						squares[i2][j2].circleColor = "black";
				 }else{
						squares[i2][j2].circleColor = "white";
				 }
					
								 
				 i2++;
				 j2--;
				 
			 }
		}
			 
		 
	}
	public void updateBottomRightDiagonal(int i, int j) {

		String currentColor;
		
		if(GameMouseListener.blackTurn) {
			currentColor = "black";
		}else {
			currentColor = "white";
		}
		
		if(checkBottomRightDiagonal(i,j)) {
			
			int i2 = i+1;
			 int j2 = j+1;
			 
			 while(j2 <= 7 && i2 <= 7) {
				 
				 if(squares[i2][j2].circleColor.equals(currentColor)) {
					 break;
				 }
				 
						
				 if(currentColor.equals("black")) {
						squares[i2][j2].circleColor = "black";
				 }else{
						squares[i2][j2].circleColor = "white";
				 }
					
								 
				 i2++;
				 j2++;
				 
			 }
		}	 
	}
	
}



