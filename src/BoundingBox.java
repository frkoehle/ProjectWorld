
public class BoundingBox {
	double[] p1 = new double[2];
	double[] p2 = new double[2];
	double[] p3 = new double[2];
	double[] p4 = new double[2];
	
	public BoundingBox(double x1, double y1, double x2, double y2)
	{
		p1[0] = x1;
		p1[1] = y1;
		p2[0] = x1;
		p2[1] = y2;
		p3[0] = x2;
		p3[1] = y2;
		p4[0] = x2;
		p4[1] = y1;
	}
	
	public static boolean intersect(BoundingBox a, BoundingBox b)
	{
		if(pointIn(a, b.p1) || pointIn(a, b.p2) || pointIn(a, b.p3) || pointIn(a, b.p4))
		{
			return true;
		}
		if(pointIn(b, a.p1) || pointIn(b, a.p2) || pointIn(b, a.p3) || pointIn(b, a.p4))
		{
			return true;
		}
		return false;
	}
	public static boolean pointIn(BoundingBox a, double[] b)
	{
		if(a.p1[0]-10 <= b[0] && a.p3[0] >= b[0] && a.p1[1] <= b[1] && a.p3[1] >= b[1])
		{
			return true;
		}
		return false;
	}
}
