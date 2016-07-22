(function() {
    var ws = new SockJS('http://192.168.10.115:8080/stomp');
    var stompClient = Stomp.over(ws);
    stompClient.heartbeat.outgoing = 0;
    stompClient.heartbeat.incoming = 0;
    
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/user/queue/list', function(message) {
            var rooms = JSON.parse(message.body);
            var room = rooms[0];

            stompClient.send('/zbra-chat/join/' + room.id, {}, JSON.stringify({ username: 'jdoe', email: 'jdoe@test.com' }));
            stompClient.subscribe('/user/queue/room/' + room.id, function(message) {
                var serverRoom = JSON.parse(message.body);
                var messagePayload = {
                    user: { username: 'jdoe', email: 'jdoe@test.com' },
                    text: 'HALLO THAR! ' + new Date().getTime()
                };
                stompClient.send('/zbra-chat/messages/' + room.id, {}, JSON.stringify(messagePayload));
            });
            stompClient.subscribe('/queue/messages/' + room.id, function(message) {
            });
        });
        stompClient.send('/zbra-chat/list', {});
    }, function(frame) {
        console.log('ERROR => ' + frame);
    }); 
})();