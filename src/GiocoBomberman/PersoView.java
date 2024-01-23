package GiocoBomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * PersoView gestisce l'interfaccia utente per la schermata che appare quando il giocatore perde nel gioco Bomberman.
 * Questa classe si occupa di visualizzare i bottoni per iniziare una nuova partita o tornare al menu principale,
 * oltre a gestire un'immagine di sfondo per la schermata.
 * 
 * @author Davide Vittucci
 * @see JFrame
 * @see JButton
 * @see JLabel
 */
public class PersoView extends JPanel {
	/**
     * Frame principale del gioco.
     */
    private JFrame frame;

    /**
     * Pannello principale per la schermata di perdita.
     */
    private JPanel pausaPanel;

    /**
     * Etichetta per l'immagine di sfondo.
     */
    private JLabel backgroundLabel;

    /**
     * Bottoni per iniziare una nuova partita 
     */
    private JButton newGameButton;
    /**
     * Bottoni per  tornare al menu.
     */
    
    private JButton  menuButton;

    /**
     * Gestore per scalare gli elementi dell'interfaccia in base alla dimensione della finestra.
     */
    private Scaler scaler;

    /**
     * Costruisce la vista della schermata di perdita con il frame di riferimento.
     * 
     * @param frame Il frame principale del gioco.
     */
    public PersoView(JFrame frame) {
    	
        this.frame = frame;
        scaler = new Scaler();
        setLayout(new BorderLayout());
        
        pausaPanel = new JPanel(new BorderLayout());
        pausaPanel.setLayout(null); // Usa un layout nullo per posizionamento manuale
        add(pausaPanel, BorderLayout.CENTER);
        newGameButton = new JButton();
        menuButton = new JButton();
        newGameButton.setBounds(350, 400, 400, 160);
        menuButton.setBounds(350, 600, 400, 80);
        pausaPanel.add(newGameButton);
        pausaPanel.add(menuButton);
        newGameButton.setBorderPainted(false); // Rimuove la bordatura
        newGameButton.setContentAreaFilled(false); // Rimuove lo sfondo
        newGameButton.setFocusPainted(false); // Rimuove il focus border
        newGameButton.setOpaque(false); 
        menuButton.setBorderPainted(false); // Rimuove la bordatura
        menuButton.setContentAreaFilled(false); // Rimuove lo sfondo
        menuButton.setFocusPainted(false); // Rimuove il focus border
        menuButton.setOpaque(false); 
        // Imposta le immagini iniziali dei bottoni
        setButtonImages();
        // Carica e imposta l'immagine di sfondo
        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/back (1).png"));
            ImageIcon backgroundIcon = new ImageIcon(backgroundImage);
            backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());
            pausaPanel.add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crea e aggiunge i bottoni al JPanel
       
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });
    }
    /**
     * Imposta le immagini iniziali dei bottoni.
     */
    private void setButtonImages() {
        try {
            BufferedImage resumeImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/nuovaPartita.png"));
            BufferedImage menuImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/menu.png"));
            newGameButton.setIcon(new ImageIcon(resumeImage));
            menuButton.setIcon(new ImageIcon(menuImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Aggiorna il layout in base alla dimensione corrente della finestra.
     * Questo metodo scala e riposiziona i componenti dell'interfaccia.
     */
    private void updateLayout() {
        Dimension currentSize = frame.getSize();
        Dimension overlaySize = scaler.scaleDimension(new Dimension(880, 760), currentSize);

        // Aggiorna le dimensioni e posizioni dei bottoni
        scaler.scaleComponent(newGameButton, 350, 400, 400, 160, currentSize);
        scaler.scaleComponent(menuButton, 350, 600, 400, 80, currentSize);

        // Aggiorna le immagini dei bottoni
        scaler.scaleButtonImage(newGameButton, "/Opzioni/nuovaPartita.png", newGameButton.getSize());
        scaler.scaleButtonImage(menuButton, "/Opzioni/menu.png", menuButton.getSize());

        // Aggiorna la dimensione e la posizione del JPanel e del JLabel di sfondo
        pausaPanel.setPreferredSize(overlaySize);
        pausaPanel.setBounds(
            (currentSize.width - overlaySize.width) / 2,
            (currentSize.height - overlaySize.height) / 2,
            overlaySize.width,
            overlaySize.height
        );
        backgroundLabel.setSize(overlaySize);
        scaler.scaleLabelImage(backgroundLabel, "/back (1).png", overlaySize);

      
    }
    /**
     * Ottiene il bottone per iniziare una nuova partita.
     * 
     * @return Il bottone per iniziare una nuova partita.
     */
    public JButton getGameButton() {
        return newGameButton;
    }
    /**
     * Ottiene il bottone per tornare al menu principale.
     * 
     * @return Il bottone per tornare al menu.
     */
    public JButton getMenuButton() {
        return menuButton;
    }
    /**
     * Alterna la visibilità della schermata di perdita.
     * 
     * @param visible True per rendere visibile la schermata, false altrimenti.
     */
    public void toggleVisibility(boolean visible) {
        setVisible(visible);
    }
}

