package JOUEUR;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import LOGS.Logs;
import PARTIE.Joueur;
import PARTIE.Partie;



public class ServeurDIXIT extends Thread{

	public static final int NB_JOUEUR = 4;
	public static final int NB_CARTE = 6;
	public static final int MAX_PIOCHE = 84;
	private static int port = 1001; 
	private ServerSocket serveur = null;

	// 
	private List <Socket> listClient;
	private List <ThreadMessage> poolThread;

	//
	private List<Joueur> listJoueur;
	private List<String> pioche;
	private List<Partie> listPartie;
	//Ficier Logs
	private Logs fichierLogServeur;

	//Thread
	private ThreadMessage threadMessage;
	private ThreadFonction threadFonction;


	private PrintWriter AffichageClient;
	private BufferedReader LireServeur;

	public ServeurDIXIT() throws IOException{

		// Affectation du socket serveur
		serveur = new ServerSocket(port);
		listClient = new LinkedList<Socket>();
		//poolThread = new LinkedList<ThreadMessage>();
		//partie = new ThreadPartie();
		fichierLogServeur = new Logs("logServeur.txt");
		listPartie= new ArrayList<Partie>();
		pioche = new ArrayList<String>();
	}

	public void connexion() throws IOException{
		//Mise en route des Threads
		listJoueur = new ArrayList<Joueur>();

		while(listJoueur.size() < NB_JOUEUR) {
			Socket ClientNew = serveur.accept();
			fichierLogServeur.ecrireLog("Client "+ ClientNew.getPort()+ " connecte");
			LireServeur = new BufferedReader(new InputStreamReader(ClientNew.getInputStream()));
			AffichageClient = new PrintWriter(System.out, true);

			if(listJoueur.size()==0){
				listJoueur.add(new Joueur(LireServeur.readLine(),ClientNew,true));
			}else{
				listJoueur.add(new Joueur(LireServeur.readLine(),ClientNew));
			}
		}
		int nbCarte = MAX_PIOCHE%listJoueur.size();		
		for(int i = 1; i <= (MAX_PIOCHE - nbCarte); i++){
			pioche.add(""+i);
		}
		Partie partie=new Partie(listJoueur,pioche,fichierLogServeur);
		partie.start();
		listPartie.add(partie);

	}

	public static void main (String[] args) throws IOException {
		ServeurDIXIT s = new ServeurDIXIT();
		s.fichierLogServeur.ecrireLog("Serveur en ligne");
		while(true){
			s.connexion();
		}
		//s.fichierLogServeur.ecrireLog("Serveur deconnecte");
	}
}