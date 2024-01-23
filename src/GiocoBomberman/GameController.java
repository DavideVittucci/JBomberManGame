package GiocoBomberman;



import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * La classe GameController e il nucleo centrale della logica di gioco di Bomberman. Coordina l'interazione tra
 * vari componenti del gioco, come Bomberman, nemici, bombe, esplosioni e il livello di gioco. Implementa l'interfaccia
 * Runnable per gestire il ciclo di gioco in un thread separato.
 *
 * Gestisce la logica di gioco, aggiornando lo stato dei vari elementi, gestendo le collisioni, e reagendo a eventi
 * come la sconfitta o la vittoria del giocatore. Inoltre, gestisce la pausa e la ripresa del gioco, e la transizione
 * tra diversi stati di gioco (come la vittoria o la sconfitta).
 *
 * Usa il pattern Observer per aggiornare la vista del gioco in base ai cambiamenti nello stato dei modelli.
 *
 * @author Davide Vittucci
 * @see java.lang.Runnable
 * @see HudModel
 * @see Bomberman
 * @see CollisionChecker
 * @see KeyHandler
 * @see GamePanel
 */

public class GameController implements Runnable {
	/** Modello HUD per la visualizzazione delle informazioni di gioco. */
	private HudModel hudModel;

	/** Riferimento al personaggio principale del gioco, Bomberman. */
	private Bomberman bomberman;

	/** Gestore delle collisioni del gioco. */
	private CollisionChecker collisionChecker;

	/** Gestore degli input da tastiera. */
	private KeyHandler keyHandler;

	/** Lista delle bombe piazzate nel gioco. */
	private ArrayList<Bomba> bombe = new ArrayList<>();

	/** Lista delle esplosioni attive nel gioco. */
	private ArrayList<Esplosione> esplosioni = new ArrayList<>();

	/** Pannello di gioco principale per la visualizzazione dei componenti del gioco. */
	private GamePanel gamePanel;

	/** Indica se il ciclo di gioco è in esecuzione. */
	private boolean running;

	/** Dimensione delle piastrelle nel gioco. */
	private int tileSize;

	/** Thread dedicato al ciclo di gioco. */
	private Thread gameThread;

	/** Modello della mappa del gioco. */
	private MapModel mapModel;

	/** Frequenza di aggiornamento del gioco in frame al secondo. */
	private static final int FPS = 60;

	/** Mappa delle posizioni delle bombe nel gioco. */
	private int[][] bombaMap;

	/** Lista dei nemici presenti nel gioco. */
	private ArrayList<Nemico> nemici;

	/** Livello corrente del gioco. */
	private Level currentLevel;

	/** Mappa delle esplosioni nel gioco. */
	private boolean[][] esplosioniMap;

	/** Durata del timer per il livello corrente. */
	private int durataTimer;

	/** Timer per il controllo del tempo nel livello corrente. */
	private Timer livelloTimer;

	/** Controller del menu del gioco. */
	private MenuController menuController;

	/** Indica se il gioco è in pausa. */
	private boolean isPaused;

	/** Indica se le statistiche del gioco sono state aggiornate dopo la fine del livello. */
	private boolean statisticheAggiornate;

	/** Vista per la schermata di pausa. */
	private PausaView pausaView;

	/** Riferimento alla finestra principale del gioco. */
	private JFrame finestra;

	/** Vista per la schermata di vittoria. */
	private VintoView vintoView;

	/** Ultima posizione X in cui è stata piazzata una bomba. */
	private int lastBombx;

	/** Ultima posizione Y in cui è stata piazzata una bomba. */
	private int lastBomby;

	/** Tempo rimanente nel livello corrente. */
	public int tempoRimanente;

	/** Vista per la schermata di sconfitta. */
	private PersoView persoView;

	/** Indica se l'audio di vittoria è stato riprodotto. */
	private boolean audioVittoriaRiprodotto;

