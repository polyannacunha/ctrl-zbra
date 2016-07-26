namespace Repository {

    import User = Model.User;
    import IHttpService = angular.IHttpService;
    import IQService = angular.IQService;
    import IPromise = angular.IPromise;
    import Message = Model.Message;
    import Dictionary = _.Dictionary;
    import ChatRoom = Model.ChatRoom;

    export class MessageRepository {
        static $inject = ['$q', '$http'];

        private messagesByChatRoom: { [id: string] : Message[]; } = {};

        constructor(private $q:IQService,
                    private $http:IHttpService) {
        }

        public addMessageToRoom(message: Message): IPromise<any> {
            var deferred = this.$q.defer();

            if (message != null) {
                this.messagesByChatRoom[message.room.id].push(message);
                deferred.resolve();
            } else {
                deferred.reject("Message cannot be null")
            }

            return deferred.promise;
        }

        public getMessagesByRoom(roomId: string):IPromise<Message[]> {
            var deferred = this.$q.defer<Message[]>();

            if (roomId != null) {
                deferred.resolve(this.messagesByChatRoom[roomId]);
            } else {
                deferred.reject("Room cannot be null")
            }

            return deferred.promise;
        }
    }
}