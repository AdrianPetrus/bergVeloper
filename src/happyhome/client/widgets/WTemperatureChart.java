package happyhome.client.widgets;

import happyhome.client.jsoverlays.DataReader;

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
import com.google.gwt.i18n.client.DateTimeFormat;
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
	@SuppressWarnings("unused")
	private Long startPoint;

	@UiField(provided = true)
	public final Chart tempChart;

	public WTemperatureChart() {
		startPoint = DateTimeFormat.getFormat("yyyy-MM-dd").parse((DateTimeFormat.getFormat("yyyy-MM-dd").format(new Date()))).getTime();
		tempChart = createChart("");		
		
		initWidget(uiBinder.createAndBindUi(this));
		tmr.schedule(1000);
		//readTemp();
	}

	public Chart createChart(String dbInfo) {

		 final Chart chart = new Chart()  
         .setType(Series.Type.SPLINE)  
         .setChartTitleText("Wind speed during two days")  
         .setChartSubtitleText("October 6th and 7th 2009 at two locations in Vik i Sogn, Norway")  
         .setToolTip(new ToolTip()  
             .setFormatter(new ToolTipFormatter() {  
                 public String format(ToolTipData toolTipData) {  
                     return DateTimeFormat.getFormat("d. MMMM YYYY HH:00").format(  
                         new Date(toolTipData.getXAsLong())  
                     ) + ": " + toolTipData.getYAsDouble() + " m/s";  
                 }  
             })  
         )  
         .setSplinePlotOptions(new SplinePlotOptions()  
             .setLineWidth(4)  
             .setHoverStateLineWidth(5)  
             .setMarker(new Marker()  
                 .setEnabled(false)  
                 .setHoverState(new Marker()  
                     .setEnabled(true)  
                     .setSymbol(Marker.Symbol.CIRCLE)  
                     .setRadius(5)  
                     .setLineWidth(1)  
                 )  
             )  
             .setPointInterval(3600000)  // one hour  
             .setPointStart(startPoint)  
         );  

     chart.getXAxis()  
         .setType(Axis.Type.DATE_TIME)
         .setTickInterval(1800000);  

     final YAxis axis = chart.getYAxis();  
//     final Color blueColor = new Color(68, 170, 213, 0.1);  
//     final Color clearColor = new Color(0, 0, 0, 0);  
     axis.setAxisTitleText("Wind speed (m/s)")  
         .setMin(0)  
         .setMinorGridLineWidth(0)  
         .setGridLineWidth(0)  
         .setAlternateGridColor("#F0F0F0");  

     chart.addSeries(chart.createSeries()  
         .setName("Hestavollane")  
         .setPoints(new Number[]{  
             4.3, 5.1, 4.3, 5.2, 5.4, 4.7, 3.5, 4.1, 5.6, 7.4, 6.9, 7.1,  
             7.9, 7.9  
         })  
     );
		return chart;
	}

	private void readTemp() {
		try {
			JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
			jsonp.setTimeout(5000);

			String url = "http://192.168.44.200:8080/api/wifinou/TMP";

			jsonp.requestObject(url, new AsyncCallback<DataReader>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(DataReader result) {					
					//temp = result.getData();
					Window.alert(temp);
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
}
