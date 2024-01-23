package GiocoBomberman;

/**
 * TipoPotenziamento enumera i diversi tipi di potenziamenti disponibili nel gioco Bomberman.
 * Ogni potenziamento ha un effetto specifico che influisce sul gameplay in modi vari.
 * @author davide Vittucci
 */
public enum TipoPotenziamento {
    /**
     * Aumenta il raggio delle esplosioni delle bombe.
     */
    RAGGIO,

    /**
     * Aumenta la velocità di movimento del giocatore.
     */
    VELOCITA,

    /**
     * Permette al giocatore di piazzare più bombe contemporaneamente.
     */
    BOMBA,

    /**
     * Rende il giocatore temporaneamente invincibile.
     */
    INVINCIBILITA,

    /**
     * Conferisce punti bonus al giocatore.
     */
    PUNTI,

    /**
     * Aumenta il numero delle vite del giocatore.
     */
    VITA,

    /**
     * Indica l'assenza di un potenziamento.
     */
    NESSUNO
}