package GiocoBomberman;

import java.util.ArrayList;
import java.util.List;
/**
 * Modello del menu del gioco Bomberman. Questa classe è responsabile della gestione
 * dei profili dei giocatori e delle notifiche agli osservatori quando il profilo selezionato cambia.
 * 
 * @author Davide Vittucci
 */

public class MenuModel {
	 /** Lista dei giocatori registrati. */
    private List<Giocatore> giocatori;

    /** Profilo del giocatore attualmente selezionato. */
    private Giocatore profiloSelezionato;

    /** Lista degli osservatori interessati agli aggiornamenti del modello. */
    private List<Observer> observers = new ArrayList<>();

    /**
     * Costruttore della classe MenuModel. Carica i profili dei giocatori iniziali.
     */
    public MenuModel() {       giocatori = caricaProfiliGiocatori();
    }

    /**
     * Aggiunge un osservatore al modello.
     *
     * @param observer L'osservatore da aggiungere.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    /**
     * Rimuove un osservatore dal modello.
     *
     * @param observer L'osservatore da rimuovere.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori registrati che il modello è stato aggiornato.
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
            
        }
    }

    /**
     * Restituisce la lista dei giocatori registrati.
     *
     * @return La lista dei giocatori.
     */
    public List<Giocatore> getGiocatori() {
        return giocatori;
    }
    /**
     * Restituisce il profilo del giocatore attualmente selezionato.
     *
     * @return Il profilo del giocatore selezionato.
     */
    public Giocatore getProfiloSelezionato() {
        return profiloSelezionato;
    }
    /**
     * Imposta il profilo del giocatore selezionato e notifica gli osservatori.
     *
     * @param profilo Il profilo del giocatore selezionato.
     */
    public void setProfiloSelezionato(Giocatore profilo) {
        this.profiloSelezionato = profilo;
        notifyObservers();
    }
    /**
     * Carica i profili dei giocatori dal gestore dei giocatori.
     *
     * @return Una lista dei profili dei giocatori.
     */
    private List<Giocatore> caricaProfiliGiocatori() {
        GestoreGiocatore gestore = new GestoreGiocatore();
        try {
            List<Giocatore> giocatoriCaricati = gestore.caricaGiocatori();
            notifyObservers();  // Notifica gli osservatori dopo il caricamento iniziale
            return giocatoriCaricati;
        } catch (Exception e) {
            e.printStackTrace();
            // Gestione dell'errore e/o ritorno di una lista vuota se necessario
            return new ArrayList<>();
        }
    }
}
