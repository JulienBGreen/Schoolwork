/**
 * SceneGraph3DAPI is the API used for 3D hierarchical modeling, specifically
 * for lab5(midterm).  SceneGraphNode takes a a texture and draws it, with two
 * objects extending from it.  Compound object is an object made up of several
 * TransformedObject.  TransformedObject are the only object that translations
 * can be applied to.  Also contains basic shapes to be drawn.
 * @author John Hibbard
 * @author Julien Green
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;


public class SceneGraph3DAPI {
	
	private static GLUT glut = new GLUT(); // An object for drawing GLUT shapes.
	private static GLU glu = new GLU(); // An object for calling GLU functions.
	
	/**
	 * SceneGraphNode is the node in the tree
	 */
	public static abstract class SceneGraphNode{
		//holds the parent of the node
		SceneGraphNode parent;
		Texture texture;		//texture for the node
		
		/**
		 * sets the texture of the node
		 * @param t - input Texture that the node will be set to have
		 */
		SceneGraphNode setTexture(Texture t){
			this.texture = t;
			return this;
		}
		/**
		 * draws objects in the nodes
		 * @param gl - current gl that is doing the drawing
		 */
		//draws the object
		final void draw(GL2 gl){
			//Texture saveTexture = null;
			if(texture != null){
				//saveTexture = gl.getTexture();
				texture.bind(gl);
			}
			doDraw(gl);
			//if(saveTexture != null){
			//	saveTexture.bind(gl);
			//}
		}
		abstract void doDraw(GL2 gl);
	}
	/**
	 * CompoundObject() is a series of Transformed objects that 
	 * are drawn
	 */
	public static class CompoundObject extends SceneGraphNode{
		ArrayList<SceneGraphNode> subobjects = new ArrayList<SceneGraphNode>();
		CompoundObject add(SceneGraphNode node){
			node.parent = this;
			subobjects.add(node);
			return this;
		}
		void doDraw(GL2 gl){
			for(SceneGraphNode node: subobjects){
				node.draw(gl);
			}
				
		}
	}
	
	/*
	 * All of the following create shapes based around (0,0,0)
	 */
	
	/**
	 * method which adds a basic cube shape to the given node
	 */
	public static SceneGraphNode cube = new SceneGraphNode(){
		void doDraw(GL2 gl) { glut.glutSolidCube(1); }
	}; // end of cube
	
	/**
	 * method which adds a basic cylinder shape to the given node
	 */
	public static SceneGraphNode cylinder = new SceneGraphNode(){
		void doDraw(GL2 gl) { glut.glutSolidCylinder(1, 1, 32, 8); }
	}; // end of cylinder
	
	/**
	 * method which adds a basic Sphere shape to the given node
	 */
	public static SceneGraphNode sphere = new SceneGraphNode(){
		void doDraw(GL2 gl) { glut.glutSolidSphere(1, 32, 32);}
	}; // end of sphere
	
	/**
	 * method which adds a basic cone shape to the given node
	 */
	public static SceneGraphNode cone = new SceneGraphNode(){
		void doDraw(GL2 gl) { glut.glutSolidCone(1,1,32,8); }
	}; // end of cone
	
	/**
	 * Given a set of coordinates in the form of a Polyhedron type object 
	 * this method produces a polyhedron shape
	 * @param poly - the polyhedron that will be drawn by this method
	 */
	
	public static SceneGraphNode indexedFace(Polyhedron poly){
		SceneGraphNode ifs = new SceneGraphNode(){
			void doDraw(GL2 gl) {
				for(int i = 0; i < poly.faces.length; i++){
					gl.glNormal3d(poly.normals[i][0], poly.normals[i][1], poly.normals[i][2]);
					gl.glBegin(gl.GL_TRIANGLE_FAN);
					for(int j = 0; j < 3; j++){
						int vertexNum = poly.faces[i][j];
						double x,y,z;
						x = poly.vertices[vertexNum][0];
						y = poly.vertices[vertexNum][1];
						z = poly.vertices[vertexNum][2];
						gl.glVertex3d(x, y, z);
					}
					gl.glEnd();
				}
			}
		};
		return ifs;
	} // end of indexedFace
	
	/**
	 * TransformedObject() is what is added to CompoundObjects to 
	 * create shapes to draw
	 */
	public static class TransformedObject extends SceneGraphNode{
		SceneGraphNode object;
		double rotate = 0, x = 0, y = 0, z = 0;					//variables for rotating
		double scaleX = 1, scaleY = 1, scaleZ = 1;				//variables for scaling
		double translateX = 0, translateY = 0, translateZ = 0;	//variables for translation
		Texture tex;
		
		/**
		 * constructor for Transformed objects, these are attached to scenegraph nodes
		 *  and are what is actually drawn
		 * @param object - the SceneGraphNode that this Transformed Object is a part of
		 */
		TransformedObject(SceneGraphNode object){
			this.object = object;
		}
		
		/**
		 * sets the rotation of the TransformedObject
		 * @param degrees - amount, in degrees, that the object will be rotated
		 * @param x -sets x
		 * @param y -sets y
		 * @param z -sets z
		 * @return
		 */
		TransformedObject setRotation(double degrees, double x, double y, double z){
			rotate = degrees;
			this.x = x;
			this.y = y;
			this.z = z;
			return this;
		}
		/**
		 * sets the translation of the TransformedObject
		 * @param dx - amount in change of x
		 * @param dy - amount in change of y
		 * @param dz - amount in change of z
		 * @return
		 */
		TransformedObject setTranslation(double dx, double dy, double dz){
			translateX = dx;
			translateY = dy;
			translateZ = dz;
			return this;
		}
		/**
		 * sets the scale of the TransformedObject
		 * @param sx -amount of scaling to x
		 * @param sy -amount of scaling to y
		 * @param sz -amount of scaling to z
		 * @return
		 */
		TransformedObject setScale(double sx, double sy, double sz){
			scaleX = sx;
			scaleY = sy;
			scaleZ = sz;
			return this;
		}
		/**
		 * Method to clone the object!
		 * @param t object being cloned
		 * @return new cloned object
		 */
		TransformedObject clone(TransformedObject t){
			return new TransformedObject(t);
		}
		/**
		 * method that sets the texture of the object!
		 * @param tex - input Texture being applied
		 */
		TransformedObject setTexture(Texture tex){
			this.tex = tex;
			return this;
		}
		
		/**
		 * draws it!
		 * @param gl - instance of openGL that will do the drawing of the object(s)
		 */
		void doDraw(GL2 gl){
			//make sure transformations don't effect anything else
			gl.glEnable(gl.GL_TEXTURE_2D );
			gl.glMatrixMode(gl.GL_TEXTURE);
			gl.glLoadIdentity(); 
			gl.glMatrixMode(gl.GL_MODELVIEW);
			tex.bind(gl);
			
			gl.glPushMatrix();
			if(translateX != 0 || translateY != 0 || translateZ != 0){
				gl.glTranslated(translateX, translateY, translateZ);
			}
			if(scaleX != 0 || scaleY != 0 || scaleZ != 0){
				gl.glScaled(scaleX, scaleY, scaleZ);
			}
			if(x != 0 || y != 0 || z != 0){
				gl.glRotated(rotate, x, y, z);
			}
			object.draw(gl);
			gl.glPopMatrix();
		}
	}
}
