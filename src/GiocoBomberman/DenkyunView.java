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
 * La classe DenkyunView e responsabile per la rappresentazione visiva del nemico Denkyun nel videogioco Bomberman.
 * Questa classe estende {@link NemicoView} e implementa {@link Observer} per aggiornarsi con le modifiche allo stato di Denkyun.
 * Gestisce il caricamento e la visualizzazione delle immagini sprite per le varie animazioni di Denkyun, inclusi i movimenti,
 * lo stato di invincibilità e le animazioni di morte.
 * 
 * Le immagini sprite sono caricate all'avvio e selezionate per la visualizzazione in base allo stato corrente del modello di Denkyun.
 * La classe gestisce anche la logica per determinare quale immagine sprite visualizzare in ogni momento, basandosi sullo stato di
 * movimento, invincibilità e morte di Denkyun.
 * 
 * @author Davide Vittucci
 * @see Denkyun
 * @see NemicoView
 * @see Observer
 */
public class DenkyunView extends NemicoView implements Observer {

	/**
	 * Array di immagini BufferedImage che rappresenta i vari sprite di Denkyun.
	 * Ogni immagine corrisponde a una diversa posizione o fase di animazione del personaggio.
	 */
	private BufferedImage[] spriteMap;

	/**
	 * Array di immagini BufferedImage per la rappresentazione visiva dello stato di invincibilità di Denkyun.
	 * Queste immagini vengono utilizzate per visualizzare un aspetto differente quando Denkyun è invincibile.
	 */
	private BufferedImage[] invincibilitaMap;

	/**
	 * Larghezza corrente dell'immagine sprite di Denkyun da disegnare.
	 */
	private int width;

	/**
	 * Altezza corrente dell'immagine sprite di Denkyun da disegnare.
	 */
	private int height;

	/**
	 * Posizione sull'asse X di Denkyun nel contesto del gioco.
	 */
	private int x;

	/**
	 * Posizione sull'asse Y di Denkyun nel contesto del gioco.
	 */
	private int y;

	/**
	 * Flag booleano che indica se Denkyun è attualmente in uno stato di invincibilità.
	 */
	private boolean invincibile;

	/**
	 * Flag booleano che controlla l'alternanza degli sprite in caso di invincibilità.
	 * Serve per creare un effetto lampeggiante.
	 */
	private boolean alterna;

	/**
	 * Flag booleano che indica se Denkyun è morto.
	 */
	private boolean morto;

	/**
	 * Indice corrente dell'animazione di morte di Denkyun.
	 */
	private int morteFrame;

	/**
	 * Numero corrente dello sprite di Denkyun da visualizzare.
	 */
	private int spriteNum;

