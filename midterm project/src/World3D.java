import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;

/*
 * World3D is the midterm picture using OpenGL to create a 3D picture
 * 
 * Requires: SceneGraph3DAPI.java, JOGL/OpenGL
 * Author: John Hibbard, Julien Green
 * Date: 10/22/15
 */
public class World3D extends GLJPanel implements GLEventListener{
		
	/*
	 * constructor for World3D!
	 */
	public World3D(){
		super( new GLCapabilities(null) );
		setPreferredSize( new Dimension(700,700) );
		
	}
	
	//-----------------------------------------------------------------------------------------
	
	private SceneGraph3DAPI.CompoundObject world;

	/*
	 * display() is automatically called when the panel is drawn!
	 */
	public void display(GLAutoDrawable drawable) {
		init(drawable);
		world.add(new SceneGraph3DAPI.TransformedObject(SceneGraph3DAPI.cone));
		 world.draw(drawable.getGL().getGL2());
	}


	/*
	 * init() is called before the window is opened to initialize openGL!
	 */
	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_LIGHTING);  
		gl2.glEnable(GL2.GL_LIGHT0);	
	}

	//-----------------------------------------------------------------------------------------
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) { /* ignore */	}
	
	public void dispose(GLAutoDrawable drawable) { /* ignore*/}
	
	/*
	 * Some crazy fun materials that may or may not have been copied from lab6 plus some 
	 * others!
	 */
	private final static float[][] materials = {
			{ /* "emerald" 0*/   0.0215f, 0.1745f, 0.0215f, 1.0f, 0.07568f, 0.61424f, 0.07568f, 1.0f, 0.633f, 0.727811f, 0.633f, 1.0f, 0.6f*128 },
			{ /* "jade" 1*/   0.135f, 0.2225f, 0.1575f, 1.0f, 0.54f, 0.89f, 0.63f, 1.0f, 0.316228f, 0.316228f, 0.316228f, 1.0f, 0.1f*128 },
			{ /* "obsidian" 2*/   0.05375f, 0.05f, 0.06625f, 1.0f, 0.18275f, 0.17f, 0.22525f, 1.0f, 0.332741f, 0.328634f, 0.346435f, 1.0f, 0.3f*128 },
			{ /* "pearl" 3*/   0.25f, 0.20725f, 0.20725f, 1.0f, 1.0f, 0.829f, 0.829f, 1.0f, 0.296648f, 0.296648f, 0.296648f, 1.0f, 0.088f*128 },
			{ /* "ruby" 4*/   0.1745f, 0.01175f, 0.01175f, 1.0f, 0.61424f, 0.04136f, 0.04136f, 1.0f, 0.727811f, 0.626959f, 0.626959f, 1.0f, 0.6f*128 },
			{ /* "turquoise" 5*/   0.1f, 0.18725f, 0.1745f, 1.0f, 0.396f, 0.74151f, 0.69102f, 1.0f, 0.297254f, 0.30829f, 0.306678f, 1.0f, 0.1f*128 },
			{ /* "brass" 6*/   0.329412f, 0.223529f, 0.027451f, 1.0f, 0.780392f, 0.568627f, 0.113725f, 1.0f, 0.992157f, 0.941176f, 0.807843f, 1.0f, 0.21794872f*128 },
			{ /* "bronze" 7*/   0.2125f, 0.1275f, 0.054f, 1.0f, 0.714f, 0.4284f, 0.18144f, 1.0f, 0.393548f, 0.271906f, 0.166721f, 1.0f, 0.2f*128 },
			{ /* "chrome" 8*/   0.25f, 0.25f, 0.25f, 1.0f, 0.4f, 0.4f, 0.4f, 1.0f, 0.774597f, 0.774597f, 0.774597f, 1.0f, 0.6f*128 },
			{ /* "copper" 9*/   0.19125f, 0.0735f, 0.0225f, 1.0f, 0.7038f, 0.27048f, 0.0828f, 1.0f, 0.256777f, 0.137622f, 0.086014f, 1.0f, 0.1f*128 },
			{ /* "gold" 10*/   0.24725f, 0.1995f, 0.0745f, 1.0f, 0.75164f, 0.60648f, 0.22648f, 1.0f, 0.628281f, 0.555802f, 0.366065f, 1.0f, 0.4f*128 },
			{ /* "silver" 11*/   0.19225f, 0.19225f, 0.19225f, 1.0f, 0.50754f, 0.50754f, 0.50754f, 1.0f, 0.508273f, 0.508273f, 0.508273f, 1.0f, 0.4f*128 },
			{ /* "cyan plastic" 12*/   0.0f, 0.1f, 0.06f, 1.0f, 0.0f, 0.50980392f, 0.50980392f, 1.0f, 0.50196078f, 0.50196078f, 0.50196078f, 1.0f, .25f*128 },
			{ /* "green plastic" 13*/   0.0f, 0.0f, 0.0f, 1.0f, 0.1f, 0.35f, 0.1f, 1.0f, 0.45f, 0.55f, 0.45f, 1.0f, .25f*128 },
			{ /* "red plastic" 14*/   0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.7f, 0.6f, 0.6f, 1.0f, .25f*128 },
			{ /* "cyan rubber" 15*/   0.0f, 0.05f, 0.05f, 1.0f, 0.4f, 0.5f, 0.5f, 1.0f, 0.04f, 0.7f, 0.7f, 1.0f, .078125f*128 },
			{ /* "green rubber" 16*/   0.0f, 0.05f, 0.0f, 1.0f, 0.4f, 0.5f, 0.4f, 1.0f, 0.04f, 0.7f, 0.04f, 1.0f, .078125f*128 },
			{ /* "red rubber" 17*/   0.05f, 0.0f, 0.0f, 1.0f, 0.5f, 0.4f, 0.4f, 1.0f, 0.7f, 0.04f, 0.04f, 1.0f, .078125f*128 },
	};

}
