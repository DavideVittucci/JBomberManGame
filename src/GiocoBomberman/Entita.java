package GiocoBomberman;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * La classe astratta Entita rappresenta un'entita di base nel videogioco Bomberman.
 * Essa fornisce le caratteristiche fondamentali comuni a tutti gli elementi mobili del gioco,
 * come le coordinate, la velocità, lo stato di invincibilità e la direzione di movimento.
 * 
 * La classe definisce inoltre un'area solida utilizzata per la gestione delle collisioni e
 * metodi astratti che devono essere implementati dalle classi derivate per specificare
 * il comportamento dell'entità.
 * 
 * Questa classe astratta costituisce una base per sviluppare diverse entità del gioco,
 * come i personaggi e i nemici, fornendo una struttura comune e facilitando la gestione
 * della logica di gioco e delle interazioni tra le diverse entità.
 * @author davide vittucci
 */
public abstract class Entita {
	/**
	 * Coordinate x dell'entità nel gioco.
	 */
	public int x;

	/**
	 * Coordinate y dell'entità nel gioco.
	 */
	public int y;

	/**
	 * Velocità di movimento dell'entità.
	 */
	public int speed;

	/**
	 * Stato di invincibilità dell'entità. Se true, l'entità non può essere danneggiata.
	 */
	public boolean invincibile = false;

	/**
	 * Direzione corrente dell'entità.
	 */
	public Direction direction = Direction.DOWN;

	/**
	 * Indica se l'entità è in pausa.
	 */
	protected boolean isPaused = false;

	/**
	 * Area solida utilizzata per la gestione delle collisioni.
	 */
	public Rectangle solidArea;

	/**
	 * Flag che indica se è avvenuta una collisione.
	 */
	public boolean collisionOn = false;

	/**
	 * Ottiene la direzione corrente dell'entità.
	 * 
	 * @return Direzione corrente.
	 */
	public abstract Direction getDirection();

	/**
	 * Ottiene la velocità di movimento dell'entità.
	 * 
	 * @return Velocità corrente.
	 */
	public abstract int getSpeed();

	/**
	 * Ottiene l'area solida dell'entità per la gestione delle collisioni.
	 * 
	 * @return Area solida dell'entità.
	 */
	public abstract Rectangle getSolidArea();

	/**
	 * Ottiene la coordinata x dell'entità.
	 * 
	 * @return Coordinata x.
	 */
	public abstract int getX();

	/**
	 * Ottiene la coordinata y dell'entità.
	 * 
	 * @return Coordinata y.
	 */
	public abstract int getY();

	/**
	 * Imposta lo stato di collisione dell'entità.
	 * 
	 * @param x Flag di collisione.
	 */
	public abstract void setCollision(boolean x);

	/**
	 * Imposta la coordinata x dell'entità.
	 * 
	 * @param i Nuova coordinata x.
	 */
	protected abstract void setX(int i);

	/**
	 * Imposta la coordinata y dell'entità.
	 * 
	 * @param i Nuova coordinata y.
	 */
	protected abstract void setY(int i);

	/**
	 * Imposta lo stato di "colpito" dell'entità.
	 * 
	 * @param b Flag di stato "colpito".
	 */
	public abstract void setColpito(boolean b);

	/**
	 * Ottiene la coordinata x effettiva dell'entità, calcolata in base alla sua area solida.
	 * 
	 * @return Coordinata x effettiva.
	 */
	protected abstract int getRealX();

	/**
	 * Ottiene la coordinata y effettiva dell'entità, calcolata in base alla sua area solida.
	 * 
	 * @return Coordinata y effettiva.
	 */
	protected abstract int getRealY();
	/**
     * Aggiorna lo stato dell'entità in base al controllo delle collisioni e alla mappa del gioco.
     * Questo metodo può essere sovrascritto dalle classi derivate per implementare
     * comportamenti specifici di aggiornamento.
     * 
     * @param collisionChecker Oggetto CollisionChecker per verificare le collisioni.
     * @param bombe Lista delle bombe presenti nel gioco.
     * @param bombaMap Mappa delle posizioni delle bombe.
     * @param esplosioniMap Mappa delle esplosioni.
     */
	public  void update(CollisionChecker collisionChecker, ArrayList<Bomba> bombe, int[][] bombaMap, boolean[][] esplosioniMap) {
	}
	/**
     * Verifica se l'entità è attualmente in stato di invincibilità.
     * 
     * @return true se l'entità è invincibile, altrimenti false.
     */
	public boolean isInvincibile() {
        return this.invincibile;
    }
	/**
     * Riprende l'azione dell'entità dopo una pausa.
     */
    public void resume() {
        isPaused = false;

    }

    /**
     * Mette in pausa l'azione dell'entità.
     */
    public void pause() {
        isPaused = true;
 
    }
}