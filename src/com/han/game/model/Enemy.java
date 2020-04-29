package com.han.game.model;

import java.awt.Graphics;
import java.util.Random;

/**
 * ��������
 * 
 * @author �ź���
 *
 */
public class Enemy extends GameObject {
	// ��Ļƫ����
	double c0;

	public Enemy() {

	}

	public void setData(double d, double d1, double d2, double d3, int i, int j, int k, int l) {
		super.setData(d, d1, d2, d3, i, j, k, l);
		c0 = 0;
	}

	public void move() {
		if (!exist) {
			return;
		}
		super.move();
		// ����2������
		if (type == 2) {
			if (frame % 30 == 0) {
				th = playerTh();
				if ((tmp = p.bullets.getEmpty()) != null) {
					tmp.setData(px, py, Math.cos(th) * 6, Math.sin(th) * 6, 15, 0, 0, 0);
				}
			}
		}
		// ����3������
		if (type == 3) {
			if (py > 450 || frame > 2800) {
				vx = -10;
			}
			if (frame % 90 == 0) {
				c0 = Math.random() * 30;
				for (int i = 0; i < 360; i += 60) {
					if ((tmp = p.bullets.getEmpty()) != null) {
						tmp.setData(px, py, Math.cos(Math.toRadians((double) i + c0)) * 6,
								Math.sin(Math.toRadians((double) i + c0)) * 6, 23, 0, 0, 0);
					}
				}
			}
		}
		// ����100��boss
		if (type == 100) {
			if (life > 1000) {
				count = 3;
				if (py < 128) {
					vy = 1;
				} else {
					vy = 0;
				}
				if (frame % 30 == 0) {
					c0 = Math.random() * 30;
					for (int i = 0; i < 360; i += 15) {
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px, py, Math.cos(Math.toRadians((double) i + c0)) * 8,
									Math.sin(Math.toRadians((double) i + c0)) * 8, 24, 0, 0, 0);
						}
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px - 50, py + 50, Math.cos(Math.toRadians((double) i + c0)) * 6,
									Math.sin(Math.toRadians((double) i + c0)) * 6, 24, 0, 0, 0);
						}
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px + 50, py + 50, Math.cos(Math.toRadians((double) i + c0)) * 6,
									Math.sin(Math.toRadians((double) i + c0)) * 6, 24, 0, 0, 0);
						}
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px, py + 100, Math.cos(Math.toRadians((double) i + c0)) * 6,
									Math.sin(Math.toRadians((double) i + c0)) * 6, 24, 0, 0, 0);
						}
					}
				}
			} else if (life > 500) {
				count++;
				if (count > 49) {
					count = 6;
				}
				vy = 0;
				if (frame % 60 == 0) {
					th = playerTh();
					if ((tmp = p.bullets.getEmpty()) != null) {
						tmp.setData(px, py, Math.cos(th) * 6, Math.sin(th) * 6, 32, 0, 0, 0);
					}
				}
			} else if (life > 300) {
				count++;
				if (count > 100) {
					count = 60;
				}
				p.boss.getObject(0).type = 3;
				if (frame % 30 == 0 && count > 59) {
					for (int i = 0; i < 180; i += 15) {
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px - 15, py, Math.cos(Math.toRadians((double) i)) * 6,
									Math.sin(Math.toRadians((double) i)) * 6, 17, 0, 0, 0);
						}
					}
					for (int i = 0; i < 180; i += 15) {
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px + 25, py, Math.cos(Math.toRadians((double) i)) * 6,
									Math.sin(Math.toRadians((double) i)) * 6, 17, 0, 0, 0);
						}
					}
				}

				if (px >= 500) {
					vx = -10;
					vy = Math.random() * 10;
				} else if (px <= 10) {
					vx = 10;
					vy = -Math.random() * 10;
				} else if (py >= 300) {
					vy = -5;
				} else if (py <= 100) {
					vy = 5;
				}
			} else if (life > 0) {
				vx = vy = 0;
				count++;
				if (count > 200) {
					count = 110;
				}
				if (frame % 30 == 0) {
					for (int i = 0; i < 360; i += 15) {
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px - 100, py, Math.cos(Math.toRadians((double) i) + 10) * 6,
									Math.sin(Math.toRadians((double) i)) * 6, 26, 0, 1, 0);
						}
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px + 100, py, Math.cos(Math.toRadians((double) i)) * 6,
									Math.sin(Math.toRadians((double) i) + 10) * 6, 26, 0, 2, 0);
						}
					}
				}
				if (frame % 15 == 0) {
					c0 = Math.random() * 40;
					for (int i = 0; i < 360; i += 15) {
						if ((tmp = p.bullets.getEmpty()) != null) {
							tmp.setData(px, py, Math.cos(Math.toRadians((double) i + c0)) * 6,
									Math.sin(Math.toRadians((double) i + c0)) * 6, 26, 0, 3, 0);
						}
					}
				}
			}
		}
		// ���ֵ�Ļ
		if (p.boss.getObject(0).type == 2) {
			if (frame % 8 == 0) {
				c0 = Math.random() * 30;
				for (int i = 0; i < 360; i += 15) {
					if ((tmp = p.bullets.getEmpty()) != null) {
						tmp.setData(p.boss.getObject(0).px, p.boss.getObject(0).py,
								Math.cos(Math.toRadians((double) i + c0)) * 6,
								Math.sin(Math.toRadians((double) i + c0)) * 6, 25, 0, 0, 0);

					}
				}
			}
		}

		// �Ի��ӵ�ײ������
		for (int i = 0; i < p.shoots.getArrayMax(); i++) {
			if (!(tmp = p.shoots.getObject(i)).getExist() || Math.hypot(px - tmp.getPx(),
					py - tmp.getPy()) >= 30)
				continue;
			if (life > 0) {
				life--;
				tmp.erase();
			}
			if (life <= 0) {
				p.player.setScore(type);
				double a = px;
				double y = py;
				erase();
				if ((tmp = p.bullets.getEmpty()) != null) {
					int temp = new Random().nextInt(40); // ����0��30�������
					if (temp < 10) {
						tmp.setData(a, y, 0, 6, 5, 0, 0, 0);
					} else if (temp < 33) {
						tmp.setData(a, y, 0, 5, 6, 0, 0, 0);
					} else if (temp < 38) {
						tmp.setData(a, y, 0, 3, 8, 0, 0, 0);
					} else {
						tmp.setData(a, y, 0, 3, 9, 0, 0, 0);
					}
				}
			}
		}
		if (p.player.frame > 3300) {
			if (life <= 0) {
				System.out.println("ִ��");
				p.setMenuMode(6);
				p.setTime2(0);
				p.bGM(6, 2);
			}
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	public double playerTh() {
		return Math.atan2(p.player.getPy() - py, p.player.getPx() - px);
	}

}