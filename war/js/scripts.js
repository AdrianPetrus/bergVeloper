function initJs() {
	// initialize retractable sidebar menu
	var jPM = $.jPanelMenu({
		menu : '#menu',
		trigger : '#menu-trigger',
		panel : '#app-body',
		clone : false,
		duration : 300,
		openPosition : 200,
		closeOnContentClick : false
	});
	jPM.on();
	jPM.open();

	$(".app-logo").click(function() {
		jPM.close();
	});

	// inside functionality for sidebar menu
	var icons = {
		header : "ui-icon-circle-arrow-e",
		activeHeader : "ui-icon-circle-arrow-s"
	};
	$("#nav div:first").accordion({
		icons : icons,
		collapsible : true,
		active : 0,
		heightStyle : "content"
	});
	$("#toggle").button().click(function() {
		if ($("#selector").accordion("option", "icons")) {
			$("#selector").accordion("option", "icons", null);
		} else {
			$("#selector").accordion("option", "icons", icons);
		}
	});

	// list table rollover color
	$(".tbl-list").each(function() {
		$(this).find("table").first().find("tr").mouseover(function() {
			$(this).addClass("over");
		});
		$(this).find("table").first().find("tr").mouseout(function() {
			$(this).removeClass("over");
		});
	});

	// smoth scroll to anchor
	$('a[href*=#]:not([href=#])').click(
			function() {
				if (location.pathname.replace(/^\//, '') == this.pathname
						.replace(/^\//, '')
						&& location.hostname == this.hostname) {
					var target = $(this.hash);
					target = target.length ? target : $('[name='
							+ this.hash.slice(1) + ']');
					if (target.length) {
						$('html,body').animate({
							scrollTop : target.offset().top
						}, 1000);
						return false;
					}
				}
			});
}