package GiocoBomberman;

import java.util.ArrayList;
/**
 * Classe astratta che rappresenta un nemico nel gioco Bomberman. Un nemico è un'entità
 * controllata dall'intelligenza artificiale che cerca di ostacolare il giocatore.
 * 
 * @author Davide Vittucci
 */
public abstract class Nemico extends Entita {

    /** Indica se il nemico è stato ucciso alla fine del livello. */
    public boolean mortoFine = false;

    /** Indica se il nemico è stato ucciso. */
    public boolean morto = false;

    /**
     * Metodo che aggiorna il comportamento del nemico in base agli eventi di gioco.
     * 
     * @param collisionChecker Verificatore delle collisioni.
     * @param bombe Lista delle bombe presenti nella mappa.
     * @param bombaMap Mappa delle bombe posizionate.
     * @param esplosioniMap Mappa delle esplosioni in corso.
     */
    public void update(CollisionChecker collisionChecker, ArrayList<Bomba> bombe, int[][] bombaMap, boolean[][] esplosioniMap) {
    }

    /**
     * Metodo astratto che gestisce il cambio di direzione del nemico.
     */
    public abstract void cambioDirezione();

    /**
     * Metodo astratto che restituisce il punteggio ottenuto per la sconfitta del nemico.
     *
     * @return Il punteggio ottenuto per la sconfitta del nemico.
     */
    protected abstract int getPunteggio();

    /**
     * Metodo astratto che aggiunge un osservatore al nemico.
     *
     * @param observer L'osservatore da aggiungere.
     */
    protected abstract void addObserver(Observer observer);

    /**
     * Metodo astratto che notifica gli osservatori riguardo a un evento relativo al nemico.
     */
    public abstract void notifica();
}
