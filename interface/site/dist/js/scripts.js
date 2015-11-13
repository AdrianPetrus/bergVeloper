jQuery(document).ready(function($) {
	"use strict";
	
	// ------------------------------------------------------------------------
	// HOUSE MAP
	// ------------------------------------------------------------------------
	var mapsvg = Snap("#house");
	var loadMap = Snap.load("../images/house.svg", onMapLoaded);
		
	var temp1 = 22;
	var temp2 = 21;
	
	function onMapLoaded(data){ 
		mapsvg.append(data);
		$("#sockets > g").click(function() {
			if($(this).attr("status") === "enabled") {
				disableElement("socket", $(this).attr("id"));
			} else {
				enableElement("socket", $(this).attr("id"));
			}
		});
		$("#lights > g").click(function() {
			if($(this).attr("status") === "enabled") {
				disableElement("light", $(this).attr("id"));
			} else {
				enableElement("light", $(this).attr("id"));
			}
		});
		$("#locks > g").click(function() {
			if($(this).attr("status") === "enabled") {
				disableElement("lock", $(this).attr("id"));
			} else {
				enableElement("lock", $(this).attr("id"));
			}
		});
		$("#thermo1 .up").click(function() {
			setTemperature("thermo1", temp1++ + "°");
		});
		$("#thermo1 .down").click(function() {
			setTemperature("thermo1", temp1-- + "°");
		});
		$("#thermo2 .up").click(function() {
			setTemperature("thermo2", temp2++ + "°");
		});
		$("#thermo2 .down").click(function() {
			setTemperature("thermo2", temp2-- + "°");
		});
		
		// init elements
		enableElement("socket","socket1");
		disableElement("socket","socket2");
		disableElement("socket","socket3");
		enableElement("light","light1");
		enableElement("light","light2");
		disableElement("light","light3");
		enableElement("lock","lock1");
		enableElement("lock","lock2");
		disableElement("lock","lock3");
		setTemperature("thermo1", temp1 + "°");
		setTemperature("thermo2", temp2 + "°");
	}
	// ------------------------------------------------------------------------
	function enableElement(type, id) {
		switch(type) {
			case "socket":
				$("#"+id).attr("status", "enabled");
				$("#"+id+" > path:first-child").attr("fill", "#000000");
				break;
			case "light":
				$("#"+id).attr("status", "enabled");
				$("#"+id+" > path:first-child").attr("fill", "#000000");
				$("#"+id+" .disabled").attr("opacity", 0);
				$("#"+id+" .enabled").attr("opacity", 1);
				break;
			case "lock":
				$("#"+id).attr("status", "enabled");
				$("#"+id+" > path:first-child").attr("fill", "#000000");
				$("#"+id+" .enabled").attr("opacity", 1);
				$("#"+id+" .disabled").attr("opacity", 0);
				break;
			default:
				//
		}
	}
	function disableElement(type, id) {
		switch(type) {
			case "socket":
				$("#"+id).attr("status", "disabled");
				$("#"+id+" > path:first-child").attr("fill", "#FFFFFF");
				break;
			case "light":
				$("#"+id).attr("status", "disabled");
				$("#"+id+" > path:first-child").attr("fill", "#FFFFFF");
				$("#"+id+" .disabled").attr("opacity", 1);
				$("#"+id+" .enabled").attr("opacity", 0);
				break;
			case "lock":
				$("#"+id).attr("status", "disabled");
				$("#"+id+" > path:first-child").attr("fill", "#FFFFFF");
				$("#"+id+" .enabled").attr("opacity", 0);
				$("#"+id+" .disabled").attr("opacity", 1);
				break;
			default:
				//
		}
	}
	
	function setTemperature(obj, deg) {
		$("#"+obj).find("text").html(deg);
	}
	
	// ------------------------------------------------------------------------
	// FAN
	// ------------------------------------------------------------------------
	var fansvg = Snap("#fan-icon");
	var loadFan = Snap.load("../images/fan.svg", onFanLoaded);
	
	function onFanLoaded(data) {
		fansvg.append(data);
		startFan();
	}
	// ------------------------------------------------------------------------
	function startFan(){
		var fan = Snap.select("#fan");
		fan.animate(
			{ transform: 'r359,12,12'},
			3000, 
			function(){ 
				fan.attr({ transform: 'r0 12 12'});
				startFan();
			}
		);
	}
	function stopFan(){
		var fan = Snap.select("#fan");
		fan.stop();
	}
	
	// ------------------------------------------------------------------------
	// SPRINKLERS
	// ------------------------------------------------------------------------
	var spRun = [false,false];
	
	
	var loadSprinkler1 = Snap.load("../images/sprinkler.svg", onSprinklerLoaded);
	
	function onSprinklerLoaded(data) {
		var sprinkler1 = Snap("#sprinkler1");
		sprinkler1.append(data);		
	}
	
	// ------------------------------------------------------------------------
	function startSprinkler(id){
		var drops = eval("sprinkler"+id).select("g.drops").attr("class","drops fleosch");
		spRun[id-1]=true;
	}
	function stopSprinkler(id){
		var drops = eval("sprinkler"+id).select("g.drops").attr("class","drops");
		spRun[id-1]=false;
	}
	
	$("#sbutton1").click(function() {
		if(spRun[0]){
			stopSprinkler(1);
		} else {
			startSprinkler(1);
		}
	});
	$("#sbutton2").click(function() {
		if(spRun[1]){
			stopSprinkler(2);
		} else {
			startSprinkler(2);
		}
	});
	
	// ------------------------------------------------------------------------
	// ELEMENTS
	// ------------------------------------------------------------------------
	
	$(".btn-element").click(function() {
		var obj = $(this).parent().prev().find(".icon");
		if(!obj.hasClass("element-disabled")) {
		 	obj.addClass("element-disabled");
		} else {
			obj.removeClass("element-disabled");
		}
	});

});