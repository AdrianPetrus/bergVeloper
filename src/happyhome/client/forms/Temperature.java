package happyhome.client.forms;

import gwtSql.client.forms.VForm;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.LinePlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class Temperature extends VForm {

	interface MyUiBinder extends UiBinder<Widget, Temperature> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField(provided = true)
	Chart tempHistoryChart1;
	

	public Temperature() {
		tempHistoryChart1 = createChart();		
		initWidget(uiBinder.createAndBindUi(this));		
	}

	private Chart createChart() {

		final Chart chart = new Chart().setType(Series.Type.LINE).setChartTitle(new ChartTitle().setText("Temperatura medie lunara"))
				.setChartSubtitle(new ChartSubtitle().setText("Sursa: HappyHome System")).setToolTip(new ToolTip().setEnabled(false));

		chart.getXAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

		chart.getYAxis().setAxisTitleText("Temperatura C");

		chart.setLinePlotOptions(new LinePlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		chart.addSeries(chart.createSeries().setName("Camera 1")
				.setPoints(new Number[] { 21.0, 20.9, 19.5, 22.5, 23.4, 22.5, 25.2, 26.5, 29.3, 22.3, 21.9, 20.6 }));
		chart.addSeries(chart.createSeries().setName("Camera 2")
				.setPoints(new Number[] { 19.9, 18.2, 22.7, 18.5, 11.9, 20.2, 23.0, 27.6, 22.2, 21.3, 20.6, 21.8 }));

		return chart;
	}
}