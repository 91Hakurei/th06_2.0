package com.han.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.han.game.control.GetKeys;
import com.han.game.model.GameObject;
import com.han.game.model.Player;

/**
 * ����
 * @author �ź���
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener,
MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Graphics g;
	private Image dbImage;
	private Thread gameLoop;
	private GetKeys getKeys;
	private int back_y;
	private int time;
	private int time2;
	
	/**
	 * 0���˵�
	 * 1��˵��
	 * 2���˳�
	 * 3��������Ϸ
	 * 4������
	 * 5������
	 * 6��ʤ��
	 */
	private int menuMode;

	public Image bgImg;
	public Image pImg;
	public Image bImg;
	public Image eImg;
	public Image eImg2;
	public Image aImg;
	public Image boImg;
	public Image iconImg;
	public Player player;
	public ObjectsArray enemys;
	public ObjectsArray shoots;
	public ObjectsArray bullets;
	public ObjectsArray boss;
	public Point moveP = new Point(0, 0);
	public Point checkP;
	public AudioClip bgm[];
	/**
	 * �Ƿ��޵�
	 */
	public boolean isM;

	public GamePanel() {
		dbImage = null;
		player = new Player();
		getKeys = new GetKeys();
		enemys = new ObjectsArray("Enemy", 50);
		shoots = new ObjectsArray("Shoot", 200);
		bullets = new ObjectsArray("Bullet", 400);
		boss = new ObjectsArray("Boss", 10);
		menuMode = 0;
		isM = false;
		time = 0;
		time2 = 0;
		bgm = new AudioClip[7];

		// ����
		ImageIcon imageicon1 = new ImageIcon(getClass().getResource("/images/bg.png"));
		bgImg = imageicon1.getImage();
		back_y = -bgImg.getHeight(null) + bgImg.getHeight(null);

		ImageIcon imageicon2 = new ImageIcon(getClass().getResource("/images/player00.png"));
		pImg = imageicon2.getImage();

		ImageIcon imageicon3 = new ImageIcon(getClass().getResource("/images/stg1enm.png"));
		eImg = imageicon3.getImage();

		ImageIcon imageicon5 = new ImageIcon(getClass().getResource("/images/stg6enm2.png"));
		bImg = imageicon5.getImage();

		ImageIcon imageicon6 = new ImageIcon(getClass().getResource("/images/etama3.png"));
		aImg = imageicon6.getImage();

		ImageIcon imageicon7 = new ImageIcon(getClass().getResource("/images/etama4.png"));
		eImg2 = imageicon7.getImage();

		ImageIcon imageicon8 = new ImageIcon(getClass().getResource("/images/boom.png"));
		boImg = imageicon8.getImage();
		
		ImageIcon imageicon9 = new ImageIcon(getClass().getResource("/images/timg.jpg"));
		iconImg = imageicon9.getImage();
		
		// ����
	    bgm[0]=Applet.newAudioClip(getClass().getResource("/sounds/th06_01.wav"));
	    bgm[1]=Applet.newAudioClip(getClass().getResource("/sounds/th06_02.wav"));
	    bgm[2]=Applet.newAudioClip(getClass().getResource("/sounds/th06_13.wav"));
	    bgm[3]=Applet.newAudioClip(getClass().getResource("/sounds/biubiu.mp3"));
	    bgm[4]=Applet.newAudioClip(getClass().getResource("/sounds/break.wav"));
	    bgm[5]=Applet.newAudioClip(getClass().getResource("/sounds/skill.wav"));
	    bgm[6]=Applet.newAudioClip(getClass().getResource("/sounds/th06_17.wav"));

		// ��������-����
		setFocusable(true);
		// ���Ӽ��̼����¼�
		addKeyListener(this);
		// �����������¼�
		addMouseListener(this);
		addMouseMotionListener(this);

		GameObject.gameObjectInit(this);
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	/**
	 * ��Ϸ��������
	 */
	public void gameSet() {
		menuMode = 0;
		// �����ɫ��Ϣ
		player.setData(312, 539, 0, 0, 1, 0, 0, 10);
		shoots.allErase();
		bullets.allErase();
		enemys.allErase();
		time2 = 0;
	}

	/**
	 * ��Ϸ��Դ����
	 */
	public void gameUpdate() {
		if (menuMode == 3) {
			bGM(1, 0);
			bullets.allMove();
			shoots.allMove();
			enemys.allMove();
			boss.allMove();
			player.move(getKeys);
		}
	}

	/**
	 * ��Ϸ��Դ����
	 */
	private void gameRender() {
		// �������ڣ�˫����
		if (dbImage == null) {
			dbImage = createImage(850, 1000);
			if (dbImage == null)
				return;
			g = dbImage.getGraphics();
		}

		// ��������ѭ��
		g.drawImage(bgImg, 0, back_y++, null);
		g.drawImage(bgImg, 0, back_y - bgImg.getHeight(null), null);
		if (back_y == bgImg.getHeight(null)) {
			back_y = 0;
		}

		player.draw(g);
		shoots.allDraw(g);
		enemys.allDraw(g);
		bullets.allDraw(g);
		boss.allDraw(g);
		
		if (isM) {
			ImageIcon imageicon = new ImageIcon(getClass().getResource("/images/stg7enm.png"));
			Image dun = imageicon.getImage();
			g.drawImage(dun, (int)player.getPx()-30, (int)player.getPy()-15,
					(int)player.getPx()+34, (int)player.getPy()+49, 0, 96, 64, 160, null);
			time++;
			if (time == 200) {
				isM = false;
				time = 0;
			}
		}

		// boss�볡����
		if (player.getFrame() >= 3300) {
			g.drawRect(30, 100, 500, 10);
			// bossѪ���׶ι���
			int life = enemys.getObject(0).getLife();

			if (life > 1000) {
				g.setColor(Color.yellow);
				g.fillRect(30, 100, life - 1000, 11);
			} else if (life >= 500) {
				g.setColor(Color.blue);
				g.fillRect(30, 100, life - 500, 11);
			} else if (life >= 0) {
				g.setColor(Color.red);
				g.fillRect(30, 100, life, 11);
			}
		}
		// ���Ѫ��
		g.setColor(Color.red);
		g.drawRect(600, 600, 200, 10);
		g.fillRect(600, 600, player.getLife() * 20, 11);
		
		

		// �����ͻ���
		paintScore(g);
		
		if (menuMode != 3) {
			if (menuMode == 0) {
				
				bGM(0, 1);
				// ��Ϸ��ʼ����
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/title00.png"));
				Image menu = imageicon.getImage();
				// ��������
				imageicon = new ImageIcon(getClass().getResource("/images/front.png"));
				Image title = imageicon.getImage();
				// ��ʼ
				imageicon = new ImageIcon(getClass().getResource("/images/start.png"));
				Image start = imageicon.getImage();
				// ����
				imageicon = new ImageIcon(getClass().getResource("/images/help.png"));
				Image help = imageicon.getImage();
				// �˳�
				imageicon = new ImageIcon(getClass().getResource("/images/Exit.png"));
				Image Exit = imageicon.getImage();
				////////////////////////////////////////////
				int i = 60;
				g.drawImage(menu, 0, 0, 850, 1000, 0, 0, 640, 480, null);
				g.drawImage(title, 1, 1, 128, 128, 0, 0, 64, 64, null);
				g.drawImage(title, 71 + i, 10, 198 + i, 137, 64, 0, 128, 64, null);
				g.drawImage(title, 141 + 2 * i, 10, 268 + 4 * i, 128 + i,
						128, 0, 192, 64, null);
				g.drawImage(title, 211 + 5 * i, 10, 338 + 5 * i, 137, 192, 0, 256, 64, null);
				g.drawImage(title, 281 + 6 * i, 10, 408 + 6 * i, 137, 0, 64, 64, 128, null);
				// ��ť
				g.drawImage(start, 600, 400, 800, 500, 0, 0, 100, 32, null);
				g.drawImage(help, 600, 530, 800, 630, 0, 0, 52, 32, null);
				g.drawImage(Exit, 600, 650, 800, 750, 0, 0, 100, 32, null);
				// ����ƶ�����ť�ǵ�Ч��
				// ��ʼ��Ϸ
				if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
						(moveP.getY() >= 400 && moveP.getY() <= 500)) {
					Color c = g.getColor();
					g.setColor(Color.white);
					g.drawRect(600, 400, 200, 100);
					g.setColor(c);
				}
				// ˵��
				if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
						(moveP.getY() >= 530 && moveP.getY() <= 630)) {
					Color c = g.getColor();
					g.setColor(Color.yellow);
					g.drawRect(600, 530, 200, 100);
					g.setColor(c);
				}
				// �˳�
				if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
						(moveP.getY() >= 650 && moveP.getY() <= 750)) {
					Color c = g.getColor();
					g.setColor(Color.pink);
					g.drawRect(600, 650, 200, 100);
					g.setColor(c);
				}
			// ˵��ҳ��
			} else if (menuMode == 1) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/slpl00a.png"));
				Image help1 = imageicon.getImage();
				imageicon = new ImageIcon(getClass().getResource("/images/slpl00b.png"));
				Image help2 = imageicon.getImage();
				g.drawImage(help1, 0, 0, 850, 500, 0, 0, 256, 256, null);
				g.drawImage(help2, 0, 500, 850, 1000, 0, 0, 256, 240, null);
				if ((moveP.getX() >= 705 && moveP.getX() <= 835) &&
						(moveP.getY() >= 920 && moveP.getY() <= 1000)) {
					Color c = g.getColor();
					g.setColor(Color.red);
					g.drawRect(705, 920, 130, 990);
					g.setColor(c);
				}
			// �˳�
			} else if (menuMode == 2) {
            	System.exit(0);
            // ����	
            } else if (menuMode == 4) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/result.png"));
				Image dead = imageicon.getImage();
				g.drawImage(dead, 0, 0, 850, 1000, 0, 0, 640, 480, null);
				if ((moveP.getX() >= 20 && moveP.getX() <= 310) &&
						(moveP.getY() >= 670 && moveP.getY() <= 850)) {
					Color c = g.getColor();
					g.setColor(Color.red);
					g.drawRect(20, 670, 310, 180);
					g.setColor(c);
				}
				if ((moveP.getX() >= 350 && moveP.getX() <= 620) &&
						(moveP.getY() >= 670 && moveP.getY() <= 850)) {
					Color c = g.getColor();
					g.setColor(Color.green);
					g.drawRect(350, 670, 270, 180);
					g.setColor(c);
				}
			// ����
			} else if (menuMode == 5) {
				bullets.allErase();
				player.setLife(10);
				player.setBoom(3);
				menuMode = 3;
				isM = true;
			// ʤ��	
			} else if (menuMode == 6) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/end03.jpg"));
				Image ve = imageicon.getImage();
				g.drawImage(ve, 0, 0, 850, 1000, 0, 0, 640, 480, null);
				if ((moveP.getX() >= 740 && moveP.getX() <= 815) &&
						(moveP.getY() >= 200 && moveP.getY() <= 720)) {
					Color c = g.getColor();
					g.setColor(Color.black);
					g.drawRect(740, 200, 75, 520);
					g.setColor(c);
				}
			}
		}
	}
	
	public void setTime2(int time2) {
		this.time2 = time2;
	}

	/**
	 *  bgm[0]=��������
	 *  bgm[1]=��������
	 *  bgm[2]=boss����
	 *  bgm[3]=��������
	 *  bgm[4]=��������
	 *  bgm[5]=��������
	 *  bgm[6]=ʤ������
	 * @param i
	 */
	public void bGM(int i, int j) {
		time2++;
		if (time2 == 1) {
			bgm[i].loop();
		}
		bgm[j].stop();
	}
	
	/**
	 * ����ѡ��ģ��
	 * @param i
	 */
	public void setMenuMode(int i) {
		menuMode = i;
	}
	
	/**
	 * 
	 * @param g ���֣���������B��
	 */
	public void paintScore(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("SCORE: " + player.getScore(), 600, 200);
		g.drawString("POWER: " + player.getPower(), 600, 300);
		g.drawString("BOOM: " + player.getBoom(), 600, 400);
	}

	/**
	 * �����ڳ�ʼ��
	 */
	private void paintScreen() {
		Graphics g = getGraphics();
		if (g != null && dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}

	/**
	 * �߳�20����
	 */
	public void run() {
//		System.out.println("ִ��");
		System.out.println(menuMode);
		gameSet();
		do {
			gameUpdate();
			gameRender();
			paintScreen();
			
			
			// ��Ļ˯��
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// ����������
			if (!player.getExist()) {
				setMenuMode(4);
			}
		} while (true);
	}

	// ���������¼�
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		getKeys.keyPressed(e.getKeyCode());
	}
	public void keyReleased(KeyEvent e) {
		getKeys.keyReleased(e.getKeyCode());
	}

	// ��ȡ��꽹��
	public void mouseDragged(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
		moveP = e.getPoint();
	}

	// �������¼�
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		checkP = e.getPoint();
		// ��ʼ�˵�
		if (menuMode == 0) {
			// ��ʼ��Ϸ
			if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
					(moveP.getY() >= 400 && moveP.getY() <= 500)) {
				gameSet();
				setMenuMode(3);
			}
			// ˵��
			if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
					(moveP.getY() >= 530 && moveP.getY() <= 630)) {
				setMenuMode(1);
			}
			// �˳�
			if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
					(moveP.getY() >= 650 && moveP.getY() <= 750)) {
				setMenuMode(2);
			}
		} 
		// ˵���˵�
		if (menuMode == 1) {
			if ((moveP.getX() >= 705 && moveP.getX() <= 835) &&
					(moveP.getY() >= 920 && moveP.getY() <= 1000)) {
				setMenuMode(0);
			}
		}
		// ����ҳ��
		if (menuMode == 4) {
			// ����
			if ((moveP.getX() >= 20 && moveP.getX() <= 310) &&
					(moveP.getY() >= 670 && moveP.getY() <= 850)) {
				System.out.println("����");
				setMenuMode(5);
			}
			// �����˵�
            if ((moveP.getX() >= 350 && moveP.getX() <= 620) &&
					(moveP.getY() >= 670 && moveP.getY() <= 850)) {
            	player.erase();
            	gameSet();
            	bgm[1].stop();
            	bgm[2].stop();
            	setMenuMode(0);
			}
		}
		// ʤ��ҳ��
		if (menuMode == 6) {
        	if ((moveP.getX() >= 740 && moveP.getX() <= 815) &&
					(moveP.getY() >= 200 && moveP.getY() <= 720)) {
            	setMenuMode(0);
            	bgm[6].stop();
            	gameSet();
			}
        }
	}

}