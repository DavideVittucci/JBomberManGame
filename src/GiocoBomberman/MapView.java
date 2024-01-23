package GiocoBomberman;

import java.awt.Graphics2D;
/**
 * La classe MapView e responsabile della visualizzazione della mappa nel gioco Bomberman.
 * Gestisce il rendering delle piastrelle della mappa utilizzando le informazioni fornite
 * da un'istanza di MapModel. La classe implementa l'interfaccia Observer per aggiornare
 * la sua visualizzazione quando il modello della mappa cambia.
 * 
 * @author Davide vittucci
 * @see Observer
 * @see MapModel
 * @see Piastrella
 */
public class MapView implements Observer{
	 /**
     * Il modello della mappa che contiene i dati delle piastrelle.
     */
    private MapModel mapModel;

    /**
     * La dimensione di ciascuna piastrella della mappa in pixel.
     */
    private int tileSize;

    /**
     * Matrice bidimensionale di oggetti Piastrella che rappresentano lo stato corrente della mappa.
     */
    private Piastrella mapTileNum[][];

    /**
     * Costruisce una nuova vista della mappa con un modello di mappa specificato e una dimensione delle piastrelle.
     * 
     * @param mapModel il modello della mappa da visualizzare
     * @param tileSize la dimensione di ciascuna piastrella in pixel
     */
    public MapView(MapModel mapModel, int tileSize)   {
        this.mapModel = mapModel;
        this.tileSize = tileSize;
       
    }

    /**
     * Aggiorna la vista della mappa in base alle modifiche nel modello della mappa.
     * Questo metodo viene chiamato quando il modello della mappa notifica i suoi osservatori di un cambiamento.
     * 
     * @param model il modello della mappa che è stato aggiornato
     */
    public void update(Object model) {
   	 if (model instanceof MapModel) {
   		MapModel mapModel = (MapModel) model;
   		this.mapTileNum = mapModel.getMapTile();
   
   	 }
	}
    /**
     * Disegna la mappa sullo schermo utilizzando il contesto grafico fornito.
     * Questo metodo renderizza ciascuna piastrella della mappa in base al suo stato corrente.
     * 
     * @param g2 il contesto grafico su cui disegnare la mappa
     */
    public void draw(Graphics2D g2) {
        for (int row = 0; row < mapModel.getMaxScreenRow(); row++) {
            for (int col = 0; col < mapModel.getMaxScreenCol(); col++) {
            	
            	
                if (mapTileNum[row][ col].tipo == 3) {continue;}
                
                g2.drawImage(mapTileNum[row][ col].image, col * tileSize, row * tileSize, tileSize, tileSize, null);
            }
        }
        
     
    }
}