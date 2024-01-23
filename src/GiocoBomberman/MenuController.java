package GiocoBomberman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
/**
 * La classe MenuController gestisce la logica di interazione tra l'utente e il menu principale del gioco Bomberman.
 * Questa classe si occupa di inizializzare e aggiornare la vista del menu, gestire gli input dell'utente e avviare 
 * il gioco. Implementa la logica per la selezione dei profili dei giocatori, la creazione di nuovi profili, 
 * la visualizzazione delle statistiche e l'avvio di nuove partite.
 *
 * Il controller utilizza diversi modelli e viste per gestire le diverse parti del menu, come la selezione dei profili,
 * la visualizzazione delle statistiche e le impostazioni del gioco. È responsabile anche per il collegamento 
 * con le classi di gioco principali, quali MapModel, Bomberman, CollisionChecker, e altre, preparando l'ambiente 
 * di gioco e trasferendo il controllo al GameController quando una partita viene avviata.
 * 
 * @author Davide Vittucci
 * @see MenuModel
 * @see MenuView
 * @see GameController
 * @see Bomberman
 * @see CollisionChecker
 */
public class MenuController {
	 /** Dimensione di base per le piastrelle nel gioco. */
    static final int tileSize = 64;

    /** Numero massimo di colonne dello schermo di gioco. */
    final static int maxScreenCol = 17;

    /** Numero massimo di righe dello schermo di gioco. */
    final static int maxScreenRow = 15;

    /** Dimensione dello schermo, determinata dal toolkit del sistema. */
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /** Larghezza dello schermo in pixel. */
    public static int screenWidth;

    /** Altezza dello schermo in pixel. */
    public static int screenHeight;

    /** Modello del menu contenente i dati per la vista del menu. */
    private MenuModel model;

    /** Vista del menu per l'interfaccia utente. */
    private MenuView view;

    /** Frame principale dell'applicazione. */
    private JFrame frame;

    /** Modello della mappa di gioco. */
    private MapModel mapModel;

    /** Livello di gioco corrente. */
    private  Level livello;

    /** Vista della mappa di gioco. */
    private  MapView mapView;

    /** Oggetto Bomberman, il personaggio principale del gioco. */
    private Bomberman bomberman;

    /** Vista del Bomberman per la rappresentazione grafica. */
    private BombermanView bombermanView;

    /** Gestore degli input da tastiera. */
    private  KeyHandler keyHandler;

    /** Controllore delle collisioni nel gioco. */
    private  CollisionChecker collisionChecker;

    /** Modello dell'HUD (Heads-Up Display) del gioco. */
    private HudModel hudModel;

    /** Vista delle bombe nel gioco. */
    private BombaView bombaViews;

    /** Vista delle esplosioni nel gioco. */
    private EsplosioneView esplosioneView;

    /** Vista dell'HUD del gioco. */
    private  HudView hudView;

    /** Pannello principale del gioco. */
    private GamePanel gamePanel;
    /** controller principale del gioco. */
    private GameController gameController;
    /**
     * Costruisce un MenuController con i modelli e le viste specificate.
     * Inizializza il controller con il modello del menu, la vista del menu e il frame principale dell'applicazione.
     * Imposta gli ascoltatori degli eventi e avvia la musica di sottofondo.
     *
     * @param model Il modello del menu che contiene i dati da visualizzare e modificare.
     * @param view La vista del menu per l'interazione con l'utente.
     * @param frame Il frame principale dell'applicazione.
     */
    public MenuController(MenuModel model, MenuView view, JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;
        this.model.addObserver(this.view);
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        AudioManager.getInstance().playBackgroundMusic(("res/audio/main.wav"), true,0.7f);
        setupListeners() ;
        view.getStartGameButton().setVisible(false);
        verificaGiocatoriEAbilitaBottone();
        
    }

