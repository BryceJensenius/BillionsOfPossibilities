package theGoodCode;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowLauncher {
	
	JFrame frame = new JFrame();
	JLabel label = new JLabel("This Text!");
	
	JPanel panel;;
	
	
	public WindowLauncher(Player[] ps, int width, int height) {
		int x = 100;
		int y = 100;
		panel = new CustomPanel(width + 150, height, ps);
		panel.setSize(width + 190,height);
		
		label.setBounds(0,0,100,50);//x, y, width, height
		label.setFont(new Font(null, Font.BOLD, 30));//something, font type, font size
		
		frame.add(label);
		frame.add(panel);
		
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//make sure it actually closes when we exit out of it
		frame.setSize(width,height);//size of frame
		frame.setLayout(null);//manually place everything
		frame.setVisible(true);
		
		StartGame();
	}
	
	private void StartGame() {
		((CustomPanel)panel).startGame();
	}
}