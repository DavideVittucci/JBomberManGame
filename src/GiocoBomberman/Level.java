package GiocoBomberman;

import java.awt.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * Rappresenta un livello nel gioco Bomberman. Gestisce la generazione della mappa,
 * la posizione dei nemici e altre variabili specifiche di ciascun livello.
 * 
 * @author Davide Vittucci
 */
public class Level {
	 /** Numero del livello corrente. */
    private int levelNumber;

    /** Numero di blocchi distruttibili nel livello. */
    private int destructibleBlocks;

    /** Lista dei nemici presenti nel livello. */
    private ArrayList<Nemico> nemici;

    /** Mappa delle viste dei nemici, associando ciascun nemico alla sua vista. */
    private HashMap<Nemico, NemicoView> nemicoViews = new HashMap<>();

    /** Durata del timer per il livello, espressa in secondi. */
    private int durata_Timer;

    /** Modello della mappa utilizzato per gestire la rappresentazione del livello. */
    private MapModel mapModel;

    /** Rappresentazione numerica della mappa, composta da piastrelle con diverse proprietà. */
    private Piastrella[][] mapTileNum;

    /** Generatore della mappa, utilizzato per creare la disposizione iniziale delle piastrelle e dei blocchi distruttibili. */
    public MapGenerator mapGenerator;
    /**
     * Costruttore della classe Level.
     * 
     * @param levelNumber Numero del livello.
     * @param mapModel    Modello della mappa associato al livello.
     */
    public Level(int levelNumber, MapModel mapModel ) {
    	this.mapModel = mapModel;
        this.levelNumber = levelNumber;
        
        this.nemici = new ArrayList<>();
         mapGenerator = new MapGenerator();
        setupLevel();
    }
    /**
     * Imposta le variabili e genera il livello.
     */
    public void setupLevel() {
    	setUpVar();
    	generateLevel() ;
    	setupEnemies() ;
    	setupView();
	}
    /**
     * Imposta le variabili specifiche per ciascun livello, come il numero di blocchi
     * distruttibili e la durata del timer.
     */
    private void setUpVar() {
    	if (levelNumber == 1) {
        	this.destructibleBlocks =35 ;
        	this.durata_Timer = 200 ;
        }
        else if (levelNumber == 2) {
        	this.destructibleBlocks = 40 ;
        	this.durata_Timer = 170 ;
        }
        else if (levelNumber == 3) {
        	this.destructibleBlocks = 50;
        	this.durata_Timer = 150 ;
        }
    	
    }
    /**
     * Genera la mappa del livello utilizzando il MapGenerator e carica la mappa nel modello.
     */
	public void generateLevel() {
        
     
        mapGenerator.generateMap(destructibleBlocks);

        mapModel.loadMap(this.getLevelNumber());
        setUpMapTile() ;
       
    }

    /**
     * Imposta l'array mapTileNum basato sul modello della mappa attuale.
     */
	public void setUpMapTile() {
		this.mapTileNum = mapModel.getMapTile();
	}
	 /**
     * Imposta i nemici nel livello, scegliendo posizioni casuali libere sulla mappa.
     */
	private void setupEnemies() {
	    nemici.clear();
	    ArrayList<Point> freeTiles = freeTiles(); 

	    Random rand = new Random();

	  

	    int numPuropen = levelNumber == 1 ? 2 : levelNumber == 2? 4 : 4;
	    int numDenkyun = levelNumber == 1 ? 1 : levelNumber == 2? 1 : 2;

	    for (int i = 0; i < numPuropen; i++) {
	        if (!freeTiles.isEmpty()) {
	            Point spawn = freeTiles.remove(rand.nextInt(freeTiles.size()));
	            Puropen puropen = new Puropen();
	            int x=  spawn.x;
	            
	            int y = spawn.y;
	            
	            
	           puropen.setX(x * 64);
	            puropen.setY((y * 64)-27);
	            nemici.add(puropen);
	           
	        }
	    }
	    
	    for (int i = 0; i < numDenkyun; i++) {
	        if (!freeTiles.isEmpty()) {
	            Point spawn = freeTiles.remove(rand.nextInt(freeTiles.size()));
	            Denkyun denkyun = new Denkyun();
	            denkyun.setX(spawn.x * 64);
	            denkyun.setY(( spawn.y * 64)-22);
	           
	            nemici.add(denkyun);
	           
	        }
	    }
	}
	 /**
     * Restituisce una lista di punti rappresentanti le posizioni libere sulla mappa,
     * dove non ci sono blocchi né ostacoli.
     * 
     * @return ArrayList di oggetti Point con le posizioni libere.
     */
	private ArrayList<Point> freeTiles() {
	    return IntStream.range(0, mapTileNum.length)
	                    .boxed()
	                    .flatMap(row -> IntStream.range(0, mapTileNum[row].length)
	                                             .filter(col -> mapTileNum[row][col].getTipo() == 0 && !(row == 3 && (col == 2 || col == 3)) && !(row == 4 && col == 2))
	                                             .mapToObj(col -> new Point(col, row)))
	                    .collect(Collectors.toCollection(ArrayList::new));
	}

	/**
     * Configura le viste per ciascun nemico e le aggiunge alla mappa nemicoViews.
     */
	private void setupView() {
	    nemici.stream().forEach(nemico -> {
	        NemicoView view = null;
	        if (nemico instanceof Puropen) {
	            view = new PuropenView();
	            nemico.addObserver(view);
	            nemico.notifica();
	        } else if (nemico instanceof Denkyun) {
	            view = new DenkyunView();
	            nemico.addObserver(view);
	            nemico.notifica();
	        }
	        if (view != null) {
	            nemicoViews.put(nemico, view);
	        }
	    });
	}

	 /**
     * Restituisce la lista dei nemici presenti nel livello.
     * 
     * @return ArrayList di oggetti Nemico.
     */
    public ArrayList<Nemico> getEnemies() {
        return nemici;
    }
    /**
     * Restituisce la mappa delle viste dei nemici.
     * 
     * @return HashMap che associa ogni nemico alla sua vista.
     */
    public HashMap<Nemico, NemicoView> getView() {
        return nemicoViews;
    }
    /**
     * Restituisce la durata del timer per il livello.
     * 
     * @return Durata del timer in secondi.
     */
    public int getTimer() {
    	return this.durata_Timer ;
    }
    /**
     * Restituisce il numero del livello corrente.
     * 
     * @return Numero del livello.
     */
	public int getLevelNumber() {
		// TODO Auto-generated method stub
		return levelNumber;
	}
	/**
     * Incrementa il numero del livello e reimposta le variabili per il nuovo livello.
     */
	public void changeLevel() {
		this.levelNumber++;
		setupLevel();
		
	}
}
