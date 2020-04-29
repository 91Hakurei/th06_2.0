package com.han.game.model;

import java.awt.Graphics;
import com.han.game.control.GetKeys;

/**
 * �Ի�����
 * 
 * @author �ź���
 *
 */
public class Player extends GameObject {
	private int shotInterval;
	private int boom = 3;
	private int power;
	private int score;

	public Player() {
	}

	public void setData(double d, double d1, double d2, double d3,
			int i, int j, int k, int l) {
		super.setData(d, d1, d2, d3, i, j, k, l);
		shotInterval = 0;
		power = 0;
		score = 0;
	}

	public void draw(Graphics g) {
		super.draw(g);
	}

	public void move(GetKeys getkeys) {
		super.move();
		// �ƶ�����
		vx = 0;
		vy = 0;
		if (getkeys.up) {
			vy -= 8;
		}
		if (getkeys.down)
			vy += 8;
		if (getkeys.left)
			vx = -8;
		if (getkeys.right)
			vx = 8;
		if (getkeys.shift) {
			vx *= 0.5;
			vy *= 0.5;
		}
		if (getkeys.x) {
			if (boom > 0 && life > 0) {
				if ((tmp = p.shoots.getEmpty()) != null) {
					tmp.setData(280, 900, 0, -20, 101, 0, 0, 0);
					p.bgm[5].play();
					boom--;
					p.bullets.allErase();
					getkeys.x = false;
				}
			}
		}

		// �Ի��ӵ�ģ��
		if (shotInterval < 1) {
			if (getkeys.z) {
				shotInterval = 1;
				for (int i = -8; i <= 8; i += 16) {
					// �����ӵ�
					if ((tmp = p.shoots.getEmpty()) != null)
						tmp.setData(px + (double) i, py, 0, -24, 16, 0, 0, 0);
				}

				int i = 21;
				if (power < 12)
					i = 15;
				if (power < 9)
					i = 9;
				if (power < 6)
					i = 3;
				if (power < 3)
					i = 0;
				byte byte0;
				// ��סshift���ۼ��ӵ�
				if (getkeys.shift) {
					byte0 = 6;
				} else {
					i *= 2;
					byte0 = 12;
				}
				// ���ݻ��������ӵ���Ļ
				for (int j = 90 - i; j <= 90 + i; j += byte0)
					if ((tmp = p.shoots.getEmpty()) != null) {
						tmp.setData(px, py, -Math.cos(Math.toRadians(j)) * 24,
								-Math.sin(Math.toRadians(j)) * 24, 20, 0,
								0, 0);
					}
			}

			// �����ƶ���Χ
			if (px < 10) {
				px = 10;
			}
			if (py < 150) {
				py = 150;
			}
			if (px > 540) {
				px = 540;
			}
			if (py > 925) {
				py = 925;
			}

		} else {
			shotInterval--;
		}

		int i = 0;
		do {
			if (i >= p.bullets.getArrayMax()) {
				break;
			}
			// ��ұ������¼�
			if ((tmp = p.bullets.getObject(i)).getExist()) {
				// �����¼�
				if (tmp.size == 5 || tmp.size == 6) {
					if (25 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 30) - (tmp.getPy() + tmp.getSize() / 2))) {
						if (tmp.size == 5) {
							tmp.erase();
							power++;
							break;
						} else {
							tmp.erase();
							score += 10;
							break;
						}

					}
				} else if (tmp.size == 8) {
					if (25 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 30) - (tmp.getPy() + tmp.getSize() / 2))) {
						tmp.erase();
						if (boom < 10) {
							boom++;
						}
						break;
					}
				} else if (tmp.size == 9) {
					if (25 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 30) - (tmp.getPy() + tmp.getSize() / 2))) {
						tmp.erase();
						if (life < 10) {
							life++;
						}
						break;
					}
					// ���ӵ�
				} else if (tmp.size == 32) {
					if (32 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 30) - (tmp.getPy() + tmp.getSize() / 2)) && p.isM == false) {
						tmp.erase();
						life--;
						p.bgm[3].play();
						break;
					}
				} else {
					if (10 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 30) - (tmp.getPy() + tmp.getSize() / 2)) && p.isM == false) {
						tmp.erase();
						life--;
						p.bgm[3].play();
						break;
					}
				}

				if (life < 0) {
					p.setMenuMode(4);
					px = 312;
					py = 539;
					p.shoots.allErase();
				}
			}
			i++;
		} while (true);
		// ���
		if (frame < 360) {
			stg1A_l();
			// �ұ�
		} else if (frame < 660) {
			stg1A_r();
		} else if (frame >= 700) {
			if (frame < 1000) {
				stg1A_lx();
				stg1A_rx();
			} else if (frame < 1300) {
				stg1B();
			} else if (frame < 1500) {
				stg1A_l();
			} else if (frame < 1800) {
				stg1A_r();
			} else if (frame < 2100) {
				stg1A_lx();
				stg1A_rx();
			} else if (frame < 2500) {
				stg1A_lx();
				stg1A_rx();
				stg1B();
			} else if (frame == 3300) {
				p.setTime2(0);
				p.bGM(2, 1);
				System.out.println("boss");
				p.enemys.allErase();
				tmp = p.enemys.getObject(0);
				tmp.setData(280, 200, 0, 0, 4, 0, 100, 1500);
			}
		}
		if (p.enemys.getObject(0).count == 5) {
			p.bgm[4].play();
			if ((tmp = p.boss.getEmpty()) != null) {
				tmp.setData(p.enemys.getObject(0).px - 80, p.enemys.getObject(0).py + 10,
						0, 0, 0, 0, 2, 1000);
			}
		}
		if (p.enemys.getObject(0).count == 50) {
			p.bgm[4].play();
			p.boss.allErase();
			p.enemys.getObject(0).vx = 1;
			p.enemys.getObject(0).vy = 1;
		}
		if (p.enemys.getObject(0).count == 105) {
			p.bgm[4].play();
			p.boss.allErase();
			p.enemys.getObject(0).px = 280;
			p.enemys.getObject(0).py = 200;
		}

//		System.out.println(frame);
//        System.out.println(p.enemys.getObject(0).count);
//		System.out.println(p.enemys.getObject(0).life);

	}

	/**
	 * С����-��
	 */
	private void stg1A_l() {
		if (frame % 40 == 0 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(0, Math.random() * 96 + 48, 2, 0, 2, 0, 2, 25);
		}
	}

	/**
	 * С����-��
	 */
	private void stg1A_r() {
		if (frame % 40 == 15 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(560, Math.random() * 96 + 48, -2, 0, 2, 0, 2, 25);
		}
	}
	/**
	 * С����-��б
	 */
	private void stg1A_lx() {
		if (frame % 40 == 0 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(0, Math.random() * 96 + 48, 2, 1, 2, 0, 2, 30);
		}
	}

	/**
	 * С����-��б
	 */
	private void stg1A_rx() {
		if (frame % 40 == 15 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(560, Math.random() * 96 + 48, -2, 1, 2, 0, 2, 30);
		}
	}

	/**
	 * ������
	 */
	private void stg1B() {
		if (frame % 30 == 7 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(Math.random() * 540, 0, 0, 1, 3, 0, 3, 60);
		}
	}

	public void setBoom(int boom) {
		this.boom += boom;
	}

	public int getBoom() {
		return boom;
	}

	public int getPower() {
		return power;
	}

	public int getScore() {
		return score;
	}

	public void setPower(int power) {
		this.power += power;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setScore(int score) {
		this.score += score;
	}

}
