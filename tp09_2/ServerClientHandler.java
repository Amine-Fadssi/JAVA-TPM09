package tp09_2;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// La classe ServerClientHandler qui implémente l'interface Runnable
public class ServerClientHandler implements Runnable {
    String path;
    String filePath;
    String fileName;
    Socket s;

    // Constructeur prenant un Socket et un chemin en paramètres
    public ServerClientHandler(Socket s, String path) {
        this.s = s;
        this.path = path;
    }

    // Méthode exécutée lors du démarrage du thread
    @Override
    public void run() {
        try {
            // Obtenir les flux d'entrée et de sortie du socket
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            // Créer des flux de caractères pour la lecture et l'écriture
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            OutputStreamWriter osw = new OutputStreamWriter(os);
            PrintWriter pw = new PrintWriter(osw, true);

            // Demander et lire le nom du fichier envoyé par le client
            pw.println("Nom du fichier : ");
            fileName = br.readLine();

            // Construire le chemin complet du fichier
            filePath = path + "\\" + fileName;
            File file = new File(filePath);

            // Vérifier si le fichier existe
            if (file.exists()) {
                // Si le fichier existe, lire ses lignes et les envoyer au client
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                for (String line : lines) {
                    pw.println(line);
                }
            } else {
                // Si le fichier n'est pas trouvé, envoyer un message au client
                pw.println("Fichier non trouvé !");
            }
        } catch (IOException e) {
            // En cas d'erreur d'entrée/sortie, lancer une RuntimeException
            throw new RuntimeException(e);
        }
    }
}

