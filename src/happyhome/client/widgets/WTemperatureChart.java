package happyhome.client.widgets;

import happyhome.client.jsoverlays.TemperatureReader;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.LinePlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class WTemperatureChart extends Composite {
	interface MyUiBinder extends UiBinder<FlowPanel, WTemperatureChart> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private final Timer tmr = new Timer() {

		@Override
		public void run() {
			// TODO get reading from db
			schedule(10000);
		}
	};

	private String temp = "";

	@UiField(provided = true)
	public final Chart tempChart;

	public WTemperatureChart() {
		tempChart = createChart("");
		
		initWidget(uiBinder.createAndBindUi(this));
		tmr.schedule(1000);
		readTemp();
	}

	public Chart createChart(String dbInfo) {

		final Chart chart = new Chart().setType(Series.Type.LINE).setChartTitle(new ChartTitle().setText("Monthly Average Temperature"))
				.setChartSubtitle(new ChartSubtitle().setText("Source: HappyHomeAutomation")).setToolTip(new ToolTip().setEnabled(false));

		chart.getXAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

		chart.getYAxis().setAxisTitleText("Temperature C");

		chart.setLinePlotOptions(new LinePlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		chart.addSeries(chart.createSeries().setName("Living Room")
				.setPoints(new Number[] { 10.5, 20.4, 24.5, 21, 22, 21.4, 21.2, 19, 18.4, 14.0, 22, 23 }));
		chart.addSeries(chart.createSeries().setName("Kitchen")
				.setPoints(new Number[] { 13.9, 14.2, 15.7, 18.5, 19.9, 15.2, 17.0, 16.6, 14.2, 10.3, 16.6, 14.8 }));

		return chart;
	}

	private void readTemp() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = "http://192.168.44.200:8080/api/wifinou/TMP";

			jsonp.requestObject(url, new AsyncCallback<TemperatureReader>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(TemperatureReader result) {					
					temp = result.getTemp();
					Window.alert(temp);
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
}
