package GiocoBomberman;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * La classe CollisionChecker si occupa di gestire tutte le collisioni nel gioco Bomberman.
 * Controlla le interazioni tra entità come Bomberman, nemici, bombe e l'ambiente di gioco.
 * Si occupa di verificare le collisioni con le piastrelle della mappa, le esplosioni delle bombe,
 * e le interazioni tra Bomberman, i nemici e gli oggetti bonus.
 * 
 * La classe utilizza le informazioni sulla mappa del gioco e sulle dimensioni delle piastrelle
 * per calcolare accuratamente le collisioni. Supporta diversi tipi di collisioni e gestisce le
 * reazioni appropriate, come il cambiamento di direzione dei nemici, il danno a Bomberman,
 * o l'attivazione di oggetti bonus.
 * 
 * @author Davide Vittucci
 *  @see Piastrella
 */
public class CollisionChecker {
	  /**
     * Mappa delle piastrelle rappresentanti l'ambiente di gioco.
     */
    private Piastrella[][] mapTileNum;

    /**
     * Array di piastrelle utilizzate nel gioco.
     */
    private Piastrella[] tile;

    /**
     * Dimensione delle piastrelle nell'ambiente di gioco.
     */
    private int tileSize;

    /**
     * Costruttore per CollisionChecker.
     * 
     * @param mapTileNum Mappa delle piastrelle del gioco.
     * @param tile Array delle piastrelle utilizzate.
     * @param tileSize Dimensione delle piastrelle.
     */
	    public CollisionChecker(Piastrella[][] mapTileNum, Piastrella[] tile, int tileSize) {
	        this.mapTileNum = mapTileNum;
	        this.tile = tile;
	        this.tileSize = tileSize;
	    }
	

