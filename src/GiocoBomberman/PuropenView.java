package GiocoBomberman;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
/**
 * PuropenView si occupa della rappresentazione grafica del nemico Puropen nel gioco Bomberman.
 * Gestisce il caricamento e il rendering delle immagini di Puropen, inclusa l'animazione della sua morte.
 * @author Davide Vittucci
 * @see Observer
 */
public class PuropenView  extends NemicoView implements Observer{
	 /**
     * Larghezza di Puropen in pixel.
     */
    private int width;

    /**
     * Altezza di Puropen in pixel.
     */
    private int height;

    /**
     * Coordinata X di Puropen sullo schermo.
     */
    private int x;

    /**
     * Coordinata Y di Puropen sullo schermo.
     */
    private int y;

    /**
     * Indica se Puropen è morto.
     */
    private boolean morto;

    /**
     * Frame corrente dell'animazione della morte di Puropen.
     */
    private int morteFrame;

    /**
     * Numero corrente dello sprite visualizzato per Puropen.
     */
    private int spriteNum;

    /**
     * Array di immagini per l'animazione della morte di Puropen.
     */
    public BufferedImage[] morteSprites = new BufferedImage[3];

    /**
     * Direzione corrente di movimento di Puropen.
     */
    public Direction direction;

    /**
     * Mappa che associa ogni direzione di movimento ai suoi sprite.
     */
    private Map<Direction, BufferedImage[]> spriteMap;

    /**
     * Costruttore di PuropenView. Inizializza e carica le immagini necessarie.
     */
public PuropenView() {
	morteSprites= new BufferedImage[3];
    spriteMap = new HashMap<>();
    loadPuropenImages();
    loadMorteImages();
}
/**
 * Aggiorna lo stato di PuropenView in base al modello di Puropen fornito.
 *
 * @param model Modello di Puropen da cui prendere gli stati aggiornati.
 */
@Override
public void update(Object model) {
	 if (model instanceof Puropen) {
		 Puropen puropen = (Puropen) model;
		 this.width = puropen.getWidth();
		 this.height = puropen.getHeight();
		 this.x = puropen.getX();
		 this.y = puropen.getY();
		 this.direction = puropen.getDirection();
		 this.morteFrame = puropen.getMorteFrame();
		 this.spriteNum = puropen.getSpriteNum();
		 this.morto= puropen.isMorto();
	 }
}

/**
 * Carica le immagini per l'animazione della morte di Puropen.
 */
private void loadMorteImages() {
    try {
        for (int i = 0; i < 3; i++) {
        	
        	morteSprites[i] = ImageIO.read(getClass().getResourceAsStream("/morteNemici/morte" + i + ".png"));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
/**
 * Carica le immagini per Puropen in base alla direzione di movimento.
 */
private void loadPuropenImages() {
	spriteMap.put(Direction.UP, new BufferedImage[4]);
    spriteMap.put(Direction.DOWN, new BufferedImage[4]);
    spriteMap.put(Direction.LEFT, new BufferedImage[4]);
    spriteMap.put(Direction.RIGHT, new BufferedImage[4]);
    try {
    	 spriteMap.get(Direction.UP)[0] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Su 1.png"));
         spriteMap.get(Direction.UP)[1] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Su 2.png"));
         spriteMap.get(Direction.UP)[2] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Su 3.png"));
         spriteMap.get(Direction.UP)[3] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Su 4.png"));
         spriteMap.get(Direction.DOWN)[0] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Giu 1.png"));
         spriteMap.get(Direction.DOWN)[1] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Giu 2.png"));
         spriteMap.get(Direction.DOWN)[2] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Giu 3.png"));
         spriteMap.get(Direction.DOWN)[3] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Giu 4.png"));
         spriteMap.get(Direction.RIGHT)[0] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen destra 1.png"));
         spriteMap.get(Direction.RIGHT)[1] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen destra 2.png"));
         spriteMap.get(Direction.RIGHT)[2] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen destra 3.png"));
         spriteMap.get(Direction.RIGHT)[3] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen destra 4.png"));
         spriteMap.get(Direction.LEFT)[0] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Sinistra 1.png"));
         spriteMap.get(Direction.LEFT)[1] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Sinistra 2.png"));
         spriteMap.get(Direction.LEFT)[2] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Sinistra 3.png"));
         spriteMap.get(Direction.LEFT)[3] = ImageIO.read(getClass().getResourceAsStream("/Puropen/Puropen Sinistra 4.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }
	
		
}
/**
 * Disegna Puropen sul contesto grafico fornito.
 *
 * @param g2 Contesto grafico su cui disegnare Puropen.
 */
public void draw(Graphics2D g2) {
	  BufferedImage image = getCurrentAnimationFrame();

	    // Dimensioni di default
	   
	    if(morto) {
			 
			   this.height += + 28;
			  
			     this.y-=32;
			 
		 }
 
    g2.drawImage(image,x, y, width,height, null);
    
}

/**
 * Ottiene il frame corrente dell'animazione di Puropen in base al suo stato.
 *
 * @return Immagine corrente da visualizzare per Puropen.
 */
private BufferedImage getCurrentAnimationFrame() {
if (morto) {
		
        return morteSprites[morteFrame];
    }	
		
      return (spriteNum == 1) ? spriteMap.get(direction)[0] : (spriteNum == 2) ? spriteMap.get(direction)[1] : (spriteNum == 3) ? spriteMap.get(direction)[2] : spriteMap.get(direction)[3];
   

}
}
