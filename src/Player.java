
public class Player{

	double incrz = 0;
	double incrx = 0;
	double a;
	double b;
	double c;
	double d;
	double jump = 0;
	double dt = .1;
	private double veiwRotx;
	private double veiwRoty;
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
		a = Math.sin((double)(veiwRotx)/360*2*Math.PI);
		b = Math.cos((double)(veiwRotx)/360*2*Math.PI);
		c = Math.sin((double)(veiwRoty)/360*2*Math.PI);
		d = Math.cos((double)(veiwRoty)/360*2*Math.PI);

		if(fly == 0)
		{
			vel[0] = a*incrz + b*incrx;
			vel[1] += acc[1]*dt;
			vel[2] = a*incrx - b*incrz;
			
			pos[0] += vel[0];
			pos[1]+=vel[1];
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
	public double getVeiwRotx()
	{
		return veiwRotx;
	}

	public void setVeiwRotx(double veiwRotx)
	{
		this.veiwRotx = veiwRotx%360;
	}

	public double getVeiwRoty()
	{
		return veiwRoty;
	}

	public void setVeiwRoty(double veiwRoty)
	{
		if(veiwRoty>89)
			veiwRoty = 89;
		if(veiwRoty<-89)
			veiwRoty = -89;
		this.veiwRoty = veiwRoty;
	}

}
