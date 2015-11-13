"use strict";
// server.js

// BASE SETUP
// =============================================================================

// call the packages we need
var express = require('express'); // call express
var app = express(); // define our app using express
var bodyParser = require('body-parser');
var request = require('request');
var Promise = require('bluebird');
// connect to mongodb
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/happyhome');
// temp history model
var tempHistory = require('./models/tempHistory');
// app configs
var conf = require('./config/configs');
// cron jobs
var cron = require('./cron');
// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({
	extended : true
}));
app.use(bodyParser.json());

var port = process.env.PORT || 8080; // set our port

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router(); // get an instance of the express Router

// middleware to use for all requests
router.use(function(req, res, next) {
	console.log("Something is happening");
	next(); // make sure we go to the next routes and don't stop here
});
// default route
router.get('/', function(req, res) {
	res.json({
		message : 'Welcome to our api!'
	});
});
// route to get data from db
router.get('/qdb/device/:device/type/:type', function(req, res) {
	if (conf.devices[req.params.device] != undefined) {
		var allowedAct = conf.devices[req.params.device].allowedActions;
		var isAllowed = allowedAct.indexOf(req.params.type);
		var devName = req.params.device;
		if (isAllowed != -1) {
			switch (req.params.type) {
			case 'TMP':
				tempHistory.find({
					name : devName
				}, function(err, tempRec) {
					if (err) {
						return res.jsonp({
							status : 'error',
							data : '',
							deviceName : devName,
							message : err
						});
					}
					res.jsonp({
						status : 'success',
						data : tempRec,
						deviceName : devName,
						message : ''
					});
				});
				break;
			}
		} else {
			res.jsonp({
				status : 'error',
				data : '',
				deviceName : devName,
				message : 'Action ' + req.params.action + ' is not supported'
			});
		}
	} else {
		res.jsonp({
			status : 'error',
			data : '',
			deviceName : req.params.device,
			message : 'Device ' + req.params.device + ' not found.'
		});
	}
});
// route to get the reading from the sensor
router.get('/ask4web/device/:device/action/:action', function(req, res) {
	if (conf.devices[req.params.device] != undefined) {
		var allowedAct = conf.devices[req.params.device].allowedActions;
		var isAllowed = allowedAct.indexOf(req.params.action);
		var devName = req.params.device;
		if (isAllowed != -1) {
			request.post({
				url : 'http://' + conf.devices[req.params.device].ip,
				form : {
					action : req.params.action
				}
			}, function optionalCallback(err, httpResponse, body) {
				if (err) {
				console.log(err);
					return res.jsonp({
						status : 'error',
						value : '',
						rawData : '',
						deviceName : devName,
						message : 'Conexiunea cu senzorul ' + devName + ' a esuat.'
					});
				}
				var response = JSON.parse(body);
				res.jsonp({
					status : 'success',
					value : response.data,
					rawData : response,
					deviceName : devName,
					message : ''
				});
			});
		} else {
			res.jsonp({
				status : 'error',
				value : '',
				rawData : '',
				deviceName : devName,
				message : 'Action ' + req.params.action + ' is not supported'
			});
		}
	} else {
		res.jsonp({
			status : 'error',
			value : '',
			rawData : '',
			deviceName : req.params.device,
			message : 'Device ' + req.params.device + ' not found.'
		});
	}
});
//Will create a promise and return the result
function requestPost(data){
	return new Promise(function(resolve, reject){
		request.post(data, function optionalCallback(err, httpResponse, body){
		    if (err) {
			    return reject(err);
		    }
	            return resolve(body);

		});
	});
}

//Will call status for every device
function getLightsFor(device){
	var allowedAct = device.allowedActions;
        var isAllowed = allowedAct.indexOf("ST");
        if (isAllowed != -1) {
		return requestPost({
		    url : "http://" + device.ip,
		    form : {
                       action : 'ST'
	            }
		}).then(function(data){
	            console.log(data);
		    return { data : data.data, deviceName : device.name  };
		}).then(null, function(error){
		    console.log(error);
		    return {data: "Unavailable", deviceName : device.name};
		});

	}else{
		return Promise.resolve({data : "NotAllowed", deviceName: device.name});
	}

}
//route to get all the lights that are on
router.get('/getlightson', function(req, res){
    var response = [];
    var keys = Object.keys(conf.devices);

    var allDeviceChecks = keys.map(function(device){
	return getLightsFor(conf.devices[device]);
    });

    Promise.all(allDeviceChecks).then(function(allStuff){
    	res.jsonp({status: "success", data: allStuff, message: ""});
    }, function(err){console.log(err)});

});

// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
app.use('/api', router);

// REGISTER CRON JOBS
// cron.saveTemp.start();

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Listening on: ' + port);
