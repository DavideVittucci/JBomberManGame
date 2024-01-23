package GiocoBomberman;

import java.awt.image.BufferedImage;
/**
 * Piastrella rappresenta una singola piastrella nella mappa del gioco Bomberman.
 * Ogni piastrella può avere diverse proprietà, come tipo, immagine, se è distruttibile,
 * se è stata colpita, ecc., e può contenere potenziamenti.
 * 
 * @author Davide vittucci
 */
public class Piastrella  {
	 /**
     * Tipo di piastrella, dove un valore specifico rappresenta una particolare tipologia.
     */
    public int tipo = 3;

    /**
     * Immagine della piastrella.
     */
    public BufferedImage image;

    /**
     * Indica se la piastrella ha una collisione.
     */
    public boolean collision = false;

    /**
     * Indica se la piastrella è una via di fuga.
     */
    public boolean fuga = false;

    /**
     * Indica se la piastrella contiene un potenziamento.
     */
    private boolean haPotenziamento = false;

    /**
     * Tipo di potenziamento contenuto nella piastrella, se presente.
     */
    private TipoPotenziamento tipoPotenziamento = TipoPotenziamento.NESSUNO;

    /**
     * Indica se la piastrella è distruttibile.
     */
    public boolean distruttibile = false;

    /**
     * Indica se la piastrella è stata colpita.
     */
    public boolean colpita = false;

    /**
     * Stato di distruzione della piastrella, usato per le animazioni.
     */
    public int statoDistruzione = 0;

    /**
     * Indica se la piastrella è stata distrutta.
     */
    public boolean distrutta = false;

    /**
     * Indica se la piastrella è stata calpestata.
     */
    public boolean calpestata = false;

    /**
     * Indica se il potenziamento nella piastrella è stato raccolto.
     */
    public boolean raccolto = false;

    /**
     * Imposta se la piastrella è stata colpita.
     * 
     * @param b True se la piastrella è stata colpita, altrimenti false.
     */
	public void setColpita(boolean b) {
		colpita = b;
	}
	 /**
     * Ottiene lo stato di colpita della piastrella.
     * 
     * @return True se la piastrella è stata colpita, altrimenti false.
     */
	public boolean getColpita() {return colpita;}
	/**
     * Ottiene lo stato di distruzione della piastrella.
     * 
     * @return Il valore dello stato di distruzione.
     */
    public int getStato() {return statoDistruzione;} /**
     * Verifica se la piastrella è stata distrutta.
     * 
     * @return True se la piastrella è distrutta, altrimenti false.
     */
	public boolean getDistruzione() {return distrutta;} 
	/**
     * Imposta lo stato di distruzione della piastrella.
     * 
     * @param c True per impostare la piastrella come distrutta, false altrimenti.
     */
	public void setDistruzione(boolean c) { distrutta = c;}
	 /**
     * Incrementa lo stato di distruzione della piastrella.
     * Questo metodo è tipicamente usato per gestire l'animazione di distruzione.
     */
	public void setStato() {statoDistruzione++;}
	/**
     * Reimposta lo stato di distruzione della piastrella a zero.
     */
	public void resetStato() {statoDistruzione= 0;}
	 /**
     * Imposta il tipo di piastrella.
     * 
     * @param x Il nuovo tipo di piastrella.
     */
	public void setTipo(int x) {tipo= x;}
	 /**
     * Ottiene il tipo della piastrella.
     * 
     * @return Il tipo della piastrella.
     */
	public int getTipo() {return tipo;}
	 /**
     * Imposta se la piastrella è una via di fuga.
     * 
     * @param b True per impostare la piastrella come via di fuga, false altrimenti.
     */
	public void setFuga(boolean b) {
		this.fuga = b;
		
	}
	/**
     * Verifica se la piastrella contiene un potenziamento.
     * 
     * @return True se la piastrella contiene un potenziamento, altrimenti false.
     */
	 public boolean haPotenziamento() {
	        return haPotenziamento;
	    }
	 /**
	     * Imposta un potenziamento nella piastrella.
	     * 
	     * @param tipo Il tipo di potenziamento da impostare.
	     */
	    public void setPotenziamento(TipoPotenziamento tipo) {
	        this.haPotenziamento = true;
	        this.tipoPotenziamento = tipo;
	    }
	    /**
	     * Ottiene il tipo di potenziamento della piastrella.
	     * 
	     * @return Il tipo di potenziamento della piastrella.
	     */
	    public TipoPotenziamento getTipoPotenziamento() {
	        return tipoPotenziamento;
	    }

   
}
