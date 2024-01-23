package GiocoBomberman;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.System.Logger;
import java.util.UUID;

import javax.swing.*;
/**
 * Classe principale del gioco JBomberMan.
 * Inizializza e avvia il gioco creando la finestra principale e il menu iniziale.
 * Utilizza la libreria Swing per la gestione dell'interfaccia grafica.
 * 
 * @author Davide Vittucci
 */
public class JBomberMan {
	 /** Dimensione originale di un tile in pixel. */
    final static int originalTileSize = 16;

    /** Fattore di scala per i tile. */
    final static int scale = 4;

    /** Dimensione effettiva di un tile, ottenuta moltiplicando la dimensione originale per il fattore di scala. */
    final static int tileSize = originalTileSize * scale;

    /** Numero massimo di colonne sullo schermo di gioco. */
    final static int maxScreenCol = 17;

    /** Numero massimo di righe sullo schermo di gioco. */
    final static int maxScreenRow = 15;

    /** Dimensioni dello schermo del dispositivo. */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /** Larghezza dello schermo del dispositivo in pixel. */
    public static int screenWidth;

    /** Altezza dello schermo del dispositivo in pixel. */
    public static int screenHeight;
    /**
     * Punto di ingresso principale del programma.
     * Configura e visualizza la finestra di gioco.
     * 
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {

    	screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }
    /**
     * Inizializza e visualizza la finestra di gioco e il menu principale.
     */
    private static void start() {
        
    	
    
        JFrame finestra = new JFrame("JBomberman");
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setExtendedState(JFrame.MAXIMIZED_BOTH);
        finestra.setSize(screenWidth, screenHeight);
        finestra.setLayout(new BorderLayout());
        finestra.setLocationRelativeTo(null);
        
        MenuModel menuModel = new MenuModel();
        MenuView menuView = new MenuView(finestra);
        new MenuController(menuModel, menuView, finestra);

        finestra.setVisible(true);
    }
}
