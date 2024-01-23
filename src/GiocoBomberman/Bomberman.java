package GiocoBomberman;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * La classe Bomberman rappresenta il personaggio principale nel videogioco Bomberman.
 * Questa classe è responsabile della gestione delle coordinate, della direzione, delle vite,
 * e del raggio d'azione delle bombe piazzate dal personaggio. Implementa il pattern Singleton
 * per assicurare l'esistenza di una sola istanza di Bomberman all'interno del gioco, 
 * supportando così l'unicità del personaggio principale.
 *
 * Bomberman è in grado di muoversi in quattro direzioni e interagisce con vari elementi
 * del gioco come bombe, nemici e ostacoli. La classe gestisce diversi stati del personaggio
 * come invincibile, colpito o in fuga, influenzando il comportamento e le interazioni del
 * personaggio con l'ambiente di gioco. Inoltre, implementa l'interfaccia Observable per
 * permettere la comunicazione dello stato del personaggio alla view, facilitando la
 * rappresentazione visiva di tali stati.
 *
 * La classe fornisce metodi per il movimento e l'aggiornamento dello stato del personaggio,
 * la gestione delle collisioni tramite un oggetto CollisionChecker, e l'aggiornamento
 * degli sprite a seconda dello stato e dell'azione di Bomberman. Include inoltre funzionalità
 * per la pausa e la ripresa del gioco e getter e setter.
 * @author Davide Vittucci
 * @see CollisionChecker
 * @see Observable
 * @see KeyHandler
 */
public class Bomberman extends Entita  implements Observable{
	/** Coordinate x del Bomberman nel gioco. */
    private int x;

    /** Coordinate y del Bomberman nel gioco. */
    private int y;

    /** Numero di vite rimanenti del Bomberman. */
    private int vite;

    /** Raggio delle esplosioni generate dalle bombe del Bomberman. */
    private int raggio;

    /** Direzione attuale del movimento di Bomberman. */
    private Direction direction = Direction.DOWN;

  

    /** Flag per la pausa del gioco. */
    private boolean isPaused = false;

    /** Flag  per la gestione dello stato di fuga. */
    private boolean inFuga;
    /** contatore per gestione dell'animazione  di fuga. */
    private int fugaCounter = 0;
    /** Frame dello stato di fuga. */
    private int fugaFrame = 0;

    /** Numero massimo di bombe che Bomberman può piazzare contemporaneamente. */
    private int maxBomb;

    /** Flag per la gestione dell'audio di morte di Bomberman. */
    private boolean audioMorteRiprodotta = false;

    /** Flag che indica la vittoria di Bomberman. */
    private boolean vittoria;

    /** Lista degli observer per il pattern Observer. */
    private List<Observer> observers = new ArrayList<>();
     
    /** Contatore utilizzato per l'aggiornamento degli sprite di Bomberman. */
    private int spriteCounter = 0;

    /** Contatore utilizzato per gestire l'animazione della morte di Bomberman. */
    private int morteCounter = 0;

    /** Numero dello sprite attualmente visualizzato. */
    private int spriteNum = 1;

    /** Dimensione originale di una singola tessera (tile) nel gioco. */
    final int originalTileSize = 16;

    /** Fattore di scala utilizzato per la dimensione dei sprite. */
    final int scale = 4;

    /** Larghezza standard di una tessera nel gioco. */
    private final int TILE_WIDTH = 64;

    /** Altezza standard di una tessera nel gioco. */
    private final int TILE_HEIGHT = 94;

    /** Dimensione attuale delle tessere nel gioco. */
    private int tileSize;

    /** Indica se Bomberman si trova attualmente su una bomba. */
    public boolean onBomb = false;

    /** Coordinate x dell'ultima bomba piazzata. */
    private int lastBombx;

    /** Coordinate y dell'ultima bomba piazzata. */
    private int lastBomby;

    /** Indica se Bomberman è stato colpito. */
    private boolean colpito;

    /** Punti esperienza accumulati da Bomberman. */
    private int exp = 0;

    /** Indica se Bomberman è attualmente invincibile. */
    private boolean invincibile = false;

    /** Numero di frame per l'animazione della morte di Bomberman. */
    private final int MORTEFRAME = 8;

    /** Numero di frame per l'animazione della fuga di Bomberman. */
    private final int FUGAFRAME = 10;

