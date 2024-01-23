package GiocoBomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * Gestisce la vista del menu principale nel gioco JBomberman.
 * Mostra i bottoni del menu e le informazioni del profilo del giocatore.
 * implementa observer
 * @author Davide Vittucci
 */
public class MenuView implements Observer  {
	/** Riferimento al frame principale del gioco. */
    private JFrame frame;
    /** Pannello principale del menu. */
    private JPanel menuPanel;
    /** Etichetta per lo sfondo del menu. */
    private JLabel backgroundLabel;
    /** Pulsanti per selezione profilo. */
    private JButton selectProfileButton;
    /** Pulsanti per creare profilo. */
    private JButton createProfileButton;
    /** Pulsanti per play. */
    private JButton startGameButton;
    /** Pulsanti per editare profili */
    private JButton editButton;
    /** Pulsanti per statistiche profilo profili */
    private JButton statisticsButton;
    /** Etichetta per visualizzare dettagli dell'utente come avatar  */
    
    private JLabel userAvatarLabel;
    /** Etichetta per visualizzare dettagli dell'utente come nome. */
    private JLabel userNameLabel;
    /** Etichetta per visualizzare dettagli dell'utente come livello. */
    private JLabel livelloLabel;
    /** Etichetta per visualizzare dettagli dell'utente come exp. */
    private JLabel expLabel;
    /** Gestisce il ridimensionamento dei componenti. */
	private Scaler scaler;

    /**
     * Costruttore per creare la vista del menu.
     * 
     * @param frame Il frame principale dell'applicazione.
     */
    public MenuView(JFrame frame) {
        this.frame = frame;
        initializeMenu();
        livelloLabel = new JLabel();
        expLabel = new JLabel();
    }
    /**
     * Aggiorna la vista del menu in base alle modifiche nel model.
     * 
     * @param model Il modello del menu.
     */
    @Override
    public void update(Object model) {
    	 if (model instanceof MenuModel) {
             // Aggiorna la view in base al modello specifico
    		 MenuModel menuModel = (MenuModel) model;
             // Utilizza i dati di specificModel per aggiornare la view
         
        if (menuModel.getProfiloSelezionato() != null) {
           
            try {
                updateUserDetails(menuModel.getProfiloSelezionato().getUsername(),
                		menuModel.getProfiloSelezionato().getAvatar());
                aggiornaStatisticheGiocatore(menuModel.getProfiloSelezionato());
            } catch (IOException e) {
                // Gestire l'eccezione
                e.printStackTrace();
            }
        }
    }}
    // Altri metodi privati e pubblici

