package GiocoBomberman;
/**
 * La classe Giocatore rappresenta un giocatore nel gioco Bomberman.
 * Memorizza i dettagli del giocatore come username, avatar, statistiche di gioco, esperienza e livello.
 *
 * @author Davide Vittucci
 */
public class Giocatore {
	 /**
     * Username del giocatore.
     */
    private String username;

    /**
     * Percorso dell'avatar del giocatore.
     */
    private String avatar;

    /**
     * Numero totale di partite giocate dal giocatore.
     */
    private int partiteGiocate;

    /**
     * Numero di partite vinte dal giocatore.
     */
    private int partiteVinte;

    /**
     * Numero di partite perse dal giocatore.
     */
    private int partitePerse;

    /**
     * Punti totali accumulati dal giocatore.
     */
    private int puntiTotali;

    /**
     * Livello attuale del giocatore.
     */
    private int livello;

    /**
     * Esperienza attuale accumulata dal giocatore.
     */
    private int expAttuale;

    /**
     * Esperienza necessaria per raggiungere il prossimo livello.
     */
    private int expProssimoLivello;

    /**
     * Costruttore senza parametri per inizializzare un giocatore vuoto.
     */
    public Giocatore() {
    }
    /**
     * Costruttore con tutti i parametri per inizializzare un giocatore con dettagli specifici.
     *
     * @param username Nome utente del giocatore.
     * @param avatar Percorso dell'avatar del giocatore.
     * @param partiteGiocate Numero totale di partite giocate.
     * @param partiteVinte Numero di partite vinte.
     * @param partitePerse Numero di partite perse.
     * @param puntiTotali Punti totali accumulati.
     * @param livello Livello attuale.
     * @param expAttuale Esperienza attuale.
     * @param expProssimoLivello Esperienza necessaria per il prossimo livello.
     */
    public Giocatore(String username, String avatar, int partiteGiocate, int partiteVinte, int partitePerse, int puntiTotali, int livello, int expAttuale, int expProssimoLivello)  {
        this.username = username;
        this.avatar = avatar;
        this.partiteGiocate = partiteGiocate;
        this.partiteVinte = partiteVinte;
        this.partitePerse = partitePerse;
        this.puntiTotali = puntiTotali;
        this.livello = livello;
        this.expAttuale = expAttuale;
        this.expProssimoLivello = expProssimoLivello;
    }

    /**
     * Aggiorna l'esperienza del giocatore e controlla se il livello deve essere incrementato.
     *
     * @param expGuadagnata Esperienza guadagnata.
     */
    public void aggiornaExp(int expGuadagnata) {
        this.expAttuale += expGuadagnata;
        if (this.expAttuale >= this.expProssimoLivello) {
            this.livello++;
            this.expAttuale -= this.expProssimoLivello;
            this.expProssimoLivello = calcolaExpProssimoLivello(this.livello);
        }
    }
    /**
     * Calcola l'esperienza necessaria per raggiungere il prossimo livello.
     *
     * @param livello Livello attuale del giocatore.
     * @return Esperienza necessaria per il prossimo livello.
     */
    private int calcolaExpProssimoLivello(int livello) {
        // Implementa la logica per calcolare l'EXP necessaria per il prossimo livello
        // Per esempio: expProssimoLivello = livello * 500;
        return livello * 750;
    }
    /**
     * Restituisce lo username del giocatore.
     *
     * @return Lo username del giocatore.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Imposta lo username del giocatore.
     *
     * @param username Lo username da impostare.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Restituisce il percorso dell'avatar del giocatore.
     *
     * @return Il percorso dell'avatar.
     */
    public String getAvatar() {
        return avatar;
    }
    /**
     * Imposta il percorso dell'avatar del giocatore.
     *
     * @param avatar Il percorso dell'avatar da impostare.
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    /**
     * Restituisce il numero di partite giocate dal giocatore.
     *
     * @return Il numero di partite giocate.
     */
    public int getPartiteGiocate() {
        return partiteGiocate;
    }
    /**
     * Imposta il numero di partite giocate dal giocatore.
     *
     * @param partiteGiocate Il numero di partite da impostare.
     */
    public void setPartiteGiocate(int partiteGiocate) {
        this.partiteGiocate = partiteGiocate;
    }
    /**
     * Restituisce il numero di partite vinte dal giocatore.
     *
     * @return Il numero di partite vinte.
     */
    public int getPartiteVinte() {
        return partiteVinte;
    }
    /**
     * Imposta il numero di partite vinte dal giocatore.
     *
     * @param partiteVinte Il numero di partite vinte da impostare.
     */
    public void setPartiteVinte(int partiteVinte) {
        this.partiteVinte = partiteVinte;
    }
    /**
     * Restituisce il numero di partite perse dal giocatore.
     *
     * @return Il numero di partite perse.
     */
    public int getPartitePerse() {
        return partitePerse;
    }
    /**
     * Imposta il numero di partite perse dal giocatore.
     *
     * @param partitePerse Il numero di partite perse da impostare.
     */
    public void setPartitePerse(int partitePerse) {
        this.partitePerse = partitePerse;
    }
    /**
     * Restituisce i punti totali accumulati dal giocatore.
     *
     * @return I punti totali accumulati.
     */
    public int getPuntiTotali() {
        return puntiTotali;
    }
    /**
     * Imposta i punti totali accumulati dal giocatore.
     *
     * @param puntiTotali I punti totali da impostare.
     */
    public void setPuntiTotali(int puntiTotali) {
        this.puntiTotali = puntiTotali;
    }
    /**
     * Restituisce il livello attuale del giocatore.
     *
     * @return Il livello attuale.
     */
	public int getLivello() {
		// TODO Auto-generated method stub
		return livello;
	}
	/**
	 * Restituisce l'esperienza attuale del giocatore.
	 *
	 * @return L'esperienza attuale.
	 */
	public int getExpAttuale() {
		// TODO Auto-generated method stub
		return expAttuale;
	}
	/**
	 * Restituisce l'esperienza per il next level.
	 *
	 * @return L'esperienza per il next level.
	 */
	public int getExpProssimoLivello() {
		// TODO Auto-generated method stub
		return expProssimoLivello;
	}
	/**
	 * aggiorna il livello del giocatore
	 *
	 * @param x il livello da settare.
	 */
	public void setLivello(int x) {
		this.livello = x;
		
	}
	/**
	 * Aggiunge l'esperienza al totale del giocatore.
	 *
	 * @param x L'esperienza da aggiungere.
	 */
	public void setExpAttuale(int x) {
		expAttuale += x ;
		
	}
	/**
	 * Imposta l'esperienza necessaria per raggiungere il prossimo livello.
	 *
	 * @param x L'esperienza necessaria per il prossimo livello da impostare.
	 */
	public void setExpProssimoLivello(int x) {
		this.expProssimoLivello = x;
		
	}
}