    /** Istanza singola di Bomberman (Singleton). */
    private static Bomberman instance;

    /** Frame corrente dell'animazione della morte di Bomberman. */
    public int morteFrame = 0;

    /** Punteggio attuale di Bomberman. */
    public int punteggio = 00000000;

    /** Indica se Bomberman è stato colpito per la prima volta. */
    private boolean colpitoPrimo = false;

    /** Timestamp dell'inizio della fase di invincibilità di Bomberman. */
    private long invincibileStartTime;

    /** Durata della fase di invincibilità di Bomberman. */
    private static final long INVINCIBILITÀ_DURATION = 7000;

    /** Alterna gli sprite di Bomberman durante la fase di invincibilità. */
    private boolean alternaSprite = false;

    /** Contatore per la gestione dell'audio dei passi di Bomberman. */
    private int audioPassiCounter = 0;

    /** Intervallo tra i suoni dei passi di Bomberman. */
    private final int audioPassiInterval = 40;
	 
	 /**
	  * Costruttore di default per la classe Bomberman. Inizializza il personaggio con valori predefiniti,
	  * impostando la dimensione delle tile, il numero di vite, lo stato di fuga, e altre proprietà
	  * iniziali. Il personaggio inizia con un raggio d'azione di 1 per le bombe, una bomba massima
	  * disponibile e una velocità predefinita. Il personaggio viene anche impostato come invincibile
	  * all'inizio.
	  */
		private Bomberman() {
    	this.tileSize = originalTileSize * scale;
    	this.vite= 4 ;
    	this.inFuga=false;
        setDefaultValues();
        solidArea = new Rectangle(0, 62, 60, 32);
        setInvincibile();
        this.raggio=1;
        this.maxBomb=1;
        speed= 4;
					}
		/**
		 * Metodo per ottenere l'istanza singleton di Bomberman. Se l'istanza non esiste,
		 * ne crea una nuova e la restituisce.
		 *
		 * @return l'istanza singleton di Bomberman.
		 */
		public static Bomberman getInstance() {
	        if (instance == null) {
	            instance = new Bomberman();
	        }
	        return instance;
	    }
		
		/**
		 * Aggiunge un observer a Bomberman. Gli observer sono notificati quando lo stato di Bomberman cambia.
		 *
		 * @param observer l'observer da aggiungere.
		 */
		public void addObserver(Observer observer) {
	        observers.add(observer);
	    }
		/**
		 * Rimuove un observer da Bomberman.
		 *
		 * @param observer l'observer da rimuovere.
		 */
	    public void removeObserver(Observer observer) {
	        observers.remove(observer);
	    }
	    /**
	     * Notifica tutti gli observer registrati di un cambiamento di stato di Bomberman.
	     */
	    private void notifyObservers() {
	        for (Observer observer : observers) {
	            observer.update(this);
	        }
	    }
	    /**
	     * Notifica gli observer chiamando il metodo notifyObservers.
	     */
	    public void notifica() {
	    	notifyObservers() ;
	    }
	    
	    
	    /**
	     * Reimposta Bomberman alle impostazioni predefinite. Questo include resettare l'esperienza,
	     * la puntazione, lo stato di colpito e fuga, e altre impostazioni di base. Viene usato per
	     * reinizializzare Bomberman in diverse situazioni, come ad esempio all'inizio di un nuovo gioco.
	     */  
	public void reset() {
		
		this.exp = 0;
		this.morteFrame = 0;
		this.punteggio = 00000000;
		this.colpitoPrimo = false;
	    this.fugaFrame= 0;
    	this.tileSize = originalTileSize * scale;
    	
    	this.vite= 4 ;
    	this.inFuga=false;
        setDefaultValues();
        solidArea = new Rectangle(0, 62, 60, 32);
        notifyObservers();
        this.raggio=1;
        this.maxBomb=1;
        speed= 4;
	}
	/**
	 * Cambia la direzione di movimento di Bomberman a seconda della direzione specificata.
	 * Aggiorna anche l'area solida del personaggio per le collisioni, a seconda della direzione.
	 *
	 * @param newDirection la nuova direzione di movimento.
	 */
	public void changeDirection(Direction newDirection) {
		
		switch (newDirection) {
        case UP:
        	direction = newDirection;
				solidArea.x =0;
				solidArea.y = 62;
				solidArea.width = 60;
				solidArea.height =32 ;
				break;
				
        case DOWN:
        	direction = newDirection;
			solidArea.x = 0;
			solidArea.y = 62;
			solidArea.width =60;
			solidArea.height =32 ;
            break;
        case LEFT:
        	direction =newDirection;
			solidArea.x = 0;
			solidArea.y = 62;
			solidArea.width = 48;
			solidArea.height =32 ;
            break;
        case RIGHT:
        	direction = newDirection ;
			solidArea.x = 16;
			solidArea.y = 62;
			solidArea.width = 45;
			solidArea.height =32 ;
            break;
    } 
		notifyObservers();}
	
