package com.han.game.main;

import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * �����
 * @author �ź���
 *
 */
public class Welcome {

	static JFrame jf;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		jf=new JFrame();
		jf.setTitle("������ħ��");
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-640)/2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-640)/2,850,1000);
		GamePanel gamepanel = new GamePanel();
		jf.add(gamepanel);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jf.setIconImage(gamepanel.iconImg);
	}

}