	    /**
	     * Controlla le collisioni di un'entità con le piastrelle e le bombe nel gioco.
	     * controlla sia la parte sinistra sia quella destra dell hitbox del personaggio
	     * 
	     * @param entita L'entità per cui verificare le collisioni.
	     * @param bombe Lista delle bombe presenti nel gioco.
	     * @param mapBomb Mappa delle posizioni delle bombe.
	     */
	public void checkTile (Entita entita, List<Bomba> bombe, int[][] mapBomb) {
		 
		int entityLeftWorldX = entita.getX() + entita.getSolidArea().x ;
		int entityRightWorldX = entita.getX() + entita.getSolidArea().x+entita.getSolidArea().width ;
		int entityTopWorldY = entita.getY() + entita.getSolidArea().y ;
		int entityBottomWorldY = entita.getY() + entita.getSolidArea().y+entita.getSolidArea().height ;
		
		int entityLeftCol = entityLeftWorldX/tileSize;
		int entityRightCol =entityRightWorldX/tileSize;
		int entityTopRow =  entityTopWorldY/tileSize;
		int entityBottomRow=  entityBottomWorldY/tileSize;
		Piastrella tileNum1, tileNum2 ;
		
		switch(entita.getDirection()) {
		case UP:
			entityTopRow = (entityTopWorldY-entita.getSpeed())/tileSize;
			tileNum1 = mapTileNum[entityTopRow] [entityLeftCol] ;
			tileNum2 = mapTileNum[entityTopRow][entityRightCol] ;
			if(tileNum1.collision == true || tileNum2.collision==true || mapBomb[entityTopRow][entityLeftCol] ==1||mapBomb[entityTopRow][entityRightCol] ==1){ //||mapBomb[entityTopRow][entityLeftCol] ||mapBomb[entityTopRow][entityRightCol]
				entita.collisionOn = true;	
				
			}
			
			
		break;
		case DOWN:
			entityBottomRow = (entityBottomWorldY+entita.getSpeed())/tileSize;
			
			tileNum1 = mapTileNum[entityBottomRow][entityLeftCol] ;
			tileNum2 = mapTileNum[entityBottomRow][entityRightCol] ;
			if(tileNum1.collision == true || tileNum2.collision==true ||mapBomb[entityBottomRow][entityLeftCol] ==1 ||mapBomb[entityBottomRow][entityRightCol] ==1) { // ||mapBomb[entityBottomRow][entityLeftCol] ||mapBomb[entityBottomRow][entityRightCol] 
				entita.collisionOn = true;}
			
			
		break;
		case LEFT:
			
			entityLeftCol = (entityLeftWorldX-entita.getSpeed())/tileSize;
			
		
			tileNum1 = mapTileNum[entityTopRow][entityLeftCol] ;
			tileNum2 = mapTileNum[entityBottomRow][entityLeftCol] ;
			if(tileNum1.collision == true || tileNum2.collision==true||mapBomb[entityTopRow][entityLeftCol] ==1 ||mapBomb[entityBottomRow][entityLeftCol]==1)  { // ||mapBomb[entityTopRow][entityLeftCol] ||mapBomb[entityBottomRow][entityLeftCol]
				entita.collisionOn = true;
				
			}
			
		break;
		case RIGHT:

			entityRightCol =( entityRightWorldX +entita.getSpeed())/tileSize;


		
			tileNum1 = mapTileNum[entityTopRow][entityRightCol] ;
			tileNum2 = mapTileNum[entityBottomRow][entityRightCol] ;
			
			if(tileNum1.collision == true || tileNum2.collision==true|| mapBomb[entityTopRow][entityRightCol]==1 ||mapBomb[entityBottomRow][entityRightCol]==1)  { // || (mapBomb[entityTopRow][entityRightCol] ||mapBomb[entityBottomRow][entityRightCol]) 
				entita.collisionOn = true;
				
			}
		
		break;
		}
		
	    
	    
	}
	 /**
     * Verifica le collisioni e in caso di collisioni fa slittare il personaggio verso unaa via libera
     * 
     * @param entita L'entità 
     * @param mapBomb Mappa delle posizioni delle bombe.
     */
	public void checkV(Entita entita, int[][] mapBomb) 	 {
		int entityLeftWorldX = entita.getX() + entita.getSolidArea().x ;
		int entityRightWorldX = entita.getX() + entita.getSolidArea().x+entita.getSolidArea().width ;
		int entityTopWorldY = entita.getY() + entita.getSolidArea().y ;
		int entityBottomWorldY = entita.getY() + entita.getSolidArea().y+entita.getSolidArea().height ;
		
		int entityLeftCol = entityLeftWorldX/tileSize;
		int entityRightCol =entityRightWorldX/tileSize;
		int entityTopRow =  entityTopWorldY/tileSize;
		int entityBottomRow=  entityBottomWorldY/tileSize;
		Piastrella tileNum1, tileNum2 ;
	
		switch(entita.getDirection()) {
		case UP:
			entityTopRow = (entityTopWorldY-entita.getSpeed())/tileSize;
			tileNum1 = mapTileNum[entityTopRow] [entityLeftCol] ;
			tileNum2 = mapTileNum[entityTopRow][entityRightCol] ;
			if(tileNum1.collision == false ) {
				if (entita.getSpeed()<5) {
				entita.setX(entita.getX() - entita.getSpeed());
				}
				else {entita.setX(entita.getX() - 4);}
			}
			if(tileNum2.collision==false) {
				if (entita.getSpeed()<5) {
				entita.setX(entita.getX() + entita.getSpeed());
				}
				else {entita.setX(entita.getX() + 4);}
			}
		break;
		case DOWN:
			entityBottomRow = (entityBottomWorldY+entita.getSpeed())/tileSize;
			tileNum1 = mapTileNum[entityBottomRow][entityLeftCol] ;
			tileNum2 = mapTileNum[entityBottomRow][entityRightCol] ;
			if(tileNum1.collision == false) {
				if (entita.getSpeed()<5) {
				entita.setX(entita.getX() - entita.getSpeed());
				}
				else {entita.setX(entita.getX() - 4);}
			}
			if( tileNum2.collision==false) {
				if (entita.getSpeed()<5) {
				entita.setX(entita.getX() + entita.getSpeed());
				}
				else {entita.setX(entita.getX() + 4);}
			}
		break;
		case LEFT:
			entityLeftCol = (entityLeftWorldX-entita.getSpeed())/tileSize;
			tileNum1 = mapTileNum[entityTopRow][entityLeftCol] ;
			tileNum2 = mapTileNum[entityBottomRow][entityLeftCol] ;
			if(tileNum1.collision == false) {
				if (entita.getSpeed()<5) {
				entita.setY(entita.getY() - entita.getSpeed());
				}
				else {entita.setY(entita.getY() - 4);}
			}
			if( tileNum2.collision==false) {
				if (entita.getSpeed()<5) {
				entita.setY(entita.getY() + entita.getSpeed());
				}
				else {entita.setY(entita.getY() + 4);}
			}
		break;
		case RIGHT:
			entityRightCol =( entityRightWorldX +entita.getSpeed())/tileSize;
			tileNum1 = mapTileNum[entityTopRow][entityRightCol] ;
			tileNum2 = mapTileNum[entityBottomRow][entityRightCol] ;
			if(tileNum1.collision == false) {
				if (entita.getSpeed()<6) {
				entita.setY(entita.getY() - entita.getSpeed());
				}
				else {entita.setY(entita.getY() - 4);}
			}
			if( tileNum2.collision==false){
				if (entita.getSpeed()<6) {
				entita.setY(entita.getY() + entita.getSpeed());
			}
			else {entita.setY(entita.getY() + 4);}
				
			}
		break;
		}
		;
		
	}
	 /**
     * Verifica se un'entità è stata colpita da un'esplosione.
     * 
     * @param entita L'entità da controllare.
     * @param explosionMap Mappa delle esplosioni.
     */
	public void checkDeath(Entita entita, boolean[][] explosionMap) {
	    int x = entita.getRealX() / tileSize;
	    int y = entita.getRealY() / tileSize;

	    if (explosionMap[y][x]) {
	        if (!entita.invincibile) {
	         
	                entita.setColpito(true);
	            
	        }
	    }
	}
		
	
		
