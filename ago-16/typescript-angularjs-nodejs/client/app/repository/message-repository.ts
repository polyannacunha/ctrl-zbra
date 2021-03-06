namespace Repository {

    import User = Model.User;
    import IHttpService = angular.IHttpService;
    import IQService = angular.IQService;
    import IPromise = angular.IPromise;
    import Message = Model.Message;
    import Dictionary = _.Dictionary;
    import ChatRoom = Model.ChatRoom;
    import IRootScopeService = angular.IRootScopeService;
    import IScope = angular.IScope;

    export class MessageRepository {
        static $inject = ['$q', '$http', '$rootScope'];
        static onMessagesUpdatedEvent = "sessionService.onNewMessage";

        private messagesByChatRoom:{ [id:string]:Message[]; } = {};

        constructor(private $q:IQService,
                    private $http:IHttpService,
                    private $rootScope:IRootScopeService) {
        }

        public addMessage(message:Message):IPromise<any> {
            var deferred = this.$q.defer();

            if (message != null) {
                if (!this.messagesByChatRoom.hasOwnProperty(message.room.id)) {
                    this.messagesByChatRoom[message.room.id] = [];
                }
                this.messagesByChatRoom[message.room.id].push(message);
                this.$rootScope.$broadcast(MessageRepository.onMessagesUpdatedEvent, message);
                deferred.resolve();
            } else {
                deferred.reject("Message cannot be null")
            }

            return deferred.promise;
        }

        public getMessagesByRoom(roomId:string):IPromise<Message[]> {
            var deferred = this.$q.defer<Message[]>();

            if (roomId != null) {
                deferred.resolve(this.messagesByChatRoom[roomId] || []);
            } else {
                deferred.reject("Room cannot be null")
            }

            return deferred.promise;
        }

        public addOnMessageUpdatedListener(scope: IScope, delegate: (newMessage: Message) => void) {
            scope.$on(MessageRepository.onMessagesUpdatedEvent, (event, message: Message) => {
                delegate(message);
            });
        }
    }
}