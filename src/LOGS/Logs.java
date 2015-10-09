package LOGS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logs {
	private File fichier;
	private FileWriter fos;
	private FileReader fr;
	private BufferedWriter bw;
	private BufferedReader br;
	
	private boolean estLibre;
	
	 Date date;
	static DateFormat dateFormat;
	static{
		//date = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
	}

	public Logs(String nom){
		fichier = new File(nom);
		estLibre = true;
		if(!fichier.exists()){
			try {
				fichier.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fr = new FileReader(fichier);
			fos = new FileWriter(fichier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ecrireLog(String contenu){

		synchronized (this) {
			while(!getStatus()){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changeStatus(false);
			try {
				date = new Date();
				StringBuffer s = lireLog();
				System.out.println("String buffer est :" + s);
				bw = new BufferedWriter(fos);
				System.out.println(dateFormat.format(date)+" > "+contenu);
				bw.write(s.toString());
				bw.write(dateFormat.format(date)+" > "+contenu+"\n");
				//bw.close();
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changeStatus(true);
			this.notifyAll();
		}
		
		
	}

	public StringBuffer lireLog(){
		
		StringBuffer contenu= new StringBuffer();
		String s;
		br = new BufferedReader(fr);
		try {
			s= br.readLine();
			while((s= br.readLine()) != null){
				contenu.append(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contenu;
	}
	
	public void changeStatus(boolean b){
		estLibre = b;
	}
	
	public boolean getStatus(){
		return estLibre;
	}
}
