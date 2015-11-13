"use strict";
/*
    This will be used to schedule cron like jobs for the application
*/
var cron = require('cron');
var conf = require('./config/configs');
var request = require('request');
var dateFormat = require('dateformat');
var tempHistory = require('./models/tempHistory');


/**
 * Will run every 30 minutes
 */
var recordTemp = cron.job("*/30 * * * * *", function() {
	saveTempToDb();
});
//export the job
module.exports = {
	saveTemp : recordTemp
};

/**
 * Read temperature value from sensor and stores it to database
 */
function saveTempToDb() {
console.log("save");
	for ( var device in conf.devices) {
		var allowedAct = conf.devices[device].allowedActions;
		var isAllowed = allowedAct.indexOf("TMP");
		if (isAllowed != -1) {
			request.post({
				url : "http://" + conf.devices[device].ip,
				form : {
					action : 'TMP'
				}
			}, function optionalCallback(err, httpResponse, body) {
				if (err) {
					return console.error('fail:', err);
				}

				var response = JSON.parse(body);
				var tHistory = new tempHistory();
				tHistory.sensor_id = response.MAC;
				tHistory.name = device;
				tHistory.datetime = new Date().getTime();
				tHistory.value = response.data;
				tHistory.save(function(err) {
					if (err) {
						return console.info(err);
					}
				});
			});
		}
	}
}