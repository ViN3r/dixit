package VIEW;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.BoxLayout;

import java.awt.Dialog;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import JOUEUR.ServeurDIXIT;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VuePartie extends JPanel{
	int nbJoueurs = 6;
	JPanel panel_joueurs;
	JPanel panel_main;
	JPanel panel_JeuEnCours;
	int choix_vote;
	int choix_main;
	String select_vote;
	String select_main;
	String ancienneValSelect = "0";
	String ancienneValSelectJeu = "0";
	private JLabel Carte_main_Ancien;
	private JLabel lblImg_Ancien;
	private ImageIcon resized_lblImg_Ancien;
	private ImageIcon rs_img_Ancien;
	private JFrame fenetre;
	private JLabel lblNomJoeur;
	private JLabel lblPointsJoueur;
	boolean selection_possible_main = false;
	boolean selection_possible_vote = false;
	JButton button_choix;
	JButton button_vote;
	
	//Benjamin
	private JLabel lblCompteur;

	/**
	 * Create the application.
	 */
	public VuePartie(JFrame fenetre) {
		initialize(fenetre);
	}
	
	public void setSelectionPossibleMain(boolean selection_possible){
		this.selection_possible_main= selection_possible;
	}
	public void setSelectionPossibleVote(boolean selection_possible){
		this.selection_possible_vote= selection_possible;
	}
	
	public void setChoixVote(String id_carte){
			this.choix_vote = Integer.parseInt(id_carte);
	}
	
	public int getChoixVote(){
		return(this.choix_vote);	
	}
	
	public void setChoixMain(String id_carte){
			this.choix_main = Integer.parseInt(id_carte);
	}
	
	public int getChoixMain(){
		return this.choix_main;
	}

	public void setSelectVote(String id_carte){
		if(id_carte == "dos"){}else{
			this.select_vote = id_carte;
		}
	}
	
	public String getSelectMain(){
		return this.select_main;
	}
	
	public String getSelectVote(){
		return this.select_vote;
		
	}
	
	public void setSelectMain(String id_carte){
		if(id_carte == "dos"){}else{
			this.select_main = id_carte;
		}
	}
	public void setCompteur(int val){
		lblCompteur.setText(""+val);
	}
	public String getCompteur(){
		return lblCompteur.getText();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame frame) {
		fenetre = frame;
		fenetre.setBounds(100, 100, 780, 480);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		JPanel panel_joueurs = new JPanel();
		panel_joueurs.setBounds(0, 21, 750, 30);
		this.add(panel_joueurs);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_joueurs.setLayout(gbl_panel);
		lblCompteur = new JLabel("");
		/*JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 730, 21);
		frame.getContentPane().add(menuBar);*/
		
		this.panel_joueurs = panel_joueurs;
		
		JPanel panel_main = new JPanel();
		panel_main.setBounds(178, 247, 500, 110);
		this.add(panel_main);
		GridBagLayout gbl_panel_main = new GridBagLayout();
		gbl_panel_main.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_main.rowHeights = new int[]{0, 0};
		gbl_panel_main.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_main.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_main.setLayout(gbl_panel_main);
		
		this.panel_main = panel_main;
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 247, 120, 90);
		this.add(panel_2);
		panel_2.setLayout(null);
		
		lblNomJoeur = new JLabel("Nom Joueur");
		lblNomJoeur.setBounds(10, 11, 88, 14);
		panel_2.add(lblNomJoeur);
		
		lblPointsJoueur = new JLabel("Points");
		lblPointsJoueur.setBounds(30, 36, 46, 14);
		panel_2.add(lblPointsJoueur);
		
		JPanel panel_enCours = new JPanel();
		panel_enCours.setBounds(0, 50, 760, 198);
		this.add(panel_enCours);
		GridBagLayout gbl_panel_enCours = new GridBagLayout();
		gbl_panel_enCours.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_enCours.rowHeights = new int[]{0, 0};
		gbl_panel_enCours.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_enCours.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_enCours.setLayout(gbl_panel_enCours);
		
		this.panel_JeuEnCours = panel_enCours;
		
	}
	
	private void initializeJoueurInfos(){}
	
	public void initializeJoueurs(List<String[]> TABJoueurs, String[] joueur){
		JPanel wrapping_panel = this.panel_joueurs;
			
		for(int i=0; i < ServeurDIXIT.NB_JOUEUR-1; i++){
			JPanel panel_Joueurs = new JPanel();
			GridBagConstraints gbc_panel_Joueurs = new GridBagConstraints();
			gbc_panel_Joueurs.insets = new Insets(0, 0, 0, 5);
			gbc_panel_Joueurs.fill = GridBagConstraints.BOTH;
			gbc_panel_Joueurs.gridx = i;
			gbc_panel_Joueurs.gridy = 0;
			wrapping_panel.add(panel_Joueurs, gbc_panel_Joueurs);
			//JLabel lblJoueur = new JLabel(TABJoueurs.get(i)[0] + " " + TABJoueurs.get(i)[1] + " " + TABJoueurs.get(i)[2]);
			
			BufferedImage img = null;
			try {
				if(TABJoueurs.get(i)[1].equals("o")){
			    img = ImageIO.read(new File("./images/partie/conteur.png"));
				}else{
					img = ImageIO.read(new File("./images/partie/joueur.png"));
				}
			} catch (IOException e) {
				System.out.println("Erreur image");
			}
			ImageIcon lblImg = new ImageIcon(img);
			
			Image img_stock = lblImg.getImage();  
			Image newimg = img_stock.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
			ImageIcon resized_lblImg = new ImageIcon(newimg); 
			
			JLabel typeJoueur = new JLabel(resized_lblImg);
			
			JLabel lblJoueur = new JLabel(TABJoueurs.get(i)[0]+" | ");
			JLabel lblPoints = new JLabel(""+TABJoueurs.get(i)[2]+"");
			
			final JButton button_choix = new JButton("Selectionner cette carte.");
			button_choix.setVisible(false);
			
			button_choix.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String s = getSelectMain();
					setChoixMain(s);
					button_choix.setVisible(false);
				}
			});
			this.button_choix = button_choix;
			
			final JButton button_vote = new JButton("Voter pour cette carte.");
			button_vote.setVisible(false);
			button_vote.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String s = getSelectVote();
					setChoixVote(s);
					button_vote.setVisible(false);
				}
			});
			this.button_vote = button_vote;
			
			
			
			panel_Joueurs.add(typeJoueur);
			panel_Joueurs.add(lblJoueur);
			panel_Joueurs.add(lblPoints);
			
			panel_Joueurs.add(button_choix);
			panel_Joueurs.add(button_vote);
			panel_Joueurs.add(lblCompteur);
			
			
			lblNomJoeur.setText(joueur[0]);
			lblPointsJoueur.setText(joueur[2]);
		
		}
	}
	
	public void initializeJeuEnCours(ArrayList <String> table){
		JPanel panel_enCours = this.panel_JeuEnCours;
		
for(int i=0; i < ServeurDIXIT.NB_JOUEUR; i++){
			
			JPanel panel_cartes = new JPanel();
			GridBagConstraints gbc_panel_cartes = new GridBagConstraints();
			gbc_panel_cartes.insets = new Insets(0, 0, 0, 5);
			gbc_panel_cartes.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel_cartes.gridx = i;
			gbc_panel_cartes.gridy = 0;
			
			//affichage des cartes du jeu en cours
			BufferedImage img = null;
			System.out.println(table.get(i) + "TEST");
			final String imgID = ""+table.get(i)+"";
			try {
			    img = ImageIO.read(new File("./images/cartes/"+imgID+".png"));
			} catch (IOException e) {
				System.out.println("Erreur image");
			}
			final ImageIcon rs_img = new ImageIcon(img);
			
			final JLabel lblImg = new JLabel(rs_img);
			
			
			//Ajout d'un event sur le click d'une carte
			lblImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					if(selection_possible_vote == true){
					
					
					BufferedImage imglayer = null;
					BufferedImage imgCarte = null;
					try {
					    imgCarte = ImageIO.read(new File("./images/cartes/"+imgID+".png"));
					} catch (IOException e) {
						System.out.println("Erreur image");
					}
					
					try {
					    imglayer = ImageIO.read(new File("./images/partie/layer.png"));
					} catch (IOException e) {
						System.out.println("Erreur image");
					}
					
					Graphics2D g2d = imgCarte.createGraphics();
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
					 
					g2d.drawImage(imglayer, 0, 0, null);
					 
					g2d.dispose();
					//ImageIcon rs_img = new ImageIcon(imgCarte);
					
					  
					rs_img.setImage(imgCarte);
					lblImg.repaint();
					lblImg.revalidate();
					
					setSelectVote(imgID);
					button_vote.setVisible(true);
					
					if(ancienneValSelectJeu == "0"){
						ancienneValSelectJeu = imgID;
						lblImg_Ancien = lblImg;
						rs_img_Ancien = rs_img;
					}
					else if(ancienneValSelectJeu == imgID){
					}
					else{
						BufferedImage imgAncien = null;
						try {
						    imgAncien = ImageIO.read(new File("./images/cartes/"+ancienneValSelectJeu+".png"));
						} catch (IOException e) {
							System.out.println("Erreur image");
						}
						//ImageIcon lblImgAncien = new ImageIcon(imgAncien);  
						
						rs_img_Ancien.setImage(imgAncien);
						lblImg_Ancien.repaint();
						lblImg_Ancien.revalidate();
						ancienneValSelectJeu = imgID;
						setSelectVote(imgID);
						button_vote.setVisible(true);
	
					lblImg_Ancien = lblImg;
					rs_img_Ancien = rs_img;

					}
					//setVisibleEnvoieButton(true);
				}
				}

				
			});
			panel_cartes.add(lblImg);
			
			
			
			panel_enCours.add(panel_cartes, gbc_panel_cartes);
		
		}
	}//fin initializeJeuEnCours
	
	public void initializeMain(ArrayList <String> main, int carteEnMoins){
		
		for(int i=0; i< ServeurDIXIT.NB_CARTE - carteEnMoins; i++){	
		
		final JPanel panel_carte_main = new JPanel();
		final GridBagConstraints gbc_panel_carte_main = new GridBagConstraints();
		gbc_panel_carte_main.insets = new Insets(0, 0, 0, 5);
		gbc_panel_carte_main.fill = GridBagConstraints.BOTH;
		gbc_panel_carte_main.gridx = i;
		gbc_panel_carte_main.gridy = 0;
		
		BufferedImage img = null;
		final String imgID = ""+main.get(i)+"";
		try {
		    img = ImageIO.read(new File("./images/cartes/"+imgID+".png"));
		} catch (IOException e) {
			System.out.println("Erreur image");
		}
		ImageIcon lblImg = new ImageIcon(img);
		
		Image img_stock = lblImg.getImage();  
		Image newimg = img_stock.getScaledInstance(70, 100,  java.awt.Image.SCALE_SMOOTH);  
		final ImageIcon resized_lblImg = new ImageIcon(newimg); 
		
		final JLabel Carte_main = new JLabel(resized_lblImg);
		
		Carte_main.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(selection_possible_main == true){
					
					
					BufferedImage imglayer = null;
					BufferedImage imgCarte = null;
					try {
					    imgCarte = ImageIO.read(new File("./images/cartes/"+imgID+".png"));
					} catch (IOException e) {
						System.out.println("Erreur image");
					}
					
					try {
					    imglayer = ImageIO.read(new File("./images/partie/layer.png"));
					} catch (IOException e) {
						System.out.println("Erreur image");
					}
					
					Graphics2D g2d = imgCarte.createGraphics();
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
					 
					g2d.drawImage(imglayer, 0, 0, null);
					 
					g2d.dispose();
					ImageIcon lblImg = new ImageIcon(imgCarte);
					
					
					Image img_stock = lblImg.getImage();  
					Image newimg = img_stock.getScaledInstance(70, 100,  java.awt.Image.SCALE_SMOOTH);  
					resized_lblImg.setImage(newimg);
					Carte_main.repaint();
					Carte_main.revalidate();
					//Carte_main.setVisible(false);
					//setCarteChoisie(imgID);
					
					setSelectMain(imgID);
					button_choix.setVisible(true);
					
					if(ancienneValSelect == "0"){
						ancienneValSelect = imgID;
						Carte_main_Ancien = Carte_main;
						resized_lblImg_Ancien = resized_lblImg;
					}
					else if(ancienneValSelect == imgID){
					}
					else{
						BufferedImage imgAncien = null;
						try {
						    imgAncien = ImageIO.read(new File("./images/cartes/"+ancienneValSelect+".png"));
						} catch (IOException e) {
							System.out.println("Erreur image");
						}
						ImageIcon lblImgAncien = new ImageIcon(imgAncien);
						
						Image img_stockAncien = lblImgAncien.getImage();  
						Image newimgAncien = img_stockAncien.getScaledInstance(70, 100,  java.awt.Image.SCALE_SMOOTH);  
						
						resized_lblImg_Ancien.setImage(newimgAncien);
						Carte_main_Ancien.repaint();
						Carte_main_Ancien.revalidate();
						setSelectMain(imgID);
						button_choix.setVisible(true);
						
						
						ancienneValSelect = imgID;
						Carte_main_Ancien = Carte_main;
						resized_lblImg_Ancien = resized_lblImg;

					}
					//setVisibleEnvoieButton(true);
				}
			}
		});
		
		panel_carte_main.add(Carte_main);
		
		panel_main.add(panel_carte_main, gbc_panel_carte_main);
		
		}
	}//fin initializeMain

	public void showDialog(String s){
		JOptionPane.showMessageDialog(fenetre, s );
	}
	
	public String showDialogIndice(){
		String s = (String)JOptionPane.showInputDialog( null, "Entrez l'indice concernant cette carte :", "Dialog", JOptionPane.PLAIN_MESSAGE);
		System.out.println("Vous avez tappé " + s);
		return s;
	}

}

