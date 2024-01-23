package GiocoBomberman;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
/**
 * MapModel e la classe che rappresenta il modello della mappa nel gioco Bomberman.
 * Gestisce la struttura della mappa, le piastrelle e i loro stati, e notifica
 * gli osservatori in caso di cambiamenti. La classe supporta anche il caricamento
 * di livelli e l'assegnazione di potenziamenti e vie di fuga.
 * 
 * @author Davide Vittucci
 * @see Observer
 * @see Piastrella
 */
public class MapModel {
	/**
     * Numero massimo di colonne dello schermo.
     */
    private int maxScreenCol;

    /**
     * Numero massimo di righe dello schermo.
     */
    private int maxScreenRow;

    /**
     * Array di piastrelle utilizzate per la mappa.
     */
    public Piastrella[] tiles;

    /**
     * Array di piastrelle per le animazioni di esplosione.
     */
    public Piastrella[] tilesExp;

    /**
     * Matrice bidimensionale di piastrelle che rappresenta lo stato corrente della mappa.
     */
    private Piastrella mapTileNum[][];

    /**
     * Tempo di esplosione per l'animazione.
     */
    private final int EXPLOSION_TIME_EXP = 10;

    /**
     * Contatore per l'animazione.
     */
    private int animationCounter = 0;

    /**
     * Percorso del file della mappa.
     */
    private static final String MAP_PATH = "res\\maps\\mappa1.txt";

