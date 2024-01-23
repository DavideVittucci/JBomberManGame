package GiocoBomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Gestisce gli eventi di tastiera per il gioco JBomberman.
 * Implementa {@link KeyListener} per intercettare e rispondere agli eventi di pressione dei tasti.
 * 
 * @author Davide Vittucci
 */
public class KeyHandler implements KeyListener {
	  /** Indica se il tasto per muoversi verso l'alto è premuto. */
    public boolean upPressed;

    /** Indica se il tasto per muoversi verso il basso è premuto. */
    public boolean downPressed;

    /** Indica se il tasto per muoversi verso sinistra è premuto. */
    public boolean leftPressed;

    /** Indica se il tasto per muoversi verso destra è premuto. */
    public boolean rightPressed;

    /** Indica se il tasto per piazzare una bomba è premuto. */
    public boolean bombPressed = false;

    /** Flag per gestire lo stato del tasto della bomba. */
    public boolean bombKeyPressed = false;

    /** Indica se il tasto Esc è premuto. */
    public boolean escPressed;

    /** Indica se il tasto Enter è premuto. */
    public boolean avviaPressed;

    /**
     * Gestisce l'evento di pressione di un tasto.
     * 
     * @param e L'evento di tastiera.
     */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W ||code == KeyEvent.VK_UP) {
			upPressed=true;
		}
		if (code == KeyEvent.VK_S||code == KeyEvent.VK_DOWN) {
			downPressed=true;
			

		}
		if (code == KeyEvent.VK_A||code == KeyEvent.VK_LEFT) {
			leftPressed=true;
			 
		}
		if (code == KeyEvent.VK_D||code == KeyEvent.VK_RIGHT) {
			rightPressed=true;
		}
		if (code == KeyEvent.VK_Z) {
	        
			bombPressed = true;}
		if (code == KeyEvent.VK_ESCAPE) {
			
		        escPressed = true;
		        
		    }
		if (code == KeyEvent.VK_ENTER) {
			
	        avviaPressed = true;
	        
	    }
		
	}
  
	/**
     * Gestisce l'evento di rilascio di un tasto.
     * 
     * @param e L'evento di tastiera.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W ||code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_S||code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_A||code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_D||code == KeyEvent.VK_RIGHT) rightPressed = false;
        if (code == KeyEvent.VK_Z) {bombPressed = false;}
        if (code == KeyEvent.VK_ESCAPE) {
			
	        escPressed = false;
	        
	    }
        if (code == KeyEvent.VK_ENTER) {
			
	        avviaPressed = false;
	        
	    }
        
        
    }
    /**
     * Gestisce l'evento di digitazione di un tasto.
     * 
     * @param e L'evento di tastiera.
     */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}