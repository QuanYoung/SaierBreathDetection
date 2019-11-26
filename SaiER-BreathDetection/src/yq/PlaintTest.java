package yq;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class PlaintTest {
	 public static XYSeries xyCPUseries = new XYSeries("CPU");

	    public static int hundroud = 0;
	    public static JFreeChart jfreechart = null;

	    public JPanel getCPUJFreeChart(){

	        jfreechart = ChartFactory.createXYLineChart(
	                null, "Time(s)", "value", createDataset1(),
	                PlotOrientation.VERTICAL, false, true, false);

	        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
	        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
	        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
	        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
	        ChartFactory.setChartTheme(mChartTheme);

	        jfreechart.setBorderPaint(new Color(0,204,205));
	        jfreechart.setBorderVisible(true);

	        XYPlot xyplot = (XYPlot) jfreechart.getPlot();

	        // Y轴
	        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
	        numberaxis.setLowerBound(-0.4);
	       
	        numberaxis.setUpperBound(0.4);
	        numberaxis.setTickUnit(new NumberTickUnit(0.1d));
	        // 只显示整数值
	        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        // numberaxis.setAutoRangeIncludesZero(true);
	        numberaxis.setLowerMargin(0); // 数据轴下（左）边距 ­
	        numberaxis.setMinorTickMarksVisible(true);// 标记线是否显示
	        numberaxis.setTickMarkInsideLength((float) 0.1);// 外刻度线向内长度
	        numberaxis.setTickMarkOutsideLength((float) 0.1);

	        // X轴的设计
	        NumberAxis x = (NumberAxis) xyplot.getDomainAxis();
	        x.setAutoRange(true);// 自动设置数据轴数据范围
	        // 自己设置横坐标的值
	        x.setAutoTickUnitSelection(true);
	        x.setTickUnit(new NumberTickUnit(2d));
	        // 设置最大的显示值和最小的显示值
	        x.setLowerBound(0);
	        x.setUpperBound(60);
	        // 数据轴的数据标签：只显示整数标签
	        x.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        x.setAxisLineVisible(true);// X轴竖线是否显示
	        x.setTickMarksVisible(true);// 标记线是否显示

	        RectangleInsets offset = new RectangleInsets(0, 0, 0, 0);
	        xyplot.setAxisOffset(offset);// 坐标轴到数据区的间距
//	        xyplot.setBackgroundAlpha(0.0f);// 去掉柱状图的背景色
	        xyplot.setOutlinePaint(null);// 去掉边框
	     
	        xyplot.setDomainGridlinesVisible(true); //设置网格不显示
	        // ChartPanel chartPanel = new ChartPanel(jfreechart);
	        // chartPanel.restoreAutoDomainBounds();//重置X轴
	        
	        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();  
	      //设置网格背景颜色  
	        xyplot.setBackgroundPaint(Color.white);  
	      //设置网格竖线颜色  
	        xyplot.setDomainGridlinePaint(Color.pink);  
	      //设置网格横线颜色  
	        xyplot.setRangeGridlinePaint(Color.pink);  
	      //设置曲线图与xy轴的距离  
	        xyplot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));  
	      //设置曲线是否显示数据点  
	      xylineandshaperenderer.setBaseShapesVisible(false);  
	      //设置曲线显示各数据点的值  

	        ChartPanel chartPanel = new ChartPanel(jfreechart, true);

	        return chartPanel;
	    }

	    /**
	     * 该方法是数据的设计
	     * 
	     * @return
	     */
	    public static XYDataset createDataset1() {
	        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
	        xyseriescollection.addSeries(xyCPUseries);
	        return xyseriescollection;
	    }

	    /**
	     * 随机生成的数据
	     */
	    public static void dynamicRun(double xdata,double ydata,int breathCount) {
//	        int i = 0;
//	        while (true) {

//	            double factor = Math.random()*100;

//	            hundroud = (int)factor;
//	            jfreechart.setTitle("CPU的大小是：           "+breathCount+"%");
	            jfreechart.setTitle("                                                          "
	            		+ "   生命体征监测  "+"                                  呼吸次数： "+ breathCount+"次");
	         
	            jfreechart.getTitle().setFont(new Font("微软雅黑", 0, 16));//设置标题字体

	            xyCPUseries.add(xdata, ydata);

//	            try {
	                Thread.currentThread();
//	                Thread.sleep(1000);
//	            } catch (InterruptedException e) {
//	                e.printStackTrace();
//	            }
	           
	            if (xdata== 60){
	                xdata=0;
	                xyCPUseries.delete(0, 59);
//	                continue;
	            }
//	        }
	    }

	}
	