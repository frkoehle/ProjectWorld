import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.awt.TextRenderer;
//import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class World extends GLJPanel {

	/**
	 * This class manages the window
	 * 
	 * @author Daniel Kendix (dkendix@gmail.com)
	 */
	DecimalFormat df = new  DecimalFormat ("#.##");
	TextRenderer renderer;
	ArrayList<Building> buildings = new ArrayList<Building>();
	double field = 50; //Field of view angle
	double view = 300; //View distance
	double eyelevel = 5;
	Player p;

	GLU glu = new GLU();
	/**
	 * Constructor
	 * 
	 * @param defaultCapab
	 */
	public World(GLCapabilities defaultCapab){
		super(defaultCapab);
		p = new Player(10, 00, 10);
		generateWorld();

		lastPhysicsTime = System.nanoTime();

		addGLEventListener(new GLEventListener() {
			public void display( GLAutoDrawable drawable ) {

				/* TEXT ON SCREEN */
				render( drawable.getGL().getGL2(), 
						drawable.getWidth(), drawable.getHeight() );
				renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
				// optionally set the color
				renderer.setColor(1.0f, 1f, 1f, .8f);
				renderer.draw("Perlin Noise function created by Patrick Owen", 0, 10);
				renderer.draw("Camera: " + df.format(p.pos[0]) + ", " + df.format(p.pos[1]) + ", " + df.format(p.pos[2]), 0, 0);
				// ... more draw commands, color changes, etc.
				renderer.endRendering();


			}

			public void init(GLAutoDrawable drawable) {
				drawable.getGL().glClearColor(0.0f,0.0f,0.0f,0.0f);  

				//make sure hidden faces aren't drawn
				drawable.getGL().glEnable( GL.GL_DEPTH_TEST );
				drawable.getGL().glDepthFunc(GL.GL_LEQUAL);
			}

			@Override
			public void dispose(GLAutoDrawable drawable) {

			}

			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width,
					int height) {

				setup( drawable.getGL().getGL2(), width, height );

			}

		});
	}

	/**
	 * Setup method
	 * 
	 * @param gl2
	 * @param width
	 * @param height
	 */
	public  void setup( GL2 gl, int width, int height ) {
		resetPerspective(gl);

		renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
		
		gl.glEnable(GL2.GL_NORMALIZE);

		/* SETTING UP LIGHTS */
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[] {.7f,.7f,.7f,1}, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, new float[] {.7f,.7f,.7f,1}, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[] {10,10,10,1}, 0);


		//	gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		
		/*
		gl.glEnable (GL.GL_LINE_SMOOTH);
		gl.glEnable (GL.GL_BLEND);
		gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint (GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE); */

		
		//TEXTURE

		Texture tex=null;
		try {
			tex = TextureIO.newTexture(new File("bin/grass.jpg"), false);
		} catch (GLException e) {
			System.out.println("GLEXCEPTION");
		} catch (IOException e) {
			System.out.println("IOEXCEPTION");
		}

		//tex.enable();
		//tex.bind();

		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

		gl.glViewport( 0, 0, width, height );
	}


	// Our physics must be independent of how fast we can render
	long lastPhysicsTime;
	private void updatePhysics() {
		final double PFPS = 60;
		final long nsBetweenFrames = (long) (Math.pow(10, 9) / PFPS);
		final long now = System.nanoTime();
		while (now - lastPhysicsTime > nsBetweenFrames) {
			p.update();
			lastPhysicsTime += nsBetweenFrames;
		}
	}
	
	/**
	 * Renders everything
	 * 
	 * @param gl
	 * @param width
	 * @param height
	 */
	private void resetPerspective(GL2 gl2){
		gl2.glMatrixMode( GL2.GL_PROJECTION );
		gl2.glLoadIdentity();
		gl2.glClearColor(0f, 0f, 0f, 0f); //black

		GLU glu = new GLU();

		glu.gluPerspective(field,1.0,1.0,view);

	}
	public  void render( GL2 gl, int width, int height )
	{
		updatePhysics();
		
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();
		
		/* Code to move the camera (it is in the same place as the player for now) */
		glu.gluLookAt(p.pos[0],p.pos[1]+eyelevel,p.pos[2]
				,p.pos[0]+p.d*p.a
				,p.pos[1]+p.c+eyelevel
				,p.pos[2]-p.d*p.b
				,0,1,0); //good

		gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		
		/* AXIS */
		/*
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(0, 0, 1000);
		gl.glVertex3f(0, 0, -1000);
		gl.glEnd();
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(0, 1000, 0);
		gl.glVertex3f(0, -1000, 0);
		gl.glEnd();
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(1000, 0, 0);
		gl.glVertex3f(-1000, 0, 0);
		gl.glEnd();
*/

		/* Buildings */
		for(Building a : buildings)
			if (a.within(view, p.pos[0], p.pos[2]))
				a.draw(gl);
	}

	public void generateWorld()
	{
		for(int i = 0; i < 80 ; i ++)
		{
			addBuilding(10);
			System.out.println(i);
		}
	}
	public void addBuilding(float yard)
	{
		boolean added = false;
		while(added == false)
		{
			boolean space = true;
			double st = (int)(Math.random()*10) + 8;
			Building build = new Building(
					(int)(Math.random()*7 + 2),
					(int)(Math.random()*25)+20 , 
					(int)(Math.random()*25)+20 , 
					0f, 0f, 0f, yard);
			if(buildings.size() == 0)
			{
				System.out.println("SOIRJFSIODJF");
				buildings.add(build);
				added = true;
			}
			else
				for(int i = (int)buildings.get(buildings.size()-1).pos[0] ; i < 1000 && added == false;  i ++)
					for(int j = 0 ; j < 500 && added == false;  j ++)
					{
						{
							build.setPos(new float[] {i,0,j});
							space = true;
							for(Building a : buildings)
							{
								if(BoundingBox.intersect(a.box, build.box))
								{
									space = false;
								}
							}
							if(space == true)
							{
								System.out.println(build.dim[0] + " " + build.dim[1]);
								System.out.println(i + " "  + j);
								System.out.println("YAY");
								buildings.add(build);
								added = true;
							}
						}
					}
		}
	}
}
