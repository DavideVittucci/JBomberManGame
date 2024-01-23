package GiocoBomberman;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Generatore di mappe per il gioco Bomberman. Questa classe è responsabile della creazione
 * di layout di mappe casuali, inclusa la posizione di piastrelle distruttibili e indistruttibili.
 * Le mappe generate possono essere salvate su file.
 * 
 * @author Davide Vittucci
 */
public class MapGenerator {
		

    /** Numero di righe nella mappa. */
    private static final int ROWS = 15;

    /** Numero di colonne nella mappa. */
    private static final int COLS = 17;

    /** Percorso del file in cui salvare la mappa generata. */
    private static final String MAP_PATH = "res\\maps\\mappa1.txt";

    /** Rappresentazione interna della mappa come array 2D di interi. */
    private int[][] map = new int[ROWS][COLS];
    
    /**
     * Genera una mappa con un dato numero di blocchi distruttibili.
     *
     * @param blocchi Il numero di blocchi distruttibili da generare nella mappa.
     */
	    public void generateMap(int blocchi) {
	        initializeMap();
	        addDestructibleTiles(blocchi);
	        saveMapToFile();
	    }
	    /**
	     * Inizializza la mappa con layout di base, inclusi bordi e aree non distruttibili.
	     */
	    private void initializeMap() {
	        // Imposta bordi e HUD
	        
	        // Muri esterni laterali
	        for (int i = 0; i < ROWS; i++) {
	            map[i][0] = map[i][1] = map[i][COLS - 2] = map[i][COLS - 1] = 2;
	        }

	        // Muri interni
	        for (int i = 3; i < ROWS - 1; i++) {
	            for (int j = 2; j < COLS - 2; j++) {
	                map[i][j] = ((i - 3) % 2 == 0 || (j - 2) % 2 == 0) ? 0 : 2;
	            }
	        }
	        Random rand = new Random();
	        
	       
	        switch(rand.nextInt(3)) {
	        case 0:
	        	 
	        map[3][5] = 2;
	        map[5][9] = 2;
	        map[3][11] = 2;
	        map[11][12] = 2;
	        map[10][5] = 2;
	        map[8][8] = 2;
	        map[13][2] = 2;
	        map[11][6] = 2;
	        break;
	        case 1:
	        	 
	        	map[5][3] = 2;
		        map[7][9] = 2;
		        map[5][11] = 2;
		        map[11][3] = 2;
		        map[10][5] = 2;
		        map[12][8] = 2;
		        map[13][14] = 2;
		        map[9][15] = 2;
		        break;
	        case 2:
	        	
	        	map[3][6] = 2;
		        map[4][6] = 2;
		        map[5][12] = 2;
		        map[6][14] = 2;
		        map[8][2] = 2;
		        map[10][4] = 2;
		        map[11][8] = 2;	
		        map[11][14] = 2;	
		        break;
	      
	        	}
	        
	       
	        for (int i = 0; i < COLS; i++) {
		           
	            map[2][i] = 2; // Muro esterno
	            
	            map[ROWS - 1][i] = 2; // Muro esterno
	            map[0][i] = map[1][i] = 3; // HUD
	        }

	        // Assicura che le posizioni [3][2], [3][3], [4][2] siano libere
	        map[3][2] = map[3][3] = map[4][2] = 0;
	    }

	    /**
	     * Aggiunge blocchi distruttibili casuali alla mappa.
	     *
	     * @param blocchi Il numero di blocchi distruttibili da aggiungere.
	     */
	    private void addDestructibleTiles(int blocchi) {
	        Random rand = new Random();
	        int count = 0;
	        
	        while (count < blocchi) {
	        	
	            int r = rand.nextInt(ROWS - 4) + 3; // Evita le prime tre righe e l'ultima
	            int c = rand.nextInt(COLS - 4) + 2; // Evita le colonne esterne

	            // Aggiunge una piastrella distruttibile solo se è uno spazio libero
	            if (map[r][c] == 0 && !((r == 3 && (c == 2 || c == 3)) || (r == 4 && c == 2))) {
	                map[r][c] = 1;
	                count++;
	            }
	        }
	    } 
	    /**
	     * Salva la mappa corrente su file.
	     */
	    private void saveMapToFile() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MAP_PATH, false))) {
	            for (int[] row : map) {
	                String rowAsString = Arrays.stream(row)
	                                           .mapToObj(String::valueOf)
	                                           .collect(Collectors.joining(" "));
	                writer.write(rowAsString);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  
	}