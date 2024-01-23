package GiocoBomberman;

/**
 * Interfaccia Observable che rappresenta un oggetto osservabile che può essere monitorato da
 * uno o più Observer.
 * @author Davide Vittucci
 * @see Observer
 */
public interface Observable {
    
    /**
     * Aggiunge un osservatore all'elenco degli osservatori.
     * 
     * @param observer L'osservatore da aggiungere.
     */
    void addObserver(Observer observer);

    /**
     * Rimuove un osservatore dall'elenco degli osservatori.
     * 
     * @param observer L'osservatore da rimuovere.
     */
    void removeObserver(Observer observer);

    /**
     * Metodo privato che notifica tutti gli osservatori registrati.
     * Questo metodo dovrebbe essere chiamato quando si desidera notificare gli osservatori
     * di un cambiamento nello stato dell'oggetto osservabile.
     */
    private void notifyObservers() {
    }
}