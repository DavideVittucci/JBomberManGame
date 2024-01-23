package GiocoBomberman;

import java.awt.Graphics2D;
/**
 * Classe astratta che rappresenta una view di un nemico nel gioco Bomberman. serve a disegnarlo
 * 
 * @author Davide Vittucci
 */
public abstract class NemicoView implements Observer {
	/**
	 *metodo che disegna a schermo il nemico
	 *@param g2 Il contesto grafico su cui disegnare il nemico.
	 */
	protected abstract void draw(Graphics2D g2);

}
