package JOUEUR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class ThreadCompteur extends Thread{
	
	private int compteur;

	
	public ThreadCompteur(){
		compteur = 30;
	}
	
	public void run(){
		 try {
			while(compteur > -1){
				sleep(1000);
				compteur--;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public int getCompteur(){
		return this.compteur;
	}
}
