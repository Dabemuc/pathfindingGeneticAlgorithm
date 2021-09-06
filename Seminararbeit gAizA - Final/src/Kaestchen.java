import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * (c) A. Eckert GMG Amberg April2015
 **/
public abstract class Kaestchen extends javax.swing.JFrame {
	private int randX = 20;
	private int randY = 20;
	private int kaestchenBreite = 20;
	private int kaestchenHoehe = 20;
	private int kaestchenAnzX = 50;
	private int kaestchenAnzY = 50;
	private int nichtfuellbreite = 1;
	private Color strichfarbe = Color.black;
	private JPanel jPanel1;
	private int fensterhoehe = kaestchenAnzY * kaestchenHoehe + 2 * randY;
	private int fensterbreite = kaestchenAnzX * kaestchenBreite + 2 * randX;
	private int[][] matrix;
	private String[][] text;
	private Image[][] bild;
	private Hashtable<String, Image> bilder = new Hashtable<String, Image>();

	/**
	 * Standardkonstruktor, erzeugt 20x20 Kästchen der Breite 20 und Höhe 20
	 */
	public Kaestchen() {
		this(20, 20, 20, 20);
	}

	/**
	 * Konstruktor
	 * 
	 * @param kaeBreite
	 *            - Breite eines Kästchens
	 * @param kaeHoehe
	 *            - Höhe eines Kästchens
	 * @param kaeAnzX
	 *            - Anzahl der Kästchen horizontal
	 * @param kaeAnzY
	 *            - Anzahl der Kästchen vertikal
	 */
	public Kaestchen(int kaeBreite, int kaeHoehe, int kaeAnzX, int kaeAnzY) {
		this(kaeBreite, kaeHoehe, kaeAnzX, kaeAnzY, false);
	}

