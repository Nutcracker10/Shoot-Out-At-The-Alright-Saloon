import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */ 
public class Model {

	 private GameObject Player;
	 private Revolver revolver;
	 private CopyOnWriteArrayList<Bandit> EnemiesList  = new CopyOnWriteArrayList<Bandit>();
	 private Controller controller = new Controller();
	 private CopyOnWriteArrayList<Bullet> BulletList = new CopyOnWriteArrayList<>();
	 private CopyOnWriteArrayList<Bullet> EnemyBullet = new CopyOnWriteArrayList<>();
	 private CopyOnWriteArrayList<GameObject> stalls = new CopyOnWriteArrayList<>();
	 private Point mousePos = new Point();
	 private double angleToMouse;
	 private int score=0, waveCount = 1, health =5, difficulty=2;
	 private String direction;
	 private boolean isPlayerDead = false, generatingWave = false;

	public Model() {
		 //setup game world
		Player= new GameObject("res/player.png",75,75,new Point3f(500,700,0));
		revolver = new Revolver();
		direction = "North";
		EnemiesList.add(new Bandit(500., 500., 2));

//		stalls.add(new GameObject("res/Stall.png", 150, 150, new Point3f(200, 100, 0 ) ) );
//		stalls.add(new GameObject("res/Stall.png", 150, 150, new Point3f(600, 100, 0 ) ) );
//		stalls.add(new GameObject("res/Stall.png", 150, 150, new Point3f(200, 750, 0 ) ) );
//		stalls.add(new GameObject("res/Stall.png", 150, 150, new Point3f(600, 750, 0 ) ) );

	}
	
	// This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly. 
	public void gamelogic() {
		// Player Logic first 
		playerLogic(); 
		// Enemy Logic next
		enemyLogic();
		// Bullets move next 
		bulletLogic();
		// interactions between objects 
		gameLogic();
	}

	private void gameLogic() {
		//Enemy gets hit
		for (Bandit temp : EnemiesList)
		{
			for (Bullet bullet : BulletList) {
				if  (Math.abs(temp.getX() - bullet.getX()) < temp.getWidth()
						&& Math.abs(temp.getY()- bullet.getY()) < temp.getHeight()
							&& (bullet.getOrigin().equals("Player")) )  {
					EnemiesList.remove(temp);
					BulletList.remove(bullet);
					score++;
				}
			}
		}

		//Player gets hit
		for (Bullet eBullet : EnemyBullet) {
			if  (Math.abs(Player.getCentre().getX() - eBullet.getX()) < Player.getWidth()-20
					&& Math.abs(Player.getCentre().getY()- eBullet.getY()) < Player.getHeight()-20
					&& (eBullet.getOrigin().equals("Bandit")) )  {
				health --;
				if (health <= 0) {
					isPlayerDead = true;
				}
				EnemyBullet.remove(eBullet);
			}
		}

		//Bandit bullet hits stall, remove it
		for (Bullet ebullet : EnemyBullet) {
			for (GameObject stall : stalls) {
				if ( (Math.abs(stall.getCentre().getX() - ebullet.getX()) < stall.getWidth()-20)
						&& (Math.abs(stall.getCentre().getY()- ebullet.getY()) < stall.getHeight()+70) ) {
					EnemyBullet.remove(ebullet);
				}
			}
		}

		//Player bullet hits stall
		for (Bullet bullet : BulletList) {
			for (GameObject stall : stalls) {
				if ( (Math.abs(stall.getCentre().getX() - bullet.getX()) < stall.getWidth())
						&& (Math.abs(stall.getCentre().getY()- bullet.getY()) < stall.getHeight()) ) {
					BulletList.remove(bullet);
				}
			}
		}
		
	}

	private void enemyLogic() {
		for (Bandit bandit : EnemiesList) {
			bandit.update();

			if (bandit.isMoveReached() == true) {
				EnemyBullet.add(bandit.fireBullet(Player.getCentre().getX(), Player.getCentre().getY() ));
				playSound("res/bandit_sound.wav");
			}
		}


		if (EnemiesList.size() == 0) {
			this.pickDirection();
			int enemyCount = (waveCount + difficulty);
			if (enemyCount > 15 )
				enemyCount = 15;

			while (EnemiesList.size() < enemyCount) {
				generatingWave = true;
				Point point = this.generateWave();

				EnemiesList.add(new Bandit(point.getX(), point.getY(), 2));
			}
			generatingWave = false;
			waveCount++;
		}
	}

