package GiocoBomberman;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * La classe EsplosioneView e responsabile della visualizzazione delle esplosioni nel gioco Bomberman.
 * Gestisce il rendering delle immagini dell'esplosione in diverse fasi e direzioni.
 *  @author Davide Vittucci
 */
public class EsplosioneView {
	/**
	 * Array bidimensionale di BufferedImage per gestire gli sprite delle esplosioni.
	 * Il primo indice rappresenta la direzione dell'esplosione (centro, su, giù, sinistra, destra),
	 * mentre il secondo indice rappresenta le diverse fasi dell'animazione dell'esplosione.
	 */
	private BufferedImage[][] spriteEsplosione;

	/**
	 * Array bidimensionale di BufferedImage per gestire l'allungamento degli sprite delle esplosioni.
	 * Utilizzato per rappresentare la propagazione dell'esplosione in direzioni orizzontali e verticali.
	 * Il primo indice rappresenta la direzione dell'allungamento (orizzontale, verticale),
	 * mentre il secondo indice rappresenta le diverse fasi dell'animazione dell'esplosione.
	 */
	private BufferedImage[][] spriteAllungamento;
    
    /**
     * Costruttore della classe EsplosioneView.
     * Inizializza gli array di sprite e carica le immagini necessarie.
     */
    public EsplosioneView() {
        spriteEsplosione = new BufferedImage[5][5]; // 5 direzioni, 5 stadi per ogni direzione
        spriteAllungamento = new BufferedImage[2][5];
        caricaSprite();
    }
    /**
     * Carica gli sprite delle esplosioni da file di risorse.
     * Legge le immagini per ogni direzione e fase dell'esplosione.
     */
    private void caricaSprite() {
    	try {
           
            String[] direzioni = {"centro", "su", "giu", "sinistra", "destra"};
            String[] direzioni2 = {"all orr", "all ver"};
            for (int i = 0; i < direzioni.length; i++) {
                for (int j = 0; j < 5; j++) { 
                    String percorso = "/EsplosioneDivisa/"+j+"/"+ direzioni[i]+" " + j + ".png";
                    spriteEsplosione[i][j] = ImageIO.read(getClass().getResourceAsStream(percorso));
                }
            }
            for (int i = 0; i < direzioni2.length; i++) {
                for (int j = 0; j < 5; j++) { 
                    String percorso = "/EsplosioneDivisa/"+j+"/"+ direzioni2[i]+" " + j + ".png";
                    spriteAllungamento[i][j] = ImageIO.read(getClass().getResourceAsStream(percorso));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Disegna l'esplosione sullo schermo.
     *
     * @param g2 Il contesto grafico per il rendering.
     * @param esplosione L'oggetto Esplosione da visualizzare.
     */
    public void draw(Graphics2D g2, Esplosione esplosione) {
    	
    	
    	int centroX = esplosione.getX();
		int centroY = esplosione.getY();
		int frame = esplosione.getFrame()  ;
		int raggio = esplosione.getRaggio();
        
            g2.drawImage(spriteEsplosione[0][frame], centroX, centroY,64,64, null);
        for (int i = 1; i <= esplosione.getRaggioDestra(); i++) {
        	if (i!=raggio) {
            g2.drawImage(spriteAllungamento[0][frame], centroX + (i * 64), centroY,64,64, null);
        }}
        for (int i = 1; i <= esplosione.getRaggioSinistra(); i++) {
        	if (i!=raggio) {
            g2.drawImage(spriteAllungamento[0][frame], centroX - (i * 64), centroY,64,64, null);
        }}
        for (int i = 1; i <= esplosione.getRaggioSu(); i++) {
        	if (i!=raggio) {
            g2.drawImage(spriteAllungamento[1][frame], centroX , centroY- (i * 64),64,64, null);
        }}
        for (int i = 1; i <= esplosione.getRaggioGiu(); i++) {
        	if (i!=raggio) {
            g2.drawImage(spriteAllungamento[1][frame], centroX , centroY+ (i * 64),64,64, null);
        }}
        if (esplosione.getRaggioDestra()==raggio) {
            g2.drawImage(spriteEsplosione[4][frame], centroX+ (raggio*64), centroY,64,64, null);}
        if (esplosione.getRaggioSinistra()==raggio) {
            g2.drawImage(spriteEsplosione[3][frame], centroX-(raggio*64), centroY,64,64, null);}
        if (esplosione.getRaggioSu() ==raggio){
            g2.drawImage(spriteEsplosione[1][frame], centroX, centroY-(raggio*64),64,64, null);}
        if (esplosione.getRaggioGiu() ==raggio) {
            g2.drawImage(spriteEsplosione[2][frame], centroX, centroY+ (raggio*64),64,64, null);}
        }}
    

