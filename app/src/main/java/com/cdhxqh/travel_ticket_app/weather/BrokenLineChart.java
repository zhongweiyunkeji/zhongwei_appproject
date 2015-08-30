package com.cdhxqh.travel_ticket_app.weather;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class BrokenLineChart {
	private XYMultipleSeriesDataset ds;
	private XYMultipleSeriesRenderer render;
	private XYSeries series;
	private GraphicalView gv;
	private ChartPoint[] datas;
	private XYSeriesRenderer xyRender;
	private String entity;
	private Context context;

	private int EntityColors;

	/**
	 * 传递参数，每一个曲线的标题，还有对应的曲线的颜色

	 */
	public BrokenLineChart(String Entity, int EntityColor,ChartPoint[] Datas,Context Context) {
		this.entity = Entity;
		this.EntityColors = EntityColor;
		this.datas = Datas;
		this.context = Context;
		InitRender();
		InitDataSet(datas);
		gv = ChartFactory.getLineChartView(context, ds, render);
	}

	private void InitDataSet(ChartPoint[] datas) {
		// TODO Auto-generated method stub
		ds = new XYMultipleSeriesDataset();
		series = new XYSeries(entity);
		for (int i = 0; i < datas.length; i++) {
			series.add(datas[i].getX(), datas[i].getY());
		}
		ds.addSeries(series);
	}

	private void InitRender() {
		// TODO Auto-generated method stub
		render = new XYMultipleSeriesRenderer();
		render.setAxisTitleTextSize(16); // 设置坐标轴标题文本大小
		render.setChartTitleTextSize(30); // 设置图表标题文本大小
		render.setChartTitle("30天入园人数趋势图");
		render.setLabelsTextSize(25); // 设置轴标签文本大小
		render.setLegendTextSize(15); // 设置图例文本大小
		render.setMargins(new int[]{20, 30, 15, 15}); // 设置4边留白
		render.setPanEnabled(false, false); // 设置x,y坐标轴不会因用户划动屏幕而移动
		render.setMarginsColor(Color.argb(0, 0xff, 0, 0));// 设置4边留白透明
		render.setBackgroundColor(Color.TRANSPARENT); // 设置背景色透明
		render.setApplyBackgroundColor(true); // 使背景色生效
		render.setXLabels(13);// 设置X轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		render.setYLabels(12);// 设置Y轴显示12个点，根据setChartSettings的最大值和最小值自动计算点的间隔
		render.setXLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
		render.setYLabelsAlign(Align.CENTER);// 刻度线与刻度标注之间的相对位置关系
		render.setXLabelsColor(Color.BLACK);
		render.setYLabelsColor(0, Color.BLACK);
		render.setZoomButtonsVisible(false);// 是否显示放大缩小按钮
//		render.setShowGrid(true);// 是否显示网格
		render.setShowGridX(true);
		render.setGridColor(Color.GRAY);// 设置网格颜色
		render.setAxesColor(Color.BLACK);// 设置X.y轴颜色
		render.setFitLegend(false);// 设置自动按比例缩放
		render.setYAxisMax(3000.0); // 设置Y轴最大值
		render.setYAxisMin(0); // 设置Y轴最小值

		// 设置x,y轴上的刻度的颜色
		render.setLabelsColor(Color.BLACK);

		render.setLabelsColor(Color.BLACK);
		// render.setYLabelsColor(1, R.color.black);
		xyRender = new XYSeriesRenderer();
		xyRender.setPointStyle(PointStyle.CIRCLE);
		xyRender.setLineWidth(8);
		xyRender.setColor(EntityColors); //设置线图颜色
		xyRender.setFillPoints(false);// 设置为实心点
		xyRender.setPointStrokeWidth(15);
		render.addSeriesRenderer(xyRender);// 添加到render中
	}

	public View GetView()
	{
		return gv;
	}
	/**
	 * 设置坐标轴标题的大小
	 * @param size
	 */
	public void SetAxisTextSize(int size)
	{
		render.setAxisTitleTextSize(size);
	}

	/**
	 * 设置表格标题的大小
	 * @param size
	 */
	public void SetChartTitleTextSize(int size)
	{
		render.setChartTitleTextSize(size);
	}

	/**
	 * 设置表格的主题标题
	 */
	public void SetChartTitle(String Title)
	{
		render.setChartTitle(Title);
	}

	/**
	 * 设置图表标题文字大小
	 * @param size
	 */
	public void SetLabelsTextSize(int size)
	{
		render.setLabelsTextSize(15);
	}

	/**
	 * 设置x，y坐标轴是否会因用户滑动屏幕而移动
	 * @param X
	 * @param Y
	 */
	public void SetPanEnable(boolean X,boolean Y)
	{
		render.setPanEnabled(true, false);
	}

	/**
	 * 设置是否显示网格
	 * @param isshow
	 */
	public void SetShowGrid(boolean isshow)
	{
		render.setShowGrid(isshow);
	}

	/**
	 * 设置网格的颜色，即背景颜色
	 * @param color
	 */
	public void SetBackgroundColor(int color)
	{
		render.setGridColor(color);
	}

	/**
	 * 设置x,y轴的颜色
	 * @param color
	 */
	public void SetAxesColor(int color)
	{
		render.setAxesColor(color);
	}

	/**
	 * 设置Y轴的最大值
	 * @param max
	 */
	public void SetMaxOfY(double max)
	{
		render.setYAxisMax(max);
	}
	/**
	 * 设置Y轴的最小值
	 * @param min
	 */
	public void SetMinOfX(double min)
	{
		render.setYAxisMin(min);
	}
}
