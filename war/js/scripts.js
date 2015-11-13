jQuery(document).ready(function($) {
	"use strict";

	var temp1 = 22;
	var temp2 = 21;

	window.loadSprinkler1 = function() {
		Snap.load("../../images/sprinkler.svg", onSprinkler1Loaded);
	}
	window.loadSprinkler2 = function() {
		Snap.load("../../images/sprinkler.svg", onSprinkler2Loaded);
	}
	window.loadPlan = function() {
		Snap.load("../../images/house.svg", onMapLoaded);
	}

	window.loadFan = function() {
		Snap.load("../../images/fan.svg", onFanLoaded);
	}

	function onMapLoaded(data) {
		var map = Snap("#house");
		map.append(data);

		$("#sockets > g").click(function() {
			if ($(this).attr("status") === "enabled") {
				disableElement("socket", $(this).attr("id"));
			} else {
				enableElement("socket", $(this).attr("id"));
			}
		});
		$("#lights > g").click(function() {
			if ($(this).attr("status") === "enabled") {
				disableElement("light", $(this).attr("id"));
			} else {
				enableElement("light", $(this).attr("id"));
			}
		});
		$("#locks > g").click(function() {
			if ($(this).attr("status") === "enabled") {
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
		// enableElement("socket", "socket1");
		// disableElement("socket", "socket2");
		// disableElement("socket", "socket3");
		// enableElement("light", "light1");
		// enableElement("light", "light2");
		// disableElement("light", "light3");
		 enableElement("lock", "lock1");
		// enableElement("lock", "lock2");
		// disableElement("lock", "lock3");

		setTemperature("thermo1", temp1 + "°");
		// setTemperature("thermo2", temp2 + "°");
		reDrawPlan();

	}

	function reDrawPlan() {
		$("#thermo2").hide();
		$("#socket1").hide();
		$("#socket2").hide();
		$("#lock2").hide();
		$("#light1").hide();
		$("#light3").hide();
		$("#lock3").hide();

	}

	function onFanLoaded(data) {
		var fansvg = Snap("#fan-icon");
		fansvg.append(data);
		var fan = fansvg.select("path");
		startAnimation(fan);
	}

	// ------------------------------------------------------------------------

	function startAnimation(obj) {
		obj.animate({
			transform : 'r359,12,12'
		}, 3000, function() {
			obj.attr({
				transform : 'r0 12 12'
			});
			startAnimation(obj);
		});
	}

	function stopAnimation(obj) {
		obj.stop();
	}

	// ------------------------------------------------------------------------

	function setTemperature(obj, deg) {
		$("#" + obj).find("text").html(deg);
	}

	// ------------------------------------------------------------------------

	function enableElement(type, id) {
		switch (type) {
		case "socket":
			$("#" + id).attr("status", "enabled");
			$("#" + id + " > path:first-child").attr("fill", "#000000");
			document.doAction(id, "PRZ");
			break;
		case "light":
			$("#" + id).attr("status", "enabled");
			$("#" + id + " > path:first-child").attr("fill", "#000000");
			$("#" + id + " .disabled").attr("opacity", 0);
			$("#" + id + " .enabled").attr("opacity", 1);
			document.doAction(id, "BEC");
			break;
		case "lock":
			$("#" + id).attr("status", "enabled");
			$("#" + id + " > path:first-child").attr("fill", "#000000");
			$("#" + id + " .enabled").attr("opacity", 1);
			$("#" + id + " .disabled").attr("opacity", 0);
			break;
		default:
			//
		}
	}
	function disableElement(type, id) {
		switch (type) {
		case "socket":
			$("#" + id).attr("status", "disabled");
			$("#" + id + " > path:first-child").attr("fill", "#FFFFFF");
			document.doAction(id, "PRZ");
			break;
		case "light":
			$("#" + id).attr("status", "disabled");
			$("#" + id + " > path:first-child").attr("fill", "#FFFFFF");
			$("#" + id + " .disabled").attr("opacity", 1);
			$("#" + id + " .enabled").attr("opacity", 0);
			document.doAction(id, "BEC");
			break;
		case "lock":
			$("#" + id).attr("status", "disabled");
			$("#" + id + " > path:first-child").attr("fill", "#FFFFFF");
			$("#" + id + " .enabled").attr("opacity", 0);
			$("#" + id + " .disabled").attr("opacity", 1);
			break;
		default:
			//
		}
	}

	// ------------------------------------------------------------------------
	// SPRINKLERS
	// ------------------------------------------------------------------------
	var spRun = [ false, false ];

	// var sprinkler2 = Snap("#sprinkler2");
	// var loadSprinkler2 = Snap.load("../../images/sprinkler.svg",
	// onSprinkler2Loaded);

	function onSprinkler1Loaded(data) {
		var sprinkler1 = Snap("#sprinkler1");
		sprinkler1.append(data);
		toggleSprinkler(1);
	}
	function onSprinkler2Loaded(data) {
		var sprinkler2 = Snap("#sprinkler2");
		sprinkler2.append(data);		
	}
	// function onSprinkler2Loaded(data) {
	// sprinkler2.append(data);
	// //stopSprinkler(2);
	// }
	// ------------------------------------------------------------------------
	window.toggleSprinkler = function(id) {
		var drops = $("#sprinkler" + id + " g.drops");
		drops[0].classList.toggle("fleosch");

	}
	// ------------------------------------------------------------------------

});
