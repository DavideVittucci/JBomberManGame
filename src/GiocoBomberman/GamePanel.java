package GiocoBomberman;

import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * La classe GamePanel gestisce la visualizzazione grafica del gioco Bomberman.
 * Questa classe estende JPanel e si occupa di disegnare tutti gli elementi grafici del gioco,
 * inclusi i nemici, le bombe, l'HUD e la mappa di gioco.
 * 
 * @author Davide Vittucci
 * @see BombermanView
 * @see MapView
 * @see HudView
 * @see EsplosioneView
 * @see NemicoView
 * @see BombaView
 */

public class GamePanel extends JPanel {
	/**
	 * Identificatore univoco per la serializzazione.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Vista grafica di Bomberman nel gioco.
	 */
	private BombermanView bombermanView;

	/**
	 * Timer utilizzato per gestire il ridimensionamento del pannello di gioco.
	 */
	private Timer resizeTimer;

	/**
	 * Vista della mappa di gioco, che include piastrelle, muri e altri elementi statici.
	 */
	private MapView mapView;

	/**
	 * Controller del gioco, responsabile per gestire la logica di gioco e gli aggiornamenti.
	 */
	private GameController gameController;

	/**
	 * Vista dell'HUD (Head-Up Display), mostrando informazioni come punteggio, vita, ecc.
	 */
	private HudView hudView;

	/**
	 * Vista delle esplosioni causate dalle bombe nel gioco.
	 */
	private EsplosioneView esplosioneView;

	/**
	 * Mappa che associa ogni nemico a una vista specifica per la loro rappresentazione grafica.
	 */
	private HashMap<Nemico, NemicoView> nemiciViews;

	/**
	 * Larghezza predefinita dell'immagine off-screen per il rendering.
	 */
	private static final int OFF_SCREEN_WIDTH = 1088;

	/**
	 * Altezza predefinita dell'immagine off-screen per il rendering.
	 */
	private static final int OFF_SCREEN_HEIGHT = 960;

	/**
	 * Buffer off-screen utilizzato per il rendering delle immagini prima di visualizzarle sullo schermo.
	 */
	private BufferedImage offScreenImage;

	/**
	 * Riferimento al frame principale del gioco per la gestione delle dimensioni e del layout.
	 */
	private Frame finestra;

	/**
	 * Immagine ridimensionata utilizzata per il rendering su schermo del contenuto del pannello.
	 */
	private Image scaledOffScreenImage;

	/**
	 * Mappa delle viste delle bombe per la loro rappresentazione grafica.
	 */
	private HashMap<Bomba, BombaView> bombeViews;
	/**
	 * Costruttore della classe GamePanel.
	 * Inizializza il panel con le viste necessarie per visualizzare gli elementi del gioco.
	 *
	 * @param bombermanView La vista di Bomberman.
	 * @param mapView La vista della mappa.
	 * @param hudView La vista dell'HUD.
	 * @param esplosioneView La vista delle esplosioni.
	 * @param view Mappa delle viste dei nemici.
	 * @param finestra Il frame contenente il panel.
	 */
    public GamePanel(BombermanView bombermanView,  MapView mapView, HudView hudView, EsplosioneView esplosioneView,HashMap<Nemico, NemicoView> view, Frame finestra ) {
        this.bombermanView = bombermanView;
        
        this.mapView = mapView;
        this.hudView = hudView;
        this.esplosioneView =esplosioneView;
        this.nemiciViews = view;
        this.finestra=finestra;
        bombeViews = new HashMap<>();
        initializeOffScreenBuffer(OFF_SCREEN_WIDTH, OFF_SCREEN_HEIGHT);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (resizeTimer != null) {
                    resizeTimer.stop();
                }
                resizeTimer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Questo codice verrà eseguito solo dopo 500 ms di inattività di ridimensionamento
                        scaledOffScreenImage = offScreenImage.getScaledInstance(finestra.getWidth(), finestra.getHeight(), Image.SCALE_SMOOTH);
                        resizeTimer.stop();
                    }
                });
                resizeTimer.setRepeats(false);
                resizeTimer.start();
            }
        });
    }
    /**
     * Aggiunge una vista di una bomba al panel.
     *
     * @param bomba L'oggetto Bomba da visualizzare.
     * @param bombaView La vista della bomba.
     */
    public void addBombaView(Bomba bomba, BombaView bombaView) {
        bombeViews.put(bomba, bombaView);
    }

