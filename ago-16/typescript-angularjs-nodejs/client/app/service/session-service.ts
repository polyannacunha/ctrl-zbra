namespace Services {

    import User = Model.User;
    import IHttpService = angular.IHttpService;
    import IQService = angular.IQService;
    import IPromise = angular.IPromise;
    import MessageRepository = Repository.MessageRepository;
    import ChatRoomRepository = Repository.ChatRoomRepository;
    import ChatRoom = Model.ChatRoom;
    import Message = Model.Message;
    import IRootScopeService = angular.IRootScopeService;
    import IScope = angular.IScope;

    export class SessionService {
        static $inject = ['$q', '$stomp', '$rootScope', 'MessageRepository', 'ChatRoomRepository'];

        public user:User;

        constructor(
            private $q:IQService,
            private $stomp:ngStomp,
            private $rootScope:IRootScopeService,
            private messageRepository:MessageRepository,
            private chatRoomRepository:ChatRoomRepository) {
        }

        public joinChat(user: User): IPromise<any> {
            let deferred = this.$q.defer();

            this.$stomp
                .connect('http://localhost:8080')
                .then((frame) => {

                    this.$stomp.subscribe('/user/queue/list', (payload) => {
                        let raw = <Array<any>> JSON.parse(payload);
                        let rooms = raw.map(v => {
                            let room = new ChatRoom();
                            room.id = v.id;
                            room.name = v.name;
                            room.users = [];
                            for (let rawUser of v.users) {
                                let u = new User();
                                u.id = rawUser.username;
                                u.name = rawUser.username;
                                u.email = rawUser.email;
                                room.users.push(u);
                            }

                            return room;
                        });

                        for (let room of rooms) {
                            this.chatRoomRepository.add(room);

                            this.$stomp.send(`/zbra-chat/join/${room.id}`, { username: user.id, email: user.email }, {});
                            this.$stomp.subscribe(`/user/queue/room/${room.id}`, (payload) => {
                                let messages = <Array<any>>JSON.parse(payload).messages;
                                if (messages.length > 0) {
                                    for (let rawMessage of messages) {
                                        let message = new Message();
                                        message.text = rawMessage.text;
                                        message.room = room;
                                        message.user = new User();
                                        message.user.id = rawMessage.user.username;
                                        message.user.name = rawMessage.user.username;
                                        message.user.email = rawMessage.user.email;

                                        this.messageRepository.addMessageToRoom(message);
                                    }
                                }
                            });

                            this.$stomp.subscribe(`/queue/messages/${room.id}`, (payload) => {
                                let rawMessage = <any>JSON.parse(payload);
                                let message = new Message();
                                message.text = rawMessage.text;
                                message.room = room;
                                message.user = new User();
                                message.user.id = rawMessage.user.username;
                                message.user.name = rawMessage.user.username;
                                message.user.email = rawMessage.user.email;

                                this.messageRepository.addMessageToRoom(message);
                            });
                        }

                        deferred.resolve();
                    });
                })
                .catch(() => {
                    deferred.reject();
                });

            return deferred.promise;
        }

        public sendMessage(roomId: string, text: string) {
            this.$stomp.send(`/queue/messages/${roomId}`, { user: { username: this.user.id, email: this.user.email }, text: text},  {});
        }
    }
}