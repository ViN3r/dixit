package VIEW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import JOUEUR.JoueurDIXIT;

public class VueInscription extends JPanel implements ActionListener{
	
	
	private JFrame fenetre;
	private JLabel login;
	private JLabel mdp;
	private JTextField loginText;
	private JPasswordField mdpText;
	private JButton valider;
	private JButton retour;
	private boolean ok;
	
	public VueInscription(JFrame fenetre){
		this.fenetre = fenetre;
	this.setLayout(new BorderLayout(250, 220));
	this.setBackground(new Color(222, 222, 222));

	JPanel panelCentre = new JPanel(new GridLayout(4, 2));
	JPanel panelGauche = new JPanel();
	JPanel panelDroite = new JPanel();
	JPanel panelHaut = new JPanel();
	JPanel panelBas = new JPanel();

	panelGauche.setSize(100, 400);
	panelDroite.setSize(100, 400);
	panelHaut.setSize(600, 200);
	panelBas.setSize(600, 200);

	panelGauche.setBackground(new Color(222, 222, 222));
	panelDroite.setBackground(new Color(222, 222, 222));

	login = new JLabel("LOGIN : ");
	mdp = new JLabel("MOT DE PASSE : ");
	loginText = new JTextField();
	mdpText = new JPasswordField();
	loginText.setColumns(10);
	mdpText.setColumns(10);
	valider = new JButton("VALIDER");
	retour = new JButton("RETOUR");

	panelCentre.add(new JLabel("INSCRIPTION :"));
	panelCentre.add(new JLabel());
	panelCentre.add(login);
	panelCentre.add(loginText);
	panelCentre.add(mdp);
	panelCentre.add(mdpText);
	panelCentre.add(retour);
	panelCentre.add(valider);

	this.add(panelGauche, BorderLayout.WEST);
	this.add(panelDroite, BorderLayout.EAST);
	this.add(panelHaut, BorderLayout.NORTH);
	this.add(panelBas, BorderLayout.SOUTH);
	this.add(panelCentre, BorderLayout.CENTER);
	this.setVisible(true);
	valider.addActionListener(this);
	retour.addActionListener(this);

	}
	
	
	public boolean ecrire(String path, StringBuffer stringFichier , String mdp , String login) 
	{	boolean ok;
		PrintWriter ecri ;
		try
		{	if((login ==  null )|| (mdp== null )){
			return false ;
		}
			stringFichier.append(login + "/" + mdp);
			ecri = new PrintWriter(new FileWriter(path));
			ecri.print(stringFichier);
			ecri.flush();
			ecri.close();
			return true;
		}
		catch (NullPointerException a)
		{
	
		}
		catch (IOException a)
		{
			
		}
		return false;
	}//ecrire
 
	public StringBuffer lire (String path) 
	{
		BufferedReader lect ;
		StringBuffer tmp = new StringBuffer();
		String tmp2;
		try
		{
			lect = new BufferedReader(new FileReader(path)) ;
			while ((tmp2 =lect.readLine()) != null) 
			{
				tmp.append(tmp2 + "\n");
			}
		}//try
		catch (NullPointerException a)
		{
			
		}
		catch (IOException a) 
		{
			
		}
		return tmp;
	}//lecture 
	
	
	public boolean Inscription(String login, char[] password) {
	
		StringBuffer text = new StringBuffer();
		StringBuffer mdp = new StringBuffer();
		for (int i = 0; i < password.length; i++) {
			mdp.append(password[i]);
		}
		text.append(lire("file.txt"));
		return ecrire("file.txt",text,mdp.toString(),login);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == valider) {
			if (Inscription(loginText.getText(), mdpText.getPassword())){
				fenetre.getContentPane().removeAll();
				VueIdentification vueIdentification = new VueIdentification(fenetre);
				fenetre.setContentPane(vueIdentification);
			
				fenetre.getContentPane().repaint();
				fenetre.getContentPane().revalidate();
				vueIdentification.setVisible(true);
			}
			else {
				fenetre.getContentPane().repaint();
			}
		}
			
			if (arg0.getSource() == retour){
				fenetre.getContentPane().removeAll();
				VueIdentification vueIdentification = new VueIdentification(fenetre);
				fenetre.setContentPane(vueIdentification);
			
				fenetre.getContentPane().repaint();
				fenetre.getContentPane().revalidate();
				vueIdentification.setVisible(true);
			
		}
		
	
		}
}
	