	/** Indica se l'audio di sconfitta è stato riprodotto. */
	private boolean audioSconfittaRiprodotto;
	/**
	 * Costruttore della classe GameController.
	 * Inizializza il controller con le componenti necessarie per gestire il gioco.
	 * 
	 * @param menuController Controller del menu principale.
	 * @param bomberman Il personaggio principale del gioco.
	 * @param level Il livello corrente del gioco.
	 * @param collisionChecker Gestore delle collisioni del gioco.
	 * @param mapModel Modello della mappa del gioco.
	 * @param keyHandler Gestore degli input da tastiera.
	 * @param gamePanel Pannello principale del gioco.
	 * @param hudModel Modello per l'HUD del gioco.
	 * @param dimensioni Array contenente le dimensioni del gioco.
	 * @param finestra Finestra principale del gioco.
	 */
    public GameController(MenuController menuController,Bomberman bomberman,Level level, CollisionChecker collisionChecker, MapModel mapModel, KeyHandler keyHandler, GamePanel gamePanel, HudModel hudModel, int[] dimensioni, JFrame finestra) {
    	pausaView = new PausaView(finestra);
    	persoView = new PersoView(finestra);
    	vintoView = new VintoView(finestra);
        gamePanel.add(pausaView, BorderLayout.CENTER);
        gamePanel.add(persoView, BorderLayout.CENTER);
        gamePanel.add(vintoView, BorderLayout.CENTER);
        pausaView.toggleVisibility(false); 
        persoView.toggleVisibility(false);
        vintoView.toggleVisibility(false);
        setupOverlayListeners();
    	this.mapModel = mapModel;
        this.bomberman = bomberman;
        this.currentLevel = level;
        this.nemici = currentLevel.getEnemies();
        this.collisionChecker = collisionChecker;
        this.keyHandler = keyHandler;
        this.hudModel = hudModel;
        this.menuController= menuController;
        this.gamePanel = gamePanel;
        this.running = true;
        this.tileSize= dimensioni[2];
        bombaMap = new int[dimensioni[1]][dimensioni[0]];
        esplosioniMap = new boolean[dimensioni[1]][dimensioni[0]];
        this.finestra= finestra;
        this.durataTimer = level.getTimer();
        this.tempoRimanente = durataTimer;
        this.isPaused=false;
        this.mapModel.addObserver(gamePanel.getMapView());
        this.mapModel.notifica();
        this.hudModel.addObserver(gamePanel.getHudView());
        this.hudModel.notifica();
        for (Nemico nemico : nemici) {
            nemico.addObserver(gamePanel.getNemicoView(nemico));
        }
        livelloTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) { // Controlla se il gioco è in pausa
                    tempoRimanente--; 
                    if (tempoRimanente <= 0) {
                    	if (bomberman.getVita() > 0) {
                        bomberman.setColpito(true);}
                        livelloTimer.stop();

                        Timer ritardoTimer = new Timer(2500, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                tempoRimanente = durataTimer;
                                if (!isPaused) { // Controlla di nuovo se il gioco è in pausa prima di riavviare il timer
                                    livelloTimer.start();
                                }
                            }
                        });
                        ritardoTimer.setRepeats(false);
                        ritardoTimer.start();
                    }
                }
            }
        });
        livelloTimer.start();
       
     
    }
    /**
     * Imposta i listener per le interazioni con le viste di pausa, sconfitta e vittoria.
     */
    private void setupOverlayListeners() {
    	pausaView.getResumeButton().addActionListener(e -> resumeGame());
    	pausaView.getMenuButton().addActionListener(e -> stopGame());
    	persoView.getGameButton().addActionListener(e -> startNewGame());
    	persoView.getMenuButton().addActionListener(e -> stopGame());
    	vintoView.getGameButton().addActionListener(e -> startNewGame());
    	vintoView.getMenuButton().addActionListener(e -> stopGame());
    }
    /**
     * Gestisce la fine della partita, aggiornando le statistiche del giocatore.
     *
     * @param vittoria Indica se la partita è stata vinta.
     */
    public void finePartita(boolean vittoria) {
    	 if (!statisticheAggiornate) {
    		
        Giocatore giocatore = menuController.getModel().getProfiloSelezionato();
        giocatore.setPartiteGiocate(giocatore.getPartiteGiocate() + 1);
        
        if (vittoria) {
            giocatore.setPartiteVinte(giocatore.getPartiteVinte() + 1);
            giocatore.setPuntiTotali(giocatore.getPuntiTotali() + bomberman.getPunteggio());
            giocatore.aggiornaExp(bomberman.getExp());
        } else {
            giocatore.setPartitePerse(giocatore.getPartitePerse() + 1);
            giocatore.aggiornaExp(bomberman.getExp());
            giocatore.setPuntiTotali(giocatore.getPuntiTotali() + bomberman.getPunteggio());
        }
        
        new GestoreGiocatore().salvaGiocatori(menuController.getModel().getGiocatori());
        statisticheAggiornate = true;
    }}
    /**
     * Gestisce la fine del gioco, aggiornando le statistiche e controllando lo stato di vittoria.
     *
     * @param victory Indica se il giocatore ha vinto.
     */
    public void handleEndGame(boolean victory) {
        if (!statisticheAggiornate) {
            finePartita(victory);
            statisticheAggiornate = true;
        }
    }
    /**
     * Avvia il thread di gioco.
     */
    public void startGame() {
    	
        gameThread = new Thread(this);
        gameThread.start();
        
    }
    /**
     * Avvia una nuova partita, resettando il personaggio principale e il thread.
     */
    public void startNewGame() {
    	 AudioManager.getInstance().stopSoundEffect();
    	AudioManager.getInstance().play("res/audio/Title-Screen-Select2.wav");
    	running = false;
        gameThread.interrupt();
        this.bomberman.resetInstance();
        menuController.newStartGame();
        AudioManager.getInstance().playBackgroundMusic(("res/audio/game.wav"), true,0.7f);
    }
    /**
     * Ferma la partita corrente e ritorna al menu principale.
     */
    public void stopGame() {
    	AudioManager.getInstance().play("res/audio/Title-Screen-Select2.wav");
    	   running = false;
    	   this.bomberman.resetInstance();
    	   gameThread.interrupt();
    	    menuController.mostraMenuIniziale();
    	    AudioManager.getInstance().playBackgroundMusic(("res/audio/main.wav"), true,0.7f);
    }
    /**
     * Il ciclo principale di gioco che viene eseguito nel thread dedicato.
     */
    public void run() {
    	AudioManager.getInstance().playBackgroundMusic(("res/audio/game.wav"), true,0.7f);
        long lastTime = System.nanoTime();
        double ns = 1000000000 / FPS;
        double delta = 0;
        long currentTime;

        while (running) {
        
        	controlloStatoPartita();
            
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / ns;
                lastTime = currentTime;

                if (delta >= 1) {
                    update();
                    gamePanel.repaint();
                    delta--;
                }
            } }
            
        
    /**
     * Mette il gioco in pausa, fermando il timer e le azioni di Bomberman e dei nemici.
     */
    public void pauseGame() {
    	
        isPaused = true;
        livelloTimer.stop();
        bomberman.pause();
        for (Bomba bomba : bombe) {
            bomba.pause();
        }
        for (Nemico nemico : nemici) {
            nemico.pause();
        }
        pausaView.toggleVisibility(true);  
    }
    /**
     * Gestisce la sconfitta nel gioco, mostrando la schermata appropriata.
     */
    public void loseGame() {
    	if (!statisticheAggiornate) {
            handleEndGame(false);
            
        }
    	persoView.toggleVisibility(true);  
    	 AudioManager.getInstance().stopBackgroundMusic();
        if (!audioSconfittaRiprodotto) {
            AudioManager.getInstance().play("res/audio/perso.wav",0.7f);
            audioSconfittaRiprodotto = true;
        }
    }
    /**
     * Gestisce la vittoria nel gioco, mostrando la schermata appropriata.
     */
    private void winGame() {
        if (!statisticheAggiornate) {
            handleEndGame(true);
        }

        vintoView.toggleVisibility(true);  
        AudioManager.getInstance().stopBackgroundMusic();
        if (!audioVittoriaRiprodotto) {
            AudioManager.getInstance().play("res/audio/Audience.wav");
            audioVittoriaRiprodotto = true;
        }
    }
    /**
     * Riprende il gioco dalla pausa.
     */
    public void resumeGame() {
    	AudioManager.getInstance().play("res/audio/Title-Screen-Select.wav");
        if (isPaused) {
            isPaused = false;
            livelloTimer.start();
            bomberman.resume();
            for (Bomba bomba : bombe) {
                bomba.resume();
            }
            for (Nemico nemico : nemici) {
                nemico.resume();
            }
        }pausaView.toggleVisibility(false);  
    }
    /**
     * Prepara il livello impostando i nemici e il tempo rimanente.
     */
    private void setupLevel() {
    	
        nemici = currentLevel.getEnemies();
        tempoRimanente = currentLevel.getTimer(); 

      
    }
    /**
     * Aggiorna lo stato del gioco ad ogni frame.
     * Gestisce l'aggiornamento di tutti gli elementi del gioco come Bomberman, bombe, nemici e collisioni.
     */
    public void update() {
    	
    	if (bomberman.getVittoria()) {
    			bomberman.setExp(currentLevel.getLevelNumber());
    			currentLevel.changeLevel();
    		 
    	        bomberman.setInFuga(false);
    	        bomberman.setVittoria(false);
    	        bomberman.setVite(2);
    	        bomberman.setPunteggio(1000);
    	        setupLevel();
    	}
    	updateHud();
    	
    	
    
    	bomberman.update(keyHandler, collisionChecker, bombe, bombaMap, esplosioniMap);
    
    	collisionChecker.checkFugaPot(bomberman, nemici);
    	if (!bomberman.isInvincibile()) {
    	collisionChecker.checkDeath(bomberman, esplosioniMap);}
    	
    	
    	
        for (Iterator<Bomba> iterator = bombe.iterator(); iterator.hasNext();) {
	        Bomba bomba = iterator.next();
	        
	        bomba.update(bombaMap,esplosioniMap);
	        
	        if (bomba.hasExploded()  ) { //&& bomba.isExplosionDone()
	        	
	            iterator.remove(); 
	            gamePanel.removeBombaView(bomba);
	            bombaMap[bomba.getY() / tileSize][bomba.getX() / tileSize] = 0;
	            Esplosione esplosione = new Esplosione(bomba.getX(), bomba.getY(),bomberman.getRaggio());
	            esplosioni.add(esplosione);
	           
	          }
    }	

     
      for (Iterator<Esplosione> iterator = esplosioni.iterator(); iterator.hasNext();) {
    	     Esplosione esplosione = iterator.next();
    	   
    	     collisionChecker.checkExpx(esplosione);
    	     impostaEsplosioniTrue(esplosione);
	         esplosione.update();
	         if (esplosione.isExplosionDone() ) { 
		        	
		            iterator.remove(); 
		            impostaEsplosioniFalse(esplosione);
		        }}
      for (Iterator<Nemico> iterator = nemici.iterator(); iterator.hasNext();) {
    	  Nemico nemico = iterator.next();
    	  
    	  nemico.update(collisionChecker, bombe, bombaMap, esplosioniMap);
    	  if (nemico.mortoFine) { 
    		  bomberman.setPunteggio(nemico.getPunteggio());
    		  iterator.remove(); }
      }    
      
        this.mapModel.update();
      
        if (keyHandler.bombPressed && !keyHandler.bombKeyPressed && !bomberman.getColpito()&& !bomberman.isInFuga()) {
            placeBomb(bombe, bombaMap);
            keyHandler.bombKeyPressed = true;
        } else if (!keyHandler.bombPressed) {
        	keyHandler.bombKeyPressed = false;
        }
        collisionChecker.checkNemiciCollision(nemici);
        collisionChecker.checkBombermanNemiciCollision(bomberman, nemici);
       }
   
    /**
     * Aggiorna l'HUD con il tempo rimanente e il numero del livello corrente.
     */
    private void updateHud() {
    	hudModel.setTempo(getTempo());
		hudModel.setLevel(currentLevel.getLevelNumber());
	}
    /**
     * Piazza una bomba sul campo di gioco.
     * 
     * @param bombe Lista delle bombe piazzate.
     * @param bombaMap Mappa delle bombe per gestire la loro posizione.
     */
	public void placeBomb( ArrayList<Bomba> bombe, int[][] bombaMap) {
    	if (bombe.size()<bomberman.getMaxBomb()) {
    	int bombaX =  ((bomberman.getX()+  bomberman.getSolidArea().x + bomberman.getSolidArea().width/2 ) / tileSize * tileSize);
        int bombaY = ((bomberman.getY()  + bomberman.getSolidArea().y + bomberman.getSolidArea().height ) / tileSize * tileSize);
        if (bombaMap[bombaY/tileSize][bombaX/tileSize]== 0) {
        Bomba bomba = new Bomba(tileSize,bombaX, bombaY);
        BombaView bombaView = new BombaView(finestra);
        
        bomba.addObserver(bombaView);
        bomba.notifica();
        bombe.add(bomba);
        gamePanel.addBombaView(bomba, bombaView);
      
        bomberman.setOnBomb(true);
        if (bombaMap[lastBomby/tileSize][lastBombx/tileSize]== 2) {
        bombaMap[lastBomby/tileSize][lastBombx/tileSize] = 1;}
        bombaMap[bombaY/ tileSize][bombaX / tileSize] = 2;
        lastBomby = bombaY;
        
        lastBombx = bombaX;
        bomberman.setXBomb(bombaX);
        bomberman.setYBomb(bombaY);
        }
        AudioManager.getInstance().play("res/audio/Place-Bomb.wav");}
       } 
	/**
	 * Imposta le celle della mappa delle esplosioni su true in base al raggio dell'esplosione.
	 * 
	 * @param esplosione L'oggetto esplosione che deve essere gestito.
	 */
 private void impostaEsplosioniTrue( Esplosione esplosione) {
	 
	 int centroX = esplosione.getX() / tileSize;
	 int centroY = esplosione.getY() / tileSize;
	
	 esplosioniMap[centroY][centroX] = true;
	
	 for (int i = 1; i <= esplosione.getRaggioSu(); i++) {
		 
	      esplosioniMap[centroY - i][centroX] = true;
	      
	      
	 }
	 
	 for (int i = 1; i <= esplosione.getRaggioGiu(); i++) {
		
	     esplosioniMap[centroY + i][centroX] = true;
	    
	    
	 }
	 // Sinistra
	 for (int i = 1; i <= esplosione.getRaggioSinistra(); i++) {
	     esplosioniMap[centroY][centroX - i]= true;
	    
	 }
	 // Destra
	 for (int i = 1; i <= esplosione.getRaggioDestra(); i++) {
	     esplosioniMap[centroY][centroX + i] = true;
	   
	 }
	
	}

