import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
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

	 private  GameObject Player;
	 private Revolver revolver;
	 private  CopyOnWriteArrayList<GameObject> EnemiesList  = new CopyOnWriteArrayList<GameObject>();
	 private  Controller controller = new Controller();
	 private CopyOnWriteArrayList<Bullet> BulletList = new CopyOnWriteArrayList<>();
	 private Point mousePos = new Point();
	 private double angleToMouse;
	 private int Score=0;

	public Model() {
		 //setup game world 
		//Player 
		Player= new GameObject("res/player.png",75,75,new Point3f(500,500,0));
		revolver = new Revolver();
		//Enemies  starting with four
		EnemiesList.add(new GameObject("res/UFO.png",50,50,new Point3f(((float)Math.random()*50+400 ),0,0))); 
		EnemiesList.add(new GameObject("res/UFO.png",50,50,new Point3f(((float)Math.random()*50+500 ),0,0)));
		EnemiesList.add(new GameObject("res/UFO.png",50,50,new Point3f(((float)Math.random()*100+500 ),0,0)));
		EnemiesList.add(new GameObject("res/UFO.png",50,50,new Point3f(((float)Math.random()*100+400 ),0,0)));
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
		// this is a way to increment across the array list data structure
		//see if they hit anything 
		// using enhanced for-loop style as it makes it alot easier both code wise and reading wise too 
		for (GameObject temp : EnemiesList) 
		{
			for (Bullet Bullet : BulletList) {
				if ( Math.abs(temp.getCentre().getX()- Bullet.getX())< temp.getWidth()
					&& Math.abs(temp.getCentre().getY()- Bullet.getY()) < temp.getHeight())
				{
					EnemiesList.remove(temp);
					BulletList.remove(Bullet);
					Score++;
				}
			}
		}
		
	}

	private void enemyLogic() {
		// TODO Auto-generated method stub
		for (GameObject temp : EnemiesList) {
		    // Move enemies
			temp.getCentre().ApplyVector(new Vector3f(0,-1,0));
			//see if they get to the top of the screen ( remember 0 is the top 
			if (temp.getCentre().getY()==900.0f)  // current boundary need to pass value to model 
			{
				EnemiesList.remove(temp);
				// enemies win so score decreased 
				Score--;
			} 
		}
		
		if (EnemiesList.size()<2) {
			while (EnemiesList.size()<6) {
				EnemiesList.add(new GameObject("res/UFO.png",50,50,new Point3f(((float)Math.random()*1000),0,0))); 
			}
		}
	}

	private void bulletLogic() {
		// TODO Auto-generated method stub
		// move bullets
		for (Bullet temp : BulletList) {
		    // Move Bullet via fixed scalar values in class
			temp.update();

			//TODO figure out collisions to remove bullets
		} 
		
	}

	private void playerLogic() {
		
		// smoother animation is possible if we make a target position  // done but may try to change things for students
		//check for movement and if you fired a bullet 
		  
		if(Controller.getInstance().isKeyAPressed()){
			Player.getCentre().ApplyVector( new Vector3f(-2,0,0));
		}
		
		if(Controller.getInstance().isKeyDPressed()) {
			Player.getCentre().ApplyVector( new Vector3f(2,0,0));
		}
			
		if(Controller.getInstance().isKeyWPressed()) {
			Player.getCentre().ApplyVector( new Vector3f(0,2,0));
		}
		
		if(Controller.getInstance().isKeySPressed()){
			Player.getCentre().ApplyVector( new Vector3f(0,-2,0));
		}

		if(Controller.getInstance().isMouseLeftPressed()) {
			if (revolver.canfire()) { // check if there are bullets to shoot and if hammer is cocked
				playSound("res/revolver_shot.wav");
				CreateBullet();
				revolver.fired(); // reduce ammo count and decock hammer
			}
			Controller.getInstance().setMouseLeftPressed(false);
		}

		if ( Controller.getInstance().isMouseRightPressed() ) {
			revolver.cockHammer(); // ready to pew
			Controller.getInstance().setMouseRightPressed(false);
		}

		if (Controller.getInstance().isKeyRPressed() ) {
			revolver.reload(); // add one bullet back to ammo
			Controller.getInstance().setKeyRPressed(false);
		}
		
	}

	private void CreateBullet() {
		//BulletList.add(new GameObject("res/Bullet.png",16,32, new Point3f(Player.getCentre().getX(),Player.getCentre().getY(),0.0f)));
		Bullet bullet = new Bullet(mousePos.getX(), mousePos.getY(), Player.getCentre().getX(), Player.getCentre().getY(), angleToMouse, 10 );
		//System.out.println("About to add bullet to list");
		BulletList.add(bullet);
	}

	public GameObject getPlayer() {
		return Player;
	}

	public CopyOnWriteArrayList<GameObject> getEnemies() {
		return EnemiesList;
	}
	
	public CopyOnWriteArrayList<Bullet> getBullets() {
		return BulletList;
	}

	public int getScore() { 
		return Score;
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
}


