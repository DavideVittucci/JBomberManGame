package GiocoBomberman;

import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Puropen rappresenta un tipo di nemico nel gioco Bomberman.
 * Questa classe gestisce le proprietà e i comportamenti specifici di questo nemico,
 * come il movimento, l'animazione, la morte e l'interazione con altri oggetti nel gioco.
 *
 * @author Davide Vittucci
 * @see Nemico
 */
public class Puropen extends Nemico {
	 /**
     * Coordinate X di Puropen nel gioco.
     */
    private int x;

    /**
     * Coordinate Y di Puropen nel gioco.
     */
    private int y;

    /**
     * Velocità di movimento di Puropen.
     */
    private int speed;

    /**
     * Numero di vite di Puropen.
     */
    private int vite;

    /**
     * Direzione corrente di movimento di Puropen.
     */
    private Direction direction = Direction.DOWN;

    /**
     * Punteggio ottenuto quando Puropen viene eliminato.
     */
    public int punteggio = 200;

    /**
     * Numero corrente dello sprite visualizzato per Puropen.
     */
    private int spriteNum = 1;

    /**
     * Dimensione originale delle piastrelle (tiles) nel gioco.
     */
    final int originalTileSize = 16;

    /**
     * Fattore di scala utilizzato per le dimensioni delle piastrelle.
     */
    final int scale = 4;

    /**
     * Larghezza  di Puropen in pixel.
     */
    public final int TILE_WIDTH = originalTileSize * 3 + 5;
    /**
     * altezza di Puropen in pixel.
     */
    public final int TILE_HEIGHT = originalTileSize * 5 + 5;

    /**
     * Dimensione attuale delle piastrelle nel gioco.
     */
    int tileSize;

    /**
     * Indica se Puropen è attualmente sopra una bomba.
     */
    public boolean onBomb = false;

    /**
     * Ultima posizione X di una bomba sotto Puropen.
     */
    int lastBombx;

    /**
     * Ultima posizione Y di una bomba sotto Puropen.
     */
    int lastBomby;

    /**
     * Indica se Puropen è stato colpito.
     */
    public boolean colpito;

    /**
     * Indica se Puropen è attualmente invincibile.
     */
    public boolean invincibile = false;

    /**
     * Numero massimo di frame per l'animazione della morte di Puropen.
     */
    public final int MORTEFRAME = 3;

    /**
     * Contatore per l'animazione della morte di Puropen.
     */
    private int morteCounter = 0;

    /**
     * Frame corrente dell'animazione della morte di Puropen.
     */
    public int morteFrame = 0;

    /**
     * Indica se Puropen è morto.
     */
    public boolean morto = false;

    /**
     * Lista degli osservatori nel pattern Observer.
     */
    private List<Observer> observers = new ArrayList<>();
    /**
     * Indica se l'audio della morte è già stato riprodotto.
     */
	private boolean audioMorteRiprodotta;
		
		/**
		 * Costruttore di Puropen. Imposta le dimensioni delle piastrelle e i valori di default.
		 */
		 public Puropen() {
			 this.tileSize = originalTileSize * scale;
			
			 setDefaultValues();
			 solidArea = new Rectangle(0, 27, 53, 58);}
		  /**
		     * Imposta i valori di default per Puropen.
		     */
		 private void setDefaultValues() {
			vite =1;
	 		
			speed= 2;
			direction = Direction.DOWN;
			colpito = false;
			morteFrame= 0;
		    notifyObservers();
		 	}
		 /**
		  * Aggiunge un osservatore alla lista degli osservatori.
		  * 
		  * @param observer L'osservatore da aggiungere.
		  */
			public void addObserver(Observer observer) {
		        observers.add(observer);
		    }
			/**
			 * Rimuove un osservatore dalla lista degli osservatori.
			 * 
			 * @param observer L'osservatore da rimuovere.
			 */
		    public void removeObserver(Observer observer) {
		        observers.remove(observer);
		    }
		    /**
		     * Notifica tutti gli osservatori con un aggiornamento.
		     */
		    private void notifyObservers() {
		        for (Observer observer : observers) {
		            observer.update(this);
		        }
		    }   
		    /**
			  * Aggiorna l'animazione dello sprite di Puropen.
			  */  
		 public void updateSprite() {
			 if (morto) {
				 if (!audioMorteRiprodotta) {
			            AudioManager.getInstance().play("res/audio/Enemy-Dies.wav");
			            audioMorteRiprodotta = true; // Imposta la variabile a true dopo la riproduzione
			        }
					morteCounter++;
					if(morteCounter > 15) {
						
						morteCounter = 0;
		            if (morteFrame < MORTEFRAME - 1) {
		            	notifyObservers();
		            	
		            	 morteFrame++;
		            }
		            else { mortoFine= true;
		            	}
		            }}else {
		                audioMorteRiprodotta = false; // Resetta la variabile se Bomberman non è colpito
		            }
		        {
		        		notifyObservers();
		                spriteNum = spriteNum % 4 + 1;
		                
		            }
		       
		        
		    }
		 /**
		  * Aggiorna lo stato e la posizione di Puropen. Gestisce le collisioni e lo stato di vita.
		  * 
		  * @param collisionChecker Oggetto per la verifica delle collisioni.
		  * @param bombe Lista delle bombe nel gioco.
		  * @param bombaMap Mappa delle bombe.
		  * @param explosionMap Mappa delle esplosioni.
		  */
		 public void update(CollisionChecker collisionChecker , ArrayList<Bomba> bombe, int[][] bombaMap,boolean[][] explosionMap) {
			if (!isPaused) {
			 if (!colpito ) {
				  if (direction != null) {
					  

				        this.collisionOn =  false;
				        collisionChecker.checkTile(this, bombe,bombaMap);
				       
				        if (this.collisionOn == false) {
				        	
				        	this.moveInCurrentDirection();
				        } 
				        
				        if (this.collisionOn || cambioRandom()) {
				        	
				        	cambioDirezione();
				        }

				    }
				  collisionChecker.checkDeath(this, explosionMap);	
				  
			 }
			 else {vite--;}
			 if (vite==0) {this.morto = true;}
			 
			 
			 updateSprite();}
		 }
	
