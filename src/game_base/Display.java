package game_base;

import game_logic.DebugInfo;
import game_logic.InputHandler;
import game_logic.MouseHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;



public class Display extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1806383300265934120L;
	
	Thread t = null;
	int x = 0;

	public Display() { //Called when applet initialises
		setDoubleBuffered(true);
		addKeyListener(new InputHandler());
		addMouseListener(new MouseHandler());
		setFocusable(true);
		setBackground(Color.WHITE);
	}

	public void paintComponent(Graphics g)	{ //Called each time the display is updated. Can be called manually and automatically
		
      super.paintComponent(g);
      
      //Notify mouse about position
      Mouse.setInfo(this.getLocationOnScreen());

      Graphics2D g2 = (Graphics2D) g; //Init graphics

      RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingParams.getAA());

      rh.put(RenderingHints.KEY_RENDERING,
             RenderingParams.getQuality());

      g2.setRenderingHints(rh); //Set rendering hints

      RenderEngine.render(g2);

    }

    public void addNotify() {
        super.addNotify();

        t = new Thread(this);
        t.start();
    }

    public void run() { //Called after display initialises. Handles drawing loop and waiting
    	//Init

        while (true) {
        	repaint();
        	
        	if (DebugInfo.getFPS() > 100) {
        		try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        }
    }
    
    
    //Void functions that eclipse insists on implementing despite the fact that they are never used and as such completely useless (I think). But they remove the warning errors so that's why they're here

	
	public void keyPressed(KeyEvent arg0) {
	}

	
	public void keyReleased(KeyEvent arg0) {
	}

	
	public void keyTyped(KeyEvent arg0) {
	}



}