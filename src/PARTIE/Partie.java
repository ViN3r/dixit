package PARTIE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import JOUEUR.ServeurDIXIT;
import LOGS.Logs;

public class Partie extends Thread{

	private List<Joueur> listJoueur;
	private Logs fichierLogServeur,logPartie;
	private List<String> pioche,poubelle,selection;
	private HashMap<Joueur, String> table;
	static private int numPartie =0;
	private int mainRestant;

	static String nomPartie(){
		return "partie"+numPartie; 
	}
	public Partie(List<Joueur> listJoueur, List<String> pioche, Logs l) {
		// TODO Auto-generated constructor stub
		this.listJoueur = listJoueur;
		fichierLogServeur = l;
		fichierLogServeur.ecrireLog("La partie : '" + nomPartie() +"' a ete cree sur le serveur");
		logPartie = new Logs(nomPartie()+".txt");
		this.pioche = pioche;
		poubelle = new ArrayList<String>();
		table= new HashMap<Joueur,String>();
		numPartie++;
		mainRestant = ServeurDIXIT.NB_CARTE;
		selection = new ArrayList<String>();

	}

	private void melangerPioche() {
		String tmp;
		for (int i = 0; i < pioche.size(); i++) {
			int r = (int) random(0, pioche.size());
			tmp = pioche.get(i);
			pioche.set(i,pioche.get(r)) ;
			pioche.set(r,tmp) ;
		}
	}

	private void melangerTable(){
		String tmp;
		for (Joueur j : listJoueur) {
			int r = (int) random(0, table.size());
			tmp = table.get(j);
			table.put(j,pioche.get(r)) ;
			pioche.set(r,tmp) ;
		}
	}

	private static double random(double min, double max) {
		return min + Math.random() * (max - min);
	}

	public void distributionCarte(int nbCarteDistribue, PrintWriter pw, Joueur j){
		System.out.println(j);
		int i = 0;
		for(int cpt = 0; cpt < nbCarteDistribue; cpt++){					
			pw.println(pioche.get(i));
			poubelle.add(pioche.get(i));
			logPartie.ecrireLog(j.getPseudo() + " recoit la carte "+pioche.get(i) );
			pioche.remove(i);
		}
		logPartie.ecrireLog("cartes restante :" + pioche.size());
	}

	//Recherche du conteur
	public Joueur getConteur(){
		int i=0;
		while(i < listJoueur.size()){
			if(listJoueur.get(i).estConteur())
				break;
			i++;
		}
		if(i>(listJoueur.size() -1))
			i=0;
		return listJoueur.get(i);
	}

	//Changement du conteur
	public int changeConteur(int i){
		//s�l�ction du compteur suivant
		int conteur;
		conteur = (i+1)%listJoueur.size();
		listJoueur.get(i).changeConteur(false);
		listJoueur.get(conteur).changeConteur(true);
		System.out.println();
		return conteur;
	}

	public void envoiData(String contenu) throws IOException{
		PrintWriter pw;
		for(Joueur j : listJoueur){
			pw =new PrintWriter(j.getSocket().getOutputStream(), true);
			pw.println("oui");
		}
	}

