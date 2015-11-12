package happyhome.client.widgets;

import happyhome.client.jsoverlays.DbReader;
import happyhome.client.jsoverlays.JsArray;
import happyhome.client.jsoverlays.TemperatureJsModel;
import happyhome.shared.AppConstants;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.YAxis;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.SplinePlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class WTemperatureChart extends Composite {
	interface MyUiBinder extends UiBinder<FlowPanel, WTemperatureChart> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private Long startPoint;
	private Number[] temps;
	private String device, locationLabel;

	@UiField(provided = true)
	Chart tempChart;

	public WTemperatureChart(String device, String locationLabel) {
		this.device = device;
		this.locationLabel = locationLabel;
		startPoint = DateTimeFormat
				.getFormat("yyyy-MM-dd")
				.parse((DateTimeFormat.getFormat("yyyy-MM-dd")
						.format(new Date()))).getTime();

		temps = new Number[] {};
		executeRead();
		tempChart = createChart(temps);
		initWidget(uiBinder.createAndBindUi(this));
	}

	private Chart createChart(Number[] values) {

		final Chart chart = new Chart()
				.setType(Series.Type.SPLINE)
				.setChartTitleText("Istoric Temperatura")
				.setChartSubtitleText(locationLabel)
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return DateTimeFormat.getFormat("d. MMMM YYYY HH:mm")
								.format(new Date(toolTipData.getXAsLong()))
								+ ": " + toolTipData.getYAsDouble() + " grade Celsius";
					}
				}))
				.setSplinePlotOptions(
						new SplinePlotOptions()
								.setLineWidth(4)
								.setHoverStateLineWidth(5)
								.setMarker(
										new Marker()
												.setEnabled(false)
												.setHoverState(
														new Marker()
																.setEnabled(
																		true)
																.setSymbol(
																		Marker.Symbol.CIRCLE)
																.setRadius(5)
																.setLineWidth(1)))
								.setPointInterval(1800000) // half hour
								.setPointStart(startPoint));

		chart.getXAxis().setType(Axis.Type.DATE_TIME).setTickInterval(1800000);

		final YAxis axis = chart.getYAxis();
		// final Color blueColor = new Color(68, 170, 213, 0.1);
		// final Color clearColor = new Color(0, 0, 0, 0);
		axis.setAxisTitleText("Celsius").setMin(0).setMinorGridLineWidth(0)
				.setGridLineWidth(1);

		return chart;
	}

	private void executeRead() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = AppConstants.raspberryIp + "/qdb/device/" + device
					+ "/type/TMP";

			jsonp.requestObject(url, new AsyncCallback<DbReader>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DbReader result) {
					if (result.getStatus().equals("success")) {
						JsArray<JavaScriptObject> jo = result.getData();
						for (int i = 0; i < jo.length(); i++) {
							TemperatureJsModel object = (TemperatureJsModel) jo
									.get(i);
							temps[i] = Integer.valueOf(object.getValue());
						}
						tempChart.addSeries(tempChart.createSeries()
								.setName(locationLabel).setPoints(temps));
					} else {
						Window.alert(result.getMessage());
					}
				}
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
