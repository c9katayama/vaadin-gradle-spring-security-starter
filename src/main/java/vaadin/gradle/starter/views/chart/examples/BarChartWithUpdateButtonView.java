package vaadin.gradle.starter.views.chart.examples;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import vaadin.gradle.starter.views.chart.examples.bar.RangedVerticalBarChartExample;

public class BarChartWithUpdateButtonView extends Div {

	public BarChartWithUpdateButtonView() {
		ApexChartsBuilder rangedVerticalBarChartBuilder = new RangedVerticalBarChartExample();
		ApexCharts verticalBarChartExample = rangedVerticalBarChartBuilder
				.withTitle(TitleSubtitleBuilder.get().withText("Bar Chart Example").build()).build();
		verticalBarChartExample.setWidth("80%");

		Button toggleButton = new Button("Toggle", click -> verticalBarChartExample.toggleSeries("Revenue"));
		Button hideButton = new Button("Hide", click -> verticalBarChartExample.hideSeries("Revenue"));
		Button showButton = new Button("Show", click -> verticalBarChartExample.showSeries("Revenue"));
		Button resetButton = new Button("Reset", click -> verticalBarChartExample.resetSeries(true, true));

		HorizontalLayout hl = new HorizontalLayout();
		hl.add(toggleButton, hideButton, showButton, resetButton);
		add(hl, new Span("Bar chart control: "), verticalBarChartExample);
		setWidthFull();
	}
}