    /**
     * Verifica se ci sono giocatori salvati e abilita o disabilita il bottone di selezione profilo di conseguenza.
     */
    private void verificaGiocatoriEAbilitaBottone() {
        if (model.getGiocatori().isEmpty()) {
            view.getSelectProfileButton().setEnabled(false);
        } else {
            view.getSelectProfileButton().setEnabled(true);
        }
    }
    /**
     * Configura i listener per i bottoni del menu.
     */
    private void setupListeners() {
    	 view.getSelectProfileButton().addActionListener(e -> selezionaProfilo());
         view.getCreateProfileButton().addActionListener(e -> creaProfilo());
         view.getStartGameButton().addActionListener(e -> handleStartGame());
         view.getStatisticsButton().addActionListener(e -> mostraStatistiche());
         view.getEditButton().addActionListener(e -> EditProfilo());
    }
    /**
     * Gestisce l'azione di modifica del profilo utente.
     */
	private void EditProfilo()  {
		AudioManager.getInstance().play("res/audio/Title-Screen-Select.wav");
        String username = JOptionPane.showInputDialog(frame, "Inserisci il tuo username:");
        if (username != null && !username.trim().isEmpty()) {
            // Elenco degli avatar disponibili
            ImageIcon[] avatarIcons = {
                new ImageIcon(getClass().getResource("/Avatar/avatar1.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar2.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar3.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar4.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar5.png"))
            };
            String[] avatarOptions = {"/Avatar/avatar1.png", "/Avatar/avatar2.png","/Avatar/avatar3.png","/Avatar/avatar4.png","/Avatar/avatar5.png"};

            ImageIcon avatar =  (ImageIcon) JOptionPane.showInputDialog(frame, "Scegli un avatar:",
                    "Selezione Avatar", JOptionPane.QUESTION_MESSAGE, null, avatarIcons, avatarIcons[0]);
           
            if (avatar != null) {
                int avatarIndex = Arrays.asList(avatarIcons).indexOf(avatar);
                String avatarPath = avatarOptions[avatarIndex];

                Giocatore giocatore = model.getProfiloSelezionato();
                giocatore.setAvatar(avatarPath);
                giocatore.setUsername(username);
                new GestoreGiocatore().salvaGiocatori(model.getGiocatori());
            }
        }
        verificaGiocatoriEAbilitaBottone();
        aggiornaInformazioniUtente();
    }
	 /**
     * Gestisce la selezione di un profilo da parte dell'utente.
     */
	private void selezionaProfilo() {
		AudioManager.getInstance().play("res/audio/Title-Screen-Select.wav");
        DefaultListModel<Giocatore> listModel = new DefaultListModel<>();
        model.getGiocatori().forEach(listModel::addElement);
        JList<Giocatore> list = new JList<>(listModel);
        list.setCellRenderer(new GiocatoreListCellRenderer());
        int result = JOptionPane.showConfirmDialog(frame, new JScrollPane(list), "Seleziona Profilo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Giocatore selezionato = list.getSelectedValue();
            if (selezionato != null) {
                model.setProfiloSelezionato(selezionato);
                view.getStartGameButton().setVisible(true);
            }
        } aggiornaInformazioniUtente();
    }
	  /**
     * Aggiorna le informazioni visualizzate dell'utente selezionato.
     */
	private void aggiornaInformazioniUtente() {
		
	    if (model.getProfiloSelezionato() != null) {
	        // Aggiorna le etichette con il nome utente e l'avatar
	        try {
				view.updateUserDetails(model.getProfiloSelezionato().getUsername(),
				                      model.getProfiloSelezionato().getAvatar());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } 
	}
	/**
     * Mostra le statistiche dell'utente selezionato in una nuova finestra.
     */
	private void mostraStatistiche() {
		AudioManager.getInstance().play("res/audio/Title-Screen-Select.wav");
	    if (model.getProfiloSelezionato() != null) {
	        // Apri una nuova finestra con le statistiche
	        JFrame statsFrame = new JFrame("Statistiche Giocatore");
	        statsFrame.setSize(400, 300);
	        Font statsFont = new Font("Arial", Font.BOLD, 25);
	        JLabel nameLabel = new JLabel("Nome: " + model.getProfiloSelezionato().getUsername());
	        JLabel livelloLabel = new JLabel("Livello: " + model.getProfiloSelezionato().getLivello());
	        JLabel expLabel = (new JLabel("Exp: " + model.getProfiloSelezionato().getExpAttuale() + "/" + model.getProfiloSelezionato().getExpProssimoLivello()));
	        JLabel gameLabel = new JLabel("Partite Giocate: " + model.getProfiloSelezionato().getPartiteGiocate());
	        JLabel winLabel = new JLabel("Partite Vinte: " + model.getProfiloSelezionato().getPartiteVinte());
	        JLabel loseLabel = new JLabel("Partite Perse: " + model.getProfiloSelezionato().getPartitePerse());
	        JLabel pointLabel =new JLabel("Punti Totali: " + model.getProfiloSelezionato().getPuntiTotali());
	        statsFrame.setLayout(new BoxLayout(statsFrame.getContentPane(), BoxLayout.Y_AXIS));
	        statsFrame.add(nameLabel);

	        try {
	            ImageIcon avatarIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(model.getProfiloSelezionato().getAvatar())));
	            statsFrame.add(new JLabel(avatarIcon));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       
	        nameLabel.setFont(statsFont);
	       
	        livelloLabel.setFont(statsFont);
	        expLabel.setFont(statsFont);
	        gameLabel.setFont(statsFont);
	        winLabel.setFont(statsFont);
	        loseLabel.setFont(statsFont);
	        pointLabel.setFont(statsFont);
	        statsFrame.add(livelloLabel);
	        statsFrame.add(expLabel);
	        statsFrame.add(gameLabel);
	        statsFrame.add(winLabel);
	        statsFrame.add(loseLabel);
	        statsFrame.add(pointLabel);

	        statsFrame.setLocationRelativeTo(frame);
	        statsFrame.setVisible(true);
	    }
	}
	 /**
     * Renderer personalizzato per la lista dei giocatori.
     */
    private static class GiocatoreListCellRenderer extends DefaultListCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Giocatore giocatore = (Giocatore) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(list, giocatore.getUsername(), index, isSelected, cellHasFocus);
            try {
            	
                ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream( giocatore.getAvatar())));
                label.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return label;
        }
    }
    /**
     * Gestisce la creazione di un nuovo profilo utente.
     */
    private void creaProfilo() {
    	AudioManager.getInstance().play("res/audio/Title-Screen-Select.wav");
        String username = JOptionPane.showInputDialog(frame, "Inserisci il tuo username:");
        if (username != null && !username.trim().isEmpty()) {
            // Elenco degli avatar disponibili
            ImageIcon[] avatarIcons = {
                new ImageIcon(getClass().getResource("/Avatar/avatar1.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar2.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar3.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar4.png")),
                new ImageIcon(getClass().getResource("/Avatar/avatar5.png"))
            };
            String[] avatarOptions = {"/Avatar/avatar1.png", "/Avatar/avatar2.png","/Avatar/avatar3.png","/Avatar/avatar4.png","/Avatar/avatar5.png"};

            ImageIcon avatar = (ImageIcon) JOptionPane.showInputDialog(frame, "Scegli un avatar:",
                    "Selezione Avatar", JOptionPane.QUESTION_MESSAGE, null, avatarIcons, avatarIcons[0]);

            if (avatar != null) {
                int avatarIndex = Arrays.asList(avatarIcons).indexOf(avatar);
                String avatarPath = avatarOptions[avatarIndex];

                Giocatore nuovoGiocatore = new Giocatore(username, avatarPath, 0, 0, 0, 0, 1, 0, 500);
                model.getGiocatori().add(nuovoGiocatore);
                model.setProfiloSelezionato(nuovoGiocatore);
                new GestoreGiocatore().salvaGiocatori(model.getGiocatori());
                view.getStartGameButton().setVisible(true);
            }else {
                // Nessun avatar selezionato, non fare nulla
            }
        }
        verificaGiocatoriEAbilitaBottone();
        aggiornaInformazioniUtente();
    }
    /**
     * Gestisce l'avvio della prima  partita.
     */
    public void handleStartGame() {
    	AudioManager.getInstance().play("res/audio/Title-Screen-Select2.wav");
    	frame.getContentPane().removeAll();
    	primoGioco(frame);
        frame.revalidate();
        frame.repaint();
    }
    /**
     * Avvia una nuova partita dal menu.
     */
    public void newStartGame() {
    	frame.getContentPane().removeAll();
    	nuovoGioco(frame);
        frame.revalidate();
        frame.repaint();
    }
    /**
     * Mostra il menu iniziale.
     */
    public void mostraMenuIniziale() {
        // Pulisci il frame e mostra il menu iniziale
        frame.getContentPane().removeAll();
        frame.add(view.getMenuPanel());
        frame.revalidate();
        frame.repaint();
    }
    /**
     * Configura il gioco per la prima partita.
     *
     * @param finestra Il JFrame principale dell'applicazione.
     */
    private  void primoGioco(JFrame finestra) {
    	

         this.mapModel = new MapModel(maxScreenCol, maxScreenRow);
         this.livello = new Level(1,mapModel);
         mapView = new MapView(mapModel, tileSize);
       
         this.bomberman = Bomberman.getInstance();
        int[] dimensioni = {17, 15, 64};
        this.bombermanView = new BombermanView( finestra);
        	bomberman.addObserver(bombermanView); 
        	bomberman.notifica();
        	keyHandler = new KeyHandler();
         collisionChecker = new CollisionChecker(mapModel.getMapTile(), mapModel.getPiastrelle(), tileSize);
         hudModel = HudModel.getInstance();
         bomberman.addObserver(hudModel); 
     	 bomberman.notifica();
         esplosioneView = new EsplosioneView();
        
        mapModel.loadMap(1);
         hudView = new HudView(bomberman);
         gamePanel = new GamePanel(bombermanView, mapView, hudView, esplosioneView,livello.getView(), finestra);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(keyHandler);
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setResizable(true);
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setDoubleBuffered(true);
        finestra.add(gamePanel, BorderLayout.CENTER);
        finestra.revalidate();
        finestra.repaint();
        finestra.pack();
        finestra.setLocationRelativeTo(null);
        finestra.setVisible(true);
        finestra.setExtendedState(JFrame.MAXIMIZED_BOTH);
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
        GameController gameController = new GameController( this,bomberman,livello,  collisionChecker, mapModel, keyHandler, gamePanel, hudModel, dimensioni, finestra);
        hudView.setGameController(gameController);
        gamePanel.setGameController(gameController);
       
        gameController.startGame();
        AudioManager.getInstance().stopBackgroundMusic();
    }
    /**
     * Configura il gioco per una nuova partita.
     *
     * @param finestra Il JFrame principale dell'applicazione.
     */