	//Run d'une partie
	public void run(){
		Joueur jConteur;
		logPartie.ecrireLog("Partie debuter");
		PrintWriter pw;
		BufferedReader bw;
		melangerPioche();
		String carte;
		int voteCarteConteur;
		int tmp;
		for(Joueur j : listJoueur){
			try {
				pw =new PrintWriter(j.getSocket().getOutputStream(), true);
				distributionCarte(ServeurDIXIT.NB_CARTE,pw,j);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		logPartie.ecrireLog("distrution des cartes aux joueurs");
		int conteur=0;
		boolean partieFinie = false;

		//Boucle d'un tour
		while(!partieFinie){
			try{
				for(Joueur j : listJoueur){
					System.out.println("Je suis " + j);
					pw =new PrintWriter(j.getSocket().getOutputStream(), true);
					pw.println(j);
					for(Joueur j2 : listJoueur){
						if(!j2.equals(j)){
							System.out.println("Je recois "+ j2);
							pw.println(j2);
						}
					}
				}

				String indice = "";
				System.out.println("Debut partie");
				logPartie.ecrireLog("Debut du tour");
				jConteur = getConteur();
				logPartie.ecrireLog(jConteur+ " est le conteur");

				//Envoie du nouveau conteur au joueur
				for(Joueur j : listJoueur){
					pw =new PrintWriter(j.getSocket().getOutputStream(), true);
					pw.println(jConteur);
				}

				//Demande de carte conteur
				//reception carte compteur
				bw = new BufferedReader(new InputStreamReader(jConteur.getSocket().getInputStream()));

				//Lecture de la carte
				carte =bw.readLine();
				table.put(jConteur, carte);
				selection.add(carte);
				logPartie.ecrireLog("le conteur " + jConteur.getPseudo() + " a choisi la carte "+ table.get(jConteur));

				if (!carte.equals("-1")) {
					//Lecture de l'indice
					indice = bw.readLine();
					//j.getInputStream().read();

					//ENVOIE L'INDICE DU CONTEUR
					for(Joueur j : listJoueur){
						if(!j.equals(jConteur)){
							pw =new PrintWriter(j.getSocket().getOutputStream(), true);
							pw.println(indice);
						}
					}

					for(Joueur j : listJoueur){
						if(!j.equals(jConteur)){
							//Lecture des cartes des autres joueurs
							bw = new BufferedReader(new InputStreamReader(j.getSocket().getInputStream()));
							carte =bw.readLine();
							if(!carte.equals("-1")){
								selection.add(carte);
								table.put(j, carte);
							}else{
								selection.add("dos");
								table.put(j, "dos");
							}
							logPartie.ecrireLog("le joueur " + j.getPseudo() + " a choisi la carte "+ carte);
							//j.getInputStream().read();}
						}
					}

					//Envoie des cartes pose a tous les joueurs
					Collections.shuffle(selection);
					for(Joueur j : listJoueur) {			
						pw =new PrintWriter(j.getSocket().getOutputStream(), true);
						/*for(Joueur g : listJoueur){
							if(table.containsKey(g)){
								pw.println(table.get(g));
								System.out.println("La carte "+ table.get(g) +" est envoye");
							}else{
								pw.println("dos");
							}
						}*/
						for(String s : selection){
							System.out.println("La carte "+ s +" est envoye a tous les joueur");
							pw.println(s);
						}
					}
					selection.clear();

					voteCarteConteur = 0;
					//R�cup�ration des choix des joueurs
					for(Joueur j : listJoueur) {
						if(!j.equals(jConteur)){
							System.out.println("recuperation des votes des joueurs");
							//Lecture des cartes des autres joueurs
							bw = new BufferedReader(new InputStreamReader(j.getSocket().getInputStream()));
							//j.getInputStream().read();
							carte = bw.readLine();
							logPartie.ecrireLog("le joueur " + j.getPseudo() + "a vote pour la carte "+ carte);
							selection.add(carte);
							if(carte.equals(table.get(jConteur))){
								voteCarteConteur += 1;
							}
						}
					}



					//Compte des points
					//Cas Aucun/Tous les joueurs ont trouvés la carte Conteur
					if(voteCarteConteur == 0 || voteCarteConteur == listJoueur.size()-1){
						logPartie.ecrireLog("Tous les joueurs ont trouve la carte du conteur");
						for(Joueur j : listJoueur) {
							if(!j.equals(jConteur)){
								logPartie.ecrireLog( j.getPseudo()+" gagne 2 pts" );
								j.setScore(2);
							}
						}
						//autres cas
					}else if(voteCarteConteur > 0 && voteCarteConteur < listJoueur.size()-1){
						logPartie.ecrireLog("Le conteur a reussi  tromper au moins 1 joueur");
						for(int i = 0 ; i< listJoueur.size(); i++) {
							//Autre joueurs
							if(i == listJoueur.size()-1){
								tmp = i-1;
							}else{
								tmp = i;
							}
							//vote carte conteur
							if(!listJoueur.get(i).equals(jConteur)){
								//Erreur a resoudre
								if(selection.get(tmp).equals(table.get(jConteur))){
									System.out.println( listJoueur.get(tmp).getPseudo()+" gagne 3 pts car il a choisi la carte du conteur" );
									listJoueur.get(tmp).setScore(3);
								}
								//vote autre carte
								else{
									for(Joueur j : table.keySet()){
										if(table.get(j).equals(selection.get(tmp))){
											logPartie.ecrireLog( listJoueur.get(i).getPseudo()+" gagne 1 pts car un joueur s'est trompe et a choisi sa carte" );
											j.setScore(1);
										}
									}
								}
							}
						}
						logPartie.ecrireLog("Le conteur" + jConteur.getPseudo()+" gagne 3 pts" );
						jConteur.setScore(3);
					}

				}else {
					for (Joueur j : listJoueur) {
						if (!j.equals(jConteur)) {
							pw = new PrintWriter(j.getSocket().getOutputStream(), true);
							pw.println("annuler");
						}
					}
					logPartie.ecrireLog(jConteur + " passe son tour");
				}

				selection.clear();
				table.clear();
				logPartie.ecrireLog("On vide la table");
				//Pioche vide
				if (pioche.size() == 0){
					//fin de partie
					if(mainRestant > 1){
						mainRestant --;
						conteur=changeConteur(conteur);
						for(Joueur j : listJoueur){
							pw =new PrintWriter(j.getSocket().getOutputStream(), true);
							pw.println("pioche");
						}
						logPartie.ecrireLog("fin du tour");
					}else{
						//Defausse de la main des joueurs
						for(Joueur j : listJoueur){
							pw =new PrintWriter(j.getSocket().getOutputStream(), true);
							pw.println("oui");
						}
						for(Joueur j : listJoueur){
							System.out.println("Je suis " + j);
							pw =new PrintWriter(j.getSocket().getOutputStream(), true);
							pw.println(j);
							for(Joueur j2 : listJoueur){
								if(!j2.equals(j)){
									System.out.println("Je recois "+ j2);
									pw.println(j2);
								}
							}
						}
						logPartie.ecrireLog("fin de partie");
						partieFinie=true;
					}
				}else{
					logPartie.ecrireLog("fin du tour");
					logPartie.ecrireLog("le serveur distribue les cartes");
					for(Joueur j : listJoueur){
						logPartie.ecrireLog("Le joueur" + j.getPseudo() + " recoit sa carte");
						pw =new PrintWriter(j.getSocket().getOutputStream(), true);
						pw.println("non");
						distributionCarte(1,pw,j);
					}
					conteur=changeConteur(conteur);
				}
			}catch(IOException e){
				fichierLogServeur.ecrireLog("Connexion avec un des clients perdu");
				//Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}

		//fin boucle

		//vainqueur
	}
}
