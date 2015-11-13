/*
    Definition of tempHistory model
*/
var mongoose = require('mongoose');
var Schema   = mongoose.Schema;

//definiton of schema
var tempHistorySchema = new Schema({
	sensor_id: String,
	name: String,
	datetime: { type : Date, default: Date.now },
	value: Number
});

//export the model
module.exports = mongoose.model('tempHistory', tempHistorySchema);