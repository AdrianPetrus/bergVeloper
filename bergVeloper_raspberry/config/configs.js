/*
This will export the configurations of the application
*/
module.exports = {
	"devices" : {
		"esp1" : {
			"ip" : "192.168.88.101",
			"name" : "camera1",
			"allowedActions" : "ADC, ST, TMP, CAL, BEC, PRZ, STPRZ, STCAL, STIR",
			"mac" : ""
		},
		"esp2" : {
			"ip" : "192.168.88.102",
			"name" : "camera2",
			"allowedActions" : "CAL, ST, BEC, TMP, LUX, STCAL",
			"mac" : ""
		},
		"esp3" : {
			"ip" : "192.168.88.103",
			"name" : "camera2",
			"allowedActions" : "BEC, ST",
			"mac" : ""
		}
	}
};