package JOUEUR;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

import VIEW.VueFinPartie;
import VIEW.VuePartie;

public class ThreadFonction extends Thread {

	private String message;
	private PrintWriter out;
	private BufferedReader in;
	private Socket serveurDIXIT;
	private ArrayList<String> main;
	private ArrayList<String> table;
	private List<String[]> TABJoueurs;
	private String[] Joueurs;
	private String[] Joueur;
	private JFrame fenetre;
	private String indice;
	private int carteChoisie;
	private int carteConteurPense;
	private String[] conteur;
	private String finPartie;
	private String nbJoueur;
	private int compteur;
	private int CarteEnMoins = 0;

	public ThreadFonction(PrintWriter out, BufferedReader in,
			Socket serveurDIXIT, JFrame fenetre) {
		this.fenetre = fenetre;
		this.out = out;
		this.in = in;
		this.serveurDIXIT = serveurDIXIT;
		main = new ArrayList<String>();
		table = new ArrayList<String>();
		TABJoueurs = new ArrayList<String[]>();
		finPartie = "non";
		nbJoueur = "0";
	}

	public void run() {
		try {
			// Reception des cartes de la premiere main
			for(int i = 0; i < ServeurDIXIT.NB_CARTE ; i++) {
				main.add(in.readLine());
				out.println(main.get(i));
			}
			// Debout de la boucle du tour
			while (!(finPartie.equals("oui"))) {
				// on initialise les tableaux de joueurs
				//Qui je suis
				Joueur = new String[3];
				//Les autres joueurs
				Joueurs = new String[3];
				
				// Moi
				Joueur = in.readLine().split(";");
				// On remplit de le tableau contenant les autres joueurs
				while (JoueurDIXIT.pretJouer == false) {
					Joueurs = in.readLine().split(";");
					TABJoueurs.add(Joueurs);
					if (TABJoueurs.size() == ServeurDIXIT.NB_JOUEUR - 1) {
						JoueurDIXIT.pretJouer = true;
					}
				}
				
				// On initialise chaque variable
				indice = "";
				carteChoisie = 0;
				carteConteurPense = 0;
				conteur = new String[3];
				
				// On récupère le conteur
				conteur = in.readLine().split(";");			
				// Tour de jeu different si compteur ou non
				
				System.out.println(conteur[0]);
				//*************************************************DEBUT DU IF********************************************
				System.out.println(Joueur[0]);
				
				if (Joueur[1].equals("o")) {
					// Si je suis le CONTEUR
					//initialise VUE
					VuePartie panel = new VuePartie(fenetre);
					panel.setChoixMain("0");
					panel.initializeMain(main, CarteEnMoins);
					panel.initializeJoueurs(TABJoueurs, Joueur);
					JoueurDIXIT.changerPanel(fenetre, panel);
					panel.setSelectionPossibleMain(true);
					ThreadCompteur t = new ThreadCompteur();
					t.start();
					panel.showDialog("Vous êtes le conteur !");
					// On attend un evenement : La selection d'une carte*/
					while (panel.getChoixMain() == 0 && t.getCompteur() > 0) {
						try {
							sleep(1);
							if(t.getCompteur() > -1){
								panel.setCompteur(t.getCompteur());
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// On recupere la carte choisie et on l'envoie
					if(t.getCompteur() > 0){
						carteChoisie = panel.getChoixMain();
					}else{
						carteChoisie =-1;
						panel.showDialog("Temps ecoule");
					}
					
					panel.setSelectionPossibleMain(false);
					new PrintWriter(serveurDIXIT.getOutputStream(), true)
							.println(carteChoisie);
					// On recupere et on envoie l'indice
					if(carteChoisie != -1)
					{
						indice = panel.showDialogIndice();
						new PrintWriter(serveurDIXIT.getOutputStream(), true)
								.println(indice);
						// On recupere les cartes choisie par les joueurs.
						for (int nbCarte = 0; nbCarte < ServeurDIXIT.NB_JOUEUR; nbCarte++) {
							table.add(in.readLine());
						}
						// On met  à jour la vue avec les cartes sur la table
						panel.setChoixVote("0");
						panel.initializeJeuEnCours(table);
						fenetre.getContentPane().repaint();
						fenetre.getContentPane().revalidate();
					}

				} else {
					
					// MàJ Vue
					VuePartie panel = new VuePartie(fenetre);					
					panel.setChoixMain("0");
					panel.initializeMain(main, CarteEnMoins);
					panel.initializeJoueurs(TABJoueurs, Joueur);
					JoueurDIXIT.changerPanel(fenetre, panel);
					panel.showDialog("Le conteur est " + conteur[0]);
					// On attend de recevoir l'indice
					indice = in.readLine();
					if(indice.equals("annuler")){
						panel.showDialog("Le conteur a passer son tour");
					}else{
						ThreadCompteur t = new ThreadCompteur();
						t.start();
						panel.showDialog("L'indice du conteur est : " + indice);
						
						panel.setSelectionPossibleMain(true);
						//On attend l'evenement : choix d'une carte
						
						// On attend un evenement : La selection d'une carte*/
						while (panel.getChoixMain() == 0 && t.getCompteur() > 0) {
							try {
								sleep(1);
								if(t.getCompteur() > -1){
									panel.setCompteur(t.getCompteur());
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// On recupere la carte choisie et on l'envoie
						if(t.getCompteur() > 0){
							carteChoisie = panel.getChoixMain();
						}else{
							carteChoisie =-1;
							panel.showDialog("Temps ecoule");
						}
						panel.setSelectionPossibleMain(false);
						new PrintWriter(serveurDIXIT.getOutputStream(), true)
								.println(carteChoisie);
						// On attend les cartes de la table
						for (int nbCarte = 0; nbCarte < ServeurDIXIT.NB_JOUEUR; nbCarte++) {
							table.add(in.readLine());
						}
						// Mise à jour du panel
						panel.setChoixVote("0");
						panel.initializeJeuEnCours(table);
						fenetre.getContentPane().repaint();
						fenetre.getContentPane().revalidate();
						panel.setSelectionPossibleVote(true);
						// On attend l'evenement : Le joueur choisie une carte de la table
						ThreadCompteur t2 = new ThreadCompteur();
						t2.start();
						// On attend un evenement : La selection d'une carte*/
						while (panel.getChoixVote() == 0 && t2.getCompteur() > 0) {
							try {
								sleep(1);
								if(t2.getCompteur() > -1){
									panel.setCompteur(t2.getCompteur());
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// On recupere la carte choisie et on l'envoie
						if(t2.getCompteur() > 0){
							carteConteurPense = panel.getChoixVote();
						}else{
							carteConteurPense =-1;
							panel.showDialog("Temps ecoule");
						}
						panel.setSelectionPossibleVote(false);
						new PrintWriter(serveurDIXIT.getOutputStream(), true).println(carteConteurPense);
					}
				}
				
				//*************************************************FIN DU IF********************************************
				
				// Condition de fin de jeu a implementer AFFICHE NON
				finPartie = in.readLine();
				// On nettoie la table et le tableau des joueurs
				table.clear();
				TABJoueurs.clear();
				// On supprime la carte de la main
				main.remove("" + carteChoisie);
				JoueurDIXIT.pretJouer = false;
				// On attend la carte distribué
				System.out.println();
				if(finPartie.equals("non"))
				{
					main.add(in.readLine());
				}else if(finPartie.equals("pioche")){
					CarteEnMoins++;
				}
				System.out.println(finPartie);
				
				// Et on recommence !
			}
			Joueur = new String[3];
			//Les autres joueurs
			Joueurs = new String[3];
			Joueur = in.readLine().split(";");
			System.out.println(Joueur[0] + Joueur[2]);
			while (JoueurDIXIT.pretJouer == false) {
				Joueurs = in.readLine().split(";");
				TABJoueurs.add(Joueurs);
				System.out.println(Joueurs[0] + Joueurs[2]);
				if (TABJoueurs.size() == ServeurDIXIT.NB_JOUEUR - 1) {
					JoueurDIXIT.pretJouer = true;
				}
			}
			VueFinPartie panel2 = new VueFinPartie(fenetre, TABJoueurs, Joueur);
			JoueurDIXIT.changerPanel(fenetre, panel2);
			fenetre.getContentPane().repaint();
			fenetre.getContentPane().revalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
