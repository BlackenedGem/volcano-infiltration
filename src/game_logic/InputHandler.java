package game_logic;

import interfaceComponents.menuName.InputName;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import player.Player;

//This class processes user input

public class InputHandler extends KeyAdapter {
	
        public void keyReleased(KeyEvent e) { //Process when a key is released
        	int key = e.getKeyCode();
        	
        	if (key == KeyEvent.VK_A) {
                Player.stopLeft();
            }
            if (key == KeyEvent.VK_D) {
                Player.stopRight();
            }
            if (key == KeyEvent.VK_W) {
                Player.stopUp();
            }
            if (key == KeyEvent.VK_ESCAPE) {
            	Pause.released();
            }
            
        }

        public void keyPressed(KeyEvent e) { //Process when a key is pressed
        	int key = e.getKeyCode();
        	
        	if (key == KeyEvent.VK_A) {
                Player.moveLeft();
            }
            if (key == KeyEvent.VK_D) {
                Player.moveRight();
            }
            if (key == KeyEvent.VK_W) {
                Player.moveUp();
            }
            if (key == KeyEvent.VK_F3) {
            	DebugInfo.keyPressed();
            }
            if (key == KeyEvent.VK_ESCAPE) {
            	Pause.pressed();
            }
            
            InputName.keyPressed(e.getKeyChar(), e);
        }
    }