	/**
     * Controlla le collisioni tra nemici.
     * 
     * @param nemici Lista dei nemici da controllare.
     */
		
	
	public void checkNemiciCollision(ArrayList<Nemico> nemici) {
	    for (Nemico nemico : nemici) {
	        int nextX = nemico.getX();
	        int nextY = nemico.getY();

	        switch (nemico.getDirection()) {
	            case UP:
	                nextY -= nemico.getSpeed();
	                break;
	            case DOWN:
	                nextY += nemico.getSpeed();
	                break;
	            case LEFT:
	                nextX -= nemico.getSpeed();
	                break;
	            case RIGHT:
	                nextX += nemico.getSpeed();
	                break;
	        }

	        Rectangle nextPosition = new Rectangle(nextX + nemico.getSolidArea().x, nextY + nemico.getSolidArea().y,
	                                               nemico.getSolidArea().width, nemico.getSolidArea().height);

	        for (Nemico otherNemico : nemici) {
	            if (nemico != otherNemico) {
	                Rectangle otherPosition = new Rectangle(otherNemico.getX() + otherNemico.getSolidArea().x,
	                                                        otherNemico.getY() + otherNemico.getSolidArea().y,
	                                                        otherNemico.getSolidArea().width, otherNemico.getSolidArea().height);

	                if (nextPosition.intersects(otherPosition)) {
	                    nemico.cambioDirezione();
	                    break;
	                }
	            }
	        }
	    }
	}
	 /**
     * Controlla le collisioni tra Bomberman e i nemici.
     * 
     * @param bomberman Il personaggio principale.
     * @param nemici Lista dei nemici nel gioco.
     */
	public void checkBombermanNemiciCollision(Bomberman bomberman, ArrayList<Nemico> nemici) {
	    Rectangle bombermanPosition = new Rectangle(bomberman.getX() + bomberman.getSolidArea().x, 
	                                                bomberman.getY() + bomberman.getSolidArea().y,
	                                                bomberman.getSolidArea().width, bomberman.getSolidArea().height);

	    for (Nemico nemico : nemici) {
	        Rectangle nemicoPosition = new Rectangle(nemico.getX() + nemico.getSolidArea().x,
	                                                 nemico.getY() + nemico.getSolidArea().y,
	                                                 nemico.getSolidArea().width, nemico.getSolidArea().height);

	        if (bombermanPosition.intersects(nemicoPosition)) {
	        	if (!bomberman.isInvincibile()&&!bomberman.getColpito()) {
	            if (!nemico.morto) {
	            	
	                bomberman.setColpito(true);
	            }
	            nemico.cambioDirezione();
	            
	            break;
	        }}
	    }
	}	
	/**
     * Verifica l'espansione dell'esplosione generata da una bomba.
     * 
     * @param esplosione L'oggetto esplosione da controllare.
     */
	public void checkExpx(Esplosione esplosione) {
		int raggio = esplosione.getRaggio();
	    int centroX = esplosione.getX() / tileSize;
	    int centroY = esplosione.getY() / tileSize;

	    // Espandi l'esplosione in ogni direzione
	    int raggioSu = espandiEsplosione(centroY, centroX, -1, 0, raggio);
	    int raggioGiu = espandiEsplosione(centroY, centroX, 1, 0, raggio);
	    int raggioSinistra = espandiEsplosione(centroY, centroX, 0, -1, raggio);
	    int raggioDestra = espandiEsplosione(centroY, centroX, 0, 1, raggio);
	   
	    esplosione.setDirezioniEraggi(raggioSu, raggioGiu, raggioSinistra, raggioDestra);
	}
	 /**
     * Espande un'esplosione in una direzione specifica.
     * 
     * @param startY Posizione iniziale Y.
     * @param startX Posizione iniziale X.
     * @param deltaY Moltiplicatore Y per l'espansione.
     * @param deltaX Moltiplicatore X per l'espansione.
     * @param maxRaggio Raggio massimo dell'esplosione.
     * @return Il raggio dell'esplosione in quella direzione.
     */
	private int espandiEsplosione(int startY, int startX, int deltaY, int deltaX, int maxRaggio) {
	    int raggio = 0;
	    for (int i = 1; i <= maxRaggio; i++) {
	        int y = startY + i * deltaY;
	        int x = startX + i * deltaX;

	        if (y < 0 || y >= mapTileNum.length || x < 0 || x >= mapTileNum[0].length) {
	            break; // Fuori dai limiti della mappa
	        }

	        if (mapTileNum[y][x].getTipo() == 1) { // Muro distruttibile
	        	
	            mapTileNum[y][x].setColpita(true);
	           
	            raggio = i - 1; // L'esplosione si ferma qui
	            break;
	        } else if (mapTileNum[y][x].getTipo() == 2) { // Muro indistruttibile
	            raggio = i - 1; // L'esplosione si ferma prima del muro
	            break;
	        } else {
	            raggio = i; // Continua a espandersi
	        }
	    }
	    return raggio;
	}

