package GiocoBomberman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * PausaView gestisce l'interfaccia utente per la schermata di pausa nel gioco Bomberman.
 * La classe si occupa di visualizzare i bottoni per riprendere il gioco o tornare al menu principale,
 * oltre a gestire un'immagine di sfondo per la schermata di pausa.
 * 
 * @author Davide Vittucci
 * @see JFrame
 * @see JButton
 * @see JLabel
 */
public class PausaView extends JPanel {
	 /**
     * Frame principale del gioco.
     */
    private JFrame frame;

    /**
     * Pannello principale per la schermata di pausa.
     */
    private JPanel pausaPanel;

    /**
     * Etichetta per l'immagine di sfondo.
     */
    private JLabel backgroundLabel;

    /**
     * Bottoni per riprendere il gioco 
     */
    private JButton resumeButton;
    /**
     * Bottoni per  tornare al menu.
     */
    
    private JButton  menuButton;

    /**
     * Gestore per scalare gli elementi dell'interfaccia in base alla dimensione della finestra.
     */
    private Scaler scaler;

    /**
     * Costruisce la vista della schermata di pausa con il frame di riferimento.
     * 
     * @param frame Il frame principale del gioco.
     */
    public PausaView(JFrame frame) {
        this.frame = frame;
        scaler = new Scaler();
        setLayout(new BorderLayout());
        
        pausaPanel = new JPanel(new BorderLayout());
        pausaPanel.setLayout(null); // Usa un layout nullo per posizionamento manuale
        add(pausaPanel, BorderLayout.CENTER);
        resumeButton = new JButton();
        menuButton = new JButton();
        resumeButton.setBounds(350, 400, 400, 80);
        menuButton.setBounds(350, 500, 400, 80);
        pausaPanel.add(resumeButton);
        pausaPanel.add(menuButton);
        resumeButton.setBorderPainted(false); // Rimuove la bordatura
        resumeButton.setContentAreaFilled(false); // Rimuove lo sfondo
        resumeButton.setFocusPainted(false); // Rimuove il focus border
        resumeButton.setOpaque(false); 
        menuButton.setBorderPainted(false); // Rimuove la bordatura
        menuButton.setContentAreaFilled(false); // Rimuove lo sfondo
        menuButton.setFocusPainted(false); // Rimuove il focus border
        menuButton.setOpaque(false); 
        // Imposta le immagini iniziali dei bottoni
        setButtonImages();
        // Carica e imposta l'immagine di sfondo
        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/back.png"));
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
            BufferedImage resumeImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/play.png"));
            BufferedImage menuImage = ImageIO.read(getClass().getResourceAsStream("/Opzioni/menu.png"));
            resumeButton.setIcon(new ImageIcon(resumeImage));
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
        scaler.scaleComponent(resumeButton, 350, 400, 400, 80, currentSize);
        scaler.scaleComponent(menuButton, 350, 500, 400, 80, currentSize);

        // Aggiorna le immagini dei bottoni
        scaler.scaleButtonImage(resumeButton, "/Opzioni/play.png", resumeButton.getSize());
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
        scaler.scaleLabelImage(backgroundLabel, "/back.png", overlaySize);

      
    }
    /**
     * Ottiene il bottone per riprendere il gioco.
     * 
     * @return Il bottone per riprendere il gioco.
     */
    public JButton getResumeButton() {
        return resumeButton;
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
     * Alterna la visibilità della schermata di pausa.
     * 
     * @param visible True per rendere visibile la schermata, false altrimenti.
     */
    public void toggleVisibility(boolean visible) {
        setVisible(visible);
    }
}

