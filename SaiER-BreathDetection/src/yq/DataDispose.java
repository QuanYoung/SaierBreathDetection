package yq;

import java.util.ArrayList;


public class DataDispose implements Runnable{
//	private double window;
	private ArrayList<Double>  t1Time;
	private ArrayList<Double>  t1Phase;
	private ArrayList<Double>  t2Time;
	private ArrayList<Double> t2Phase;
	private int count1;
	private int count2;
	public DataDispose() {}
	public DataDispose(int count1,int count2,ArrayList<Double> t1Time, ArrayList<Double> t1Phase,ArrayList<Double> t2Time, ArrayList<Double> t2Phase) {
//		this.window=window;
		this.count1=count1;
		this.count2=count2;
		this.t1Time=t1Time;
		this.t1Phase=t1Phase;
		this.t2Time=t2Time;
		this.t2Phase=t2Phase;
		
	}
	public DataDispose(int count1,int count2,ArrayList<Double> t1Time, ArrayList<Double> t1Phase,ArrayList<Double> t2Time, ArrayList<Double> t2Phase,int temp) {
//		this.window=window;
		this.count1=count1;
		this.count2=count2;
		this.t1Time=t1Time;
		this.t1Phase=t1Phase;
		this.t2Time=t2Time;
		this.t2Phase=t2Phase;
		 dataDispose(count1,count2,t1Time,t1Phase,t2Time,t2Phase);
	}
	@Override
	public void run() {
		dataDispose(count1,count2, t1Time,t1Phase,t2Time,t2Phase);
		
	}

	
	public static void dataDispose(int count1, int count2, ArrayList<Double> t1Time, ArrayList<Double> t1Phase,ArrayList<Double> t2Time, ArrayList<Double> t2Phase){
//		System.out.println(t2Phase.size()+" "+t2Phase.get(0)+"--------------"+t1Phase.size()+" "+t1Phase.get(0));
		System.out.println("--------------------一次新处理----------------------");
		//每轮处理会参考前一轮的数据后几个用于数据处理
		double con1=1.5;
		double con2=3.5;
		
			//数据处理//
			//倒排处理和周期环绕处理 需要用到之前处理好的数据
			double arrdptag1[]=Utils.HandInvertPI5(t1Phase, con1, con2,count1);

			
			double arrdptag2[]=Utils.HandInvertPI5(t2Phase, con1, con2,count2);

			
			//差值
			double[][] interpolationarr=Utils.Interpolation(count1,count2,t1Time, t2Time, t1Phase, t2Phase);

			

			double fs=1/((interpolationarr[interpolationarr.length-1][0]-interpolationarr[0][0])/interpolationarr.length);

		
			
			main.java.uk.me.berndporr.iirj.Butterworth bw=new main.java.uk.me.berndporr.iirj.Butterworth();
			bw.bandPass(4, fs, 0.3, 0.2);
			System.out.println("batterworth滤波完毕");
			double temparr[]=Utils.domeanfilter(interpolationarr);

			
			//计算butter worth 滤波后的数据
			for(int i=0;i<temparr.length;i++) {
				temparr[i]=bw.filter(temparr[i]);
			}
			
			//简单滑动均值过滤

			System.out.println("画图");
			for(int i=0;i<temparr.length;i++) {
			
				if(Math.abs(temparr[i])>1) {
					int count=0;
					int sum=0;
					for(int j=0;j<5;j++) {
						if(Math.abs(temparr[i+j])<1) {
							count+=1;
							sum+=temparr[i+j];
						}
						temparr[i]=sum/count;
					}
				}
				
				//计算呼吸次数

				if(global.flagbreath&&temparr[i]>=0.05) {
					global.flagbreath=false;
					global.breathcount+=1;
				}
				if(temparr[i]<0.05) {
					global.flagbreath=true;
				}
				
				PlaintTest.dynamicRun(interpolationarr[i][0],temparr[i],global.breathcount);
			}
			System.out.println("画图完毕");

	}

	
}