	/**
	 * Array di immagini BufferedImage che rappresenta le varie fasi dell'animazione di morte di Denkyun.
	 */
	public BufferedImage[] morteSprites = new BufferedImage[3];
	/**
	 * Costruttore della classe DenkyunView. Inizializza gli array di sprite e invincibilità,
	 * e carica le immagini necessarie per rappresentare visivamente Denkyun.
	 */
public DenkyunView() {
 
    spriteMap = new BufferedImage[6];
    invincibilitaMap = new BufferedImage[6];
    loadDenkuynimage();
    loadMorteImages();
}
/**
 * Aggiorna lo stato di visualizzazione di Denkyun in base al modello Denkyun.
 * Imposta la posizione, le dimensioni, lo stato di invincibilità, il numero dello sprite,
 * e altri attributi visivi in base allo stato corrente del modello.
 *
 * @param model Il modello di Denkyun da cui ottenere le informazioni per l'aggiornamento.
 */
@Override
public void update(Object model) {
	 if (model instanceof Denkyun) {
		 Denkyun denkyun = (Denkyun) model;
		 this.width = denkyun.getWidth();
		 this.height = denkyun.getHeight();
		 this.x = denkyun.getX();
		 this.y = denkyun.getY();
		 this.invincibile = denkyun.isInvincibile();
		 this.alterna = denkyun.getAlterna();
		 this.morteFrame = denkyun.getMorteFrame();
		 this.spriteNum = denkyun.getSpriteNum();
		 this.morto= denkyun.isMorto();
	 }
}
/**
 * Carica le immagini degli sprite per Denkyun, inclusi gli sprite per lo stato normale e invincibile.
 */
private void loadDenkuynimage() {
	
    try {
    	 spriteMap[0] = ImageIO.read(getClass().getResourceAsStream("/Denkyun/Denkyun 0.png"));
         spriteMap[1] = ImageIO.read(getClass().getResourceAsStream("/Denkyun/Denkyun 1.png"));
         spriteMap[2] = ImageIO.read(getClass().getResourceAsStream("/Denkyun/Denkyun 2.png"));
         spriteMap[3] = ImageIO.read(getClass().getResourceAsStream("/Denkyun/Denkyun 3.png"));
         spriteMap[4] = ImageIO.read(getClass().getResourceAsStream("/Denkyun/Denkyun 4.png"));
         spriteMap[5] = ImageIO.read(getClass().getResourceAsStream("/Denkyun/Denkyun 5.png"));
         invincibilitaMap[0] = ImageIO.read(getClass().getResourceAsStream("/DenkyunInv/Denkyun 0.png"));
         invincibilitaMap[1] = ImageIO.read(getClass().getResourceAsStream("/DenkyunInv/Denkyun 1.png"));
         invincibilitaMap[2] = ImageIO.read(getClass().getResourceAsStream("/DenkyunInv/Denkyun 2.png"));
         invincibilitaMap[3] = ImageIO.read(getClass().getResourceAsStream("/DenkyunInv/Denkyun 3.png"));
         invincibilitaMap[4] = ImageIO.read(getClass().getResourceAsStream("/DenkyunInv/Denkyun 4.png"));
         invincibilitaMap[5] = ImageIO.read(getClass().getResourceAsStream("/DenkyunInv/Denkyun 5.png"));
       
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
/**
 * Disegna Denkyun sul contesto grafico fornito.
 *
 * @param g2 Il contesto grafico su cui disegnare Denkyun.
 */
public void draw(Graphics2D g2) {
	  BufferedImage image = getCurrentAnimationFrame();

	  
	 if(morto) {
		
		   this.height += 28;
		   
		     this.y-=32;
		 
	 }
 
    g2.drawImage(image,x, y, width,height, null);
    g2.setColor(Color.RED);
   
}
/**
 * Carica le immagini dell'animazione di morte di Denkyun.
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
 * Ottiene l'immagine corrente da visualizzare per Denkyun.
 * Sceglie l'immagine appropriata in base allo stato corrente di Denkyun, come se è morto, invincibile, ecc.
 *
 * @return L'immagine BufferedImage corrente da visualizzare per Denkyun.
 */
private BufferedImage getCurrentAnimationFrame() {
	if (morto) {
		
        return morteSprites[morteFrame];
    }
	if (invincibile) {
		 BufferedImage[] spriteSource = alterna ? invincibilitaMap : spriteMap;
		 return (spriteNum == 1) ? spriteSource[0] : (spriteNum == 2) ? spriteSource[1] :
	        	(spriteNum == 3) ? spriteSource[2] : (spriteNum== 4) ? spriteSource[3] : (spriteNum == 5) ? spriteSource[4] 
	        			:(spriteNum == 6) ? spriteSource[5] : spriteSource[0];

}
        return (spriteNum == 1) ? spriteMap[0] : (spriteNum == 2) ? spriteMap[1] :
        	(spriteNum == 3) ? spriteMap[2] : (spriteNum == 4) ? spriteMap[3] : (spriteNum == 5) ? spriteMap[4] 
        			:(spriteNum == 6) ? spriteMap[5] : spriteMap[0];
   

}


}
