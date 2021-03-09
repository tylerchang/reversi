package reversi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Square {
	
	int x;
	int y;
	int width;
	int height;
	
	String circleColor = "";
	
	public Square (int x, int y, int width, int height) {
		
		//Setting up the square
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setStroke(new BasicStroke(5));
		g2.setColor(new Color(0,0,0));
		g2.drawRect(x,y,width,height);
		
		g2.setColor(new Color(72,93,63));
		g2.fillRect(x,y,width,height);
		
		if(circleColor.equals("black")) {
			g2.setColor(new Color(0,0,0));
			g2.fillOval(x+23,y+23,50,50);
		}
		else if(circleColor.equals("white")) {
			g2.setColor(new Color(255, 255, 255));
			g2.fillOval(x+23,y+23,50,50);
		}
	}

	
	//checks if click is within the range of the square
	public boolean contains(int x2, int y2) {
		boolean xok = (x2 >= x && x2 < x + width);
		boolean yok = (y2 >= y && y2 < y + height);
		
		return xok && yok;
	}
}
