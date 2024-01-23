package GiocoBomberman;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * La classe Denkyun rappresenta un tipo di nemico nel gioco Bomberman.
 * Questo nemico si muove casualmente nella mappa e può essere eliminato tramite esplosioni.
 * Denkyun può diventare temporaneamente invincibile e ha un proprio sistema di vite e punteggio.
 * 
 * Estende la classe Nemico e implementa il comportamento specifico per Denkyun,
 * inclusa la gestione delle collisioni, del movimento, dello stato di invincibilità e della morte.
 * 
 * @author Davide Vittucci
 * @see Nemico
 * @see CollisionChecker
 */
public class Denkyun extends Nemico {
	 /**
     * Coordinate X  di Denkyun nel gioco.
     */
    private int x;
    /**
     * Coordinate Y di Denkyun nel gioco.
     */
    private int y;
    /**
     * Velocità di movimento di Denkyun.
     */
    private int speed;

    /**
     * Numero di vite di Denkyun.
     */
    private int vite;

    /**
     * Direzione corrente di movimento di Denkyun.
     */
    private Direction direction = Direction.DOWN;

    /**
     * Contatore per la gestione dell'animazione degli sprite di Denkyun.
     */
    private int spriteCounter = 0;

    /**
     * Punteggio ottenuto quando Denkyun viene eliminato.
     */
    public int punteggio = 350;

    /**
     * Numero corrente dello sprite visualizzato per Denkyun.
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
     * Larghezza  di Denkyun in pixel.
     */
    public final int TILE_WIDTH = originalTileSize * 3 + 5;
    /**
     *  altezza di Denkyun in pixel.
     */
    public final int TILE_HEIGHT = originalTileSize * 5 + 5;

    /**
     * Dimensione attuale delle piastrelle nel gioco.
     */
    int tileSize;

    /**
     * Indica se Denkyun è attualmente sopra una bomba.
     */
    public boolean onBomb = false;

    /**
     * Ultima posizione X e Y di una bomba sotto Denkyun.
     */
    int lastBombx, lastBomby;

    /**
     * Indica se alternare gli sprite per l'effetto di invincibilità.
     */
    private boolean alternaSprite = false;

    /**
     * Timestamp di inizio dello stato di invincibilità.
     */
    private long invincibileStartTime;

    /**
     * Durata dell'invincibilità in millisecondi.
     */
    private static final long INVINCIBILITÀ_DURATION = 4000;

    /**
     * Indica se Denkyun è stato colpito.
     */
    public boolean colpito;

    /**
     * Indica se Denkyun è attualmente invincibile.
     */
    public boolean invincibile = false;

    /**
     * Numero massimo di frame per l'animazione della morte di Denkyun.
     */
    public final int MORTEFRAME = 3;

    /**
     * Contatore per l'animazione della morte di Denkyun.
     */
    private int morteCounter = 0;

    /**
     * Frame corrente dell'animazione della morte di Denkyun.
     */
    public int morteFrame = 0;

    /**
     * Indica se l'animazione della morte deve procedere all'indietro.
     */
    public boolean reverse = false;