    /**
     * Lista degli osservatori da notificare in caso di cambiamenti.
     */
    private List<Observer> observers = new ArrayList<>();
    /**
     * Costruisce un nuovo modello di mappa con dimensioni specificate.
     * 
     * @param maxScreenCol il numero massimo di colonne dello schermo
     * @param maxScreenRow il numero massimo di righe dello schermo
     */
    public MapModel(int maxScreenCol, int maxScreenRow) {
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        mapTileNum = new Piastrella[maxScreenRow][maxScreenCol];
        tiles = new Piastrella[11] ;
        tilesExp = new Piastrella[10] ;
        loadTileImages();
        loadTileImagesExp();
        
      
    }
    /**
     * Aggiunge un osservatore alla lista degli osservatori.
     * 
     * @param observer l'osservatore da aggiungere
     */
	public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un osservatore dalla lista degli osservatori.
     * 
     * @param observer l'osservatore da rimuovere
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori di un cambiamento.
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    } /**
     * Metodo pubblico per notificare gli osservatori.
     */
    public void notifica() {
    	notifyObservers() ;
    }
    /**
     * Carica le immagini delle piastrelle esplosive.
     * Gestisce il caricamento delle immagini per le animazioni delle esplosioni.
     */
    private void loadTileImagesExp() {
    	try {
    		tilesExp[0]= new Piastrella();
    		tilesExp[1]= new Piastrella();
    		tilesExp[2]= new Piastrella();
    		tilesExp[3]= new Piastrella();
    		tilesExp[4]= new Piastrella();
    		tilesExp[5]= new Piastrella();
    		
    		tilesExp[0].image = ImageIO.read(getClass().getResourceAsStream("/Distruzione/muroEx0.png")) ;
    		tilesExp[1].image = ImageIO.read(getClass().getResourceAsStream("/Distruzione/muroEx1.png")) ;
    		tilesExp[2].image = ImageIO.read(getClass().getResourceAsStream("/Distruzione/muroEx2.png")) ;
    		tilesExp[3].image = ImageIO.read(getClass().getResourceAsStream("/Distruzione/muroEx3.png")) ;
    		tilesExp[4].image = ImageIO.read(getClass().getResourceAsStream("/Distruzione/muroEx4.png")) ;
    		tilesExp[5].image = ImageIO.read(getClass().getResourceAsStream("/Distruzione/muroEx5.png")) ;
    		}
    		catch(IOException e) {
    			e.printStackTrace();
    		}
    }
    /**
     * Aggiorna lo stato della mappa.
     * Questo metodo gestisce l'aggiornamento delle piastrelle, incluse le esplosioni e
     * il reset delle piastrelle dopo l'esplosione.
     */
    public void update() {
    	animationCounter++;
    	
    	if(animationCounter >EXPLOSION_TIME_EXP){
    	
    		animationCounter=0;
    	int col = 0;
		int row = 0;
    	while(col<maxScreenCol && row < maxScreenRow) {
    		while(col<maxScreenCol) {
    			if(mapTileNum[row][col].calpestata) {
    				mapTileNum[row][col].calpestata=false;
    				mapTileNum[row][col].image= tiles[0].image ;
    				mapTileNum[row][col].setTipo(0);
    				
    				
    			}
			if(mapTileNum[row][col].getColpita()) {
				int x = mapTileNum[row][col].getStato();
				if (x==1) { AudioManager.getInstance().play("res/audio/murob.wav",0.7f);}
				mapTileNum[row][col].image= tilesExp[x].image ;
				
				mapTileNum[row][col].setStato();
				
				if(x==5) {
					 
					if (mapTileNum[row][col].fuga) {
						
						mapTileNum[row][col]= new Piastrella();
						mapTileNum[row][col].image= tiles[4].image ;
						mapTileNum[row][col].setTipo(4);
						mapTileNum[row][col].collision= tiles[4].collision ;
						notifyObservers() ;
					}
					else if (mapTileNum[row][col].haPotenziamento()) {
					
		                switch (mapTileNum[row][col].getTipoPotenziamento()) {
		                    case RAGGIO:
		                   
		                    	mapTileNum[row][col]= new Piastrella();
								mapTileNum[row][col].image= tiles[5].image ;
								mapTileNum[row][col].setTipo(5);
								mapTileNum[row][col].collision= tiles[5].collision ;	
		                       
		                        break;
		                    case VELOCITA:
		                    	// 	System.out.println("2");
		                    	mapTileNum[row][col]= new Piastrella();
								mapTileNum[row][col].image= tiles[6].image ;
								mapTileNum[row][col].setTipo(6);
								mapTileNum[row][col].collision= tiles[6].collision ;	
		                       
		                        break;
		                    // Aggiungi qui i casi per gli altri tipi di potenziamenti
						case BOMBA:
							//	System.out.println("1");
							mapTileNum[row][col]= new Piastrella();
							mapTileNum[row][col].image= tiles[10].image ;
							mapTileNum[row][col].setTipo(10);
							mapTileNum[row][col].collision= tiles[10].collision ;	
	                       
							break;
						case INVINCIBILITA:
							//	System.out.println("f1");
							mapTileNum[row][col]= new Piastrella();
							mapTileNum[row][col].image= tiles[9].image ;
							mapTileNum[row][col].setTipo(9);
							mapTileNum[row][col].collision= tiles[9].collision ;	
	                     
							break;
						case PUNTI:
							//System.out.println("1f");
							mapTileNum[row][col]= new Piastrella();
							mapTileNum[row][col].image= tiles[8].image ;
							mapTileNum[row][col].setTipo(8);
							mapTileNum[row][col].collision= tiles[8].collision ;	
	                       
							break;
						case VITA:
						
							mapTileNum[row][col]= new Piastrella();
							mapTileNum[row][col].image= tiles[7].image ;
							mapTileNum[row][col].setTipo(7);
							mapTileNum[row][col].collision= tiles[7].collision ;	
	                      
							break;
						default:
							//System.out.println("0");
							break;
						
		                }
		                notifyObservers() ;}
					else {
					mapTileNum[row][col]= new Piastrella();
					mapTileNum[row][col].image= tiles[0].image ;
					mapTileNum[row][col].setTipo(0);
					mapTileNum[row][col].collision= tiles[0].collision ;
					notifyObservers() ;
			}}}
		
			
				
	
		col++;
    		}
	if (col== maxScreenCol) {col=0;
	row++;
	}
    }}}
    /**
     * Carica le immagini per le piastrelle della mappa.
     * Questo metodo carica e assegna le immagini alle varie tipologie di piastrelle utilizzate nella mappa.
     */
    private void loadTileImages() {
    	try {
    		tiles[0]= new Piastrella();
    		tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/erba.png")) ;
    		tiles[0].setTipo(0);
    		tiles[0].collision = false;
    		tiles[1]= new Piastrella();
    		tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/muro.png")) ;
    		tiles[1].collision = true;
    		tiles[1].setTipo(1);
    		tiles[2]= new Piastrella();
    		tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/piastrellapietra.png")) ;
    		tiles[2].collision = true;
    		tiles[2].setTipo(2);
    		tiles[3]= new Piastrella();

    		tiles[3].setTipo(3);
    		tiles[4]= new Piastrella();
    		tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/fuga/Fuga.png")) ;
    		tiles[4].collision = false;
    		tiles[4].setTipo(4);
    		
    		tiles[5] = new Piastrella(); // Potenziamento Raggio
    		tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/drop/raggio.png"));
    		tiles[5].collision = false;
    		tiles[5].setTipo(5);
    		
    		tiles[6] = new Piastrella(); // Potenziamento Raggio
    		tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/drop/velocità.png"));
    		tiles[6].collision = false;
    		tiles[6].setTipo(6);
    		
    		tiles[7] = new Piastrella(); // Potenziamento Velocità
    		tiles[7].image = ImageIO.read(getClass().getResourceAsStream("/drop/vita.png"));
    		tiles[7].collision = false;
    		tiles[7].setTipo(7);
    		tiles[8] = new Piastrella(); // Potenziamento Velocità
    		tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/drop/punti.png"));
    		tiles[8].collision = false;
    		tiles[8].setTipo(8);
    		tiles[9] = new Piastrella(); // Potenziamento Velocità
    		tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/drop/invincibilita.png"));
    		tiles[9].collision = false;
    		tiles[9].setTipo(9);
    		tiles[10] = new Piastrella(); // Potenziamento Velocità
    		tiles[10].image = ImageIO.read(getClass().getResourceAsStream("/drop/bomba.png"));
    		tiles[10].collision = false;
    		tiles[10].setTipo(10);

    		}
    		catch(IOException e) {
    			e.printStackTrace();
    		}
    }
    /**
     * Carica la mappa da un file di testo in base al numero di livello.
     * 
     * @param levelNumber il numero del livello da caricare
     */
    public void loadMap(int levelNumber) {
    	
        try (BufferedReader br = new BufferedReader(new FileReader(MAP_PATH))) {
            int col = 0;
            int row = 0;
            ArrayList<Piastrella> piastrelleDistruttibili = new ArrayList<>();
            while (col < maxScreenCol && row < maxScreenRow) {
                String line = br.readLine();
                while (col < maxScreenCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[row][col] = new Piastrella();
                    mapTileNum[row][col].image = tiles[num].image;
                    mapTileNum[row][col].setTipo(num);
                    mapTileNum[row][col].collision = tiles[num].collision;
                    if (num == 1) {
                        piastrelleDistruttibili.add(mapTileNum[row][col]);
                    }
                    col++;
                }
                
                if (col == maxScreenCol) {
                    col = 0;
                    row++;
                }
            } assegnaFugaEPotenziamenti(piastrelleDistruttibili, levelNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Assegna la fuga e i potenziamenti a piastrelle selezionate casualmente.
     * 
     * @param piastrelle la lista di piastrelle dove assegnare fuga e potenziamenti
     * @param levelNumber il numero del livello corrente
     */
    private void assegnaFugaEPotenziamenti(ArrayList<Piastrella> piastrelle, int levelNumber) {
    	
        Random rand = new Random();
        if (!piastrelle.isEmpty()) {
            int indiceCasuale = rand.nextInt(piastrelle.size());
            piastrelle.get(indiceCasuale).setFuga(true);
            piastrelle.remove(indiceCasuale);
        }

        if (levelNumber == 1) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.RAGGIO);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.BOMBA);

        // Calcola i potenziamenti casuali
        if (rand.nextDouble() < 0.90) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
        }
        if (rand.nextDouble() < 0.30) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VELOCITA);
        }
        if (rand.nextDouble() < 0.60) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
        }
        if (rand.nextDouble() < 0.10) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.RAGGIO);
        }
        if (rand.nextDouble() < 0.25) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.INVINCIBILITA);
        }}
        if (levelNumber == 2) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.RAGGIO);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
        if (rand.nextDouble() < 0.50) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
        }
        if (rand.nextDouble() < 0.70) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
        }
        if (rand.nextDouble() < 0.25) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.INVINCIBILITA);
        }
        if (rand.nextDouble() < 0.25) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.BOMBA);
        }
        if (rand.nextDouble() < 0.10) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.BOMBA);
        }
        if (rand.nextDouble() < 0.25) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VELOCITA);
        }
        if (rand.nextDouble() < 0.10) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.RAGGIO);
        }}
        if (levelNumber == 3) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
        if (rand.nextDouble() < 0.50) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VITA);
        }
        if (rand.nextDouble() < 0.30) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.INVINCIBILITA);
        }
        if (rand.nextDouble() < 0.25) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.VELOCITA);
        }
        if (rand.nextDouble() < 0.30) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.INVINCIBILITA);
        }
        if (rand.nextDouble() < 0.10) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.BOMBA);
        }
        if (rand.nextDouble() < 0.05) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.RAGGIO);
        }
        if (rand.nextDouble() < 0.15) {
            assegnaPotenziamentoFisso(piastrelle, TipoPotenziamento.PUNTI);
        }}
    }
    /**
     * Assegna un potenziamento fisso a una piastrella casuale.
     * 
     * @param piastrelle la lista di piastrelle dove assegnare il potenziamento
     * @param tipoPotenziamento il tipo di potenziamento da assegnare
     */
    private void assegnaPotenziamentoFisso(ArrayList<Piastrella> piastrelle, TipoPotenziamento tipoPotenziamento) {
    	
        if (!piastrelle.isEmpty()) {
            int indiceCasuale = new Random().nextInt(piastrelle.size());
           
            piastrelle.get(indiceCasuale).setPotenziamento(tipoPotenziamento);
            piastrelle.remove(indiceCasuale); // Rimuove la piastrella per evitare doppioni
        }
    }
    /**
     * Ottiene una piastrella specifica dalla mappa.
     * 
     * @param row la riga della piastrella
     * @param col la colonna della piastrella
     * @return la piastrella alla posizione specificata
     */
    public Piastrella getTileNum(int row, int col) {
        return mapTileNum[row][col];
    }

    /**
     * Ottiene una piastrella dall'array di piastrelle in base all'indice.
     * 
     * @param x l'indice della piastrella nell'array
     * @return la piastrella corrispondente all'indice
     */
    public Piastrella getTile(int x) {return tiles[x];}
    /**
     * Ottiene il numero massimo di colonne dello schermo.
     * 
     * @return il numero massimo di colonne
     */
    public int getMaxScreenCol(){return maxScreenCol;}
    /**
     * Ottiene il numero massimo di righe dello schermo.
     * 
     * @return il numero massimo di righe
     */
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    /**
     * Ottiene la matrice bidimensionale delle piastrelle della mappa.
     * 
     * @return la matrice delle piastrelle della mappa
     */
    public Piastrella[][] getMapTile(){return mapTileNum;}

    /**
     * Ottiene l'array delle piastrelle utilizzate nella mappa.
     * 
     * @return l'array delle piastrelle
     */
    public Piastrella[] getPiastrelle() {return tiles;}
}