private  void nuovoGioco(JFrame finestra) {

		
        
        this.livello = new Level(1,mapModel);
       
        this.bomberman.reset();
        int[] dimensioni = {17, 15, 64};
        
        this.collisionChecker = new CollisionChecker(mapModel.getMapTile(), mapModel.getPiastrelle(), tileSize);
        this.keyHandler = new KeyHandler();
        
        hudModel.notifica();
        mapModel.loadMap(1);
        this.gamePanel = new GamePanel(bombermanView,  mapView, hudView, esplosioneView,livello.getView(), finestra);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(keyHandler);
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setResizable(true);
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setDoubleBuffered(true);
        finestra.add(gamePanel, BorderLayout.CENTER);
        finestra.revalidate();
        finestra.repaint();
        finestra.pack();
        finestra.setLocationRelativeTo(null);
        finestra.setVisible(true);
        finestra.setExtendedState(JFrame.MAXIMIZED_BOTH);
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
        this.gameController = new GameController( this,bomberman,livello,  collisionChecker, mapModel, keyHandler, gamePanel, hudModel, dimensioni, finestra);
       
        gamePanel.setGameController(gameController);

        gameController.startGame();
        AudioManager.getInstance().stopBackgroundMusic();
    }
/**
 * Restituisce il modello di menu corrente.
 *
 * @return Il modello di menu corrente.
 */
public MenuModel getModel() {
	// TODO Auto-generated method stub
	return model;
}
}
