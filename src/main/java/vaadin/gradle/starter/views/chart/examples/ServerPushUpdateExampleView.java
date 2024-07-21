package vaadin.gradle.starter.views.chart.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Update chart via server push.
 * 
 * <pre>
 * If you use this view, add @Push annotation to vaadin.gradle.starter.Application.java class.
 * </pre>
 */
public class ServerPushUpdateExampleView extends VerticalLayout {

	private ExecutorService threadPool;
	private AtomicBoolean update = new AtomicBoolean(false);

	private ApexCharts chart;
	private Button autoUpdateButton;

	public ServerPushUpdateExampleView() {
		chart = ApexChartsBuilder.get().withChart(ChartBuilder.get().withType(Type.BAR).build())
				.withPlotOptions(
						PlotOptionsBuilder.get().withBar(BarBuilder.get().withHorizontal(true).build()).build())
				.withDataLabels(DataLabelsBuilder.get().withEnabled(false).build())
				.withSeries(new Series<>(400.0, 430.0, 448.0, 470.0, 540.0, 580.0, 690.0, 1100.0, 1200.0, 1380.0))
				.withXaxis(XAxisBuilder.get().withCategories().build()).build();
		chart.setHeight("400px");
		autoUpdateButton = new Button("Auto Update:disabled", buttonClickEvent -> {
			if (update.get()) {
				stopAutoUpdate();
			} else {
				startAutoUpdate();
			}
		});
		add(chart, autoUpdateButton);
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		stopAutoUpdate();
	}

	private void startAutoUpdate() {
		autoUpdateButton.setText("Auto update:enabled");
		update.set(true);
		threadPool = Executors.newFixedThreadPool(1);
		threadPool.submit(() -> {
			while (update.get()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
				chart.getUI().ifPresent(ui -> ui.access(() -> {
					System.out.println("update2");
					chart.updateSeries(new Series<>(randomValue(), randomValue(), randomValue(), randomValue(),
							randomValue(), randomValue()));
				}));
			}
		});
	}

	private int randomValue() {
		return (int) (500 * Math.random() * 2);
	}

	private void stopAutoUpdate() {
		autoUpdateButton.setText("Auto update:disabled");
		update.set(false);
		if (threadPool != null) {
			threadPool.shutdown();
		}
	}
}