    /**
     * Indica se Denkyun è morto.
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
	 * Costruttore di Denkyun. Imposta le dimensioni delle piastrelle e i valori di default.
	 */
		 public Denkyun() {
			 this.tileSize = originalTileSize * scale;

			 setDefaultValues();
			 solidArea = new Rectangle(0, 22, 53, 62);}
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
		     * Imposta i valori di default per Denkyun.
		     */   
		 private void setDefaultValues() {
			vite =2;
			speed= 2;
			direction = Direction.DOWN;
			colpito = false;
			morteFrame= 0;
		 	}
		 /**
		  * Imposta Denkyun come invincibile e inizia a tracciare il tempo di invincibilità.
		  */
		 public void setInvincibile() {
		        this.invincibile = true;
		        this.invincibileStartTime = System.currentTimeMillis();
		        notifyObservers() ;
		    }
		 /**
		  * Decrementa una vita di Denkyun e lo imposta come invincibile.
		  */
		 public void perdereVita() {
			   {
			        vite--;
			        setInvincibile();
			    } 
			}
		 /**
		  * Gestisce lo stato di Denkyun quando viene colpito.
		  */
		 private void gestisciColpito() {
			    // Se Denkyun è colpito e ha più di una vita e non è invincibile
			    if (vite >= 1 && !isInvincibile()) {
			        perdereVita();  // Decrementa una vita e imposta l'invincibilità
			    } else if (vite <1) {
			        morto = true;  // Se le vite sono finite, imposta Denkyun come morto
			    }

			   
			    this.colpito = false;
			}
		 /**
		  * Aggiorna l'animazione dello sprite di Denkyun.
		  */
		 public void updateSprite() {
			 if (this.invincibile) {
		            alternaSprite = !alternaSprite;
		            notifyObservers() ;// Alterna ad ogni aggiornamento
		        }
			 if (morto) {
				 if (!audioMorteRiprodotta) {
			            AudioManager.getInstance().play("res/audio/Enemy-Dies.wav");
			            audioMorteRiprodotta = true; // Imposta la variabile a true dopo la riproduzione
			        }
					morteCounter++;
					if(morteCounter > 15) {
						
						morteCounter = 0;
		            if (morteFrame <MORTEFRAME - 1) {
		            	 notifyObservers() ;
		            	 morteFrame++;
		            }
		            else { mortoFine= true;
		            	}
		            }}else {
		                audioMorteRiprodotta = false; // Resetta la variabile se Bomberman non è colpito
		            }
			 spriteCounter++;
	            if(spriteCounter > 5) {
	            	
	               
	                spriteCounter = 0;
	                if (!reverse) {
	                	 notifyObservers() ;
	                	 spriteNum++;
	                	 if (spriteNum==6) {reverse= true;}
	                }
	                else {
	                	 notifyObservers() ;
	                	spriteNum--;
	                	 if (spriteNum==0) {reverse= false;}
	                }
	            }
	         
		       
		        
		    }
		 /**
		  * Aggiorna lo stato e la posizione di Denkyun. Gestisce le collisioni e lo stato di vita.
		  * 
		  * @param collisionChecker Oggetto per la verifica delle collisioni.
		  * @param bombe Lista delle bombe nel gioco.
		  * @param bombaMap Mappa delle bombe.
		  * @param explosionMap Mappa delle esplosioni.
		  */
		 public void update(CollisionChecker collisionChecker , ArrayList<Bomba> bombe, int[][] bombaMap,boolean[][] explosionMap) {
			 
			 if (!isPaused) {
				 if (this.invincibile && System.currentTimeMillis() - this.invincibileStartTime > INVINCIBILITÀ_DURATION) {
			            this.invincibile = false;
			        }
			 if (!colpito) {
				  if (direction != null) {					 
				        this.collisionOn =  false;
				        collisionChecker.checkTileDenkyun(this, bombe,bombaMap);
				       if(vite != 0) {
				        if (this.collisionOn == false) {
				        	
				        	this.moveInCurrentDirection();
				        } 
				       
				        if (this.collisionOn || cambioRandom()) {
				        	cambioDirezione();
				        }}

				    }
				  collisionChecker.checkDeath(this, explosionMap);
			 }else  {
				 
				 gestisciColpito();
		        }
			 
			 
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
    }
	/**
	 * Cambia casualmente la direzione di Denkyun.
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
	    }
	 /**
	  * Muove Denkyun nella direzione corrente.
	  */
	private void moveInCurrentDirection() {
     switch (direction) {
         case UP: y -= speed; break;
         case DOWN: y += speed; break;
         case LEFT: x -= speed; break;
         case RIGHT: x += speed; break;
         
     }
     notifyObservers() ;
 }
	
	/**
	 * Restituisce la direzione attuale del nemico Denkyun.
	 * @return la direzione corrente come valore dell'enumerazione {@link Direction}.
	 */
	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
		return direction;
	}
	/**
	 * Restituisce la velocità di movimento attuale di Denkyun.
	 * @return la velocità attuale del nemico.
	 */
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}
	/**
	 * Restituisce l'area solida di Denkyun per la rilevazione delle collisioni.
	 * @return l'area rettangolare che rappresenta lo spazio fisico occupato da Denkyun.
	 */
	@Override
	public Rectangle getSolidArea() {
		// TODO Auto-generated method stub
		return solidArea;
	}
	/**
	 * Restituisce la coordinata X corrente di Denkyun.
	 * @return la posizione X del nemico.
	 */
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	/**
	 * Restituisce la coordinata Y corrente di Denkyun.
	 * @return la posizione Y del nemico.
	 */
	@Override
	public int getY() {
		return y;
	}
	/**
	 * Imposta se Denkyun è in collisione o meno.
	 * @param x vero se è in collisione, falso altrimenti.
	 */
	@Override
	public void setCollision(boolean x) {	
	}
	/**
	 * Imposta la posizione X di Denkyun.
	 * @param i la nuova posizione X del nemico.
	 */
	@Override
	public void setX(int i) {
		this.x = i;
	}
	/**
	 * Imposta la posizione Y di Denkyun.
	 * @param i la nuova posizione Y del nemico.
	 */
	@Override
	public void setY(int i) {	
		this.y = i;
	}
	/**
	 * Imposta lo stato di "colpito" di Denkyun.
	 * @param b vero se Denkyun è colpito, falso altrimenti.
	 */
	@Override
	public void setColpito(boolean b) {
		this.colpito= b;
	}
	/**
	 * Calcola la posizione X effettiva di Denkyun sulla mappa, centrata rispetto all'area solida.
	 * @return la posizione X effettiva di Denkyun.
	 */
	public int getRealX() {
		// TODO Auto-generated method stub
		return ((this.getX()+  this.getSolidArea().x + this.getSolidArea().width/2 ) / tileSize * tileSize);
	}
	/**
	 * Calcola la posizione Y effettiva di Denkyun sulla mappa, centrata rispetto all'area solida.
	 * @return la posizione Y effettiva di Denkyun.
	 */
	public int getRealY() {
		// TODO Auto-generated method stub
		return ((this.getY()  + this.getSolidArea().y + this.getSolidArea().height ) / tileSize * tileSize);
	}

	/**
	 * Restituisce il numero attuale dello sprite per l'animazione di Denkyun.
	 * @return il numero dello sprite attuale.
	 */
	public int getSpriteNum() {
		// TODO Auto-generated method stub
		return spriteNum;
	}
	/**
	 * Restituisce il punteggio associato a Denkyun.
	 * @return il punteggio di Denkyun.
	 */
	@Override
	protected int getPunteggio() {
		// TODO Auto-generated method stub
		return punteggio;
	}
	/**
	 * Verifica se l'animazione sprite di Denkyun è in modalità alternata.
	 * @return vero se l'animazione è alternata, falso altrimenti.
	 */
	 public boolean getAlterna() {
	        return alternaSprite;
	    }
	 /**
	  * Verifica se Denkyun è attualmente invincibile.
	  * @return vero se Denkyun è invincibile, falso altrimenti.
	  */
	 public boolean isInvincibile() {
	        return this.invincibile;
	    }
	 /**
	  * Restituisce la larghezza dell'immagine sprite di Denkyun.
	  * @return la larghezza dell'immagine sprite.
	  */
	public int getWidth() {
		// TODO Auto-generated method stub
		return TILE_WIDTH;
	}
	/**
	 * Restituisce l'altezza dell'immagine sprite di Denkyun.
	 * @return l'altezza dell'immagine sprite.
	 */
	public int getHeight() {
		// TODO Auto-generated method stub
		return TILE_HEIGHT ;
	}
	/**
	 * Restituisce il numero dello sprite corrente per l'animazione di morte di Denkyun.
	 * @return l'indice del frame di morte.
	 */
	public int getMorteFrame() {
		// TODO Auto-generated method stub
		return this.morteFrame;
	}
	/**
	 * Verifica se Denkyun è morto.
	 * @return vero se Denkyun è morto, falso altrimenti.
	 */
	public boolean isMorto() {
		// TODO Auto-generated method stub
		return morto;
	}
	/**
	 * Notifica gli osservatori di un cambiamento nello stato di Denkyun.
	 */
	@Override
	public void notifica() {
		 notifyObservers() ;
		
	}
	
}
