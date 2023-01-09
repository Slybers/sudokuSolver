package sudoku;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Sudoku {
	private static boolean verifica(char[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.') {
					for (int k = 0; k < 9; k++) {
						if (board[i][k] == board[i][j] && !(k == j)) {
							return false;
						}
					}
					for (int k = 0; k < 9; k++) {
						if (board[k][j] == board[i][j] && !(k == i)) {
							return false;
						}
					}
					int qi = -1, qj = -1;

					if (i >= 0 && i <= 2) {
						qi = 0;
					}
					if (i >= 3 && i <= 5) {
						qi = 3;
					}
					if (i >= 6 && i <= 8) {
						qi = 6;
					}
					if (j >= 0 && j <= 2) {
						qj = 0;
					}
					if (j >= 3 && j <= 5) {
						qj = 3;
					}
					if (j >= 6 && j <= 8) {
						qj = 6;
					}

					for (int qx = qi; qx < qi + 3; qx++) {
						for (int qy = qj; qy < qj + 3; qy++) {
							if (board[qx][qy] == board[i][j] && !(qx == i && qy == j)) {
								return false;
							}

						}
					}

				}
			}

		}
		return true;
	}

	private boolean canAdd(int x, Soluzione s) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (s.board[i][j] == '.') {
					for (int k = 0; k < 9; k++) {
						if (s.board[i][k] == (char) (x + '0')) {
							return false;
						}
					}
					for (int k = 0; k < 9; k++) {
						if (s.board[k][j] == (char) (x + '0')) {
							return false;
						}
					}
					int qi = -1, qj = -1;

					if (i >= 0 && i <= 2) {
						qi = 0;
					}
					if (i >= 3 && i <= 5) {
						qi = 3;
					}
					if (i >= 6 && i <= 8) {
						qi = 6;
					}
					if (j >= 0 && j <= 2) {
						qj = 0;
					}
					if (j >= 3 && j <= 5) {
						qj = 3;
					}
					if (j >= 6 && j <= 8) {
						qj = 6;
					}

					for (int qx = qi; qx < qi + 3; qx++) {
						for (int qy = qj; qy < qj + 3; qy++) {
							if (s.board[qx][qy] == (char) (x + '0')) {
								return false;
							}

						}
					}

					return true;
				}
			}

		}
		return false;
	}

	private boolean isComplete(Soluzione s) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (s.board[i][j] == '.') {
					return false;
				}
			}
		}
		return true;
	}

	private void add(int x, Soluzione s, Indice indice) {
		char a = (char) (x + '0');
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (s.board[i][j] == '.') {
					s.board[i][j] = a;
					indice.cx = i;
					indice.cy = j;
					return;
				}
			}
		}
	}

	private void remove(Soluzione s, Indice indice) {
		s.board[indice.cx][indice.cy] = '.';
	}

	public boolean solve(Soluzione s) {
		int x = 1;
		Indice indice = new Indice();
		while (x <= 9) {
			if (canAdd(x, s)) {
				add(x, s, indice);
				if (isComplete(s))
					return true;
				else if (solve(s))
					return true;
				remove(s, indice);
				x++;
			} else
				x++;
		}
		return false;
	}

	public static void risolutore(ArrayList<JTextField> campi) {
		Sudoku sudoku = new Sudoku();

		char[][] board = new char[9][9];
		int k = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (campi.get(k).getText().toString().length() == 0) {
					board[i][j] = '.';
				} else {
					board[i][j] = campi.get(k).getText().toString().charAt(0);
				}
				k++;
			}
		}

		Soluzione s = new Soluzione(board);
		if (!verifica(board)) {
			JOptionPane.showMessageDialog(null, "Inizializzazione errata.", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (sudoku.solve(s)) {
			k = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					campi.get(k++).setText(((Character) board[i][j]).toString());
				}

			}

		} else {
			JOptionPane.showMessageDialog(null, "Nessuna soluzione", "Errore", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void main(String[] args) {
		ImageIcon sfondo = new ImageIcon(new Sudoku().getClass().getResource(File.separator+"Sfondo.png"));
		JFrame f = new JFrame("Sudoku solver");
		f.setResizable(false);
		f.setSize(910, 1000);
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(900, 900));
		final JPanel glass = (JPanel) f.getGlassPane();
		glass.setVisible(true);
		glass.add(new JLabel(sfondo));
		f.add(p);
		GridLayout grid = new GridLayout(9, 9, 0, 0);
		p.setLayout(grid);
		ArrayList<JTextField> campi = new ArrayList<JTextField>();
		for (int i = 0; i < 81; i++) {
			campi.add(new JTextField(" "));
			p.add(campi.get(i));
			campi.get(i).setHorizontalAlignment(SwingConstants.CENTER);
			campi.get(i).setDocument(new JTextFieldLimit(1));
			campi.get(i).setFont(new Font("Comic Sans MS", 1, 40));
			campi.get(i).getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
						try {
							Robot robot = new Robot();
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);
						} catch (AWTException e1) {
							e1.printStackTrace();
						}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
				}
			});
		}
		JButton bottone = new JButton("Risolvi");
		JButton bottone1 = new JButton("Resetta");
		bottone.setPreferredSize(new Dimension(100, 50));
		bottone1.setPreferredSize(new Dimension(100, 50));
		bottone1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				for (int i = campi.size()-1; i >= 0; i--) {
					campi.get(i).setText(" ");
				}
				Thread t = new Thread(new ResetHandler(campi.get(0)));
				t.start();
			}
		});
		bottone.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				risolutore(campi);
			}
		});
		JPanel p1 = new JPanel();
		GridLayout grid2 = new GridLayout(1, 3, 10, 0);
		p1.setLayout(grid2);
		JButton spazio = new JButton();
		spazio.setVisible(false);
		p1.add(spazio);
		p1.add(bottone);
		p1.add(bottone1);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p, p1);
		splitPane.setDividerLocation(909);
		splitPane.setDividerSize(0);
		f.add(splitPane);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
