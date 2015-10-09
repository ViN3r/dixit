package VIEW;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import JOUEUR.JoueurDIXIT;
import JOUEUR.ServeurDIXIT;

public class VueAttente extends JPanel  {
	private JLabel labelNombreJoueur;
	private JFrame fenetre;
	private JoueurDIXIT j;
	public VueAttente(JFrame fenetre) throws IOException {
		
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
		 labelNombreJoueur = new JLabel("ATTENTE DES JOUEURS...... ");
		 panelCentre.add(labelNombreJoueur);
		 this.add(panelGauche, BorderLayout.WEST);
         this.add(panelDroite, BorderLayout.EAST);
         this.add(panelHaut, BorderLayout.NORTH);
         this.add(panelBas, BorderLayout.SOUTH);
         this.add(panelCentre, BorderLayout.CENTER);
		this.setVisible(true);
	}
	



	

}
