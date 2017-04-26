var io;
var gameSocket;
var players = [];
var games = [];
/**
 * This function is called by index.js to initialize a new game instance.
 *
 * @param sio The Socket.IO library
 * @param socket The socket object for the connected client.
 */
exports.initGame = function(sio, socket){
    io = sio;
    gameSocket = socket;
    gameSocket.emit('connected', { message: "You are connected!" });
    // Host Events
    gameSocket.on('hostCreateNewGame', hostCreateNewGame);
    gameSocket.on('hostRoomFull', hostPrepareGame);
    gameSocket.on('ready',ready);
    gameSocket.on('startGame', startGame);
    gameSocket.on('hostStartGame', hostStartGame);
    gameSocket.on('hostNextRound', hostNextRound);
    gameSocket.on('sendImg', sendImage);
    // Player Events
    gameSocket.on('playerJoinGame', playerJoinGame);
    gameSocket.on('playerAnswer', playerAnswer);
    gameSocket.on('playerRestart', playerRestart);
    gameSocket.on('getAllPlayers', getAllPlayers);
    gameSocket.on('putPlayer', putPlayer);
    gameSocket.on('disconnect', removePlayer);
    gameSocket.on('leaveRoom', playerLeftRoom);
    gameSocket.on('decodedImg', sendCorrectImg);
    gameSocket.on('roundWinner', getWinner);

}

/* *******************************
   *                             *
   *       HOST FUNCTIONS        *
   *                             *
   ******************************* */

/**
 * The 'START' button was clicked and 'hostCreateNewGame' event occurred.
 */
function hostCreateNewGame() {
    // Create a unique Socket.IO Room
    var thisGameId = ( Math.random() * 100000 ) | 0;
    var gameObj = {playerCount: 0, gameId: thisGameId, readyCount: 0, hintGiver: 0};
    games.push(gameObj);
    // Return the Room ID (gameId) and the socket ID (mySocketId) to the browser client
    this.emit('newGameCreated', {gameId: thisGameId, mySocketId: this.id});
    //console.log(this.id.toString());
    //console.log(thisGameId.toString());
    // Join the Room and wait for the players
    this.join(thisGameId.toString());
};

/*
 * Two players have joined. Alert the host!
 * @param gameId The game ID / room ID
 */
function hostPrepareGame(gameId) {
    var sock = this;
    var data = {
        mySocketId : sock.id,
        gameId : gameId
    };
    //console.log("All Players Present. Preparing game...");
    io.sockets.in(data.gameId).emit('beginNewGame', data);
};

/*
 * The Countdown has finished, and the game begins!
 * @param gameId The game ID / room ID
 */
function ready(gameId) {
    for(var i = 0; i < games.length ; i++) {
            if(games[i].gameId == gameId) {
                games[i].readyCount++;
                //console.log(games[i]);
                if(games[i].readyCount == games[i].playerCount && games[i].playerCount != 1) {
                    sendHintGiver(gameId,i);
                  //  io.sockets.in(gameId).emit('startGame');
                    break;
                }
                break;
            }

    }
}

function startGame(gameId) {
	var index;
	var room = io.sockets.adapter.rooms[gameId];
    var playersInRoom = [];
 
	for(var i = 0; i < games.length; i++) {
		if(games[i].gameId == gameId) {
			index = i;
		}
	}

    for(var i = 0; i < players.length; i++) {
    	    if(room.sockets[players[i].socketId]==true) {
    	        playersInRoom.push(players[i]);
    	        io.sockets.in(playersInRoom[i].socketId).emit('startGame');
    	    }
    } 
    
    
    // console.log(games[index]);
    // console.log(gameId);
    // console.log(games);
    var hintGiverSock = playersInRoom[games[index].hintGiver].socketId;
    games[index].hintGiver++;
    io.sockets.to(hintGiverSock).emit('goToPicturePage');
}

function hostStartGame(gameId) {
    setHintGiver(gameId);
}
/**
 * A player answered correctly. Time for the next word.
 * @param data Sent from the client. Contains the current round and gameId (room)
 */
function hostNextRound(data) {
    if(data.round < wordPool.length ){
        // Send a new set of words back to the host and players.
        sendWord(data.round, data.gameId);
    } else {
        // If the current round exceeds the number of words, send the 'gameOver' event.
        io.sockets.in(data.gameId).emit('gameOver',data);
    }
}
/* *****************************
   *                           *
   *     PLAYER FUNCTIONS      *
   *                           *
   ***************************** */

