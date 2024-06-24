package theGoodCode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PageLauncher implements ActionListener {

	JFrame frame = new JFrame();
	JButton openPageB = new JButton("Open Organizer");

	private int width;
	private int height;

	private Player[] ps;

	public PageLauncher(Player[] ps, int width, int height) {
		this.width = width;
		this.height = height;
		this.ps = ps;
		openPageB.setBounds(100, 160, 200, 40);
		openPageB.setFocusable(false);// do not highlight
		openPageB.addActionListener(this);

		frame.add(openPageB);// add the button to the frame

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// make sure it actually closes when we exit out of it
		frame.setSize(600, 400);// size of frame
		frame.setLayout(null);// manually place everything
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {// action performed on button click
		if (e.getSource() == openPageB) {// if the openPageB clicked
			WindowLauncher testWindow = new WindowLauncher(ps, width, height);// launch new window instance
		}
	}

}
