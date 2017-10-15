package com.isoftstone.smartsite.model.map.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by zw on 2017/10/15.
 */

public class MapTestFragment extends BaseFragment {

    private PieChart pieChart;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_map_test;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView();
    }

    private void initView(){
        pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);

        // 设置饼图是否接收点击事件，默认为true
        pieChart.setTouchEnabled(false);
        //设置饼图是否使用百分比
        pieChart.setUsePercentValues(true);

        //设置中间透明圈的半径,值为所占饼图的百分比
        pieChart.setTransparentCircleRadius(40);


        //设置比例图
        Legend mLegend = pieChart.getLegend();
        //设置比例图显示在饼图的哪个位置
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //设置比例图的形状，默认是方形,可为方形、圆形、线性
        mLegend.setForm(Legend.LegendForm.DEFAULT);

//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);

/*        //设置X轴动画
        pieChart.animateX(1800);
//        //设置y轴动画
//        pieChart.animateY(1800);
//        //设置xy轴一起的动画
//        pieChart.animateXY(1800, 1800);*/



        pieChart.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
        pieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
        pieChart.setHoleRadius(58f);//饼状图中间的圆的半径大小
        pieChart.setRotationEnabled(false);//设置饼状图是否可以旋转(默认为true)
        pieChart.setRotationAngle(0);//设置饼状图旋转的角度


        // entry label styling
        pieChart.setDrawEntryLabels(true);//设置是否绘制Label
        pieChart.setEntryLabelColor(Color.BLACK);//设置绘制Label的颜色
        //pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(10f);//设置绘制Label的字体大小

        setPieChartData(4,100);
    }

    private void setPieChartData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry(25 * (i + 1)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }
}
