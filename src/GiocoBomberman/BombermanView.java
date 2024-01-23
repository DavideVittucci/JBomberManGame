package GiocoBomberman;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;
/**
 * La classe BombermanView rappresenta la visualizzazione grafica di Bomberman nel gioco. 
 * Implementa l'interfaccia Observer per rispondere ai cambiamenti dello stato di Bomberman. 
 * Gestisce il rendering delle immagini e delle animazioni del personaggio, incluse le animazioni 
 * di movimento, invincibilità, morte e fuga.
 *
 * @author Davide Vittucci
 */
public class BombermanView implements Observer {
	/** Dimensioni originali della finestra di gioco. */
	private Dimension originalSize;

	/** Mappa delle immagini sprite di Bomberman per ogni direzione di movimento. */
	private Map<Direction, BufferedImage[]> spriteMap;

	/** Mappa delle immagini sprite per lo stato di invincibilità di Bomberman. */
	private Map<Direction, BufferedImage[]> invicibilitaMap;

	/** Array di BufferedImage per le immagini della sequenza di morte di Bomberman. */
	public BufferedImage[] morteSprites = new BufferedImage[8];

	/** Array di BufferedImage per le immagini della sequenza di fuga di Bomberman. */
	public BufferedImage[] fugaSprites = new BufferedImage[10];

	/** Larghezza di Bomberman nell'ambito della view. */
	public int width;

	/** Altezza di Bomberman nell'ambito della view. */
	public int height;

	/** Posizione X di Bomberman nella view. */
	public int x;

	/** Posizione Y di Bomberman nella view. */
	public int y;

	/** Indice dell'immagine corrente nella sequenza di morte di Bomberman. */
	public int morteFrame;

	/** Indice dell'immagine corrente nella sequenza di fuga di Bomberman. */
	public int fugaFrame;

	/** Numero dello sprite attualmente visualizzato per Bomberman. */
	public int spriteNum;

	/** Indica se alternare tra sprite normali e di invincibilità. */
	public boolean alterna;

	/** Indica se Bomberman è attualmente invincibile. */
	public boolean invincibile;

	/** Indica se Bomberman è stato colpito. */
	public boolean colpito;

	/** Indica se Bomberman è in fuga. */
	public boolean inFuga;

	/** Direzione attuale di movimento di Bomberman. */
	public Direction direction;
	 /**
     * Costruttore di BombermanView. Inizializza le mappe per le immagini sprite e
     * carica le immagini necessarie per le animazioni di Bomberman.
     *
     * @param finestra Il frame principale del gioco, utilizzato per calcolare le dimensioni originali.
     */
    public BombermanView( Frame finestra) {
    	 this.originalSize = finestra.getSize();
        this.spriteMap = new HashMap<>();
        invicibilitaMap = new HashMap<>();
        loadBombermanImages();
        loadMorteImages();
        loadFugaImages();
    }

