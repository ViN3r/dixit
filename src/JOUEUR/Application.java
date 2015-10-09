package JOUEUR;

import java.net.UnknownHostException;

import javax.swing.JFrame;

import VIEW.VueIdentification;

class Application extends JFrame{
	  
    public Application(){
    	
    	this.setTitle("Application");
    	this.setSize(900, 600);
        // génération de la page d'identification
        VueIdentification f = new VueIdentification(this);
        this.setContentPane(f);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        this.setResizable(false);
        this.setVisible(true); 
    }

	public static void main (String[] args) throws InterruptedException{
	
		Application a = new Application();
	}
}