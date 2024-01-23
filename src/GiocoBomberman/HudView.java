package GiocoBomberman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * HudView si occupa di visualizzare l'interfaccia utente del gioco, 
 * inclusi il punteggio, la vita, il tempo rimanente e altri dettagli relativi al giocatore.
 * Implementa l'interfaccia Observer per aggiornare la visualizzazione in base ai cambiamenti del modello HUD.
 *@author Davide Vittucci
 * @see Observer
 */
public class HudView implements  Observer {
	 /** Immagine di sfondo per l'HUD. */
    private BufferedImage hudImage;

    /** Array di sprite per i numeri visualizzati nell'HUD. */
    private BufferedImage[] numeriSprites = new BufferedImage[10];

    /** Posizione X fissa per il punteggio. */
    private final int xPunteggio = 240;
    
    /** Posizione X fissa per il tempo. */
    private final int xTempo = 530;
    
    /** Posizione X fissa per la vita. */
    private final int xVita = 109;
    
    /** Posizione X fissa per il livello. */
    private final int xLevel = 9;
    
    /** Posizione Y fissa per tutti gli elementi visualizzati. */
    private final int yGenerale = 40;
    
    /** Larghezza di ogni sprite numerico. */
    private final int larghezzaSprite = 32;
    
    /** Tempo rimanente nel livello corrente. */
    private int tempoRimanente;

    /** Riferimento all'istanza di Bomberman per accedere ai dati del giocatore. */
    Bomberman bomberman;

    /** Riferimento al GameController per comunicare con altre parti del gioco. */
    private GameController gameController;

    /**Punteggio del giocatore. */
    private int punteggio; 
    /**Numero di vite del giocatore */
    private int vita;        
    /**Numero massimo di bombe */
    private int bombe;      
    /**raggio delle esplosioni*/
    private int raggio;      
    /**Velocità del giocatore*/
    private int speed;       

    /**Tempo rimanente*/
    private int tempo;       
    /**Livello corrente*/
    
    private int currentLevel;
    /**
     * Costruttore di HudView.
     * @param bomberman Riferimento all'oggetto Bomberman per accedere ai dati del giocatore.
     */
		
