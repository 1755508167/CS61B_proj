public class NBody{
	//从文件中阅读宇宙半径
	public static double readRadius(String filename){
		In in = new In(filename);
		int p_num=in.readInt();
		double radius=in.readDouble();//宇宙半径
		return radius;
	}
	//从文件中阅读行星的数据，返回一个行星数组
	public static Planet[] readPlanets(String filename){
		In in=new In(filename);
		int p_num=in.readInt();
		double radius=in.readDouble();
		
		Planet[] result=new Planet[p_num];
		
		double xxPos;
		double yyPos;
		double xVel;
		double yVel;
		double mass;
		String imgname;
		
		
		for(int i=0;i<p_num;i++){
			xxPos=in.readDouble();
			yyPos=in.readDouble();
			xVel=in.readDouble();
			yVel=in.readDouble();
			mass=in.readDouble();
			imgname=in.readString();

			result[i]=new Planet(xxPos, yyPos, xVel, yVel, mass, imgname);
		}
		return result;

	}
	
	public static void main(String[] args){
		double T=Double.parseDouble(args[0]);
		double dt=Double.parseDouble(args[1]);//一次增加的时间
		int waitTimeMilliseconds = 10;
		String filename=args[2];
		//读取宇宙半径
		double radius=readRadius(filename);
		String imageToDraw="images/starfield.jpg";
		//行星列表
		Planet[] planets=readPlanets(filename);
		
		double time=0;//创建一个时间变量
		
		
		//double fx=planets[1].calcNetForceExertedByX(planets);
		//double fy=planets[1].calcNetForceExertedByY(planets);
		StdDraw.enableDoubleBuffering();

		while(time < T){
			Double[] xForces = new Double[planets.length];
			Double[] yForces = new Double[planets.length];
			for(int i=0;i < planets.length;i++){
				xForces[i]=planets[i].calcNetForceExertedByX(planets);
				yForces[i]=planets[i].calcNetForceExertedByY(planets);
			}
			
			for(int i=0;i< planets.length;i++){
				//更新行星数据
				planets[i].update(dt,xForces[i],yForces[i]);
				//System.out.println("已更新");
				//绘制图像
				
			}
			StdDraw.setScale(-radius,radius);//从原点开始往x正半轴绘制100个单位，x负半轴也是100个三位，y轴正负轴也是100个单位
			StdDraw.clear();
			StdDraw.picture(0,0,imageToDraw);//绘制背景图像
			for(Planet p : planets){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(waitTimeMilliseconds);
			time=time+dt;
			
		}
		
	}
	StdOut.printf("%d\n", planets.length);
	StdOut.printf("%.2e\n", radius);
	for (int i = 0; i < planets.length; i++) {
		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
					  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
	}
}