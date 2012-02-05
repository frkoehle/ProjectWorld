
public class Player{
	double incrz = 0;
	double incrx = 0;
	double a;
	double b;
	double c;
	double d;
	double jump = 0;
	double dt = .1;
	private double viewRotx;
	private double viewRoty;
	double fly = 0;
	double[] acc = new double[] {0,-9.8,0};
	double[] vel = new double[] {0,0,0};
	double[] pos = new double[3];
	public Player(int i, int j, int k)
	{
		pos[0] = i;
		pos[1] = j;
		pos[2] = k;
	}

	public void update()
	{
		a = Math.sin((double)(viewRotx)/360*2*Math.PI);
		b = Math.cos((double)(viewRotx)/360*2*Math.PI);
		c = Math.sin((double)(viewRoty)/360*2*Math.PI);
		d = Math.cos((double)(viewRoty)/360*2*Math.PI);

		
		if(fly == 0)
		{
			vel[0] = a*incrz + b*incrx;
			vel[1] += acc[1]*dt;
			vel[2] = a*incrx - b*incrz;
			
			pos[0] += vel[0];
			pos[1] += vel[1];
			pos[2] += vel[2];
			
			if(pos[1] <0)
			{
				jump = 0;
				vel[1] = 0;
				pos[1] = 0;
			}	
		}
		else
		{
			pos[0]+=d*a*incrz;
			pos[1]+=c*incrz;
			pos[2]-=d*b*incrz;
			pos[0]+=b*incrx;
			pos[2]+=a*incrx;
		}

	}

	//Getters and Setters
	public double getViewRotx()
	{
		return viewRotx;
	}

	public void setViewRotx(double viewRotx)
	{
		this.viewRotx = viewRotx%360;
	}

	public double getViewRoty()
	{
		return viewRoty;
	}

	public void setViewRoty(double viewRoty)
	{
		if(viewRoty>89)
			viewRoty = 89;
		if(viewRoty<-89)
			viewRoty = -89;
		this.viewRoty = viewRoty;
	}

}
