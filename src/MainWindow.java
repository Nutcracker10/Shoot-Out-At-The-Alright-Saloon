import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


import util.UnitTests;

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



public class MainWindow {
	 private static JFrame frame = new JFrame("Shootout at the Alright Saloon");   // Change to the name of your game
	 private static Model gameworld= new Model();
	 private static Viewer canvas = new  Viewer( gameworld);
	 private static KeyListener Controller =new Controller()  ;
	 private static MouseListener Mouse = new Controller();
	 private static int TargetFPS = 100;
	 private static boolean startGame= false;
	private static JLabel deathScreen;
	private static JLabel BackgroundImageForStartMenu;


	public MainWindow() {
	        frame.setSize(1000, 1000);  // you can customise this later and adapt it to change on size.  
	      	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
	        frame.setLayout(null);
	        frame.add(canvas);  
	        canvas.setBounds(0, 0, 1000, 1000); 
	        canvas.setBackground(new Color(255,255,255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen
		    canvas.setVisible(false);   // this will become visible after you press the key.

			// Difficulty select
			JLabel difficultyMenu = new JLabel("Difficulty");
			difficultyMenu.setVisible(true);
			difficultyMenu.setBounds(250, 600, 200, 40);
			Integer[] choices = { 1, 2, 3};
			final JComboBox<Integer> box = new JComboBox<Integer>(choices);
			box.setVisible(true);
			box.setBounds(400, 600, 200, 40);

	        JButton startMenuButton = new JButton("Start Game");  // start button 
	        startMenuButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { 
					startMenuButton.setVisible(false);
					BackgroundImageForStartMenu.setVisible(false); 
					canvas.setVisible(true);
					canvas.addMouseListener(Mouse);
					canvas.addKeyListener(Controller);    //adding the controller to the Canvas  
	            	canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
					startGame=true;
				}
	           })
			;
	        startMenuButton.setBounds(400, 500, 200, 40);


	        
	        //loading background image 
	        File BackroundToLoad = new File("res/startscreen.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
			try {
				 
				 BufferedImage myPicture = ImageIO.read(BackroundToLoad);
				 BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
				 BackgroundImageForStartMenu.setBounds(0, 0, 1000, 1000);
				 frame.add(BackgroundImageForStartMenu);
			}  catch (IOException e) { 
				e.printStackTrace();
			}   
			 
			frame.add(startMenuButton);
//			frame.add(difficultyMenu);
//			frame.add(box);
			frame.setVisible(true);
	}

	public static void main(String[] args) {
		MainWindow hello = new MainWindow();  //sets up environment 
		while(true)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
		{ 
			//swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it 
			
			int TimeBetweenFrames =  1000 / TargetFPS;
			long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames; 
			
			//wait till next time step 
		 while (FrameCheck > System.currentTimeMillis()){} 
			
			
			if(startGame) {
				 gameloop();
				 if (gameworld.isPlayerDead())
				 	//hello = new MainWindow();
				 	//reset();
				 	break;
			}

			//UNIT test to see if framerate matches 
		 UnitTests.CheckFrameRate(System.currentTimeMillis(),FrameCheck, TargetFPS); 
			  
		}
		
		
	} 
	//Basic Model-View-Controller pattern 
	private static void gameloop() { 
		// GAMELOOP  
		
		// controller input  will happen on its own thread 
		// So no need to call it explicitly 
		
		// model update   
		gameworld.gamelogic();
		// view update 
		
		canvas.updateview();

		// Both these calls could be setup as  a thread but we want to simplify the game logic for you.  
		//score update  
		 frame.setTitle("Score =  "+ gameworld.getScore());

		 // get relevant info for shooting
		 gameworld.setPoint(canvas.getMousePosition());
		 gameworld.setAngle(canvas.getImageAngleRad());

		 if (gameworld.isPlayerDead()) {
		 	//TODO DIE!!!
			 endState();
		 }
		 
	}

	private static void endState() {
		//startGame = false;
		File BackgroundToLoad = new File("res/Death_Screen.png");
		try {

			BufferedImage myPicture = ImageIO.read(BackgroundToLoad);
			deathScreen = new JLabel(new ImageIcon(myPicture));
			deathScreen.setBounds(0, 0, 1000, 1000);
			canvas.setVisible(false);

//			JButton playAgainbtn = new JButton("Play Again");  // start button
//			playAgainbtn.setBounds(400, 600, 200, 40);
//
//			deathScreen.add(playAgainbtn);
//			playAgainbtn.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					reset();
//				}
//			});

			frame.add(deathScreen);

		}  catch (IOException e) {
			e.printStackTrace();
		}
	}

//	private static void reset() {
//		frame = new JFrame("Shootout at the Alright Saloon");   // Change to the name of your game
//		Model gameworld= new Model();
//		Viewer canvas = new  Viewer( gameworld);
//		KeyListener Controller =new Controller()  ;
//		MouseListener Mouse = new Controller();
//		TargetFPS = 100;
//		startGame= false;
//	}

}