		 /**
		  * Genera un cambiamento casuale di direzione.
		  * 
		  * @return True se il cambio di direzione è necessario.
		  */
	private boolean cambioRandom() {
        Random rand = new Random();
        int x = rand.nextInt(300);
        
       
        return x <= 1; 
    }/**
	 * Cambia casualmente la direzione di Puropen.
	 */
	 public void cambioDirezione() {
		
		 	Direction oldDirection = direction;
	        Random rand = new Random();
	        int dir = rand.nextInt(4); 
	        Direction newDirection = Direction.values()[dir];
	        while (newDirection.equals(oldDirection)){
	        	 dir = rand.nextInt(4); 
	        	newDirection = Direction.values()[dir];
	        }
	        direction = newDirection;
	        collisionOn = false;
	        notifyObservers();
	    }
	 /**
	  * Muove Puropen nella direzione corrente.
	  */
	private void moveInCurrentDirection() {
        switch (direction) {
            case UP: y -= speed; break;
            case DOWN: y += speed; break;
            case LEFT: x -= speed; break;
            case RIGHT: x += speed; break;
        }
        notifyObservers();
    }
	
	 /**
     * Ottiene la direzione corrente del Puropen.
     * 
     * @return La direzione attuale del Puropen.
     */
	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
		return direction;
	}
	/**
     * Ottiene la velocità del nemico.
     * 
     * @return La velocità del Puropen.
     */
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}
	/**
     * Ottiene l'area solida del Puropen per la gestione delle collisioni.
     * 
     * @return L'area solida del Puropen.
     */
	@Override
	public Rectangle getSolidArea() {
		// TODO Auto-generated method stub
		return solidArea;
	}
	/**
	 * Restituisce la coordinata X corrente di Puropen.
	 * @return la posizione X del nemico.
	 */
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	/**
	 * Restituisce la coordinata Y corrente di Puropen.
	 * @return la posizione Y del nemico.
	 */
	@Override
	public int getY() {
		return y;
	}
	/**
	 * Imposta se Puropen è in collisione o meno.
	 * @param x vero se è in collisione, falso altrimenti.
	 */
	@Override
	public void setCollision(boolean x) {	
		this.collisionOn = x;
	}
	 /**
     * Aggiorna la posizione X del Puropen.
     * 
     * @param i La nuova posizione X.
     */
	@Override
	public void setX(int i) {
		this.x = i;
	}
	 /**
     * Aggiorna la posizione Y del Puropen.
     * 
     * @param i La nuova posizione Y.
     */
	@Override
	public void setY(int i) {	
		this.y = i;
	}
	/**
     * Aggiorna lo stato colpito del Puropen.
     * 
     * @param b nuovo stato colpito
     */
	@Override
	public void setColpito(boolean b) {
		this.colpito= b;
	}
	/**
	 * Calcola la posizione X effettiva di Puropen sulla mappa, centrata rispetto all'area solida.
	 * @return la posizione X effettiva di Puropen.
	 */
	public int getRealX() {
		// TODO Auto-generated method stub
		return ((this.getX()+  this.getSolidArea().x + this.getSolidArea().width/2 ) / tileSize * tileSize);
	}
	/**
	 * Calcola la posizione Y effettiva di Puropen sulla mappa, centrata rispetto all'area solida.
	 * @return la posizione Y effettiva di Puropen.
	 */
	public int getRealY() {
		// TODO Auto-generated method stub
		return ((this.getY()  + this.getSolidArea().y + this.getSolidArea().height ) / tileSize * tileSize);
	}

	/**
     * Ottiene il numero corrente dello sprite per l'animazione.
     * 
     * @return Il numero dello sprite attuale.
     */
	public int getSpriteNum() {
		// TODO Auto-generated method stub
		return spriteNum;
	}
	 /**
     * Ottiene il punteggio ottenuto quando il Puropen è sconfitto.
     * 
     * @return Il punteggio ottenuto dalla sconfitta del Puropen.
     */
	protected int getPunteggio() {
		// TODO Auto-generated method stub
		return punteggio;
	}
	  /**
     * Ottiene la larghezza del Puropen.
     * 
     * @return La larghezza del Puropen.
     */
	public int getWidth() {
		// TODO Auto-generated method stub
		return TILE_WIDTH;
	}
	/**
     * Ottiene l'altezza del Puropen.
     * 
     * @return L'altezza del Puropen.
     */
	public int getHeight() {
		// TODO Auto-generated method stub
		return TILE_HEIGHT ;
	}
	/**
     * Ottiene il frame corrente di morte per l'animazione della morte.
     * 
     * @return Il frame corrente dell'animazione di morte.
     */
	public int getMorteFrame() {
		// TODO Auto-generated method stub
		return this.morteFrame;
	}
	/**
     * Verifica se il puropen è morto.
     * 
     * @return True se il puropen è morto, altrimenti false.
     */
	public boolean isMorto() {
		// TODO Auto-generated method stub
		return morto;
	}
	 /**
     * Notifica gli osservatori di un cambiamento.
     */
	@Override
	public
	void notifica() {
		// TODO  notifyObservers() ;-generated method stub
		 notifyObservers() ;
	}
	
}