/**
 * Imposta il controller di gioco per il panel.
 *
 * @param gameController Il controller di gioco da impostare.
 */
    public void setGameController(GameController gameController) {  
        this.gameController = gameController;
    }
    /**
     * Inizializza il buffer off-screen per il doppio buffering.
     *
     * @param width Larghezza del buffer.
     * @param height Altezza del buffer.
     */
    private void initializeOffScreenBuffer(int width, int height) {
        // Controlla se l'immagine off-screen esiste già e se le sue dimensioni sono cambiate
        if (offScreenImage == null || offScreenImage.getWidth() != width || offScreenImage.getHeight() != height) {
            offScreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
    }
    /**
     * Metodo sovrascritto di JPanel per disegnare il panel.
     *
     * @param g Oggetto Graphics usato per disegnare.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2OffScreen = offScreenImage.createGraphics();
        drawGame(g2OffScreen);
        g2OffScreen.dispose();
        int newWidth = finestra.getWidth();
        int newHeight = finestra.getHeight();
        g.drawImage(offScreenImage, 0, 0, newWidth, newHeight, null);
    }

/**
 * Disegna i nemici sul panel.
 *
 * @param g2 Oggetto Graphics2D usato per disegnare.
 */
    private void drawEnemies(Graphics2D g2) {
    	for (Nemico nemico : gameController.getNemici()) {
    		NemicoView view = nemiciViews.get(nemico);
    	    if (view != null) {
    	        view.draw(g2); 
    	    }
	    	
	        
	    }
		
	}
    /**
     * Disegna le bombe sul panel.
     *
     * @param g2 Oggetto Graphics2D usato per disegnare.
     */
	public void drawBombs(Graphics2D g2) {
	    for (Bomba bomba : gameController.getBombe()) {
	         
	    	getBombaView(bomba).draw(g2);
	        
	    }
	}
	/**
	 * Disegna gli elementi di gioco sul buffer off-screen.
	 *
	 * @param g2 Oggetto Graphics2D usato per disegnare.
	 */
	private void drawGame(Graphics2D g2) {
		
	    mapView.draw(g2);
	    hudView.draw(g2, gameController.getHudModel());
	    drawBombs(g2);
	    drawExp(g2);
	    drawEnemies(g2);
	    bombermanView.draw(g2);
	    
		
		
	   
	    
	    
	}
	/**
	 * Disegna le esplosioni sul panel.
	 *
	 * @param g2 Oggetto Graphics2D usato per disegnare.
	 */
    public void drawExp(Graphics2D g2) {
	    for (Esplosione esplosione : gameController.getEsplosioni()) {
	         
	    	esplosioneView.draw(g2,esplosione);
	        
	    }
	}
    
    /**
     * Restituisce la vista di Bomberman.
     *
     * @return L'oggetto BombermanView associato a Bomberman.
     */
    public BombermanView getBombermanView() {
        return this.bombermanView;
    }

    /**
     * Ottiene la vista del nemico specificato.
     *
     * @param nemico L'oggetto Nemico di cui si vuole ottenere la vista.
     * @return La vista del nemico corrispondente.
     */
    public NemicoView getNemicoView(Nemico nemico) {
        return nemiciViews.get(nemico);
    }

    /**
     * Restituisce la vista di una specifica bomba.
     *
     * @param bomba L'oggetto Bomba di cui si vuole ottenere la vista.
     * @return La vista della bomba corrispondente.
     */
    public BombaView getBombaView(Bomba bomba) {
        return bombeViews.get(bomba);
    }

    /**
     * Rimuove la vista di una bomba dal pannello di gioco.
     *
     * @param bomba L'oggetto Bomba di cui rimuovere la vista.
     */
    public void removeBombaView(Bomba bomba) {
        bombeViews.remove(bomba);
    }

    /**
     * Ottiene l'Observer associato alla vista della mappa.
     *
     * @return L'Observer collegato alla mappa.
     */
    public Observer getMapView() {
        return mapView;
    }

    /**
     * Ottiene l'Observer associato alla vista dell'HUD.
     *
     * @return L'Observer collegato all'HUD.
     */
    public Observer getHudView() {
        return hudView;
    }
	}
