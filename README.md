Davide Vittucci
Per la gestione del profilo utente, nickname, avatar, partite giocate, vinte e 
perse, livello ci affidiamo alle
Seguenti Classi :
Classe Giocatore : Rappresenta il profilo di un singolo giocatore. Contiene le 
informazioni come nickname, avatar, partite giocate, partite vinte, partite perse 
e il livello del giocatore, exp necessarie .
Classe MenuModel : Gestisce un elenco di tutti i profili dei giocatori e conosce il 
giocatore selezionato in quel momento, Interagisce con GestoreGiocatori per 
caricare e salvare i profili dei giocatori.
Classe MenuController:
Funziona come intermediario tra la vista del menu e il modello .Gestisce le 
azioni dell'utente come selezionare un profilo, creare un nuovo profilo, 
visualizzare le statistiche, ecc. Comunica i cambiamenti nel modello alla vista, 
aggiornando l'interfaccia utente quando un nuovo profilo è selezionato o creato.
Classe MenuView:
Fa visualizzare le opzioni disponibili all’utente, come i profili selezionabili, le 
statistiche, i pulsanti.
Classe GestoreGiocatori: Responsabile nel salvare e caricare i giocatori da un file 
json, fornisce i loro dati al model.
Il gioco offre 3 livelli giocabili, a ogni livello cambiano i blocchi distruttibili, la 
distribuzione di quelli non distruttibili, il numero di nemici e il tipo di 
potenziamento nascosto sotto un blocco, con difficoltà sempre 
Maggiore, con timer sempre minore. È stata introdotta una funzione di pausa 
che blocca tutti gli elementi presenti a schermo nel loro ultimo stato (per 
esempio gli sprite o il tempo di esplosione), dalla schermata di pausa è 
possibile tornare al menu o continuare la partita. Ogni livello inoltre accumulerà 
i punteggi dei precedenti e anche i potenziamenti. Se la partità finirà con esito 
vittorioso, le statistiche del giocatore saranno aggiornate, guadagnerà l 
exp(ogni livello da exp) e il contatore di win aumenterà, apparirà la schermata di 
vittoria che permette di tornare al menu o di iniziare una nuova partita. Se si 
perde sarà aggiornato solo il counter di sconfitte senza guadagnare punteggio 
o exp. Apparira la schermata di sconfitta che permetterà di iniziare una nuova 
partita o tornare al menu.
Sono presenti due tipologie di nemici, il puropen e il denkyun. Il primo è il più 
debole, ha 1 vita, si muove casualmente e collide con i blocchi distruttibili. Il 
secondo è il più arduo da affrontare, può volare sopra i blocchi distruttibili e ha 
due vite. Se colpito sarà invulnerabile per qualche secondo. Il puropen fa 
guadagnare 200 punti mentre il denkyn 350.
Sono presenti vari Power up in ogni livello, dalle bombe extra, al raggio 
aumentato delle esplosioni , alla vita extra , velocità extra, punteggio e 
un’invicibilità temporanea. Ogni livello ha dei potenziamenti che saranno 
sicuramente presenti sotto qualche muro distruttibile casuale, mentre altri 
hanno solo delle percentuali di spawnare. Le vite, il punteggio e i power 
up(quelli accumulabili) sono visibili nell’hud, cosi come il tempo rimanente. Se 
scade il timer, si perde una vita e si resetta il tempo.
Come detto abbiamo la possibilità di continuare una partita messa in pausa. 
E’ stato utilizzato il design MVC per ogni oggetto del gioco, infatti ogni oggetto 
è diviso in un modello che gestisce i dati e da una vista che li disegna a 
schermo. Questa divisione è gestita dal GameController che connette i model e 
le views tramite una classe GamePanel, la quale è incaricata solo di disegnare a 
schermo tutte le viste possibili. Inoltre per notificare i cambiamenti nel model, 
usiamo il pattern Observer Observable, grazie al quale le viste ricevono 
automaticamente i cambiamenti dei modelli, mentre il gameController gestisce 
la loro connessione aggiungendo e rimuovendo i collegamenti tra gli 
observable e gli observer.
Viene usato anche il Pattern singleton per il Bomberman e per l hud, cosi 
facendo a ogni nuova partita basta resettarne i valori .
Utilizziamo gli stream in varie Classi. Per Esempio nella classe Level gli stream 
sono utilizzati nel metodo
freeTiles(),questo metodo usa gli stream per esaminare la mappa del gioco e 
individuare tutte le posizioni libere, creando una lista di queste posizioni. Per 
poi poterci far spawnare i nemici.
Nel metodo setupView() della classe Level, viene utilizzato uno Stream per 
iterare su una lista di oggetti Nemico.questo metodo utilizza uno Stream per 
iterare su una lista di nemici, creare e associare una vista appropriata a ciascun 
nemico, e infine aggiungere questa associazione a una mappa
La classe GestoreGiocatori utilizza gli stream in SalvaGiocatori(), in questo 
metodo uno streama sull elenco giocatori , in modo che trasforma una lista di 
oggetti Giocatore in stringhe formattate e le salva in un file.
E’ stato utilizzato Java Swing
Per la riproduzione Audio è stata Modificata la classe fornita, aggiungendo la 
possibilità di loop e di settaggio del volume, distinguendo tra la musica di 
sottofondo e tra i suoni che fa ogni oggetto quando deve.
Nel Progetto ci sono varie animazioni: Puropen e Bomberman a seconda della 
direzione che stanno seguendo hanno una serie di sprite diversi. L animazione 
di camminata alterna i piedi, tornando al personaggio fermo quando non si 
clicca nulla. Le bombe Hanno un animazione che fa avanti e indietro per un 
tempo settato, quando esplode appare un esplosione che può allungarsi a 
seconda del raggio, questo è possibile grazie a un calcolo nel collisionChecker 
che controlla di quanto può allungarsi l esplosione prima di incontrare un muro. 
Poi la view la disegna sapendo quanto si può allungare. Sono presenti le 
animazioni di morte sia per il bomberman che per i nemici. E i muri distruttibili 
si distruggono sempre con animazione lasicando spazio o all erba o alla botola 
o a potenziamenti. E’ anche presente l animazione di uscita nella botola.
Presente anche un sistema di scivolamento laterale quando si incontra una 
collisione, in modo di poter andare nel riquadro libero più vicino. 
Ci sono dei pattern di blocchi non distruttiibli per ogni livello, sono scelti 
casualmente ogni volta. Anche i blocchi distruttibili vengono messi in modo 
randomico, cosi come lo spawn dei nemici è randomico.
Il personaggio se colpito, dopo essere morto respawna in alto a sinistra nello 
schermo, il luogo di spawn è una zona protetta che sarà sempre libera all inizio.
Possono essere usate sia le freccette che wasd per giocare.
Parlando delle statistiche dei giocatori, l exp per il livello successivo cambia a 
ogni livello. E’ possibile editare nome e avatar del proprio profilo mantenendo i 
dati. Se non ci sono profili salvati, per giocare sarà necessario crearne uno.
E’ stata anche implementata una funzione di rescaling dinamico degli oggetti, 
infatti il gioco e il menu si adattano a qualunque misura verrà data alla finestra 
di gioco, anche se piccola. Normalmente inizia a schermo intero , ma e’ possibile 
cambiarla e non vedere parti di schermo nascoste fuori dal monitor.
Questo è possibile grazie a un lavoro di mirroring , il gioco è disegnato a una 
grandezza designata su uno schermo fittizio, per poi essere scalato tramite la 
classe Scaler e proiettato dal GamePanel sull’effettiva finestra di gioco visibile 
all’utente