	/**
	 * Sposta Bomberman in una nuova posizione. Questo metodo aggiorna le coordinate x e y
	 * del personaggio e notifica gli observer del cambiamento.
	 *
	 * @param newX la nuova coordinata x.
	 * @param newY la nuova coordinata y.
	 */
    public void move(int newX ,int newY) {
   
        this.x = newX;
        this.y = newY;
        notifyObservers();
    }
    
    /**
     * Aggiorna lo stato di Bomberman in base agli input ricevuti e verifica le collisioni.
     * Gestisce anche la logica di movimento, l'invincibilità e le azioni in risposta a stati
     * specifici come essere colpito o in fuga.
     * Controlla lo stato del gioco, in pausa o no.
     *
     * @param keyHandler Gestisce gli input da tastiera.
     * @param collisionChecker Controlla le collisioni con altri oggetti nel gioco.
     * @param bombe Lista delle bombe piazzate nel gioco.
     * @param bombaMap Mappa che indica la posizione delle bombe.
     * @param explosionMap Mappa che indica le aree di esplosione delle bombe.
     */
	
	public void update(KeyHandler keyHandler, CollisionChecker collisionChecker , ArrayList<Bomba> bombe, int[][] bombaMap,boolean[][] explosionMap) {	
		
		if (!isPaused) {
			if (!isPaused)	
			
		if (this.invincibile && System.currentTimeMillis() - this.invincibileStartTime > INVINCIBILITÀ_DURATION) {
            this.invincibile = false;
        }
		boolean keyPressed= false;
		if (!inFuga)	{
		if (!this.colpito) {
		 
		if(keyHandler.upPressed== true || keyHandler.downPressed == true || keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
			keyPressed = true;
		    Direction direction = null;
		    if (keyHandler.upPressed)
		    { direction = Direction.UP;
		    this.changeDirection(direction);}
		    
		    else if (keyHandler.downPressed) 
		    { direction = Direction.DOWN;
		    this.changeDirection(direction);}
		    else if (keyHandler.leftPressed)
		    { direction = Direction.LEFT;
		    this.changeDirection(direction);}
		    else if (keyHandler.rightPressed) 
		    { direction = Direction.RIGHT;
		    this.changeDirection(direction);}
		   
		    
		    if (direction != null) {
		    	this.changeDirection(direction);
		     
		        int newX = this.getX();
		        int newY = this.getY();
		        switch (direction) {
		        case UP: newY -= this.getSpeed(); break;
		        case DOWN: newY += this.getSpeed(); break;
		        case LEFT: newX -= this.getSpeed(); break;
		        case RIGHT: newX += this.getSpeed(); break;
		        }
		      
		        this.collisionOn =  false;
		        collisionChecker.checkTile(this, bombe,bombaMap);
		        
		        if (this.collisionOn == false) {
		        	
		        	this.move(newX, newY);
		        	
		        } else {
		        	
		        	
		        	collisionChecker.checkV(this,bombaMap);
		        	notifyObservers();
		        }
		    
			
		   
		    }
		}
		 
		int bombermanY =  ((this.getY()+ this.getSolidArea().y+this.getSolidArea().height)/(tileSize));
		int  bombermanX = ((this.getX() + this.getSolidArea().x+this.getSolidArea().width/2)/(tileSize)) ;
		
		if (bombaMap[bombermanY][bombermanX]==2 ){
			
			this.setOnBomb(true);
		} 
		else  {
			this.setOnBomb(false);
			if (bombaMap[lastBomby/tileSize][lastBombx/tileSize]== 2) {
			    bombaMap[lastBomby/tileSize][lastBombx/tileSize] = 1;}
		} 
		
		
		}
		 if (colpito) {
	            if (!colpitoPrimo) {
	                if (punteggio - 100 < 0) {
	                    punteggio = 0;
	                } else {
	                    punteggio -= 100;
	                }
	                colpitoPrimo = true; 
	            }
	        } else {
	        	colpitoPrimo = false; 
	        }}
		this.updateSprite(keyPressed );}}
	/**
	 * Aggiorna gli sprite di Bomberman a seconda degli input e dello stato attuale.
	 * Gestisce la transizione di sprite per il movimento, la morte, la vittoria e l'invincibilità.
	 *
	 * @param keyPressed Indica se un tasto è stato premuto per determinare l'aggiornamento degli sprite di movimento .
	 */
	public void updateSprite(boolean keyPressed) {
		 if (invincibile) {
	            alternaSprite = !alternaSprite; 
	            notifyObservers();
	        }
		if (colpito) {
			
			morteCounter++;
			
			if (!audioMorteRiprodotta) {
	            AudioManager.getInstance().play("res/audio/Bomberman-Dies.wav");
	            audioMorteRiprodotta = true; // Imposta la variabile a true dopo la riproduzione
	        }
             
               
			if(morteCounter > 15) {
				
				morteCounter = 0;
           if (morteFrame < MORTEFRAME - 1) {
           	 notifyObservers();
           	 morteFrame++;
           	 
           } else {
           	vite--;
               setDefaultValues();
               audioMorteRiprodotta = false;
               
           }}
       } else {
           audioMorteRiprodotta = false; // Resetta la variabile se Bomberman non è colpito
       }
		if(inFuga) {
			 
				
				fugaCounter++;
				if(fugaCounter > 15) {
					
					fugaCounter = 0;
	            if (fugaFrame < FUGAFRAME - 1) {
	            	notifyObservers();
	            	fugaFrame++;
	            	
	            } else {
	            	
	                setDefaultValues();
	                vittoria = true;
	                fugaFrame= 0;
	            }
	        } 
			
			
			
			
		}
		else {
       if(keyPressed) {
           spriteCounter++;
           audioPassiCounter++;
           if(spriteCounter > 10) {
           	
           	notifyObservers();
               spriteNum = spriteNum % 4 +1;
               
               spriteCounter = 0;
               if (audioPassiCounter >= audioPassiInterval) {
                   AudioManager.getInstance().play("res/audio/Walking-2.wav",0.85f);
                   audioPassiCounter = 0; // Resetta il contatore dopo la riproduzione
               }
               }
               
       } else {
       	
           spriteNum = 1;
           notifyObservers();
           audioPassiCounter = 20; // 
       }
       
   }}
	
	
	/**
	 * Restituisce l'area solida di Bomberman utilizzata per la collisione.
	 *
	 * @return Rectangle rappresentante l'area solida.
	 */
	
