package GiocoBomberman;

import java.awt.Rectangle;
/**
 * La classe Esplosione rappresenta l'effetto di un'esplosione nel gioco Bomberman.
 * Gestisce le caratteristiche di un'esplosione come la sua posizione, raggio,
 * stato di animazione e la durata dell'effetto. La classe fornisce metodi per
 * controllare l'animazione dell'esplosione, determinare se è terminata e gestire
 * le direzioni in cui si espande.
 * 
 * Le esplosioni sono generate dall'attivazione delle bombe nel gioco e hanno un
 * impatto diretto sulle entità circostanti, come il Bomberman, i nemici e gli ostacoli.
 * 
 * La classe è caratterizzata da un sistema di animazione che visualizza l'espansione
 * dell'esplosione in varie direzioni e gestisce la sua durata nel tempo, oltre
 * a fornire informazioni sulle aree colpite.
 * 
 * @author Davide Vittucci
 * @see Bomba
 * @see CollisionChecker
 */
public class Esplosione
{
	 /**
     * Contatore per la durata dell'esplosione.
     */
    private int explosionCounter = 0;

    /**
     * Indice del frame corrente per l'animazione dell'esplosione.
     */
    private int explosionFrame = 0;

    /**
     * Raggi dell'esplosione nelle quattro direzioni (su, giù, sinistra, destra).
     */
    private int raggioSu, raggioGiu, raggioSinistra, raggioDestra;

    /**
     * Flag per l'animazione reversibile dell'esplosione.
     */
    private boolean reverse2 = false;

    /**
     * Tempo di animazione per ogni frame dell'esplosione.
     */
    private final int ANIMATION_TIME_EXP = 6;

    /**
     * Durata totale dell'esplosione.
     */
    private final int EXPLOSION_TIME_EXP = 53;

    /**
     * Contatore per l'animazione dell'esplosione.
     */
    private int animationCounter = 0;

    /**
     * Raggio dell'esplosione.
     */
    private int raggio;

    /**
     * Coordinate x e y del centro dell'esplosione.
     */
    private int x, y;

    /**
     * Array per gestire la direzione dell'esplosione (centro, su, giù, sinistra, destra).
     */
    private boolean[] direzioniAttive;

    /**
     * Flag per indicare se l'esplosione è terminata.
     */
    private boolean explosionDone;

    /**
     * Costruttore per inizializzare l'esplosione.
     * 
     * @param x      Coordinata x del centro dell'esplosione.
     * @param y      Coordinata y del centro dell'esplosione.
     * @param raggio Raggio dell'esplosione.
     */

    public Esplosione(int x, int y, int raggio) {
    	this.x = x;
        this.y = y;
        this.raggio= raggio;
        direzioniAttive = new boolean[5];
    }
    /**
     * Imposta i raggi dell'esplosione nelle quattro direzioni.
     * 
     * @param raggioSu      Raggio verso l'alto.
     * @param raggioGiu     Raggio verso il basso.
     * @param raggioSinistra Raggio verso sinistra.
     * @param raggioDestra   Raggio verso destra.
     */
    public void setDirezioniEraggi(int raggioSu, int raggioGiu, int raggioSinistra, int raggioDestra) {
        this.raggioSu = raggioSu;
        this.raggioGiu = raggioGiu;
        this.raggioSinistra = raggioSinistra;
        this.raggioDestra = raggioDestra;
    }
    /**
     * Aggiorna lo stato dell'esplosione. Gestisce il contatore dell'esplosione, 
     * l'animazione e determina se l'esplosione è terminata.
     */
	 public void update() {
		 {
	            explosionCounter++;
	            if (explosionCounter < EXPLOSION_TIME_EXP) {
	                animationCounter++;
	                if (animationCounter >= ANIMATION_TIME_EXP) {
	                    animationCounter = 0;
	                    if (!reverse2) {
	                        explosionFrame++;
	                        if (explosionFrame == 4) {
	                            reverse2 = true;
	                        }
	                    } else {
	                        explosionFrame--;
	                        if (explosionFrame == 0) {
	                            reverse2 = false;
	                        }
	                    }
	                }
	            } else if (explosionCounter >= EXPLOSION_TIME_EXP) {

	                explosionDone = true;
	            }
	        }
	        }
	 /**
	     * Imposta le direzioni in cui l'esplosione è attiva.
	     * 
	     * @param centro   Indica se l'esplosione è attiva al centro.
	     * @param su       Indica se l'esplosione si espande verso l'alto.
	     * @param giu      Indica se l'esplosione si espande verso il basso.
	     * @param sinistra Indica se l'esplosione si espande verso sinistra.
	     * @param destra   Indica se l'esplosione si espande verso destra.
	     */
	public void setDirezioniAttive(boolean centro, boolean su, boolean giu, boolean sinistra, boolean destra) {
		direzioniAttive[0]= centro;
		direzioniAttive[1]= su;
		direzioniAttive[2]= giu;
		direzioniAttive[3]= sinistra;
		direzioniAttive[4]= destra;
		
		
	}
	 /**
     * Verifica se una specifica direzione dell'esplosione è attiva per il disegno.
     * 
     * @param direzione Indice della direzione da verificare.
     * @return true se la direzione è attiva, altrimenti false.
     */
	public boolean getDirezioneDisegnabile(int direzione) {
        return direzioniAttive[direzione];
    }
	/**
	 * Restituisce la coordinata X dell'esplosione.
	 *
	 * @return La coordinata X dell'esplosione.
	 */
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	/**
	 * Restituisce la coordinata Y dell'esplosione.
	 *
	 * @return La coordinata Y dell'esplosione.
	 */
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
	/**
	 * Ottiene l'indice corrente dell'animazione dell'esplosione.
	 *
	 * @return Il frame corrente dell'animazione dell'esplosione.
	 */
	public int getFrame() {return explosionFrame;}

	/**
	 * Determina se l'animazione dell'esplosione è terminata.
	 *
	 * @return true se l'esplosione è terminata, altrimenti false.
	 */
	public boolean isExplosionDone() {
		return explosionDone;
	}

	/**
	 * Ottiene il raggio dell'esplosione.
	 *
	 * @return Il raggio dell'esplosione.
	 */
	public int getRaggio() {
		// TODO Auto-generated method stub
		return raggio;
	}

/**
 * Ottiene il raggio dell'esplosione verso destra.
 *
 * @return Il raggio dell'esplosione verso destra.
 */
public int getRaggioDestra() {
    return raggioDestra;
}

/**
 * Ottiene il raggio dell'esplosione verso sinistra.
 *
 * @return Il raggio dell'esplosione verso sinistra.
 */
public int getRaggioSinistra() {
    return raggioSinistra;
}

/**
 * Ottiene il raggio dell'esplosione verso l'alto.
 *
 * @return Il raggio dell'esplosione verso l'alto.
 */
public int getRaggioSu() {
    return raggioSu;
}

/**
 * Ottiene il raggio dell'esplosione verso il basso.
 *
 * @return Il raggio dell'esplosione verso il basso.
 */
public int getRaggioGiu() {
    return raggioGiu;
}
}