		public HudView( Bomberman bomberman) {
		this.bomberman= bomberman;
		loadNumeriSprites();
	   loadHudImage();
    }
		public void update(Object model) {
			 if (model instanceof HudModel) {
				 HudModel hudModel = (HudModel) model;
			 
			this.punteggio = hudModel.getPunteggio();
			this.vita = hudModel.getVita();
			this.bombe = hudModel.getMaxBomb();
			this.raggio =hudModel.getRaggio();
			this.speed =hudModel.getSpeed();
			this.tempo = hudModel.getTempo();
			this.currentLevel = hudModel.getLevel();
			 }
		}
		/**
		 * Carica l'immagine dell'HUD.
		 */
    private void loadHudImage() {
    	try {
            hudImage = ImageIO.read(getClass().getResourceAsStream("/Hud/hud.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
    /**
     * Disegna l'HUD sul pannello.
     * @param g2 Contesto grafico utilizzato per il disegno.
     * @param hudModel Il modello dell'HUD contenente i dati da visualizzare.
     */
	public void draw(Graphics2D g2, HudModel hudModel) {
		g2.setColor(Color.BLACK); // o qualsiasi sia il colore di sfondo
	    g2.fillRect(0, 0, 1115, 128);
		g2.drawImage(hudImage, 0, 0, 1115,128,null);
		drawPunteggio(g2, punteggio);
		drawVita( g2, vita);
		drawTempo( g2,tempo);
		drawLevel( g2,currentLevel);
		drawPot(g2,bombe, raggio,speed);
    }
	/**
	 * Carica gli sprite numerici.
	 */
private void loadNumeriSprites() {
    try {
        for (int i = 0; i < numeriSprites.length; i++) {
            numeriSprites[i] = ImageIO.read(getClass().getResourceAsStream("/numeri/" + i + ".png"));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
/**
 * Disegna il punteggio sull'HUD.
 * @param g2 Contesto grafico.
 * @param punteggio Il punteggio da visualizzare.
 */
public void drawPunteggio(Graphics2D g2, int punteggio) {
    String punteggioStr = String.valueOf(punteggio);
    int x = xPunteggio;

    for (int i = 0; i < punteggioStr.length(); i++) {
        int numero = Character.getNumericValue(punteggioStr.charAt(i));
        g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
        x += larghezzaSprite; // Sposta la x per il prossimo numero
    }
}
/**
 * Disegna il Tempo sull'HUD.
 * @param g2 Contesto grafico.
 * @param c Il Tempo da visualizzare.
 */
public void drawTempo(Graphics2D g2,int c) {
	int tempo = c;
    String tempostr = String.valueOf(tempo);
    int x = xTempo;

    for (int i = 0; i < tempostr.length(); i++) {
        int numero = Character.getNumericValue(tempostr.charAt(i));
        g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
        x += larghezzaSprite; // Sposta la x per il prossimo numero
    }
}
/**
 * Disegna le vite sull'HUD.
 * @param g2 Contesto grafico.
 * @param vita le vite da visualizzare.
 */
public void drawVita(Graphics2D g2, int vita) {
  
		int x = xVita;
		String vitaStr = String.valueOf(vita);
        for (int i = 0; i < vitaStr.length(); i++) {
            int numero = Character.getNumericValue(vitaStr.charAt(i));
            g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
            x += larghezzaSprite; // Sposta la x per il prossimo numero
        }
    }
/**
 * Disegna il livello sull'HUD.
 * @param g2 Contesto grafico.
 * @param level il livello da visualizzare.
 */
public void drawLevel(Graphics2D g2, int level) {
	  
	int x = xLevel;
	String vitaStr = String.valueOf(level);
    for (int i = 0; i < vitaStr.length(); i++) {
        int numero = Character.getNumericValue(vitaStr.charAt(i));
        g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
        x += larghezzaSprite; // Sposta la x per il prossimo numero
    }
}
/**
 * Disegna i potenziamenti sull'HUD.
 * @param g2 Contesto grafico.
 * @param mmaxBombe max bombe
 * @param raggioExp  max raggio attuale
 * @param velocita  velocita attuale
 */
public void drawPot(Graphics2D g2, int mmaxBombe, int raggioExp, int velocita) {
	BufferedImage raggio;
	BufferedImage speed;
	BufferedImage bombe;
 int x = 758;
	try {
		raggio = ImageIO.read(getClass().getResourceAsStream("/drop/raggio.png"));
		g2.drawImage(raggio, x, yGenerale,32,48, null);
		x+=32;
		String raggStr = String.valueOf(raggioExp);
		for (int i = 0; i < raggStr.length(); i++) {
	        int numero = Character.getNumericValue(raggStr.charAt(i));
	        g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
	        x += larghezzaSprite; // Sposta la x per il prossimo numero
	        x+=32;
	    }
		speed = ImageIO.read(getClass().getResourceAsStream("/drop/velocità.png"));
		g2.drawImage(speed, x, yGenerale,32,48, null);
		x+=32;
		String speedStr = String.valueOf(velocita);
		for (int i = 0; i < speedStr.length(); i++) {
	        int numero = Character.getNumericValue(speedStr.charAt(i));
	        g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
	        x += larghezzaSprite; // Sposta la x per il prossimo numero
	        x+=32;
	    }
		bombe = ImageIO.read(getClass().getResourceAsStream("/drop/bomba.png"));
		g2.drawImage(bombe, x, yGenerale,32,48, null);
		x+=32;
		String bombStr = String.valueOf(mmaxBombe);
		for (int i = 0; i < bombStr.length(); i++) {
	        int numero = Character.getNumericValue(bombStr.charAt(i));
	        g2.drawImage(numeriSprites[numero], x, yGenerale,32,48, null);
	        x += larghezzaSprite; // Sposta la x per il prossimo numero
	       
	    }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	
	String vitaStr = String.valueOf(bomberman.getVita());
    for (int i = 0; i < vitaStr.length(); i++) {
        int numero = Character.getNumericValue(vitaStr.charAt(i));
      
    }
}
/**
 * Imposta il GameController associato all'HUD.
 * @param gameController Il controller del gioco.
 */
public void setGameController(GameController gameController) {  
    this.gameController = gameController;
}
}