    public Rectangle getSolidArea() {
        return new Rectangle(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
    }
    /**
     * Restituisce la posizione x di Bomberman .
     *
     * @return x rappresentante la x.
     */
    public int getX() { 
    	return x;
    	}
    /**
     * Restituisce la posizione y di Bomberman .
     *
     * @return y rappresentante la y.
     */
    public int getY() { return y; }
    /**
     * Restituisce la speed di Bomberman .
     *
     * @return speed rappresentante la velocità.
     */
    public int getSpeed() { return speed; }
    /**
     * Imposta un nuovo valore per la coordinata X di Bomberman.
     *
     * @param x Nuovo valore della coordinata X.
     */
    public void setX(int x) { this.x = x; }
    /**
     * Imposta un nuovo valore per la coordinata Y di Bomberman.
     *
     * @param y Nuovo valore della coordinata Y.
     */
    public void setY(int y) { this.y = y; }
    /**
     * Restituisce la Direzione di Bomberman .
     *
     * @return direction rappresentante la direzione.
     */
    public Direction getDirection() {return direction;}
    /**
     * Restituisce lo spriteNum del  Bomberman .
     *
     * @return spriteNum rappresentante lo sprite corrente.
     */
    public int getSpriteNum() {
        return spriteNum;
    }
    /**
     * Imposta lo stato di collisione di Bomberman.
     *
     * @param x Stato di collisione da impostare.
     */
	@Override
	public void setCollision(boolean x) {
		collisionOn = x ;
		
	}
	/**
	 * Restituisce la larghezza di Bomberman in pixel.
	 *
	 * @return Larghezza di Bomberman.
	 */
	public int getWidth() {
		return TILE_WIDTH;
	}
	/**
	 * Restituisce l'altezza di Bomberman in pixel.
	 *
	 * @return Altezza di Bomberman.
	 */
	public int getHeight() {
		return TILE_HEIGHT;
	}
	/**
	 * Imposta lo stato "colpito" di Bomberman.
	 *
	 * @param b Stato "colpito" da impostare.
	 */
	@Override
	public void setColpito(boolean b) {
		this.colpito= b ;	
	}
	/**
	 * Restituisce lo stato "colpito" di Bomberman.
	 *
	 * @return True se Bomberman è stato colpito, altrimenti false.
	 */
	public boolean getColpito() {
		return colpito;
	}
	/**
	 * Calcola e restituisce la coordinata X reale di Bomberman sul campo di gioco, 
	 * una variabile speciale per posizionare bene le bombe.
	 *
	 * @return Coordinata X reale di Bomberman.
	 */
	public int getRealX() {
		return ((this.getX()+  this.getSolidArea().x + this.getSolidArea().width/2 ) / tileSize * tileSize);
	}
	/**
	 * Calcola e restituisce la coordinata Y reale di Bomberman sul campo di gioco.
	 *una variabile speciale per posizionare bene le bombe.
	 * @return Coordinata Y reale di Bomberman.
	 */
	public int getRealY() {
		return ((this.getY()  + this.getSolidArea().y + this.getSolidArea().height ) / tileSize * tileSize);
	}
	/**
	 * Restituisce il numero di vite rimanenti di Bomberman.
	 *
	 * @return Numero di vite di Bomberman.
	 */
	public int getVita() {
		return vite;
	}
	/**
	 * Riduce di uno il numero di vite di Bomberman a seguito di una collisione o danno.
	 */
	public void venirColpito() {
		 vite--;
	}
	/**
	 * Aumenta il punteggio di Bomberman.
	 *
	 * @param x Quantità di punteggio da aggiungere.
	 */
	public void setPunteggio(int x) {
		this.punteggio += x;	
	}
	/**
	 * Incrementa il raggio delle bombe di Bomberman di uno.
	 */
	public void setRaggio() {
		this.raggio++;
	}
	/**
	 * Aumenta la velocità di movimento di Bomberman di uno.
	 */
	public void setVelocita() {
		this.speed++;
	}
	/**
	 * Aggiunge vite al numero di vite di Bomberman.
	 *
	 * @param x Numero di vite da aggiungere.
	 */
	public void setVite(int x) {
		this.vite += x;	
	}
	/**
	 * Restituisce il raggio attuale delle bombe di Bomberman.
	 *
	 * @return Raggio delle bombe.
	 */
	public int getRaggio() {
        return raggio;
    }
	/**
	 * Restituisce il punteggio attuale di Bomberman.
	 *
	 * @return Punteggio di Bomberman.
	 */
	public int getPunteggio() {
        return punteggio;
    }
	/**
	 * Restituisce lo stato di alternanza degli sprite di invincibilita di Bomberman.
	 *
	 * @return Stato di alternanza degli sprite.
	 */
	 public boolean getAlterna() {
	        return alternaSprite;
	    }
	 /**
	  * Mette in pausa lo stato di gioco di Bomberman.
	  */
	 public void pause() {
	        isPaused = true;
	    }
	 /**
	  * Riprende lo stato di gioco di Bomberman dopo una pausa.
	  */
	 public void resume() {
	        isPaused = false;
	        
	    }
	 /**
	  * Imposta nello stato "in fuga" di Bomberman.
	  *
	  * @param b Stato "in fuga" da impostare.
	  */
	 public void setInFuga(boolean b) {
this.inFuga= b;		
	 }
	 /**
	  * Restituisce lo stato "in fuga" di Bomberman.
	  *
	  * @return True se Bomberman è in fuga, altrimenti false.
	  */
	 public boolean isInFuga() {
		 return this.inFuga;
	 }
	 /**
	  * Imposta lo stato di vittoria di Bomberman.
	  *
	  * @param b Stato di vittoria da impostare.
	  */
	public void setVittoria(boolean b) {
		this.vittoria= b;	
	}
	/**
	 * Aumenta il numero massimo di bombe che Bomberman può piazzare contemporaneamente.
	 *
	 * @param i Numero di bombe da aggiungere al massimo.
	 */
	public void setMaxBomb(int i) {
		this.maxBomb += i ;	
	}
	/**
	 * Imposta la coordinata X dell'ultima bomba piazzata da Bomberman.
	 *
	 * @param i Coordinata X da impostare.
	 */
	public void setXBomb(int i) {
		this.lastBombx=i; ;	
	}
	/**
	 * Imposta la coordinata Y dell'ultima bomba piazzata da Bomberman.
	 *
	 * @param i Coordinata Y da impostare.
	 */
	public void setYBomb(int i) {
		this.lastBomby=i ;
	}
	/**
	 * Restituisce il numero massimo di bombe che Bomberman può piazzare contemporaneamente.
	 *
	 * @return Numero massimo di bombe.
	 */
	public int getMaxBomb() {
		return this.maxBomb;
	}
	/**
	 * Aggiunge esperienza a Bomberman in base al livello specificato.
	 *
	 * @param level Livello basato sul quale viene aggiunta l'esperienza.
	 */
	public void setExp(int level) {
		switch(level) {
			case 1: this.exp += 100 ;
			case 2: this.exp += 150 ;
			case 3: this.exp += 300 ;
		}	
	}
	/**
	 * Restituisce l'esperienza attuale di Bomberman.
	 *
	 * @return Esperienza di Bomberman.
	 */
	public int getExp() {
		return this.exp;
	}
	/**
	 * Restituisce il frame corrente dell'animazione di morte di Bomberman.
	 *
	 * @return Frame dell'animazione di morte.
	 */
	public int getMorteFrame() {
		return this.morteFrame;
	}
	/**
	 * Restituisce il frame corrente dell'animazione di fuga di Bomberman.
	 *
	 * @return Frame dell'animazione di fuga.
	 */
	public int getFugaFrame() {
		return this.fugaFrame;
	}
	/**
	 * Restituisce lo stato "colpito" di Bomberman.
	 *
	 * @return True se Bomberman è stato colpito, altrimenti false.
	 */
	public boolean isColpito() {
		return this.colpito;
	}
	/**
	 * Restituisce lo stato di vittoria di Bomberman.
	 *
	 * @return True se Bomberman ha vinto, altrimenti false.
	 */
	public boolean getVittoria() {
		return vittoria;
	}
	
	/**
	 * Imposta i valori predefiniti per Bomberman. Questo metodo viene utilizzato per inizializzare
	 * Bomberman allo stato iniziale o per resettarlo dopo essere stato colpito.
	 * Imposta la posizione iniziale, la direzione, il flag di invincibilità e altri valori di stato.
	 */
	private void setDefaultValues() {
    	x= 128;
		y= 128;
		if (this.colpito){
			if (speed>4) {
				speed--;
			}
			
		}
		direction = Direction.DOWN;
		colpito = false;
		morteFrame= 0;
		setInvincibile();
	}
	
	/**
	 * Imposta lo stato di invincibilità di Bomberman. Quando invincibile, Bomberman non può essere
	 * danneggiato o colpito.
	 */
    public void setInvincibile() {
        this.invincibile = true;
        this.invincibileStartTime = System.currentTimeMillis();
        notifyObservers();
    }
    
    /**
     * Restituisce lo stato di invincibilità di Bomberman.
     *
     * @return True se Bomberman è attualmente invincibile, altrimenti false.
     */
    public boolean isInvincibile() {
        return this.invincibile;
    }
    
    /**
     * Imposta lo stato di Bomberman relativo alla bomba su cui si trova.
     * Quando Bomberman piazza una bomba, può  trovarsi sopra di essa.
     *
     * @param x Stato da impostare. True se Bomberman è sulla bomba, altrimenti false.
     */
    public void setOnBomb(boolean x) {
    	this.onBomb = x;
    }
    /**
     * imposta la vecchia istanza di Bomberman a Null, serve quando si inizia una nuova partita
     
     */
   public void resetInstance() {
	   this.instance = null;
   }
}