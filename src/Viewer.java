import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.Bandit;
import util.Bullet;
import util.GameObject;
import util.Revolver;

/*
	Name: James Kirwan
	Student # : 17402782

 */


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
 
 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel implements MouseMotionListener {
	private long CurrentAnimationTime= 0; 
	Model gameworld =new Model();
	private double imageAngleRad = 0;
	 
	public Viewer(Model World) {
		this.gameworld=World;
		addMouseMotionListener(this);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		this.repaint();
		// TODO Auto-generated method stub
		
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		CurrentAnimationTime++; // runs animation time step 

		//Draw player Game Object 
		int x = (int) gameworld.getPlayer().getCentre().getX();
		int y = (int) gameworld.getPlayer().getCentre().getY();
		int width = (int) gameworld.getPlayer().getWidth();
		int height = (int) gameworld.getPlayer().getHeight();
		String texture = gameworld.getPlayer().getTexture();
		
		//Draw background 
		drawBackground(g);

		//Draw player
		drawPlayer(x, y, width, height, texture,g);
		drawHUD(gameworld.getRevolver(), g);


		//Draw Bullets 
		// change back
		gameworld.getBullets().forEach((temp) -> 
		{
			temp.draw(g);
		});

		gameworld.getEnemyBullet().forEach((temp) ->
		{
			temp.draw(g);
		});
		
		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
		 	temp.draw(g, x, y);
	    });

//		if (gameworld.isGeneratingWave() == true) {
//			displayDirection(g);
//		}

//		gameworld.getStalls().forEach((stall) -> {
//			drawStall(g, stall);
//		});
	}

//	private void drawStall(Graphics g, GameObject stall ) {
//		File toLoad = new File( stall.getTexture() );
//		try {
//			Image myImage = ImageIO.read(toLoad);
//			g.drawImage(myImage, (int)stall.getCentre().getX(), (int)stall.getCentre().getY(), stall.getWidth(), stall.getHeight(), null);
//		} catch (IOException e) { e.printStackTrace(); }
//	}

	private void drawBackground(Graphics g)
	{
		File TextureToLoad = new File("res/background.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			 //g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 1000, 1000, null);
			g.drawImage(myImage, 0,0, 1000, 1000,  null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawPlayer(int x, int y, int width, int height, String texture,Graphics g) { 
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Graphics2D graphics2D = (Graphics2D)g;
			graphics2D.setRenderingHint(
					RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY
			);

			BufferedImage myImage = ImageIO.read(TextureToLoad);
			// centre of object
			int cx = myImage.getWidth() / 2;
			int cy = myImage.getHeight() / 2;
			AffineTransform oldAT = graphics2D.getTransform();
			graphics2D.translate(cx+x, cy+y);

			//int currentPositionInAnimation= ((int) ((CurrentAnimationTime%40)/10))*32; //slows down animation so every 10 frames we get another frame so every 100ms
			graphics2D.rotate(imageAngleRad+1.5708); //1.5708 is 90 degrees in radian
			graphics2D.translate(-x, -y);
			graphics2D.drawImage(myImage, x-40, y-40, width, height,  null);
			graphics2D.setTransform(oldAT);

			//credit to this user from stack overflow for showing how to do mouse following
			//https://stackoverflow.com/questions/26607930/java-rotate-image-towards-mouse-position
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	}

	private void drawHUD(Revolver revolver, Graphics g) {

		Font font = new Font("Courier", Font.BOLD, 20);

		g.setFont(font);
		g.setColor(Color.white);
		g.drawString((revolver.getAmmo() + " / " + revolver.getCapacity() ), 940, 950);

		if (revolver.isCocked() ) {
			g.setColor(Color.white);
		}
		else
			g.setColor(Color.red);

		g.drawString("Fire", 895, 950);

		g.setColor(Color.white);

		g.drawString("HP: " + gameworld.getHealth(), 830, 950);

		/*
		This method was partially inspired by this article
		https://www.geeksforgeeks.org/jlabel-java-swing/
		 */
	}

//	private void displayDirection(Graphics g) {
//		Font font = new Font("Courier", Font.BOLD, 50);
//		String direction = gameworld.getDirection();
//		long then = System.currentTimeMillis();
//		g.setFont(font);
////		Timer timer = new Timer(3000, (e) -> {
////			g.drawString(("A wind blows from the " + direction + "..."), 500, 500);
////		});
////		timer.start();
//
//		if (System.currentTimeMillis() == (then+300) ) {
//			g.drawString(("A wind blows from the " + direction + "..."), 500, 500);
//		}
//	}


	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) {
		imageAngleRad = getMouseAngle(e);
		repaint();
	}

	public double getMouseAngle(MouseEvent e) {
		double xDist = (e.getX()-gameworld.getPlayer().getCentre().getX());
		double yDist = (e.getY()-gameworld.getPlayer().getCentre().getY());
		double angle = Math.atan2(yDist, xDist);
		return  angle;
	}

	public double getImageAngleRad() { return this.imageAngleRad; }

}