/**
 * Imposta le celle della mappa delle esplosioni su false dopo che l'esplosione è terminata.
 * 
 * @param esplosione L'oggetto esplosione che deve essere gestito.
 */
private void impostaEsplosioniFalse( Esplosione esplosione) {
	 
	 int centroX = esplosione.getX() / tileSize;
	 int centroY = esplosione.getY() / tileSize;

	 
	 esplosioniMap[centroY][centroX] = false;
	 esplosioniMap.getClass();
	
	 for (int i = 1; i <= esplosione.getRaggioSu(); i++) {
		 
	      esplosioniMap[centroY - i][centroX] = false;
	      esplosioniMap.getClass();
	 }
	 // Giù
	 for (int i = 1; i <= esplosione.getRaggioGiu(); i++) {
		
	     esplosioniMap[centroY + i][centroX] = false;
	     esplosioniMap.getClass();
	   
	 }
	 // Sinistra
	 for (int i = 1; i <= esplosione.getRaggioSinistra(); i++) {
	     esplosioniMap[centroY][centroX - i] = false;
	     esplosioniMap.getClass();
	 }
	 // Destra
	 for (int i = 1; i <= esplosione.getRaggioDestra(); i++) {
	     esplosioniMap[centroY][centroX + i] = false;
	     esplosioniMap.getClass();
	 }
	 //stampaEsplosioniMap();
	}
