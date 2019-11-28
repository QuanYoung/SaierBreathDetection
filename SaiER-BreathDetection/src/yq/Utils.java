package yq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class Utils {
	
	/**
	 *@return 窗口大小为5处理倒π和周期环绕 返回一个double类型数组
	 *@param arr 待处理数组
	 *@param con1 两个窗口划分值时 用于与窗口1平均值比较分类的参考值
	 *@param con2倒排处理是的参考上届
	 * 
	 */
	
	public static double[] HandInvertPI5(ArrayList<Double> al,double con1,double con2,int tklen) {//处理倒π
		int len=0;
//		if(al.size()>tklen+5) {//非第一次处理
//			len=tklen+5;
//		}else {//第一次周期环绕处理
			len=tklen;
//		}
		double[] HIPI=new double[len];
		int allen=al.size();
		for(int k=0;k<len;k++){
		
			HIPI[k]=al.get(k);
		}
		//for(int i=0,point=0;i<arr.length-arr.length%7;){
		  for(int i=0,point=0;i<HIPI.length-4;){
			//		for(int i=0,point=0;i<50;){//测试
			double slippingWindow[]=new double[6];
			double window1[]=new double[6];
			double window2[]=new double[6];
			double sum1=0;
			double sum2=0;
			int count1=0;//窗口一计数器
			int count2=0;//窗口二计数器
			window1[0]=HIPI[i];//1窗口第0号位置放参考的第一个值
			for(int j=1;j<6;j++,i++){//算窗口内数组的平均值,取7个数的平均数来作为处理第一个值的参照
				slippingWindow[j]=HIPI[i];
			}
			for(int j=1;j<6;j++){//小窗口赋值			
				if((Math.abs(slippingWindow[j]-window1[0])>=2)&&(Math.abs(slippingWindow[j]-window1[0])<=con2)){//若值大于窗口1的平均值但不是周期环绕  就放到窗口2中相应位置
					window2[j]=slippingWindow[j];
					sum2+=window2[j];
					count2++;
					window2[0]=sum2/count2;
				}else{ 
					window1[j]=slippingWindow[j];
					if(Math.abs(slippingWindow[j]-window1[0])<=2){//周期环绕值放在同一组但不改变平均值
					sum1+=slippingWindow[j];
					count1++;
					window1[0]=sum1/count1;
					}
				}
			}
//			if(i>=1110&&i<=1150){
//			System.out.println("处理前： ");
//			System.out.println("slippingWindow: "+Arrays.toString(slippingWindow));
//			System.out.println("window1: "+Arrays.toString(window1));
//			System.out.println("window2: "+Arrays.toString(window2));
//			System.out.println("count1: "+count1+" count2: "+count2);
//			System.out.println("处理后：");
//			}
//			if(i<=8){	
			if(count1>=count2){//将疑似倒π窗口的数据进行处理
				for(int j=1;j<6;j++){
					if(window1[j]!=0){
						HIPI[point+j-1]=window1[j];
					}else if(window2[j]-window1[0]>=2&&window2[j]-window1[0]<=con2){
						window2[j]=(window2[j]+Math.PI)%(2*Math.PI);//预防先周期环绕再倒排
						HIPI[point+j-1]=window2[j];
					}else if(window2[j]-window1[0]<=-2&&window2[j]-window1[0]>=-con2){
						window2[j]=(window2[j]+Math.PI)%(2*Math.PI);
						HIPI[point+j-1]=window2[j];
					}
					else{
						HIPI[point+j-1]=window2[j];//值就是0
					}
				}
			}else if(count1<count2){//自身处理+将疑似倒π窗口的数据进行处理
				for(int j=1;j<6;j++){
					if(window2[j]-window2[0]>=2&&window1[j]-window2[0]<=con2){
						window2[j]=(window2[j]+Math.PI)%(2*Math.PI);//预防先周期环绕再倒排
						HIPI[point+j-1]=window2[j];
					}else if(window2[j]-window2[0]<=-2&&window2[j]-window2[0]>=-con2){
						window2[j]=(window2[j]+Math.PI)%(2*Math.PI);
						HIPI[point+j-1]=window2[j];
					}else if(window2[j]!=0){
						HIPI[point+j-1]=window2[j];
					}else if(window1[j]-window2[0]>=2&&window1[j]-window2[0]<=con2){
						window1[j]=(window1[j]+Math.PI)%(2*Math.PI);//预防先周期环绕再倒排
						HIPI[point+j-1]=window1[j];
					}else if(window1[j]-window2[0]<=-2&&window1[j]-window2[0]>=-con2){
						window1[j]=(window1[j]+Math.PI)%(2*Math.PI);
						HIPI[point+j-1]=window1[j];
					}
					else{
						HIPI[point+j-1]=window1[j];//值就是0
					}
				}
			}
			sum1=0;
			sum2=0;
			count1=0;
			count2=0;
			i-=4;
			point+=1;
		}
		System.out.println("处理倒π和周期环绕完毕！！！");
//		arr=HIPI;//更新al
		for(int i=0;i<tklen;i++){
			al.set(allen-tklen+i, HIPI[len-tklen+i]);
		}
		return HIPI;
}
	public static double[][] Interpolation(int length1,int length2,ArrayList<Double> t1Time,ArrayList<Double> t2Time,ArrayList<Double> t1Phase,ArrayList<Double> t2Phase) {
		System.out.println("差值");
		if(t1Time.get(0)<=t2Time.get(0)){//标签1接收第一个数据
			double arr5[][]=doInterpolation(t1Time,t2Time,t2Phase,length1,length2);//��ֵ
			double arrbreath[][]=new double[length1][2];//�������պ�����λ����
			for(int i=0;i<length1;i++){
				arrbreath[i][0]=t1Time.get(i);
				arrbreath[i][1]=(t1Phase.get(i)+arr5[i][1])%(2*Math.PI);
			}
			//
//			SaveToFile(arr5,"E:\\RFIDBreath\\breathDetection\\差值Data.txt");
			System.out.println("差值完毕");
			return arrbreath;
		}else{
			double arr5[][]=doInterpolation(t2Time,t1Time,t1Phase,length2,length1);//��ֵ
			double arrbreath[][]=new double[length2][2];//�������պ�����λ����
//			SaveToFile(arr5,"E:\\RFIDBreath\\breathDetection\\差值Data.txt");

			for(int i=0;i<length2;i++){
				arrbreath[i][0]=t2Time.get(i);
				arrbreath[i][1]=(t2Phase.get(i)+arr5[i][1])%(2*Math.PI);
			}
			System.out.println("差值完毕");
			return arrbreath;
		}
	}
	
	private static double[][] doInterpolation(ArrayList<Double> t1Time,ArrayList<Double> t2Time,ArrayList<Double> t2Phase,int length1,int length2) {
		
		System.out.println("dointerpolation开始");

		double[][] ansarr=new double[length1][2];
//		for(int i=0;i<length1;i++) {
//			ansarr[i][0]=t1Time.get(i);
//			ansarr[i][1]=4;
//		}
//		return ansarr;
		
		for(int i=0;i<3;i++){//处理前三个点
			ansarr[i][0]=t1Time.get(i);
			ansarr[i][1]=DoLagrange(ansarr[i][0],2,t2Time,t2Phase);
		}
		for(int i=3;i<length1;i++){
			for(int j=2;j<length2;j++){//j��2��ʼ�е��ʱ��
				
				 if(j==length2-1){//����arr2��ĩβ��Ȼδ�ҵ�ֵ
						ansarr[i][0]=t1Time.get(i);
						ansarr[i][1]=DoLagrange(t1Time.get(i),t2Time.size()-2,t2Time,t2Phase);
						break;
					}
				 
				if(t2Time.get(j)>=t1Time.get(i)){
					ansarr[i][0]=t1Time.get(i);
					ansarr[i][1]=DoLagrange(t1Time.get(i),j,t2Time,t2Phase);
					break;
				}
			}
		}
		System.out.println("dointerpolation完毕");
		return ansarr;
}
	/**
	 * 
	 * @param point 中间位置点
	 * @param arr  待处理数组
	 * @return 拉格朗日四点插值，返回一个double型数据值
	 */
public static double DoLagrange(double x,int point,ArrayList<Double> t2Time,ArrayList<Double> t2Phase) {
	 
	System.out.println("调用拉格朗日函数 "+point+" size:"+t2Phase.size());	
//	boolean flag=true;
		double x0=t2Time.get(point-2);
		double y0=t2Phase.get(point-2);
		double x1=t2Time.get(point-1);
		double y1=t2Phase.get(point-1);
		double x2=t2Time.get(point);
		double y2=t2Phase.get(point);
		double x3=t2Time.get(point+1);
		double y3=t2Phase.get(point+1);
		System.out.println("-------1------");
//		if(flag) {
////			System.out.println("----------");
////	System.out.println(x0+" "+y0+" "+x1+" "+y1+" "+x2+" "+y2+" "+x3+" "+y3);
//	flag=false;
//		}
		double ans=0;
		if(y0>5||y1>5||y2>5||y3>5){//周期环绕处理
			if(y0<2){
				y0+=2*Math.PI;
			}
			if(y1<2){
				y1+=2*Math.PI;
			}
			if(y2<2){
				y2+=2*Math.PI;
			}
			if(y3<2){
				y3+=2*Math.PI;
			}
		}
		ans=((x-x1)*(x-x2)*(x-x3)*y0/((x0-x1)*(x0-x2)*(x0-x3)))+
			((x-x0)*(x-x2)*(x-x3)*y1/((x1-x0)*(x1-x2)*(x1-x3)))+
			((x-x0)*(x-x1)*(x-x3)*y2/((x2-x0)*(x2-x1)*(x2-x3)))+
			((x-x0)*(x-x1)*(x-x2)*y3/((x3-x0)*(x3-x1)*(x3-x2)));
		System.out.println("拉格朗日函数调用完毕");
		if(ans<=0||ans>=2*Math.PI) {
			return 4;
		}
		return Math.abs(ans%(2*Math.PI));
		//return ans%(2*Math.PI);
	}

public static double[] domeanfilter(double[][] arr) {
	System.out.println("domeanfilter");
//	double t[] =new double[arr.length];//时间
	double x[] =new double[arr.length];//数据
	int len=arr.length;
	double xsum=0;
	for(int i=0;i<len;i++) {
//		t[i]=arr[i][0];
		x[i]=arr[i][1];
		xsum+=x[i];
	}
	
	//x去直流分量
	double avex=xsum/len;
	for(int i=0;i<len;i++) {
		x[i]-=avex;
	}
	return x;
}

public static String[][] File2ArrayIDtoIPv6( File f) {//文件转数组
	BufferedReader br=null;
	String arr[][]=new String[(int) (getFileLineCount(f))][2];//第一列用户ID第二列IPv6地址
	try {
		 br=new BufferedReader(new FileReader(f));//读文件
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("文件不存在");
		e.printStackTrace();
	}
	
	String temps;
	int count=0;
		try {
			while((temps=br.readLine())!=null){
				String tempstr1[]=temps.split(" ");
				arr[count][0]=tempstr1[0];
				arr[count][1]=tempstr1[1];
				count++;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("IDtoIPv6数据获取完毕！！！");
		return arr;
}

public static String[][] File2ArrayIpv6toUserInfo( File f) {//文件转数组
	BufferedReader br=null;
	String arr[][]=new String[(int) (getFileLineCount(f))][4];//第0列ipv6地址 第一列用户阅读器地址第二列tag1 epc第二列tag2 epc
	try {
		 br=new BufferedReader(new FileReader(f));//读文件
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("文件不存在");
		e.printStackTrace();
	}
	
	String temps;
	int count=0;
		try {
			while((temps=br.readLine())!=null){
				String tempstr1[]=temps.split(" ");
				arr[count][0]=tempstr1[0];
				arr[count][1]=tempstr1[1];
				arr[count][2]=tempstr1[2];
				arr[count][3]=tempstr1[3];
				count++;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ipv6toUserInfo数据获取完毕！！！");
		return arr;
}

/**
 * @return 获取文件的行数用于数组大小设置 返回一个int值
 */
public static int getFileLineCount(File file) {//获取文件行数
    int cnt = 0;
    LineNumberReader reader = null;
    try {
        reader = new LineNumberReader(new FileReader(file));
        @SuppressWarnings("unused")
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {
        }
        cnt = reader.getLineNumber();
    } catch (Exception ex) {
        cnt = -1;
        ex.printStackTrace();
    } finally {
        try {
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return cnt;
}
}
