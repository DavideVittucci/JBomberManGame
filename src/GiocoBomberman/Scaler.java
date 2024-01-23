package GiocoBomberman;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * Scaler e una classe utilizzata per scalare componenti, immagini e dimensioni in base alle dimensioni correnti della finestra di gioco.
 * Fornisce metodi utili per adattare l'interfaccia utente e le immagini del gioco a diverse risoluzioni dello schermo.
 *
 * @author Davide Vittucci
 * @see Component
 * @see Dimension
 * @see Image
 * @see ImageIcon
 */
public class Scaler {

    private final static int originalWidth = 1088;
    private final static int originalHeight = 960;
    private final static int baseTileSize = 64;
    /**
     * costruttore vuoto
     */
    public Scaler() {
       
    }
    /**
     * Scala un componente in base alle dimensioni correnti della finestra.
     *
     * @param component    Il componente da scalare.
     * @param originalX    Posizione X originale.
     * @param originalY    Posizione Y originale.
     * @param originalW    Larghezza originale.
     * @param originalH    Altezza originale.
     * @param currentSize  Dimensioni correnti della finestra.
     */
    public void scaleComponent(Component component, int originalX, int originalY, int originalW, int originalH, Dimension currentSize) {
        double widthRatio = currentSize.getWidth() / originalWidth;
        double heightRatio = currentSize.getHeight() / originalHeight;

        int newX = (int) (originalX * widthRatio);
        int newY = (int) (originalY * heightRatio);
        int newWidth = (int) (originalW * widthRatio );
        int newHeight = (int) (originalH * heightRatio);

        component.setBounds(newX, newY, newWidth, newHeight);
    }
    /**
     * Scala l'immagine di un bottone in base alle nuove dimensioni.
     *
     * @param button     Il bottone da aggiornare.
     * @param imagePath  Percorso dell'immagine del bottone.
     * @param newSize    Nuove dimensioni per l'immagine.
     */
    public void scaleButtonImage(JButton button, String imagePath, Dimension newSize) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            Image scaledImage = originalImage.getScaledInstance(newSize.width, newSize.height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
            // Gestione dell'errore
        }
    }
    /**
     * Scala la larghezza in base alle dimensioni correnti della finestra.
     *
     * @param width         Larghezza originale.
     * @param currentSize   Dimensioni correnti della finestra.
     * @return Larghezza scalata.
     */
    public static int scaleWidth(int width, Dimension currentSize) {
        double scaleRatio = currentSize.getWidth() / originalWidth;
        return (int) (width * scaleRatio);
    }
    /**
     * Scala l'altezza in base alle dimensioni correnti della finestra.
     *
     * @param height        Altezza originale.
     * @param currentSize   Dimensioni correnti della finestra.
     * @return Altezza scalata.
     */
    public static int scaleHeight(int height, Dimension currentSize) {
        double scaleRatio = currentSize.getHeight() /originalHeight;
        return (int) (height * scaleRatio);
    }
    /**
     * Calcola la dimensione scalata delle piastrelle in base alle dimensioni correnti della finestra.
     *
     * @param currentSize   Dimensioni correnti della finestra.
     * @return Dimensione scalata delle piastrelle.
     */
    public static int scaleTileSize(Dimension currentSize) {
        double scaleFactor = calculateScaleFactor(currentSize);
        return (int) (baseTileSize * scaleFactor);
    }
    /**
     * Calcola il fattore di Scale in base alle dimensioni correnti della finestra.
     *
     * @param currentSize   Dimensioni correnti della finestra.
     * @return Dimensione scalata della finestra.
     */
    private static double calculateScaleFactor(Dimension currentSize) {
    	
        double widthScale = currentSize.width / (double) originalWidth;
        double heightScale = currentSize.height / (double) originalHeight;
        
        return Math.min(widthScale, heightScale);  // Mantiene le proporzioni
    }
    /**
     * Scala l'immagine di un'etichetta in base alle nuove dimensioni.
     *
     * @param label       L'etichetta da aggiornare.
     * @param imagePath   Percorso dell'immagine dell'etichetta.
     * @param newSize     Nuove dimensioni per l'immagine.
     */
    public void scaleLabelImage(JLabel label, String imagePath, Dimension newSize) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            Image scaledImage = originalImage.getScaledInstance(newSize.width, newSize.height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
            // Gestione dell'errore
        }
    }
    /**
     * Scala una dimensione in base alle dimensioni correnti della finestra.
     *
     * @param originalDimension  Dimensione originale.
     * @param currentWindowSize  Dimensioni correnti della finestra.
     * @return Dimensione scalata.
     */
    public Dimension scaleDimension(Dimension originalDimension, Dimension currentWindowSize) {
        double widthRatio = currentWindowSize.getWidth() / originalDimension.getWidth();
        double heightRatio = currentWindowSize.getHeight() / originalDimension.getHeight();

        int scaledWidth = (int) (originalDimension.getWidth() * widthRatio);
        int scaledHeight = (int) (originalDimension.getHeight() * heightRatio);

        return new Dimension(scaledWidth, scaledHeight);
    }
}
