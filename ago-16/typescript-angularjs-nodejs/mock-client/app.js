(function() {
    var ws = new SockJS('http://localhost:8080/stomp');
    var stompClient = Stomp.over(ws);
    stompClient.heartbeat.outgoing = 0;
    stompClient.heartbeat.incoming = 0;
    
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/user/queue/rooms', function(message) {
            var rooms = JSON.parse(message.body);
            var room = rooms[0];

            stompClient.send('/zbra-chat/join/' + room.id, {}, JSON.stringify({ username: 'jdoe', email: 'jdoe@test.com' }));

            var serverRoom = JSON.parse(message.body);
                var messagePayload = {
                    user: { username: 'jdoe', email: 'jdoe@test.com' },
                    text: 'HALLO THAR! ' + new Date().getTime()
                };
            stompClient.send('/zbra-chat/messages/' + room.id, {}, JSON.stringify(messagePayload));
            //stompClient.subscribe('/queue/room/' + room.id, function(message) {
                
            //});
            stompClient.subscribe('/queue/messages/' + room.id, function(message) {

            });
        });
        stompClient.send('/zbra-chat/list/rooms', {});
    }, function(frame) {
        console.log('ERROR => ' + frame);
    }); 
})();