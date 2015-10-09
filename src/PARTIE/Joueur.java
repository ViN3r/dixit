package PARTIE;

import java.net.Socket;

public class Joueur {
	private String nom;
	private boolean conteur;
	private int score;
	private Socket socket;
	
	public Joueur(String nom,Socket s){
		this(nom,s,false);

	}
	
	public Joueur(String pseudo, Socket s, boolean b) {
		// TODO Auto-generated constructor stub
		nom = pseudo;
		conteur = b;
		score = 0;
		socket = s;
	}
	
	public void setScore(int valeur){
		score += valeur;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public String stringConteur(){
		if(conteur){
			return "o";
		}
		return "n";
	}
	
	public boolean estConteur(){
		return conteur;
	}
	
	public void changeConteur(boolean b){
		conteur = b;
	}
	public String toString(){
		return nom +";"+stringConteur()+";"+score;
	}

	public String getPseudo() {
		// TODO Auto-generated method stub
		return nom;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Joueur){
			Joueur j = (Joueur)obj;
			return j.getPseudo().equals(this.getPseudo());
		}
		return false;
	}
	
}
