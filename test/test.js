var assert = require('assert');
var io = require('socket.io-client');
var socketURL = 'http://10.186.4.183:8080';
var should = require('should');

describe("server started correctly ", function() {

	it('Should succesfully create a server and socket', function(done) {
	var client = io(socketURL);
		client.should.be.ok;
		client.disconnect();
		done();
	
	
	})

	it('Should listen to a message', function(done) {
		var client = io(socketURL);
		client.on('connected', function(msg) {
			should.equal("You are connected!",msg.message);
			client.disconnect();
			done();
		});
	})

	it('Should emit game id and socket id', function(done) {
		var client = io(socketURL);
		client.emit('hostCreateNewGame');
		client.on('newGameCreated', function(msg) {
			should.exist(msg.gameId);
			should.exist(msg.mySocketId);
			client.disconnect();
			done();
		})
	})

});