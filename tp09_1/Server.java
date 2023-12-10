package tp09_1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

// Classe principale du serveur
public class Server {
    // Méthode principale du serveur
    public static void main(String[] args) {
        try {
            // Création d'un ServerSocket écoutant le port 9090
            ServerSocket ss = new ServerSocket(9090);
            // Création d'un pool de threads avec une capacité fixe de 5 threads
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            // Liste pour stocker les sockets des joueurs
            ArrayList<Socket> players = new ArrayList<>();
            // Génération d'un nombre aléatoire entre 1 et 50
            int magicNumber = ThreadLocalRandom.current().nextInt(1, 51);
            // Boucle d'attente des connexions entrantes des clients
            while (true) {
                // Acceptation d'une connexion entrante et création d'un Socket
                Socket s = ss.accept();
                // Ajout du Socket à la liste des joueurs connectés
                players.add(s);
                // Soumission d'une tâche au pool de threads pour gérer le client
                executorService.submit(new ClientHandler(s, players, magicNumber));
            }
        } catch (IOException e) {
            // En cas d'exception d'entrée/sortie, lancer une RuntimeException
            throw new RuntimeException(e);
        }
    }
}
