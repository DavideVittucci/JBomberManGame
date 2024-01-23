package GiocoBomberman;

import java.util.ArrayList;
import java.util.List;
/**
 * HudModel e un modello per l'HUD nel gioco Bomberman.
 * Implementa sia il pattern Observable che Observer per aggiornare e essere aggiornato sullo stato del gioco.
 * È realizzato come Singleton per garantire un'unica istanza in tutto il gioco.
 *
 * @author Davide Vittucci
 * @see Observable
 * @see Observer
 * @see Bomberman
 */
public class HudModel implements Observable , Observer{
	/**
	 * Singleton instance of HudModel.
	 * Utilizzata per mantenere una singola istanza di HudModel in tutto il gioco.
	 */
	private static HudModel instance;

	/**
	 * Punteggio attuale del giocatore.
	 * Rappresenta il punteggio accumulato dal giocatore durante il gioco.
	 */
	private int punteggio;

	/**
	 * Numero di vite rimanenti del giocatore.
	 * Indica quante volte il giocatore può essere colpito prima di perdere il gioco.
	 */
	private int vita;

	/**
	 * Tempo rimanente nel livello corrente.
	 * Utilizzato per tracciare quanto tempo il giocatore ha per completare il livello.
	 */
	private int tempo;

	/**
	 * Livello di gioco corrente.
	 * Indica il livello attuale a cui il giocatore sta giocando.
	 */
	private int level;

	/**
	 * Numero di bombe disponibili per il giocatore.
	 * Mostra quante bombe il giocatore può piazzare contemporaneamente.
	 */
	private int bombe;

	/**
	 * Raggio di esplosione delle bombe del giocatore.
	 * Determina la portata dell'effetto delle bombe quando esplodono.
	 */
	private int raggio;

	/**
	 * Velocità di movimento del giocatore.
	 * Indica la velocità con cui il giocatore si muove nel gioco.
	 */
	private int speed;

	/**
	 * Controller del gioco associato all'HudModel.
	 * Fornisce un collegamento al controller del gioco per aggiornare i dati dell'Hud.
	 */
	private GameController gameController;
    /** Lista degli observer per il pattern Observer. */
    private List<Observer> observers = new ArrayList<>();
    /**
     *
 *costruttore privato
     
     */
private HudModel()  {	
}
/**
 * Restituisce l'istanza singleton di HudModel. Se non esiste, viene creata.
 *
 * @return L'istanza singleton di HudModel.
 */
public static HudModel getInstance() {
    if (instance == null) {
        instance = new HudModel();
    }
    return instance;
}
/**
 * Aggiorna lo stato di HudModel in base ai dati di un altro modello (ad esempio Bomberman).
 *
 * @param model Il modello da cui HudModel riceve gli aggiornamenti.
 */
public void update(Object model) {
	 if (model instanceof Bomberman) {
		 Bomberman bomberman = (Bomberman) model;
	 
	this.punteggio = bomberman.getPunteggio();
	this.vita = bomberman.getVita();
	this.bombe = bomberman.getMaxBomb();
	this.raggio =bomberman.getRaggio();
	this.speed =bomberman.getSpeed();
	 }
}

/**
 * Aggiunge un observer a Bomberman. Gli observer sono notificati quando lo stato di Bomberman cambia.
 *
 * @param observer l'observer da aggiungere.
 */
public void addObserver(Observer observer) {
    observers.add(observer);
}
/**
 * Rimuove un observer da Bomberman.
 *
 * @param observer l'observer da rimuovere.
 */
public void removeObserver(Observer observer) {
    observers.remove(observer);
}
/**
 * Notifica tutti gli observer registrati di un cambiamento di stato di Bomberman.
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
 * Imposta il tempo rimanente nel gioco.
 * @param tempo Il tempo rimanente da impostare.
 */
public void setTempo(int tempo) {
    this.tempo = tempo;
    notifyObservers();
}

/**
 * Imposta il numero di vite del giocatore.
 * @param vita Il numero di vite da impostare.
 */
public void setVita(int vita) {
    this.vita = vita;
    notifyObservers();
}

/**
 * Imposta il punteggio del giocatore.
 * @param punteggio Il punteggio da impostare.
 */
public void setPunteggio(int punteggio) {
    this.punteggio = punteggio;
    notifyObservers();
}

/**
 * Imposta il raggio di esplosione delle bombe del giocatore.
 * @param raggio Il raggio di esplosione da impostare.
 */
public void setRaggio(int raggio) {
    this.raggio = raggio;
    notifyObservers();
}

/**
 * Imposta il numero massimo di bombe che il giocatore può piazzare.
 * @param bombe Il numero massimo di bombe da impostare.
 */
public void setBombe(int bombe) {
    this.bombe = bombe;
    notifyObservers();
}

/**
 * Imposta la velocità di movimento del giocatore.
 * @param speed La velocità da impostare.
 */
public void setSpeed(int speed) {
    this.speed = speed;
    notifyObservers();
}

/**
 * Restituisce il tempo rimanente nel gioco.
 * @return Il tempo rimanente.
 */
public int getTempo() {
    return tempo;
}

/**
 * Imposta il livello di gioco corrente.
 * @param levelNumber Il numero del livello da impostare.
 */
public void setLevel(int levelNumber) {
    this.level = levelNumber;
    notifyObservers();
}

/**
 * Restituisce il punteggio corrente del giocatore.
 * @return Il punteggio corrente.
 */
public int getPunteggio() {
    return punteggio;
}

/**
 * Restituisce il numero di vite rimanenti del giocatore.
 * @return Il numero di vite rimanenti.
 */
public int getVita() {
    return vita;
}

/**
 * Restituisce il numero massimo di bombe che il giocatore può piazzare.
 * @return Il numero massimo di bombe.
 */
public int getMaxBomb() {
    return bombe;
}

/**
 * Restituisce il raggio di esplosione delle bombe del giocatore.
 * @return Il raggio di esplosione.
 */
public int getRaggio() {
    return raggio;
}

/**
 * Restituisce la velocità di movimento del giocatore.
 * @return La velocità di movimento.
 */
public int getSpeed() {
    return speed;
}

/**
 * Restituisce il livello di gioco corrente.
 * @return Il numero del livello corrente.
 */
public int getLevel() {
    return level;
}
}