	private void bulletLogic() {
		// TODO Auto-generated method stub
		// move bullets
		for (Bullet temp : BulletList) {
		    // Move Bullet via fixed scalar values in class
			temp.update();

			//TODO figure out collisions to remove bullets

			if (temp.getY() < 0.0 || temp.getX() < 0. || temp.getY() > 1000 || temp.getX() > 1000) {
				BulletList.remove(temp);
			}
		}

		for (Bullet bullet : EnemyBullet) {
			bullet.update();

			if (bullet.getY() < 0.0 || bullet.getX() < 0. || bullet.getY() > 1000 || bullet.getX() > 1000) {
				BulletList.remove(bullet);
			}
		}
		
	}

	private void playerLogic() {
		int speed = 3;
		
		// smoother animation is possible if we make a target position  // done but may try to change things for students
		//check for movement and if you fired a bullet
		  
		if(Controller.getInstance().isKeyAPressed()){
			Player.getCentre().ApplyVector( new Vector3f(-speed,0,0));
		}
		
		if(Controller.getInstance().isKeyDPressed()) {
			Player.getCentre().ApplyVector( new Vector3f(speed,0,0));
		}
			
		if(Controller.getInstance().isKeyWPressed()) {
			Player.getCentre().ApplyVector( new Vector3f(0,speed,0));
		}
		
		if(Controller.getInstance().isKeySPressed()){
			Player.getCentre().ApplyVector( new Vector3f(0,-speed,0));
		}

		if(Controller.getInstance().isMouseLeftPressed()) {
			if (revolver.canfire()) { // check if there are bullets to shoot and if hammer is cocked
				playSound("res/revolver_shot.wav");
				CreateBullet();
				revolver.fired(); // reduce ammo count and decock hammer
			}
			Controller.getInstance().setMouseLeftPressed(false);
		}

		if (Controller.getInstance().isKeySpacePressed()) {
			//TODO Add dash feature
			Controller.getInstance().setKeySpacePressed(false);
		}

		if ( Controller.getInstance().isMouseRightPressed() ) {
			if (revolver.isCocked() == false) {
				playSound("res/revolver_cocked.wav");
				revolver.cockHammer(); // ready to pew
			}
			Controller.getInstance().setMouseRightPressed(false);
		}

		if (Controller.getInstance().isKeyRPressed() ) {
			if (revolver.getAmmo() < revolver.getCapacity() ){
				playSound("res/revolver_reload.wav");
				revolver.reload(); // add one bullet back to ammo
			}
			Controller.getInstance().setKeyRPressed(false);
		}
		
	}

	private void CreateBullet() {
		Bullet bullet = new Bullet(Player.getCentre().getX(), Player.getCentre().getY(), angleToMouse, 10, "Player");
			BulletList.add(bullet);
	}

	public GameObject getPlayer() {
		return Player;
	}

	public CopyOnWriteArrayList<Bandit> getEnemies() {
		return EnemiesList;
	}
	
	public CopyOnWriteArrayList<Bullet> getBullets() {
		return BulletList;
	}

	public CopyOnWriteArrayList<Bullet> getEnemyBullet() {
		return EnemyBullet;
	}

	public CopyOnWriteArrayList<GameObject> getStalls() {
		return stalls;
	}

	public int getScore() { 
		return score;
	}

	public Revolver getRevolver() {
		return revolver;
	}

	public void setPoint(Point point) {
		this.mousePos = point;
	}

	public void setAngle(double angle) {
		this.angleToMouse = angle;
	}

	public void playSound(String path) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}catch (Exception e) { e.printStackTrace(); }
	}

	public Point generateWave() {
		int randomX=0, randomY=0;
		switch (direction) {
			case "North":
				// TODO pick a point in a range on that side of the screen
				randomX = (int) (Math.random() * (900 - 100 + 1) + 100);
				randomY = (int) (Math.random() * (400 - 75 + 1) + 75);
				break;
			case "South":
				randomX = (int) (Math.random() * (900 - 100 + 1) + 100);
				randomY = (int) (Math.random() * (935 - 600 + 1) + 600);
				break;
			case "East":
				randomX = (int) (Math.random() * (935 - 600 + 1) + 600);
				randomY = (int) (Math.random() * (900 - 100 + 1) + 100);
				break;
			case "West":
				randomX = (int) (Math.random() * (400 - 75 + 1) + 75);
				randomY = (int) (Math.random() * (900 - 100 + 1) + 100);
				break;
		}

		return (new Point(randomX, randomY));
	}

	public void pickDirection() {
		Random random = new Random();
		int num = random.nextInt(4);

		switch (num) {
			case 0:
				direction = "North";
				break;
			case 1:
				direction = "South";
				break;
			case 2:
				direction = "East";
				break;
			case 3:
				direction = "West";
				break;
		}
	}

	public int getHealth() { return health; }

	public String getDirection() { return this.direction; }

	public boolean isGeneratingWave() {
		return generatingWave;
	}

	public boolean isPlayerDead() { return isPlayerDead; }

	public int getDifficulty() { return this.difficulty; }

	public void setDifficulty(int difficulty)  { this.difficulty = difficulty;}
}


