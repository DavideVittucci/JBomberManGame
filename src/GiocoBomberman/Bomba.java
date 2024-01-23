package GiocoBomberman;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * La classe Bomba rappresenta una bomba nel gioco Bomberman.
 * Gestisce le coordinate della bomba, il suo stato di esplosione, e la logica
 * dell'animazione. Include funzionalità per aggiornare lo stato della bomba,
 * gestire la pausa e la ripresa del gioco e notificare gli osservatori degli
 * aggiornamenti di stato.
 *
 * @author Davide Vittucci
 */
public class Bomba {
	/** Coordinate X e Y della bomba nel gioco. */
    private int x, y;

    /** Stato di esplosione della bomba. */
    private boolean exploded = false;

    /** Contatore utilizzato per gestire il tempo prima dell'esplosione. */
    private int bombCounter = 0;

    /** Variabile per tenere traccia se il gioco è in pausa. */
    private boolean isPaused = false;

    /** Contatore del tempo di pausa. */
    private int pauseCounter = 0;

    /** Frame corrente dell'animazione della bomba. */
    private int bombFrame = 0;

    /** Frame corrente dell'animazione dell'esplosione. */
    private int explosionFrame = 0;

    /** Indica se l'animazione deve essere riprodotta al contrario. */
    private boolean reverse = false;

    /** Tempo totale per l'esplosione. */
    private final int EXPLOSION_TIME = 130;

    /** Tempo per ogni frame dell'animazione. */
    private final int ANIMATION_TIME = 20;

    /** Contatore dell'animazione. */
    private int animationCounter = 0;

    /** Indica se l'esplosione è terminata. */
    private boolean explosionDone = false;

    /** Indica se la bomba è calpestabile. */
    private boolean isCalpestabile = true;

    /** Area di collisione della bomba. */
    private Rectangle collisionArea;

    /** Dimensione della piastrella (tile) del gioco. */
    private int tileSize;

    /** Flag per controllare la riproduzione dell'audio dell'esplosione. */
    private boolean audioBombaRiprodotto = false;

    /** Lista di osservatori che vengono notificati sugli aggiornamenti di stato. */
    private List<Observer> observers = new ArrayList<>();
    
    /**
     * Costruisce una nuova bomba con coordinate specificate.
     *
     * @param tile Dimensione della piastrella (tile) del gioco.
     * @param x Coordinate X della bomba.
     * @param y Coordinate Y della bomba.
     */
    public Bomba(int tile, int x, int y) {
    	
        this.x = x;
        this.y = y;
        
        this.tileSize = tile ;
         collisionArea = new Rectangle(x + (tileSize / 2) - 32, y + (tileSize / 2) - 32, 64, 64);
    }
    /**
     * Aggiunge un observer alla bomba.
     *
     * @param observer L'observer da aggiungere.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    /**
     * Rimuove un observer dalla bomba.
     *
     * @param observer L'observer da rimuovere.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    /**
     * Notifica tutti gli observer registrati di un cambiamento di stato della bomba.
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
    /**
     * Notifica gli observer chiamando il metodo notifyObservers.
     */
    public void notifica() {
    	notifyObservers() ;
    }
    /**
     * Verifica se la bomba è calpestabile, cioè se un personaggio può passarci sopra senza innescarla.
     *
     * @return boolean che indica se la bomba è calpestabile.
     */
    public boolean isCalpestabile() {
        return isCalpestabile;
    }
    /**
     * Imposta lo stato di calpestabilità della bomba.
     *
     * @param calpestabile booleano che indica se la bomba dovrebbe essere calpestabile.
     */
    public void setCalpestabile(boolean calpestabile) {
        isCalpestabile = calpestabile;
    }
    /**
     * Recupera l'area di collisione della bomba.
     *
     * @return Rectangle rappresentante l'area di collisione.
     */
    public Rectangle getCollisionArea() {
        return collisionArea;
    }
    /**
     * Aggiorna l'area di collisione della bomba basandosi sulla sua posizione attuale.
     */
    public void updateCollisionArea() {
        collisionArea.setLocation(x + (tileSize / 2) - 32, y + (tileSize / 2) - 32);
    }
    /**
     * Aggiorna lo stato della bomba, inclusa la sua animazione e verifica se è esplosa.
     * Gestisce anche la riproduzione del suono di esplosione e l'animazione dell'esplosione.
     *
     * @param bombaMap Array bidimensionale per la mappa delle bombe.
     * @param esplosioniMap Array bidimensionale per la mappa delle esplosioni.
     */
    public void update(int[][] bombaMap, boolean[][] esplosioniMap) {
    	
        if (!exploded&& !isPaused) {
            bombCounter++;
            if (bombCounter < EXPLOSION_TIME) {
                animationCounter++;
                if (animationCounter >= ANIMATION_TIME) {
                    animationCounter = 0;
                    if (!reverse) {
                    	notifyObservers();
                        bombFrame++;
                        if (bombFrame == 2) {
                            reverse = true;
                        }
                    } else {
                    	notifyObservers();
                        bombFrame--;
                        if (bombFrame == 0) {
                            reverse = false;
                        }
                    }
                }
            } else if (bombCounter >= EXPLOSION_TIME) {
                explode();
                if (!audioBombaRiprodotto) {
    	            AudioManager.getInstance().play("res/audio/Bomb-Explodes.wav");
    	            audioBombaRiprodotto = true; // Imposta la variabile a true dopo la riproduzione
    	        }
            }
        } 
    }
    /**
     * Mette in pausa l'animazione e il timer della bomba.
     */
    public void pause() {
        isPaused = true;
        pauseCounter = bombCounter; // Salva il contatore corrente al momento della pausa
    } 
    /**
     * Riprende l'animazione e il timer della bomba dalla posizione di pausa.
     */
    public void resume() {
        isPaused = false;
        bombCounter = pauseCounter; // Ripristina il contatore quando si riprende
    }
    /**
     * Gestisce l'esplosione della bomba, modificando lo stato e notificando gli observer.
     */
    private void explode() {
    	
        exploded = true;
        bombCounter = 0;
        notifyObservers();
    }
    /**
     * Restituisce la coordinata X della bomba.
     *
     * @return int che rappresenta la coordinata X.
     */
    public int getX() { return x; }
    /**
     * Restituisce la coordinata Y della bomba.
     *
     * @return int che rappresenta la coordinata Y.
     */
    public int getY() { return y; }
    /**
     * Verifica se la bomba è esplosa.
     *
     * @return boolean che indica se la bomba è esplosa.
     */
    public boolean hasExploded() { return exploded; }
    /**
     * Ottiene il frame corrente dell'animazione della bomba.
     *
     * @return int rappresentante il frame corrente dell'animazione.
     */
    public int getBombFrame() { return bombFrame; }
    /**
     * Ottiene il frame corrente dell'animazione dell'esplosione.
     *
     * @return int rappresentante il frame corrente dell'animazione dell'esplosione.
     */
    public int getExplosionFrame() { return explosionFrame; }
    /**
     * Verifica se l'animazione dell'esplosione è terminata.
     *
     * @return boolean che indica se l'animazione dell'esplosione è conclusa.
     */
    public boolean isExplosionDone() { return explosionDone; }

}