    /**
     * Aggiorna la visualizzazione di Bomberman in base allo stato corrente del modello Bomberman.
     * Questo metodo viene chiamato automaticamente quando lo stato di Bomberman cambia.
     *
     * @param model Il modello di Bomberman che contiene lo stato attuale.
     */
    public void update(Object model) {
    	 if (model instanceof Bomberman) {
    		 Bomberman bomberman = (Bomberman) model;
    	 
    	this.width = bomberman.getWidth();
    	this.height = bomberman.getHeight();
    	this.x = bomberman.getX();
    	this.y =bomberman.getY();
    	this.morteFrame =bomberman.getMorteFrame();
    	this.fugaFrame =bomberman.getFugaFrame();
    	this.spriteNum =bomberman.getSpriteNum();
    	this.alterna =bomberman.getAlterna();
    	this.invincibile =bomberman.isInvincibile();
    	this.colpito =bomberman.isColpito();
    	this.inFuga = bomberman.isInFuga();
    	this.direction = bomberman.getDirection();
    	 }
	}
    /**
     * Carica le immagini sprite per Bomberman per ogni direzione di movimento e per le corrispettive immagini di invincibilita.
     * Le immagini sono caricate in una mappa di sprite, dove ogni direzione ha quattro sprite corrispondenti.
     */
    private void loadBombermanImages() {
    	spriteMap.put(Direction.UP, new BufferedImage[4]);
        spriteMap.put(Direction.DOWN, new BufferedImage[4]);
        spriteMap.put(Direction.LEFT, new BufferedImage[4]);
        spriteMap.put(Direction.RIGHT, new BufferedImage[4]);
        invicibilitaMap.put(Direction.UP, new BufferedImage[4]);
        invicibilitaMap.put(Direction.DOWN, new BufferedImage[4]);
        invicibilitaMap.put(Direction.LEFT, new BufferedImage[4]);
        invicibilitaMap.put(Direction.RIGHT, new BufferedImage[4]);
        try {
        	 spriteMap.get(Direction.UP)[0] = ImageIO.read(getClass().getResourceAsStream("/Player/schienafermo.png"));
             spriteMap.get(Direction.UP)[1] = ImageIO.read(getClass().getResourceAsStream("/Player/schienamov.png"));
             spriteMap.get(Direction.UP)[2] = ImageIO.read(getClass().getResourceAsStream("/Player/schienamov2.png"));
             spriteMap.get(Direction.UP)[3] = ImageIO.read(getClass().getResourceAsStream("/Player/schienamov2.png"));
             spriteMap.get(Direction.DOWN)[0] = ImageIO.read(getClass().getResourceAsStream("/Player/davanti.png"));
             spriteMap.get(Direction.DOWN)[1] = ImageIO.read(getClass().getResourceAsStream("/Player/davantimov.png"));
             spriteMap.get(Direction.DOWN)[2] = ImageIO.read(getClass().getResourceAsStream("/Player/davantimov2.png"));
             spriteMap.get(Direction.DOWN)[3] = ImageIO.read(getClass().getResourceAsStream("/Player/davantimov2.png"));
             spriteMap.get(Direction.RIGHT)[0] = ImageIO.read(getClass().getResourceAsStream("/Player/destrafermo.png"));
             spriteMap.get(Direction.RIGHT)[1] = ImageIO.read(getClass().getResourceAsStream("/Player/destramov.png"));
             spriteMap.get(Direction.RIGHT)[2] = ImageIO.read(getClass().getResourceAsStream("/Player/destramov2.png"));
             spriteMap.get(Direction.RIGHT)[3] = ImageIO.read(getClass().getResourceAsStream("/Player/destramov2.png"));
             spriteMap.get(Direction.LEFT)[0] = ImageIO.read(getClass().getResourceAsStream("/Player/sinistrafermo.png"));
             spriteMap.get(Direction.LEFT)[1] = ImageIO.read(getClass().getResourceAsStream("/Player/sinistramov.png"));
             spriteMap.get(Direction.LEFT)[2] = ImageIO.read(getClass().getResourceAsStream("/Player/sx movimento2.png"));
             spriteMap.get(Direction.LEFT)[3] = ImageIO.read(getClass().getResourceAsStream("/Player/sx movimento2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            invicibilitaMap.get(Direction.UP)[0] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/schienafermo.png"));
            invicibilitaMap.get(Direction.UP)[1] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/schienamov.png"));
            invicibilitaMap.get(Direction.UP)[2] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/schienamov2.png"));
            invicibilitaMap.get(Direction.UP)[3] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/schienamov2.png"));
            invicibilitaMap.get(Direction.DOWN)[0] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/davanti.png"));
            invicibilitaMap.get(Direction.DOWN)[1] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/davantimov.png"));
            invicibilitaMap.get(Direction.DOWN)[2] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/davantimov2.png"));
            invicibilitaMap.get(Direction.DOWN)[3] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/davantimov2.png"));
            invicibilitaMap.get(Direction.RIGHT)[0] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/destrafermo.png"));
            invicibilitaMap.get(Direction.RIGHT)[1] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/destramov.png"));
            invicibilitaMap.get(Direction.RIGHT)[2] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/destramov2.png"));
            invicibilitaMap.get(Direction.RIGHT)[3] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/destramov2.png"));
            invicibilitaMap.get(Direction.LEFT)[0] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/sinistrafermo.png"));
            invicibilitaMap.get(Direction.LEFT)[1] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/sinistramov.png"));
            invicibilitaMap.get(Direction.LEFT)[2] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/sx movimento2.png"));
            invicibilitaMap.get(Direction.LEFT)[3] = ImageIO.read(getClass().getResourceAsStream("/invincibilita/sx movimento2.png"));
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
    /**
     * Carica le immagini per la sequenza di morte di Bomberman.
     * Le immagini sono caricate in un array e utilizzate quando Bomberman viene colpito.
     */
    private void loadMorteImages() {
        try {
            for (int i = 0; i < 8; i++) {
            	
                morteSprites[i] = ImageIO.read(getClass().getResourceAsStream("/morte/morte" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Carica le immagini per la sequenza di fuga di Bomberman.
     * Le immagini sono caricate in un array e utilizzate quando Bomberman è in fuga.
     */
    private void loadFugaImages() {
        try {
            for (int i = 0; i < 9; i++) {
            	
                fugaSprites[i] = ImageIO.read(getClass().getResourceAsStream("/fuga/fuga" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Disegna il bomberman su schermo, distinguendo quando è morto e quando no
     * 
     *
     *@param g2 Il contesto grafico su cui disegnare bomberman
     */
    public void draw(Graphics2D g2) {
    	  BufferedImage image = getCurrentAnimationFrame();

    	
    	  
    	   
    	    
    	    if (colpito && morteFrame == 1) {
    	    	
    	        this.height +=48;/* nuova altezza */;
    	       
    	        this.y = this.y -16 ;
    	    } else if (colpito) {
    	    	this.height= 126 ;
    	    	//this.y = this.y -16 ;
    	    	}
     
        g2.drawImage(image,this.x, this.y, width,height, null);
      
    }
    
    /**
     * Determina quale immagine di Bomberman deve essere visualizzata in base allo stato attuale.
     * Seleziona l'immagine appropriata dalle mappe di sprite o dagli array in base allo stato di Bomberman.
     *
     * @return BufferedImage L'immagine da visualizzare per Bomberman in questo frame.
     */
    private BufferedImage getCurrentAnimationFrame() {
    	
    	if (colpito) {
    		
            return morteSprites[morteFrame];
        }
if (inFuga) {
    		
            return fugaSprites[fugaFrame];
        }
    	else if (invincibile) {
    		Map<Direction, BufferedImage[]> spriteSource = alterna ? invicibilitaMap : spriteMap;
            return (spriteNum == 1) ? spriteSource.get(direction)[0] : (spriteNum== 2) ? spriteSource.get(direction)[1] : (spriteNum == 3) ? spriteSource.get(direction)[0] : spriteSource.get(direction)[2];
       
    
    }
    	return (spriteNum == 1) ? spriteMap.get(direction)[0] : (spriteNum == 2) ? spriteMap.get(direction)[1] : (spriteNum == 3) ? spriteMap.get(direction)[0] : spriteMap.get(direction)[2];}

	
	
    
}
    
