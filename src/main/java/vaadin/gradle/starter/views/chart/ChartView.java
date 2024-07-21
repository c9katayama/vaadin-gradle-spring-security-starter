package vaadin.gradle.starter.views.chart;

import java.util.Arrays;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import vaadin.gradle.starter.views.MainLayout;
import vaadin.gradle.starter.views.chart.examples.BarChartWithUpdateButtonView;
import vaadin.gradle.starter.views.chart.examples.ExampleChartGenerator;

@PageTitle("Chart")
@Route(value = "chart", layout = MainLayout.class)
@RolesAllowed("USER")
public class ChartView extends VerticalLayout {

	private Tabs tabs = new Tabs();
	private Tab chartTab = new Tab("Charts");
	private Tab coloredChartTab = new Tab("Colored Charts");
	private Tab barChartWithUpdateButtonTab = new Tab("Bar Chart With Update Button");

	private VerticalLayout tabContents = new VerticalLayout();
	private VerticalLayout chartLayout = new VerticalLayout();
	private VerticalLayout coloredChartLayout = new VerticalLayout();
	private BarChartWithUpdateButtonView barChartWithUpdateButtonView = new BarChartWithUpdateButtonView();

	public ChartView() {
		addClassNames("chart-view");
		setSizeFull();
		setSizeUndefined();

		tabs.setWidthFull();
		tabs.add(chartTab, coloredChartTab, barChartWithUpdateButtonTab);

		chartLayout.setHeight("unset");
		chartLayout.setWidth("80%");
		coloredChartLayout.setHeight("unset");
		coloredChartLayout.setWidth("80%");
		setupExampleCharts();

		tabContents.add(chartLayout);
		add(tabs, tabContents);

		tabs.addSelectedChangeListener(event -> {
			tabContents.removeAll();
			if (event.getSelectedTab().equals(chartTab)) {
				tabContents.add(chartLayout);
			} else if (event.getSelectedTab().equals(coloredChartTab)) {
				tabContents.add(coloredChartLayout);
			} else if (event.getSelectedTab().equals(barChartWithUpdateButtonTab)) {
				tabContents.add(barChartWithUpdateButtonView);
			}
		});
	}

	private void setupExampleCharts() {
		Arrays.stream(ExampleChartGenerator.getCharts()).map(ApexChartsBuilder::build).forEach(chartLayout::add);
		Arrays.stream(ExampleChartGenerator.getColoredCharts()).map(ApexChartsBuilder::build)
				.forEach(coloredChartLayout::add);
	}
}
