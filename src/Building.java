import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;


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
		setPos(new float[] {x, y, z});
	}

	public void setPos(float[] pos) {
		this.pos = pos;
		box = new BoundingBox(pos[0]-yard, pos[2]-yard, pos[0]+dim[0]+yard, pos[2]+dim[1]+yard);	
	}


	ArrayList<RenderObject> renderComponents = new ArrayList<RenderObject>();
	
	public void draw(GL gl2)
	{
		GL2 gl = gl2.getGL2();

		if (renderComponents.isEmpty()) {
			for(int i = 0 ; i < floors ; i ++)
			{
				float y = i * (between+ceiling)+ pos[1];
				addOrtho(new float[] {pos[0], y, pos[2]}, new float[] {dim[0], ceiling, dim[1]});
				y = i * (between+ceiling)+ ceiling + pos[1];
				addOrtho(new float[] {pos[0], y, pos[2]}, new float[] {dim[0], between, dim[1]});
			}
		}
		
		gl.glLineWidth(2);
		gl.glColor3f(0, 0, 0);
		
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		for (RenderObject r : renderComponents)
			r.draw(gl);
	}
		
	
	static class RenderObject {
		final int vertices;
		final FloatBuffer points;
		final int mode;
		float r=0, g=0, b=0;
		public RenderObject(int nvertices, int nmode) {
			vertices = nvertices;
			points =  Buffers.newDirectFloatBuffer(vertices * 3);
			mode = nmode;
		}
		
		void vtx(float x, float y, float z) {
			points.put(x);
			points.put(y);
			points.put(z);
		}
		
		void color(float _r, float _g, float _b) {
			r = _r;
			g = _g;
			b = _b;
		}
		
		private void setAsVertexPointer(GL2 gl) {
			gl.glVertexPointer(3, GL.GL_FLOAT, 0, points);
		}
		void draw(GL2 gl) {
			gl.glColor3f(r,g,b);
			setAsVertexPointer(gl);
			gl.glDrawArrays(mode, 0, vertices);
		}
		
		void rewind() {
			points.rewind();
		}
	}
	
	public void addOrtho(float[] pos, float[] dim)
	{
		RenderObject sides = new RenderObject(10, GL2.GL_QUAD_STRIP);
		
		// Normals are disabled for now, since we don't care about lighting buildings (yet?)
		//gl.glNormal3f(1,0,0);
		//gl.glBegin(GL2.GL_QUAD_STRIP);
		// sides
		sides.color(0,0,0);
		sides.vtx(pos[0]			, pos[1] 			,pos[2]);
		sides.vtx(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		sides.vtx(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		sides.vtx(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);

		
		//gl.glNormal3f(0,0,1);
		sides.vtx(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		sides.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);

		
		//gl.glNormal3f(0,0,-1);
		sides.vtx(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		sides.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);

		
		//gl.glNormal3f(1,0,0);
		sides.vtx(pos[0]			, pos[1] 			,pos[2]);
		sides.vtx(pos[0]			, pos[1]+dim[1]		,pos[2]);
		sides.rewind();
		
		renderComponents.add(sides);
		
		//TOP
		//gl.glNormal3f(0,1,0);
		RenderObject top = new RenderObject(4, GL2.GL_QUADS);
		top.color(0,0,0);
		top.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		top.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		top.vtx(pos[0]			, pos[1]+dim[1]		,pos[2]+dim[2]);
		top.vtx(pos[0]			, pos[1]+dim[1]		,pos[2]);
		top.rewind();
		renderComponents.add(top);
		
		RenderObject lineStrip = new RenderObject(10, GL2.GL_LINE_STRIP);
		lineStrip.color(1,1,1);
		lineStrip.vtx(pos[0]			, pos[1] 			,pos[2]);
		lineStrip.vtx(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		lineStrip.vtx(pos[0]			, pos[1]+dim[1] 	,pos[2]+dim[2]);
		lineStrip.vtx(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		lineStrip.vtx(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		lineStrip.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		lineStrip.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]);
		lineStrip.vtx(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		lineStrip.vtx(pos[0]			, pos[1] 			,pos[2]);
		lineStrip.vtx(pos[0]			, pos[1]+dim[1]		,pos[2]);
		lineStrip.rewind();
		renderComponents.add(lineStrip);
		
		RenderObject lines = new RenderObject(8, GL2.GL_LINES);
		lines.color(1,1,1);
		lines.vtx(pos[0]			, pos[1] 			,pos[2]);
		lines.vtx(pos[0]			, pos[1] 			,pos[2]+dim[2]);
		
		lines.vtx(pos[0]+dim[0]		, pos[1] 			,pos[2]+dim[2]);
		lines.vtx(pos[0]+dim[0]		, pos[1] 			,pos[2]);
		
		lines.vtx(pos[0]+dim[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		lines.vtx(pos[0]		, pos[1]+dim[1] 	,pos[2]+dim[2]);
		
		lines.vtx(pos[0]			, pos[1]+dim[1] 	,pos[2]);
		lines.vtx(pos[0]+dim[0]			, pos[1]+dim[1] 	,pos[2]);
		lines.rewind();
		renderComponents.add(lines);
		
	}

	public boolean within(double view, double x, double z) {
		//FIXME? add "grace" to consider the dimensions of building
		return Math.pow(view, 2) > Math.pow(pos[0] - x, 2) + Math.pow(pos[2] - z, 2);
	}
}