	/**
	 * Konstruktor
	 * 
	 * @param kaeBreite
	 *            - Breite eines Kästchens
	 * @param kaeHoehe
	 *            - Höhe eines Kästchens
	 * @param kaeAnzX
	 *            - Anzahl der Kästchen horizontal
	 * @param kaeAnzY
	 *            - Anzahl der Kästchen vertikal
	 * @param ohneRand
	 *            - true oder false
	 */
	public Kaestchen(int kaeBreite, int kaeHoehe, int kaeAnzX, int kaeAnzY, boolean ohneRand) {
		if (ohneRand == false) {
			strichfarbe = Color.black;
			nichtfuellbreite = 1;
		} else {
			strichfarbe = Color.white;
			nichtfuellbreite = 0;
			randX = 0;
			randY = 0;
		}
		this.kaestchenAnzX = kaeAnzX;
		this.kaestchenAnzY = kaeAnzY;
		this.kaestchenBreite = kaeBreite;
		this.kaestchenHoehe = kaeHoehe;
		fensterhoehe = kaestchenAnzY * kaestchenHoehe + 2 * randY;
		fensterbreite = kaestchenAnzX * kaestchenBreite + 2 * randX;
		matrix = new int[kaestchenAnzX][kaestchenAnzY];
		text = new String[kaestchenAnzX][kaestchenAnzY];
		bild = new Image[kaestchenAnzX][kaestchenAnzY];
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				String taste = KeyEvent.getKeyText(evt.getKeyCode());
				tasteClick(taste);
			}
		});
		jPanel1 = new JPanel() {
			private static final long serialVersionUID = 1651104240296058955L;

			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(strichfarbe);
				for (int x = 0; x <= kaestchenAnzX; x++) {
					g.drawLine(randX + x * kaestchenBreite, randY, randX + x * kaestchenBreite, fensterhoehe - randY);
				}
				for (int y = 0; y <= kaestchenAnzY; y++) {
					g.drawLine(randX, randY + y * kaestchenHoehe, fensterbreite - randX, randY + y * kaestchenHoehe);
				}
				for (int x = 0; x < kaestchenAnzX; x++) {
					for (int y = 0; y < kaestchenAnzY; y++) {
						switch (matrix[x][y]) {
						case 0:
							g.setColor(Color.white);
							break;
						case 1:
							g.setColor(Color.green);
							break;
						case 2:
							g.setColor(Color.red);
							break;
						case 3:
							g.setColor(Color.blue);
							break;
						case 4:
							g.setColor(Color.black);
							break;
						case 5:
							g.setColor(Color.gray);
							break;
						case 6:
							g.setColor(Color.lightGray);
							break;
						case 7:
							g.setColor(Color.cyan);
							break;
						case 8:
							g.setColor(Color.orange);
							break;
						case 9:
							g.setColor(Color.pink);
							break;
						case 10:
							g.setColor(Color.yellow);
							break;
						}
						g.fillRect(randX + x * kaestchenBreite + nichtfuellbreite, randY + y * kaestchenHoehe + nichtfuellbreite, kaestchenBreite - nichtfuellbreite,
								kaestchenHoehe - nichtfuellbreite);
					}
				}
				g.setColor(Color.black);
				for (int x = 0; x < kaestchenAnzX; x++) {
					for (int y = 0; y < kaestchenAnzY; y++) {
						if (text[x][y] != null) {
							int breite = g.getFontMetrics().stringWidth(text[x][y]);
							int hoehe = g.getFontMetrics().getAscent();
							g.drawString(text[x][y], (int) (randX + (x + 0.5) * kaestchenBreite - 0.5 * breite), (int) (randY + (y + 0.5) * kaestchenHoehe + 0.5 * hoehe));
						}
						if (bild[x][y] != null) {
							g.drawImage(bild[x][y], (int) (randX + x * kaestchenBreite), (int) (randY + y * kaestchenHoehe), kaestchenBreite, kaestchenHoehe, null);
						}
					}
				}
			}
		};
		getContentPane().add(jPanel1, BorderLayout.CENTER);
		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setPreferredSize(new java.awt.Dimension(1044, 1042));
		jPanel1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				int x = (evt.getX() - randX + kaestchenBreite) / kaestchenBreite;
				int y = (evt.getY() - randY + kaestchenHoehe) / kaestchenHoehe;
				if (x < 1 || x > kaestchenAnzX)
					x = -1;
				if (y < 1 || y > kaestchenAnzY)
					y = -1;
				mausClick(x, y);
				if (evt.getButton() == MouseEvent.BUTTON1)
					mausLeftClick(x, y);
				if (evt.getButton() == MouseEvent.BUTTON3)
					mausRightClick(x, y);
			}
		});
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setBackground(new java.awt.Color(255, 255, 255));
		setSize(fensterbreite + 25, fensterhoehe + 50);
		setVisible(true);
		repaint();
	}

	/**
	 * Achtung: zu überschreibende Methode, muss genau so übernommen werden:
	 * 
	 * public void mausClick(int x, int y) { ... }
	 * 
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 */
	public void mausClick(int x, int y) {
	}

	/**
	 * Achtung: zu überschreibende Methode, muss genau so übernommen werden:
	 * 
	 * public void mausClick(int x, int y) { ... }
	 * 
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 */
	public void mausLeftClick(int x, int y) {
	}

	/**
	 * Achtung: zu überschreibende Methode, muss genau so übernommen werden:
	 * 
	 * public void mausClick(int x, int y) { ... }
	 * 
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 */
	public void mausRightClick(int x, int y) {
	}

	/**
	 * Achtung: zu überschreibende Methode, muss genau so übernommen werden:
	 * 
	 * public void tasteClick(String taste) { ... }
	 * 
	 * @param taste
	 *            - gedrückte Taste als char
	 */
	public void tasteClick(String taste) {
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 * @param farbe
	 *            - weiß, grün, rot, blau, schwarz, grau, hellgrau, cyan,
	 *            orange, pink, gelb
	 */
	public void farbeSetzen(int x, int y, String farbe) {
		if (x < 1 || x > kaestchenAnzX)
			return;
		if (y < 1 || y > kaestchenAnzY)
			return;
		if (farbe == "weiß")
			matrix[x - 1][y - 1] = 0;
		else if (farbe == "grün")
			matrix[x - 1][y - 1] = 1;
		else if (farbe == "rot")
			matrix[x - 1][y - 1] = 2;
		else if (farbe == "blau")
			matrix[x - 1][y - 1] = 3;
		else if (farbe == "schwarz")
			matrix[x - 1][y - 1] = 4;
		else if (farbe == "grau")
			matrix[x - 1][y - 1] = 5;
		else if (farbe == "hellgrau")
			matrix[x - 1][y - 1] = 6;
		else if (farbe == "cyan")
			matrix[x - 1][y - 1] = 7;
		else if (farbe == "orange")
			matrix[x - 1][y - 1] = 8;
		else if (farbe == "pink")
			matrix[x - 1][y - 1] = 9;
		else if (farbe == "gelb")
			matrix[x - 1][y - 1] = 10;
		else
			return;
		repaint();
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 * @return
	 */
	public String farbeGeben(int x, int y) {
		if (x < 1 || x > kaestchenAnzX)
			return null;
		if (y < 1 || y > kaestchenAnzY)
			return null;
		if (matrix[x - 1][y - 1] == 0)
			return "weiß";
		else if (matrix[x - 1][y - 1] == 1)
			return "grün";
		else if (matrix[x - 1][y - 1] == 2)
			return "rot";
		else if (matrix[x - 1][y - 1] == 3)
			return "blau";
		else if (matrix[x - 1][y - 1] == 4)
			return "schwarz";
		else if (matrix[x - 1][y - 1] == 5)
			return "grau";
		else if (matrix[x - 1][y - 1] == 6)
			return "hellgrau";
		else if (matrix[x - 1][y - 1] == 7)
			return "cyan";
		else if (matrix[x - 1][y - 1] == 8)
			return "orange";
		else if (matrix[x - 1][y - 1] == 9)
			return "pink";
		else if (matrix[x - 1][y - 1] == 10)
			return "gelb";
		else
			return null;
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 */
	public void farbeLoeschen(int x, int y) {
		if (x < 1 || x > kaestchenAnzX)
			return;
		if (y < 1 || y > kaestchenAnzY)
			return;
		matrix[x - 1][y - 1] = 0;
		repaint();
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 * @param s
	 *            - Text
	 */
	public void textSetzen(int x, int y, String s) {
		if (x < 1 || x > kaestchenAnzX)
			return;
		if (y < 1 || y > kaestchenAnzY)
			return;
		text[x - 1][y - 1] = s;
		repaint();
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 * @return
	 */
	public String textGeben(int x, int y) {
		if (x < 1 || x > kaestchenAnzX)
			return null;
		if (y < 1 || y > kaestchenAnzY)
			return null;
		return text[x - 1][y - 1];
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 */
	public void textLoeschen(int x, int y) {
		if (x < 1 || x > kaestchenAnzX)
			return;
		if (y < 1 || y > kaestchenAnzY)
			return;
		text[x - 1][y - 1] = null;
		repaint();
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 * @param s
	 *            - dateiname
	 */
	public void bildSetzen(int x, int y, String s) {
		if (x < 1 || x > kaestchenAnzX)
			return;
		if (y < 1 || y > kaestchenAnzY)
			return;
		if (bilder.containsKey(s))
			bild[x - 1][y - 1] = bilder.get(s);
		else
			try {
				Image b = ImageIO.read(new File(s));
				bild[x - 1][y - 1] = b;
				bilder.put(s, b);
			} catch (IOException e) {
				meldung("Bildname unbekannt: " + s);
				System.exit(0);
			}
		repaint();
	}

	/**
	 * @param x
	 *            - von 1 bis Kästchenanzahl
	 * @param y
	 *            - von 1 bis Kästchenanzahl
	 */
	public void bildLoeschen(int x, int y) {
		if (x < 1 || x > kaestchenAnzX)
			return;
		if (y < 1 || y > kaestchenAnzY)
			return;
		bild[x - 1][y - 1] = null;
		repaint();
	}

	/**
	 * @param text
	 *            - Auszugebender Text
	 */
	public void meldung(String text) {
		JOptionPane.showMessageDialog(null, text);
	}

	public void sound(String filename) {
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filename));
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
