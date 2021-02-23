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
			drawBullet(temp, g);
		});

		gameworld.getEnemyBullet().forEach((temp) ->
		{
			drawBullet(temp, g);
		});
		
		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
			//drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);
		 	drawEnemies(temp, g, x, y);
	    });
	}
	
	private void drawEnemies(Bandit bandit, Graphics g, int pX, int pY) {
		bandit.draw(g, pX, pY);
	}

	private void drawBackground(Graphics g)
	{
		File TextureToLoad = new File("res/spacebackground.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			 g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 1000, 1000, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//private void drawBullet(int x, int y, int width, int height, String texture,Graphics g)
	private void drawBullet(Bullet bullet, Graphics g)
	{
		/*File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			//64 by 128 
			 g.drawImage(myImage, x,y, x+width, y+height, 0 , 0, 63, 127, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		 bullet.draw(g);
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

			//credit to this stack overflow for showing how to do mouse following
			//https://stackoverflow.com/questions/26607930/java-rotate-image-towards-mouse-position
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
		//Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time 
		// Bullets from https://opengameart.org/forumtopic/tatermands-art 
		// background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images
		
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

	}


	@Override
	public void mouseDragged(MouseEvent e) {

	}

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
