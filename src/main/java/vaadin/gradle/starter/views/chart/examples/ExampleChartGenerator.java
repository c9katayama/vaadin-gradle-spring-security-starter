package vaadin.gradle.starter.views.chart.examples;

import java.util.Arrays;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.CandlestickBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.candlestick.builder.ColorsBuilder;

import vaadin.gradle.starter.views.chart.examples.areachart.AreaChartExample;
import vaadin.gradle.starter.views.chart.examples.areachart.SparkLineExample;
import vaadin.gradle.starter.views.chart.examples.bar.HorizontalBarChartExample;
import vaadin.gradle.starter.views.chart.examples.bar.RangedVerticalBarChartExample;
import vaadin.gradle.starter.views.chart.examples.bar.VerticalBarChartExample;
import vaadin.gradle.starter.views.chart.examples.bubble.BubbleChartExample;
import vaadin.gradle.starter.views.chart.examples.candlestick.CandleStickChartExample;
import vaadin.gradle.starter.views.chart.examples.donut.DonutChartExample;
import vaadin.gradle.starter.views.chart.examples.formatter.IntFormatterExample;
import vaadin.gradle.starter.views.chart.examples.formatter.NumberFormatFormatterExample;
import vaadin.gradle.starter.views.chart.examples.formatter.StringFormatterExample;
import vaadin.gradle.starter.views.chart.examples.formatter.SuffixFormatterExample;
import vaadin.gradle.starter.views.chart.examples.heatmap.HeatmapChartExample;
import vaadin.gradle.starter.views.chart.examples.heatmap.RangedHeatmapChartExample;
import vaadin.gradle.starter.views.chart.examples.line.LineChartExample;
import vaadin.gradle.starter.views.chart.examples.line.LineMultiYAxesChartExample;
import vaadin.gradle.starter.views.chart.examples.locale.LocaleExampleView;
import vaadin.gradle.starter.views.chart.examples.mixed.LineAndAreaChartExample;
import vaadin.gradle.starter.views.chart.examples.mixed.LineAndColumnAndAreaChartExample;
import vaadin.gradle.starter.views.chart.examples.mixed.LineAndColumnChartExample;
import vaadin.gradle.starter.views.chart.examples.mixed.LineAndScatterChartExample;
import vaadin.gradle.starter.views.chart.examples.pie.PieChartExample;
import vaadin.gradle.starter.views.chart.examples.radar.RadarChartExample;
import vaadin.gradle.starter.views.chart.examples.radialbar.GradientRadialBarChartExample;
import vaadin.gradle.starter.views.chart.examples.radialbar.MultiRadialBarChartExample;
import vaadin.gradle.starter.views.chart.examples.radialbar.RadialBarChartExample;
import vaadin.gradle.starter.views.chart.examples.scatter.ScatterChartExample;
import vaadin.gradle.starter.views.chart.examples.stacked.StackedBarChartExample;
import vaadin.gradle.starter.views.chart.examples.syncronised.SyncronisedLineChartExample1;
import vaadin.gradle.starter.views.chart.examples.syncronised.SyncronisedLineChartExample2;
import vaadin.gradle.starter.views.chart.examples.timeline.TimeLineChartCustomColorExample;
import vaadin.gradle.starter.views.chart.examples.timeline.TimeLineChartExample;
import vaadin.gradle.starter.views.chart.examples.tooltip.ScatterChartWithCustomTooltipsExample;

public class ExampleChartGenerator {
	public static ApexChartsBuilder[] getColoredCharts() {
		return Arrays.stream(getCharts())
				.map(builder -> builder instanceof CandleStickChartExample
						? builder.withPlotOptions(PlotOptionsBuilder.get()
								.withCandlestick(CandlestickBuilder.get()
										.withColors(ColorsBuilder.get().withDownward("#1e88e5").withUpward("#00acc1")
												.build())
										.build())
								.build())
						: builder.withColors("#1e88e5", "#00acc1", "#5e35b1"))
				.map(builder -> builder.withTitle(
						TitleSubtitleBuilder.get().withText("Colored" + builder.getClass().getSimpleName()).build()))
				.toArray(ApexChartsBuilder[]::new);
	}

	public static ApexChartsBuilder[] getCharts() {
		return Arrays.stream(new ApexChartsBuilder[] { //
				new PieChartExample(), //
				new DonutChartExample(), //
				new LineChartExample(), //
				new LineMultiYAxesChartExample(), //
				new AreaChartExample(), //
				new SparkLineExample(), //
				new LineAndAreaChartExample(), //
				new LineAndColumnChartExample(), //
				new LineAndColumnAndAreaChartExample(), //
				new LineAndScatterChartExample(), //
				new BubbleChartExample(), //
				new HorizontalBarChartExample(), //
				new TimeLineChartExample(), //
				new TimeLineChartCustomColorExample(), //
				new VerticalBarChartExample(), //
				new RangedVerticalBarChartExample(), //
				new RadialBarChartExample(), //
				new StackedBarChartExample(), //
				new GradientRadialBarChartExample(), //
				new MultiRadialBarChartExample(), //
				new CandleStickChartExample(), //
				new RadarChartExample(), //
				new ScatterChartExample(), //
				new ScatterChartWithCustomTooltipsExample(), //
				new LocaleExampleView(), //
				new NumberFormatFormatterExample(), //
				new IntFormatterExample(), //
				new StringFormatterExample(), //
				new SuffixFormatterExample(), //
				new HeatmapChartExample(), //
				new RangedHeatmapChartExample(), //
				new SyncronisedLineChartExample1(), //
				new SyncronisedLineChartExample2() //
		}).map(builder -> builder
				.withTitle(TitleSubtitleBuilder.get().withText(builder.getClass().getSimpleName()).build()))
				.toArray(ApexChartsBuilder[]::new);
	}
}
