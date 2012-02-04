import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class Building {

	//all buildings have 8ft feet ceilings unless otherwise set
	double ceiling = 8;

	//1.5 feet between floors
	double between = 1.5;
	double yard;
	BoundingBox box;
	double floors;
	double[] dim = new double[2]; //x and z
	//houses will have a 6 feet square around them which will be their lot not including the street
	double[] pos = new double[3];

	public Building(int fl, double len, double wid, double x, double y, double z, double ya)
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

	public void setPos(double[] pos) {
		this.pos = pos;
		box = new BoundingBox(pos[0]-yard, pos[2]-yard, pos[0]+dim[0]+yard, pos[2]+dim[1]+yard);
	}



	public void draw(GL gl2)
	{
		GL2 gl = gl2.getGL2();
		for(int i = 0 ; i < floors ; i ++)
		{
			double y = i * (between+ceiling)+ pos[1];
			Building.drawOrtho(gl, new double[] {pos[0], y, pos[2]}, new double[] {dim[0], ceiling, dim[1]});
			y = i * (between+ceiling)+ ceiling + pos[1];
			Building.drawOrtho(gl, new double[] {pos[0], y, pos[2]}, new double[] {dim[0], between, dim[1]});
			
		}
	}
	
	public static void drawOrtho(GL2 gl , double[] pos, double[] dim)
	{
		gl.glLineWidth(2);
		gl.glColor3d(0, 0, 0);
		gl.glNormal3d(1,0,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		gl.glEnd();
		
		gl.glNormal3d(0,0,1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glEnd();
		
		gl.glNormal3d(0,0,-1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		gl.glEnd();
		
		gl.glNormal3d(1,0,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1]		,pos[2]);
		gl.glEnd();
		
		//TOP
		gl.glNormal3d(0,1,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1]		,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1]		,pos[2]);
		gl.glEnd();
		
		gl.glColor3d(1,1,1);
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1]+dim[1]		,pos[2]);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]);
		gl.glVertex3d(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		
		gl.glVertex3d(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		gl.glVertex3d(pos[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		
		gl.glVertex3d(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		gl.glVertex3d(pos[0]+dim[0]			, pos[1]+dim[1] 	,pos[2]);
		
		gl.glEnd();
	}
}
