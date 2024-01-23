package GiocoBomberman;
/**
 * Interfaccia Observer che rappresenta un oggetto osservatore che puo essere registrato
 * per monitorare un oggetto Observable.
 * @author Davide Vittucci
 * @see Observable
 */
public interface Observer {
	 /**
     * Metodo chiamato quando l'oggetto osservato notifica un cambiamento.
     * 
     * @param model L'oggetto Observable che ha notificato il cambiamento.
     */
    void update(Object model);
}