package GiocoBomberman;

/**
 * Potenziamento rappresenta un potenziamento all'interno del gioco Bomberman.
 * Ogni potenziamento ha un tipo specifico, che definisce il suo effetto sul giocatore
 * o sull'ambiente di gioco.
 * 
 * @author Davide Vittucci
 */
public class Potenziamento {
	 /**
     * Il tipo di potenziamento.
     */
    private TipoPotenziamento tipo;
    /**
     * Costruisce un potenziamento con un tipo specificato.
     * 
     * @param tipo Il tipo di potenziamento.
     */
    public Potenziamento(TipoPotenziamento tipo) {
        this.tipo = tipo;
    }

    /**
     * Ottiene il tipo di potenziamento.
     * 
     * @return Il tipo di potenziamento.
     */
    public TipoPotenziamento getTipo() {
        return tipo;
    }
    /**
     * Imposta il tipo di potenziamento.
     * 
     * @param tipo Il tipo di potenziamento da impostare.
     */
    public void setTipo(TipoPotenziamento tipo) {
        this.tipo = tipo;
    }
}


