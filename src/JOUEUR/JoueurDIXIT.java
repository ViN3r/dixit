package JOUEUR;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
 
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import VIEW.*;



 
public class JoueurDIXIT extends Thread{
	
    private static int port = 1001;
    public static boolean pretJouer = false;
    private static String host = "localhost";
    private Socket serveurDIXIT;
    // Pipe de lecture et d'ecriture
    private PrintWriter AffichageServeur;
    private PrintWriter AffichageClient;
    private BufferedReader LireServeur;
    private BufferedReader LireClient;
    private String pseudo;
    JFrame fenetre;
    
    
    //Threads
    private ThreadMessage thread1;
    private ThreadFonction thread2;
    
    public JoueurDIXIT(JFrame fenetre, String pseudo) throws IOException, InterruptedException{
    	this.pseudo = pseudo;
    	serveurDIXIT = new Socket(host, port);
    	AffichageServeur = new PrintWriter(serveurDIXIT.getOutputStream(), true); 
    	AffichageClient = new PrintWriter(System.out, true);
        LireServeur = new BufferedReader(new InputStreamReader(serveurDIXIT.getInputStream())); 
		LireClient = new BufferedReader(new InputStreamReader(System.in));
		thread1 = new ThreadMessage(AffichageServeur, LireClient, "CLIENT : ");
		thread2 = new ThreadFonction(AffichageClient, LireServeur, serveurDIXIT,  fenetre);
		VueAttente panel = new VueAttente(fenetre);
		fenetre = changerPanel(fenetre, panel);
		initialisation();
		sendData(pseudo);
    }
 
	public void initialisation() throws IOException {	
		thread1.start();
		thread2.start();
	}
	public void partie(){
			
	}

	public ThreadMessage getThread1() {
		return thread1;
	}

	public ThreadFonction getThread2() {
		return thread2;
	}
	
	public static JFrame changerPanel(JFrame fenetre, JPanel panel) throws IOException{
		fenetre.getContentPane().removeAll();
		fenetre.setContentPane(panel);					
		fenetre.getContentPane().repaint();
		fenetre.getContentPane().revalidate();
		return fenetre;
	}
	
	public void sendData(String contenu){
		thread1.send(contenu);
	}

	
}
