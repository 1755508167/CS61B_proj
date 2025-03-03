public class Planet {
    public double xxPos;//current x position
    public double yyPos;//current y position
    public double xxVel;//current velocity(速度) in the x direction
    public double yyVel;//current velocity(速度) in the y direction
    public double mass;//质量
    public String imgFileName;//行星的图片文件名

    //第一个构造函数
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        this.xxPos=xP;
        this.yyPos=yP;
        this.xxVel=xV;
        this.yyVel=yV;
        this.mass=m;
        this.imgFileName=img;
    }
    //第二个构造函数
    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }
	//判断两个行星是否相同
	/*	public boolean equals(Planet p){
		boolean result;
		if (this.xxPos==p.xxPos && this.yyPos == p.yyPos && this.xxVel == p.xxVel && this.yyVel == p.yyVel && this.mass == p.mass && this.imgFileName == p.imgFileName)
		{
			result=true;
		}
		else{
			result=false;
		}
		return result;
		
	}*/

	//计算两个行星之间的距离
	public double calcDistance(Planet p){
		double r;
		double dx;
		double dy;
		dx=Math.abs(this.xxPos-p.xxPos);
		dy=Math.abs(this.yyPos-p.yyPos);
		r=Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
		
		return r;
		
	}
	//返回一个指定行星对此行星的力
	public double calcForceExertedBy(Planet p){
		final double G=6.67e-11;
		double f;
		double r=this.calcDistance(p);
		f=(G*this.mass*p.mass)/(r*r);
		
		return f;
		
	}
	//返回一个指定行星对此行星在x方向上的力
	public double calcForceExertedByX(Planet p){
		double fx;
		double f=this.calcForceExertedBy(p);
		double dx=Math.abs(this.xxPos-p.xxPos);
		double r=this.calcDistance(p);
		//System.out.println(r);
		if (r == 0.0){
			fx=0.0;
		}
		else{
			fx=(f*dx)/(r);
		}
		
		return fx;
	}
	
	//计算一个指定行星对此行星在y方向上的力
	public double calcForceExertedByY(Planet p){
		double fy;
		double f=this.calcForceExertedBy(p);
		double dy=Math.abs(this.yyPos-p.yyPos);
		double r=this.calcDistance(p);
		
		if (r == 0.0){
			fy=0.0;
		}
		else{
			fy=(f*dy)/(r);
		}
		
		return fy;
	}
	
	//接收一个Planet列表，计算列表中所有行星对指定行星的fx力
	public double calcNetForceExertedByX(Planet[] allPlanets){
		double fx;
		double result=0.0;
		for (Planet p : allPlanets){
			fx=this.calcForceExertedByX(p);
			result=result+fx;
		}
		return result;
	}
	//接收一个Planet列表，计算列表中所有行星对指定行星的fy力
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double fy;
		double result=0.0;
		for (Planet p : allPlanets){
			fy=this.calcForceExertedByY(p);
			result=result+fy;
		}
		return result;
	}
	//计算加速度，更新位置，接收时间，fx,fy
	public void update(double dt,double fx,double fy){
		double accelx;//x方向的加速度
		double accely;//y方向的加速度
		accelx=fx/this.mass;
		accely=fy/this.mass;
		
		//计算新的速度
		//double vx;
		//double vy;
		this.xxVel=this.xxVel+dt*accelx;
		this.yyVel=this.yyVel+dt*accely;
		
		//计算新位置
		this.xxPos=this.xxPos+dt*this.xxVel;
		this.yyPos=this.yyPos+dt*this.yyVel;
	}
	//在星球的位置绘制星球
	public void draw(double radius){
		StdDraw.picture((this.xxPos/radius)*100.0,(this.yyPos/radius)*100.0,"images/"+this.imgFileName);
	}
    public static void main(String[] args){
        Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");
		Planet p4 = new Planet(3.0, 2.0, 3.0, 4.0, 5.0, "jupiter.gif");
		Planet[] allPlanets = {p2,p3,p4};
		p1.update(2.0, 1.0, -0.5);
		System.out.println(p1.xxPos);
    }
}