/**
 * Restituisce la lista delle bombe attualmente attive nel gioco.
 * 
 * @return Lista delle bombe.
 */
public ArrayList<Bomba> getBombe() {
        return bombe;
    }
/**
 * Restituisce il tempo rimanente nel livello corrente.
 * 
 * @return Il tempo rimanente.
 */
public int getTempo() {
	// TODO Auto-generated method stub
	return tempoRimanente;
}
/**
 * Controlla lo stato della partita, gestendo pause e fine del gioco.
 */
public void controlloStatoPartita() {
	
	if (keyHandler.escPressed) {
		pauseGame() ;}
	if (keyHandler.avviaPressed) {
		resumeGame() ;}
	if (bomberman.getVita()==0) {
		finePartita(false);
	  loseGame();
        
    }
	if (currentLevel.getLevelNumber() > 3) {
		finePartita(true);
		  winGame();
	        
	    }
}

/**
 * Restituisce il modello HUD associato al controller di gioco.
 * 
 * @return Il modello HUD.
 */
public HudModel getHudModel() {
	return hudModel;
}
/**
 * Restituisce la lista delle esplosioni attualmente attive nel gioco.
 * 
 * @return Lista delle esplosioni.
 */
public  ArrayList<Esplosione> getEsplosioni() {
	// TODO Auto-generated method stub
	return esplosioni;
}
/**
 * Restituisce la lista dei nemici attualmente attivi nel gioco.
 * 
 * @return Lista dei nemici.
 */
public ArrayList<Nemico> getNemici() {
	// TODO Auto-generated method stub
	return nemici;
}}