package VIEW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import JOUEUR.*;


public class VueFinPartie extends JPanel{

	private JFrame fenetre;
	private JLabel login;
	private JLabel mdp;
	private String[] gagnant;
	private List <String[]> TABJoueur;

	public VueFinPartie(JFrame fenetre, List<String[]> TABJoueurs, String[] Joueur) {     

		this.fenetre = fenetre;
		this.TABJoueur = TABJoueurs;
		this.setLayout(new BorderLayout(250, 220));
		this.setBackground(new Color(222, 222, 222));


		for(int i = 0; i < TABJoueur.size(); i++ ){
			this.showDialog(""+TABJoueur.get(i)[0] + " : " + TABJoueur.get(i)[2]);
		}
		this.showDialog(Joueur[0] + " : " + Joueur[2]);
	}
	
	public void showDialog(String s){
		JOptionPane.showMessageDialog(fenetre, s );
	}
}
