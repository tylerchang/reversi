package reversi;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game {

	public static void main(String[] args) {
		
		//Setting up the frame
		JFrame game = new JFrame("Reversi");
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(800,900);
		
		//Making the board and adding MouseListener to it
		Board board = new Board();
		GameMouseListener listener = new GameMouseListener(board);
		board.addMouseListener(listener);
		
		
		
		//Adding Board to Game
		game.add(board);
		
		
		

	}

}
