package GiocoBomberman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * La classe GestoreGiocatore è responsabile della gestione dei dati dei giocatori nel gioco Bomberman.
 * Fornisce metodi per caricare e salvare i dati dei giocatori da e su un file.
 *
 * @author Davide Vittucci
 * @see Giocatore
 */
public class GestoreGiocatore {
	 /**
     * Percorso del file dove vengono salvati e caricati i dati dei giocatori.
     */
    private static final String FILE_PATH = "res\\giocatori.json";
    /**
     * Carica i dati dei giocatori da un file.
     *
     * @return Una lista di oggetti Giocatore contenente i dati caricati.
     */
    public List<Giocatore> caricaGiocatori() {
    	
        List<Giocatore> giocatori = new ArrayList<>();
       
        File file = new File(FILE_PATH);
       
        if (!file.exists() || file.length() == 2) {
            return giocatori; // Restituisce una lista vuota se il file è vuoto o non esiste
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Giocatore giocatore = new Giocatore();
                giocatore.setUsername(data[0]);
                giocatore.setAvatar(data[1]);
                giocatore.setPartiteGiocate(Integer.parseInt(data[2]));
                giocatore.setPartiteVinte(Integer.parseInt(data[3]));
                giocatore.setPartitePerse(Integer.parseInt(data[4]));
                giocatore.setPuntiTotali(Integer.parseInt(data[5]));
                giocatore.setLivello(Integer.parseInt(data[6]));
                giocatore.setExpAttuale(Integer.parseInt(data[7]));
                giocatore.setExpProssimoLivello(Integer.parseInt(data[8]));
                
                giocatori.add(giocatore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return giocatori;
    }
    /**
     * Salva i dati dei giocatori in un file.
     *
     * @param giocatori Lista di oggetti Giocatore da salvare.
     */
    public void salvaGiocatori(List<Giocatore> giocatori) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            giocatori.stream()
                     .map(giocatore -> String.join(",",
                            giocatore.getUsername(),
                            giocatore.getAvatar(),
                            String.valueOf(giocatore.getPartiteGiocate()),
                            String.valueOf(giocatore.getPartiteVinte()),
                            String.valueOf(giocatore.getPartitePerse()),
                            String.valueOf(giocatore.getPuntiTotali()),
                            String.valueOf(giocatore.getLivello()),
                            String.valueOf(giocatore.getExpAttuale()),
                            String.valueOf(giocatore.getExpProssimoLivello())))
                     .forEach(line -> {
                         try {
                             writer.write(line);
                             writer.newLine();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