/**
 * A player clicked the 'START GAME' button.
 * Attempt to connect them to the room that matches
 * the gameId entered by the player.
 * @param data Contains data entered via player's input - playerName and gameId. And also the socketId
 */
function playerJoinGame(data) {
    //console.log('Player ' + data.playerName + 'attempting to join game: ' + data.gameId );

    // A reference to the player's Socket.IO socket object
    var sock = this;

    // Look up the room ID in the Socket.IO manager object.
    var room = io.sockets.adapter.rooms[data.gameId];
    //If the room exists...
    if( room != undefined ){
        // attach the socket id to the data object.

        // Join the room
        //console.log('Player ' + data.playerName + ' joining game: ' + data.gameId );
        this.join(data.gameId);
        data.socketId = this.id;
        // Emit an event notifying the clients that the player has joined the room.
        //console.log(data);
        io.in(data.gameId).emit('playerJoinedRoom', data);

    } else {
        // Otherwise, send an error message back to the player.
        io.emit('error',{message: "This room does not exist."} );
    }
}

// *
//     Player left room
//     parameter includes - String Game Id
// *
function playerLeftRoom(gameId) {
    //CONTINUE DOING THIS
    this.leave(gameId);
}

/**
    Gets all the player in the current room.
    Data consists of -gameId, playerName and also the avatarName of sender.
**/
function getAllPlayers(data) {
    var room = io.sockets.adapter.rooms[data.gameId];
    var playersInRoom = [];

    for(var i = 0; i < players.length; i++) {
        if(room.sockets[players[i].socketId]==true) {
            playersInRoom.push(players[i]);
        }
    }
    var imgSubmitted;
    this.emit('playersInRoom', playersInRoom);
    this.broadcast.to(data.gameId).emit('newPlayerEntered', data);
    this.broadcast.to(data.gameId).emit('timerIs', data.timer);
    
}

function sendImage (data) {
        data.senderSocket = this.id;
        console.log(this.id);
        console.log(data);  
        this.broadcast.to(data.gameId).emit('newImageSubmitted', data);
}

/**
 * A player has tapped a word in the word list.
 * @param data gameId
 */
function playerAnswer(data) {
    // console.log('Player ID: ' + data.playerId + ' answered a question with: ' + data.answer);

    // The player's answer is attached to the data object.  \
    // Emit an event with the answer so it can be checked by the 'Host'
    io.sockets.in(data.gameId).emit('hostCheckAnswer', data);
}

/**
 * The game is over, and a player has clicked a button to restart the game.
 * @param data
 */
function playerRestart(data) {
    // console.log('Player: ' + data.playerName + ' ready for new game.');

    // Emit the player's data back to the clients in the game room.
    data.playerId = this.id;
    io.sockets.in(data.gameId).emit('playerJoinedRoom',data);
}

function putPlayer(data) {
    players.push(data);
    for(var i = 0 ; i < games.length; i++) {
            if(games[i].gameId == data.gameId) {
                        games[i].playerCount++;
            }

    }
    //console.log("putting data");
    //console.log(data);

}

function removePlayer() {
    for(var i = 0; i < players.length; i++) {
        if (players[i].socketId==this.id) {
            players.splice(i,1);
        }
    }
    
}





/* *************************
   *                       *
   *      GAME LOGIC       *
   *                       *
   ************************* */

function sendHintGiver(gameId, index) {
	var room = io.sockets.adapter.rooms[gameId];
    var playersInRoom = [];

    for(var i = 0; i < players.length; i++) {
        if(room.sockets[players[i].socketId]==true) {
            playersInRoom.push(players[i]);
        }
    }
    var hintGiverSock = playersInRoom[games[index].hintGiver].socketId;
    io.sockets.in(gameId).emit('hintGiver', hintGiverSock);
    //console.log(hintGiverSock);
}

function sendCorrectImg(data) {
    //console.log(data.decodedImg);
    io.sockets.in(data.gameId).emit('correctImg', data.decodedImg);
}

function getWinner(data) {
    var winner;
  for(var i = 0; i < players.length; i++) {
    if(players[i].socketId == data.winnerSocket) {
        winner = players[i];
        players[i].score++;
        break;
    }
  }
  console.log(winner);
  this.broadcast.to(data.gameId).emit("winner", winner);

}