    /**
     * Restituisce il pannello del menu.
     * 
     * @return Il pannello del menu.
     */
    public JPanel getMenuPanel() {
        return menuPanel;
    }
    /**
     * Inizializza i componenti del menu, inclusi bottoni, etichette e sfondo.
     * Imposta le dimensioni, le posizioni e le icone dei bottoni.
     * Aggiunge i componenti al pannello del menu.
     */
    private void initializeMenu() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(JBomberMan.screenWidth, JBomberMan.screenHeight));
        scaler= new Scaler();
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });
        try {
        	BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Menu/menu.png"));
            Image scaledImage = backgroundImage.getScaledInstance(JBomberMan.screenWidth, JBomberMan.screenHeight, Image.SCALE_SMOOTH);
            ImageIcon backgroundIcon = new ImageIcon(scaledImage);
            backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setLayout(null);

            // Imposta posizione e dimensioni dei bottoni
            selectProfileButton = new JButton();
            createProfileButton = new JButton();
            startGameButton = new JButton();
            statisticsButton = new JButton();
            editButton = new JButton();
            statisticsButton.setBounds(700, 800, 200, 40);
            statisticsButton.setVisible(false);
            editButton.setBounds(700, 840, 200, 40);
            editButton.setVisible(false);
            try {
                BufferedImage selectImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/Selezione profilo.png"));
                BufferedImage playImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/play.png"));
                BufferedImage newImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/Nuovo profilo.png"));
                BufferedImage stats = ImageIO.read(getClass().getResourceAsStream("/Opzioni/stats.png"));
                BufferedImage edit = ImageIO.read(getClass().getResourceAsStream("/Opzioni/edit.png"));
                Image scaledImage1 = selectImage.getScaledInstance(500, 40, Image.SCALE_SMOOTH);
                Image scaledImage2 = newImage.getScaledInstance(500, 40, Image.SCALE_SMOOTH);
                Image scaledImage3 = playImage.getScaledInstance(300, 60, Image.SCALE_SMOOTH);
                Image scaledImage4 = stats.getScaledInstance(200, 40, Image.SCALE_SMOOTH);
                Image scaledImage5 = edit.getScaledInstance(200, 40, Image.SCALE_SMOOTH);
                selectProfileButton.setIcon(new ImageIcon(scaledImage1));
                selectProfileButton.setBorderPainted(false);
                selectProfileButton.setContentAreaFilled(false); 
                selectProfileButton.setFocusPainted(false); 
                selectProfileButton.setOpaque(false); 
                
                editButton.setIcon(new ImageIcon(scaledImage5));
                editButton.setBorderPainted(false); // Rimuove la bordatura
                editButton.setContentAreaFilled(false); // Rimuove lo sfondo
                editButton.setFocusPainted(false); // Rimuove il focus border
                editButton.setOpaque(false); // Rendi il bottone trasparente
                
                createProfileButton.setIcon(new ImageIcon(scaledImage2));
                createProfileButton.setBorderPainted(false); // Rimuove la bordatura
                createProfileButton.setContentAreaFilled(false); // Rimuove lo sfondo
                createProfileButton.setFocusPainted(false); // Rimuove il focus border
                createProfileButton.setOpaque(false); // Rendi il bottone trasparente
                
                startGameButton.setIcon(new ImageIcon(scaledImage3));
                startGameButton.setBorderPainted(false); // Rimuove la bordatura
                startGameButton.setContentAreaFilled(false); // Rimuove lo sfondo
                startGameButton.setFocusPainted(false); // Rimuove il focus border
                startGameButton.setOpaque(false); // Rendi il bottone trasparente
                
                statisticsButton.setIcon(new ImageIcon(scaledImage4));
                statisticsButton.setBorderPainted(false); // Rimuove la bordatura
                statisticsButton.setContentAreaFilled(false); // Rimuove lo sfondo
                statisticsButton.setFocusPainted(false); // Rimuove il focus border
                statisticsButton.setOpaque(false); // Rendi il bottone trasparente
            } catch (IOException e) {
                e.printStackTrace();
                // Gestisci l'errore nel caricamento dell'immagine
            }
            startGameButton.setBounds(300, 500, 300, 60); // x, y, width, height
            createProfileButton.setBounds(300, 600, 500, 40);
            selectProfileButton.setBounds(300, 700, 500, 40);
            userAvatarLabel = new JLabel();
            userAvatarLabel.setBounds(300, 760, 80, 80);
          
            userNameLabel = new JLabel();
            userNameLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
            
            userNameLabel.setBounds(380, 800, 320, 40);
            
            backgroundLabel.add(statisticsButton);
            backgroundLabel.add(editButton);
            backgroundLabel.add(userAvatarLabel);
            backgroundLabel.add(userNameLabel);

       
            backgroundLabel.add(selectProfileButton);
            backgroundLabel.add(createProfileButton);
            backgroundLabel.add(startGameButton);

            menuPanel.add(backgroundLabel, BorderLayout.CENTER);
            frame.add(menuPanel);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Impossibile caricare l'immagine di sfondo");
        }
    }
    /**
     * Aggiorna le statistiche del giocatore visualizzate nel menu.
     * Mostra il livello attuale del giocatore e i suoi punti esperienza (EXP).
     *
     * @param giocatore Il giocatore di cui visualizzare le statistiche.
     */
    public void aggiornaStatisticheGiocatore(Giocatore giocatore) {
        livelloLabel.setText("Livello: " + giocatore.getLivello());
        expLabel.setText("EXP: " + giocatore.getExpAttuale() + "/" + giocatore.getExpProssimoLivello());
    }
    /**
     * Aggiorna i dettagli dell'utente nel menu, inclusi il nome utente e l'avatar.
     * Imposta l'icona dell'avatar e il nome utente sulla GUI e rende visibili i bottoni di statistiche e modifica.
     *
     * @param username   Il nome utente da visualizzare.
     * @param avatarIcon Il percorso dell'icona dell'avatar da visualizzare.
     * @throws IOException Se si verifica un errore nel caricamento dell'immagine dell'avatar.
     */
    public void updateUserDetails(String username, String avatarIcon) throws IOException {

        userNameLabel.setText(username);
        userNameLabel.setOpaque(true);
        userNameLabel.setBackground(Color.ORANGE);
        statisticsButton.setVisible(true);
        editButton.setVisible(true);
        BufferedImage avatar = ImageIO.read(getClass().getResourceAsStream(avatarIcon));	
        Image avatar1 = avatar.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        if (avatarIcon != null) {
            userAvatarLabel.setIcon(new ImageIcon(avatar1));
            scaler.scaleLabelImage(userAvatarLabel, avatarIcon, userAvatarLabel.getSize());
        } else {
        	System.out.println(avatarIcon);
            userAvatarLabel.setIcon(null);
        }
    }
  
  
    /**
   	 * return bottone seleziona
   	 * @return  bottone seleziona
   	 */
    public JButton getSelectProfileButton() {
        return selectProfileButton;
    }
    /**
   	 * return bottone crea
   	 * @return  bottone crea
   	 */
    public JButton getCreateProfileButton() {
        return createProfileButton;
    }
    /**
   	 * return bottone start
   	 * @return  bottone start
   	 */
    public JButton getStartGameButton() {
        return startGameButton;
    }
    /**
	 * return bottone statistiche
	 * @return  bottone statistiche
	 */
	public AbstractButton getStatisticsButton() {
		// TODO Auto-generated method stub
		return statisticsButton;
	}
	/**
	 * return bottone edit
	 * @return  bottone edit
	 */
	public AbstractButton getEditButton() {
		// TODO Auto-generated method stub
		return editButton;
	}
	
	/**
	 * Aggiorna il layout del menu in base alle dimensioni attuali della finestra.
	 * Ridimensiona e riposiziona i componenti del menu.
	 */
	private void updateLayout() {
	    Dimension currentSize = frame.getSize();
	   
	    scaler.scaleComponent(selectProfileButton, 300, 700, 500, 40, currentSize);
	    scaler.scaleComponent(createProfileButton, 300, 600, 500, 40, currentSize);
	    scaler.scaleComponent(startGameButton, 300, 500, 300, 60, currentSize);
	    scaler.scaleComponent(statisticsButton, 700, 800, 200, 40, currentSize);
	    scaler.scaleComponent(editButton, 700, 840, 200, 40, currentSize);
	    scaler.scaleComponent(userAvatarLabel, 300, 760, 80, 80, currentSize);
	    scaler.scaleComponent(userNameLabel, 380, 800, 320, 40, currentSize);
	    
	    scaler.scaleButtonImage(selectProfileButton, "/Opzioni/Selezione profilo.png", selectProfileButton.getSize());
	    scaler.scaleButtonImage(createProfileButton, "/Opzioni/Nuovo profilo.png", createProfileButton.getSize());
	    scaler.scaleButtonImage(startGameButton, "/Opzioni/play.png", startGameButton.getSize());
	    scaler.scaleButtonImage(statisticsButton, "/Opzioni/stats.png", statisticsButton.getSize());
	    scaler.scaleButtonImage(editButton, "/Opzioni/edit.png", statisticsButton.getSize());
	
        
	}
}

          
