package tp09_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur {
    // Le point d'entrée du programme
    public static void main(String[] args) {
        try {
            // Création d'un ServerSocket qui écoute sur le port 9080
            ServerSocket ss = new ServerSocket(9080);

            // Création d'un pool de threads avec un nombre fixe de threads (10)
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            // Accepter continuellement les connexions entrantes
            while (true) {
                // Accepter une connexion et créer un Socket lorsqu'un client se connecte
                Socket s = ss.accept();

                // Définir un chemin pour gérer les requêtes du client
                String path = "C:\\Users\\pc\\Documents\\.Courses\\DSA doc";

                // Soumettre une tâche au pool de threads pour gérer la demande du client
                executorService.submit(new ServerClientHandler(s, path));
            }
        } catch (IOException e) {
            // Si une IOException se produit, lancer une RuntimeException
            throw new RuntimeException(e);
        }
    }
}





