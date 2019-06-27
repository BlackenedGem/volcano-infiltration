package game_logic;

import game_base.Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
	//Receive mouse actions and pass them onto other sections of the program. Required because the methods can't be static

	
	public void mouseClicked(MouseEvent arg0) {
	}

	
	public void mouseEntered(MouseEvent arg0) {
		Pause.mouseJoinedScreen();
	}

	
	public void mouseExited(MouseEvent arg0) {
		Pause.mouseLeftScreen();
	}

	
	public void mousePressed(MouseEvent arg0) {
		Mouse.Pressed(arg0);
	}

	
	public void mouseReleased(MouseEvent arg0) {
		Mouse.Released(arg0);
	}

}
