import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class Building {

	//all buildings have 8ft feet ceilings unless otherwise set
	float ceiling = 8;

	//1.5 feet between floors
	float between = 1.5f;
	double yard;
	BoundingBox box;
	double floors;
	float[] dim = new float[2]; //x and z
	//houses will have a 6 feet square around them which will be their lot not including the street
	float[] pos = new float[3];

	public Building(int fl, float len, float wid, float x, float y, float z, float ya)
	{
		yard = ya;
		floors = fl;
		dim[0] = len;
		dim[1] = wid;
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;
		box = new BoundingBox(pos[0]-yard, pos[2]-yard, pos[0]+dim[0]+yard, pos[2]+dim[1]+yard);
	}

	public void setPos(float[] pos) {
		this.pos = pos;
		box = new BoundingBox(pos[0]-yard, pos[2]-yard, pos[0]+dim[0]+yard, pos[2]+dim[1]+yard);
	}



	public void draw(GL gl2)
	{
		GL2 gl = gl2.getGL2();
		for(int i = 0 ; i < floors ; i ++)
		{
			float y = i * (between+ceiling)+ pos[1];
			Building.drawOrtho(gl, new float[] {pos[0], y, pos[2]}, new float[] {dim[0], ceiling, dim[1]});
			y = i * (between+ceiling)+ ceiling + pos[1];
			Building.drawOrtho(gl, new float[] {pos[0], y, pos[2]}, new float[] {dim[0], between, dim[1]});
			
		}
	}
	
	public static void drawOrtho(GL2 gl , float[] pos, float[] dim)
	{
	
		gl.glLineWidth(2);
		gl.glColor3f(0, 0, 0);
		gl.glNormal3f(1,0,0);
		gl.glBegin(GL2.GL_QUAD_STRIP);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);

		
		gl.glNormal3f(0,0,1);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);

		
		gl.glNormal3f(0,0,-1);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);

		
		gl.glNormal3f(1,0,0);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1]		,pos[2]);


		gl.glEnd();
		
		//TOP
		gl.glNormal3f(0,1,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1]		,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1]		,pos[2]);
		gl.glEnd();
		
		gl.glColor3f(1,1,1);
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1]+dim[1]		,pos[2]);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3f(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		
		gl.glVertex3f(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		
		gl.glVertex3f(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3f(pos[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		
		gl.glVertex3f(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3f(pos[0]+dim[0]			, pos[1]+dim[1] 	,pos[2]);
		
		gl.glEnd();
	}
}
