// // Import the Express module
// var express = require('express');

// // Import the 'path' module (packaged with Node.js)
// var path = require('path');

// // Create a new instance of Express
// var app = express();



// //Import the http server
// var server = require('http').Server(app);

// var io = require('socket.io').listen(server);

// // Create a simple Express application
// app.configure(function() {
//     // Turn down the logging activity
//     app.use(express.logger('dev'));

//     // Serve static html, js, css, and image files from the 'public' directory
//     app.use(express.static(path.join(__dirname,'public')));
// });

// // Create a Node.js based http server on port 8080
// server.listen(process.env.PORT || 8080, function() {
// 	console.log("server listening at port 8080");
// });

// // Create a Socket.IO server and attach it to the http server

// // Reduce the logging output of Socket.IO
// io.set('log level',1);

// // Listen for Socket.IO Connections. Once connected, start the game logic.
// io.sockets.on('connection', function (socket) {
//     console.log('client connected');
//     guessit.initGame(io, socket);
// });
var express = require('express')
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);

// Import the Guessit game file.
var guessit= require('./guessit');
// Reduce the logging output of Socket.IO
io.set('log level',1);


io.on('connection',function(socket){
    console.log('one user connected '+socket.id);
    guessit.initGame(io, socket);
    socket.on('message',function(data){
        var sockets = io.sockets.sockets;
        /*sockets.forEach(function(sock){
            if(sock.id != socket.id)
            {
                sock.emit('message',data);
            }
        })*/
        socket.broadcast.emit('message', data);
    })
    socket.on('disconnect',function(){
        console.log('one user disconnected '+socket.id);
    })
})



http.listen(8080 ,function(){
    console.log('server listening on port 8080');
})