	 /**
     * Controlla le collisioni di un Denkyun con le piastrelle e le bombe nel gioco.
     * diverso dall'altro visto che il Denkyun puo volare
     * @param entita L'entità Denkyun da controllare.
     * @param bombe Lista delle bombe presenti nel gioco.
     * @param mapBomb Mappa delle posizioni delle bombe.
     */

	public void checkTileDenkyun(Denkyun entita, ArrayList<Bomba> bombe, int[][] mapBomb) {
	int entityLeftWorldX = entita.getX() + entita.getSolidArea().x ;
	int entityRightWorldX = entita.getX() + entita.getSolidArea().x+entita.getSolidArea().width ;
	int entityTopWorldY = entita.getY() + entita.getSolidArea().y ;
	int entityBottomWorldY = entita.getY() + entita.getSolidArea().y+entita.getSolidArea().height ;
	
	int entityLeftCol = entityLeftWorldX/tileSize;
	int entityRightCol =entityRightWorldX/tileSize;
	int entityTopRow =  entityTopWorldY/tileSize;
	int entityBottomRow=  entityBottomWorldY/tileSize;
	Piastrella tileNum1, tileNum2 ;
	
	switch(entita.getDirection()) {
	case UP:
		entityTopRow = (entityTopWorldY-entita.getSpeed())/tileSize;
		tileNum1 = mapTileNum[entityTopRow] [entityLeftCol] ;
		tileNum2 = mapTileNum[entityTopRow][entityRightCol] ;
		if(tileNum1.getTipo()==2 || tileNum2.getTipo()==2 || mapBomb[entityTopRow][entityLeftCol] ==1||mapBomb[entityTopRow][entityRightCol] ==1){ //||mapBomb[entityTopRow][entityLeftCol] ||mapBomb[entityTopRow][entityRightCol]
			entita.collisionOn = true;	
			
		}
		
		
	break;
	case DOWN:
		entityBottomRow = (entityBottomWorldY+entita.getSpeed())/tileSize;
		
		tileNum1 = mapTileNum[entityBottomRow][entityLeftCol] ;
		tileNum2 = mapTileNum[entityBottomRow][entityRightCol] ;
		if(tileNum1.getTipo()==2 || tileNum2.getTipo()==2 ||mapBomb[entityBottomRow][entityLeftCol] ==1 ||mapBomb[entityBottomRow][entityRightCol] ==1) { // ||mapBomb[entityBottomRow][entityLeftCol] ||mapBomb[entityBottomRow][entityRightCol] 
			entita.collisionOn = true;}
		
		
	break;
	case LEFT:
		
		entityLeftCol = (entityLeftWorldX-entita.getSpeed())/tileSize;
		
	
		tileNum1 = mapTileNum[entityTopRow][entityLeftCol] ;
		tileNum2 = mapTileNum[entityBottomRow][entityLeftCol] ;
		if(tileNum1.getTipo()==2 || tileNum2.getTipo()==2||mapBomb[entityTopRow][entityLeftCol] ==1 ||mapBomb[entityBottomRow][entityLeftCol]==1)  { // ||mapBomb[entityTopRow][entityLeftCol] ||mapBomb[entityBottomRow][entityLeftCol]
			entita.collisionOn = true;
			
		}
		
	break;
	case RIGHT:

		entityRightCol =( entityRightWorldX +entita.getSpeed())/tileSize;


	
		tileNum1 = mapTileNum[entityTopRow][entityRightCol] ;
		tileNum2 = mapTileNum[entityBottomRow][entityRightCol] ;
		
		if(tileNum1.getTipo()==2|| tileNum2.getTipo()==2|| mapBomb[entityTopRow][entityRightCol]==1 ||mapBomb[entityBottomRow][entityRightCol]==1)  { // || (mapBomb[entityTopRow][entityRightCol] ||mapBomb[entityBottomRow][entityRightCol]) 
			entita.collisionOn = true;
			
		}
	
	break;
	}
	
    
    
}  /**
     * Verifica le condizioni per l'attivazione della modalità fuga in Bomberman.
     * 
     * @param bomberman Il personaggio principale.
     * @param nemici Lista dei nemici nel gioco.
     */
	 public void checkFugaPot(Bomberman bomberman, ArrayList<Nemico> nemici) {
	        int tileX = bomberman.getRealX()/64 ;
	        int tileY = bomberman.getRealY() /64 ;
	        
	        Piastrella piastrella = mapTileNum[tileY][ tileX];
	       
	        if (piastrella != null && piastrella.getTipo() == 4 && nemici.isEmpty() && !bomberman.isInFuga()) {
	        	bomberman.setX(bomberman.getRealX());
	        	bomberman.setY(bomberman.getRealY()-48);
	            bomberman.setInFuga(true);
	            AudioManager.getInstance().play("res/audio/Stage-Clear.wav");
	        }
	
	    	
	        if (piastrella != null && piastrella.getTipo() == 7 && !piastrella.raccolto) {
	        	piastrella.calpestata=true;
	            bomberman.setVite(1);;
	            piastrella.raccolto=true;
	            AudioManager.getInstance().play("res/audio/Item Get.wav");
	        }
	        if (piastrella != null && piastrella.getTipo() == 10&& !piastrella.raccolto ) {
	        	piastrella.calpestata=true;
	            bomberman.setMaxBomb(5);
	            piastrella.raccolto=true;
	            AudioManager.getInstance().play("res/audio/Item Get.wav");
	        }
	        if (piastrella != null && piastrella.getTipo() == 8 && !piastrella.raccolto) {
	        	piastrella.calpestata=true;
	        	bomberman.setPunteggio(300);;
	        	piastrella.raccolto=true;
	        	AudioManager.getInstance().play("res/audio/Item Get.wav");
	        }
	        if (piastrella != null && piastrella.getTipo() == 5&& !piastrella.raccolto) {
	        	piastrella.calpestata=true;
	        	bomberman.setRaggio();
	        	piastrella.raccolto=true;
	        	AudioManager.getInstance().play("res/audio/Item Get.wav");
	        }
	        if (piastrella != null && piastrella.getTipo() == 6 && !piastrella.raccolto) {
	        	piastrella.calpestata=true;
	        	bomberman.setVelocita();
	        	piastrella.raccolto=true;
	        	AudioManager.getInstance().play("res/audio/Item Get.wav");
	        }
	        if (piastrella != null && piastrella.getTipo() == 9  && !piastrella.raccolto) {
	        	piastrella.calpestata=true;
	        	bomberman.setInvincibile();
	        	piastrella.raccolto=true;
	        	AudioManager.getInstance().play("res/audio/Item Get.wav");
	        	}
	    }}

