package GiocoBomberman;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
/**
 * La classe BombaView implementa l'interfaccia Observer e si occupa della rappresentazione grafica delle bombe
 * e delle loro esplosioni nel gioco Bomberman. Questa classe carica e gestisce le immagini per le diverse fasi
 * di una bomba, dal suo posizionamento fino all'esplosione.
 *
 * @author Davide Vittucci
 */
public class BombaView implements Observer {
 
	/** Array di immagini per la bomba 
	 * */
    private BufferedImage[] bombImages = new BufferedImage[3];
    /** Array di immagini per  le  fasi di esplosione.
	 * */
    private BufferedImage[] explosionImages = new BufferedImage[6];

    /** Dimensioni originali della finestra di gioco.
     * 
     *  */
     
    private Dimension originalSize;

    /**
    cordinata c della bomba
    */
    private int x;
    /**
    cordinata y della bomba
    */
    private int y;
    /**
    frame della bomba
    */
    private int bombFrame;
    /**
    frame dell esplosione
    */
    private int explosionFrame;

    
    /**
    stato dell esplosione
    */
    private boolean esplosa;
    
    /**
     * Costruttore della classe BombaView. Inizializza le immagini della bomba e dell'esplosione.
    *
    * @param finestra La finestra del gioco in cui la bomba viene visualizzata.
    */
    public BombaView(Frame finestra) {
    	 this.originalSize = finestra.getSize(); // Salva le dimensioni originali del frame
	      
        loadBombImages();
        loadExplosionImages();
        
    }
    /**
     * Aggiorna lo stato di visualizzazione della bomba in base al modello Bomba.
     *
     * @param model Il modello Bomba da cui ricevere gli aggiornamenti.
     */
    public void update(Object model) {
   	 if (model instanceof Bomba) {
   		 Bomba bomba = (Bomba) model;
   		 this.x = bomba.getX();
   		 this.y = bomba.getY();
   		this.esplosa = bomba.hasExploded();
   		 this.explosionFrame = bomba.getExplosionFrame();
   		this.bombFrame = bomba.getBombFrame();
	}}
    /**
     * Carica le immagini utilizzate per visualizzare la bomba nelle sue diverse fasi.
     * Questo metodo legge le immagini da file e le memorizza in un array per un facile accesso.
     * In caso di errore di lettura, verrà stampato un messaggio di errore.
     */
    private void loadBombImages() {
        try {
            for (int i = 0; i < 3; i++) {
                bombImages[i] = ImageIO.read(getClass().getResourceAsStream("/Bomba/Bomba" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Carica le immagini utilizzate per visualizzare l'esplosione di una bomba.
     * Questo metodo legge le immagini da file e le memorizza in un array per un facile accesso.
     * In caso di errore di lettura, verrà stampato un messaggio di errore.
     */
    private void loadExplosionImages() {
        try {
            for (int i = 1; i < 7; i++) {
                explosionImages[i - 1] = ImageIO.read(getClass().getResourceAsStream("/Esplosione/esplosione" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Disegna la bomba o l'esplosione sul componente grafico fornito.
     *
     * @param g2 Il componente grafico su cui disegnare.
     */
    public void draw(Graphics2D g2) {
    	int width ; // Larghezza scalata
        int height ;
       
        if (!esplosa) {
        	 width = 72; // Larghezza scalata
             height = 72;
            g2.drawImage(bombImages[bombFrame], this.x, this.y-5,width,height, null);
           
        } else {
        	 width =208; // Larghezza scalata
             height = 208;
            g2.drawImage(explosionImages[explosionFrame],x-72, x-72, width, height, null);
            
            
        }
    }
  
}