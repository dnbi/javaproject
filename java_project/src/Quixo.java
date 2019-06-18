import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Quixo extends JFrame implements ActionListener{
	public static final int FRAMEWIDTH = 644;
	public static final int FRAMEHEIGTH = 764;
	private JButton[][] block = null;
	private JButton[][] push = null; //0:up 1:down 2:right 3:left
	private JLabel gameEnd;
	private int myturn = 1;
	private int row, col;
	private boolean end = false;
	private String winp = null;
	
	public static void main(String[] args) {
		Quixo quixo = new Quixo();
		quixo.setVisible(true);
	}

	public Quixo() {
		setSize(FRAMEWIDTH, FRAMEHEIGTH);
		setTitle("Quixo Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel blockpanel = new JPanel();
		blockpanel.setLayout(new GridLayout(5, 5, 8, 8));
		blockpanel.setBackground(Color.WHITE);
		
		block = new JButton[5][5];
		for (int i=0; i<5; i++) {
			block[i] = new JButton[5];
			for (int j=0; j<5; j++) {
				block[i][j] = new JButton(" ");
				block[i][j].setBackground(Color.WHITE);
				block[i][j].setFont(new Font("SansSerif", Font.PLAIN, 50));
				block[i][j].setPreferredSize(new Dimension(100, 100));
				block[i][j].setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
				if (!(0<i && i<4 && 0<j && j<4)) {
					block[i][j].addActionListener(this);
					block[i][j].setBorder(new LineBorder(Color.ORANGE, 3, true));
				}
				blockpanel.add(block[i][j]);
			}
		}
		panel.add(blockpanel, BorderLayout.CENTER);
		
		JPanel[] pushpanel = new JPanel[4];
		push = new JButton[4][5];
		for (int i=0; i<4; i++) {
			pushpanel[i] = new JPanel();
			pushpanel[i].setBackground(Color.WHITE);
			if (i==0 || i==1) {
				pushpanel[i].setLayout(new FlowLayout());
			}
			else {
				pushpanel[i].setLayout(new GridLayout(5, 1, 8, 8));
			}
			push[i] = new JButton[5];
			for (int j=0; j<5; j++) {
				push[i][j] = new JButton();
				if (i==0) {
					push[i][j].setText("▲");
					push[i][j].setPreferredSize(new Dimension(105, 42));
					push[i][j].addActionListener(this);	
				}
				else if (i==1) {
					push[i][j].setText("▼");
					push[i][j].setPreferredSize(new Dimension(105, 42));
					push[i][j].addActionListener(this);
				}
				else if (i==2) {
					push[i][j].setText("▶");
					push[i][j].setPreferredSize(new Dimension(47, 100));
					push[i][j].addActionListener(this);
				}
				else if (i==3) {
					push[i][j].setText("◀");
					push[i][j].setPreferredSize(new Dimension(47, 100));
					push[i][j].addActionListener(this);
				}
				push[i][j].setBackground(Color.WHITE);
				push[i][j].setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
				push[i][j].setEnabled(false);
				pushpanel[i].add(push[i][j]);
			}
		}
		panel.add(pushpanel[0], BorderLayout.SOUTH);
		panel.add(pushpanel[1], BorderLayout.NORTH);
		panel.add(pushpanel[2], BorderLayout.WEST);
		panel.add(pushpanel[3], BorderLayout.EAST);
		
		add(panel, BorderLayout.NORTH);
		
		JPanel buttonpanel = new JPanel();
		buttonpanel.setBackground(Color.WHITE);
		
		JButton restart = new JButton("Restart");
		restart.addActionListener(this);
		restart.setPreferredSize(new Dimension(150, 40));
		restart.setFont(new Font("SansSerif", Font.PLAIN, 20));
		buttonpanel.add(restart);
		
		add(buttonpanel, BorderLayout.SOUTH);
		
		gameEnd = new JLabel();
		gameEnd.setText(" [Game Start] Turn - O ");
		gameEnd.setPreferredSize(new Dimension(600, 40));
		gameEnd.setFont(new Font("SansSerif", Font.PLAIN, 23));
		add(gameEnd, BorderLayout.CENTER);
	}
	
	public void move(String cmd, int row, int col) {
		String b = block[row][col].getText();
		if (cmd.equals("▲")) {
			for (int i=row; i<4; i++) {
				block[i][col].setText(block[i+1][col].getText());
			}
			block[4][col].setText(b);
		}
		else if (cmd.equals("▼")) {
			for (int i=row; i>0; i--) {
				block[i][col].setText(block[i-1][col].getText());
			}
			block[0][col].setText(b);
		}
		else if (cmd.equals("▶")) {
			for (int i=col; i>0; i--) {
				block[row][i].setText(block[row][i-1].getText());
			}
			block[row][0].setText(b);
		}
		else if (cmd.equals("◀")) {
			for (int i=col; i<4; i++) {
				block[row][i].setText(block[row][i+1].getText());
			}
			block[row][4].setText(b);
		}
	}
	
	public void selectblock() {
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				block[i][j].removeActionListener(this);
				block[i][j].setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
			}	
		}	
		if (myturn == 1) {
			for (int i=0; i<5; i++) {
				for (int j=0; j<5; j++) {
					if (block[i][j].getText().equals("O") || block[i][j].getText().equals(" ")) {
						if (!(0<i && i<4 && 0<j && j<4)) {
							block[i][j].addActionListener(this);
							block[i][j].setBorder(new LineBorder(Color.ORANGE, 3, true));
						}
					}
				}	
			}	
		}
		else {
			for (int i=0; i<5; i++) {
				for (int j=0; j<5; j++) {
					if (block[i][j].getText().equals("X") || block[i][j].getText().equals(" ")) {
						if (!(0<i && i<4 && 0<j && j<4)) {
							block[i][j].addActionListener(this);
							block[i][j].setBorder(new LineBorder(Color.ORANGE, 3, true));
						}
					}
				}	
			}	
		}
	}
	
	public void selectpush(int row, int col) {
		if (row==0) {
			push[0][col].setEnabled(true);
			if (col != 0) {
				push[2][row].setEnabled(true);
			}
			if (col != 4) {
				push[3][row].setEnabled(true);
			}
		}
		else if (row==4) {
			push[1][col].setEnabled(true);
			if (col != 0) {
				push[2][row].setEnabled(true);
			}
			if (col != 4) {
				push[3][row].setEnabled(true);
			}
		}
		else if (row!=0 && row!=4) {
			push[0][col].setEnabled(true);
			push[1][col].setEnabled(true);
			if (col != 0) {
				push[2][row].setEnabled(true);
			}
			else if (col != 4) {
				push[3][row].setEnabled(true);
			}
		}
		
		for (int i=0; i<4; i++) {
			for (int j=0; j<5; j++) {
				if (push[i][j].isEnabled()) {
					push[i][j].setBackground(Color.ORANGE);
				}
			}
		}
	}
	
	public void checkend() {
		String b = null;
		for (int i=0; i<5; i++) {
			end = true;
			for (int j=0; j<4; j++) {
				b = block[i][j].getText();
				if (b.equals(block[i][j+1].getText()) == false || b.equals(" ")){
					end = false;
					break;
				}	
			}
			if (end == true) {
				winp = block[i][0].getText();
				break;
			}
			
			end = true;
			for (int j=0; j<4; j++) {
				b = block[j][i].getText();
				if (b.equals(block[j+1][i].getText()) == false || b.equals(" ")){
					end = false;
					break;
				}		
			}	
			if (end == true) {
				winp = block[0][i].getText();
				break;
			}
			
			end = true;
			for (int j=4; j>0; j--) {
				b = block[4-j][j].getText();
				if (b.equals(block[4-j+1][j-1].getText()) == false || b.equals(" ")){
					end = false;
					break;
				}		
			}	
			if (end == true) {
				winp = block[0][4].getText();
				break;
			}
			
			end = true;
			for (int j=0; j<4; j++) {
				b = block[j][j].getText();
				if (b.equals(block[j+1][j+1].getText()) == false || b.equals(" ")){
					end = false;
					break;
				}		
			}	
			if (end == true) {
				winp = block[0][0].getText();
				break;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String click = e.getActionCommand();
		if (click.equals("Restart")) {
			this.dispose();
			Quixo q = new Quixo();
			q.setVisible(true);
		}
		else if (click.equals("O") || click.equals("X") || click.equals(" ")){
			for (int i=0; i<5; i++) {
				for (int j=0; j<5; j++) {
					block[i][j].removeActionListener(this);
					block[i][j].setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
				}	
			}	
			
			boolean find = false;
			for (int i=0; i<5; i++) {
				for (int j=0; j<5; j++) {
					if (block[i][j].getMousePosition() != null) {
						row = i;
						col = j;
						find = true;
						break;
					}
				}
				if (find == true) {
					break;
				}
			}
			
			if (click.equals(" ")) {
				if (myturn == 1) {
					block[row][col].setText("O");
				}
				else if (myturn == 0){
					block[row][col].setText("X");
				}
			}
			
			block[row][col].setBackground(Color.LIGHT_GRAY);
			block[row][col].setBorder(new LineBorder(Color.ORANGE, 3, true));
			
			selectpush(row, col);
		}
		else {
			for (int i=0; i<4; i++) {
				for (int j=0; j<5; j++) {
					push[i][j].setEnabled(false);
					push[i][j].setBackground(Color.WHITE);
				}	
			}
			
			move(click, row, col);
			
			block[row][col].setBackground(Color.WHITE);
			block[row][col].setBorder(new LineBorder(Color.LIGHT_GRAY, 3, true));
			
			checkend();
			if (!end) {
				myturn = (myturn+1)%2;
				if (myturn == 1) {
					gameEnd.setText(" Turn - O ");
				}
				else {
					gameEnd.setText(" Turn - X ");
				}
				selectblock();	
			}
			else {
				gameEnd.setText(" " + winp + " Turn > [Game End]");
				for (int i=0; i<5; i++) {
					for (int j=0; j<5; j++) {
						block[i][j].setEnabled(false);
					}	
				}
			}
		}
	}
	
}
