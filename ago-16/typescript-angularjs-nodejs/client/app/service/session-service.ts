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
            this.user = user;
            this.$stomp
                .connect('http://localhost:8080/stomp')
                .then((frame) => {

                    this.$stomp.send(`/zbra-chat/list/rooms`, null,  {});
                    this.$stomp.subscribe('/user/queue/rooms', (payload:any) => {
                        let rooms = payload.map(v => {
                            let room = new ChatRoom();
                            room.id = v.id;
                            room.name = v.name;
                            room.users = [];
                            for (let rawUser of v.users) {
                                let u = new User();
                                u.id = rawUser.userName;
                                u.name = rawUser.userName;
                                u.email = rawUser.email;
                                room.users.push(u);
                            }

                            return room;
                        });

                        for (let room of rooms) {
                            this.chatRoomRepository.add(room);

                            this.$stomp.send(`/zbra-chat/join/${room.id}`, { userName: user.name, email: user.email }, {});
                            this.$stomp.subscribe(`/queue/messages/${room.id}`, (rawMessage:any) => {
                                let message = new Message();
                                message.text = rawMessage.text;
                                message.room = room;
                                message.user = new User();
                                message.user.id = rawMessage.user.userName;
                                message.user.name = rawMessage.user.userName;
                                message.user.email = rawMessage.user.email;

                                this.messageRepository.addMessage(message);
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

        public sendMessage(room: ChatRoom, text: string) {
            this.$stomp.send(`/queue/messages/${room.id}`, { user: { userName: this.user.name, email: this.user.email }, text: text},  {});
        }
    }
}