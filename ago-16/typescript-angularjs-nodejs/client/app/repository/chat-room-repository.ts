namespace Repository {

    import IQService = angular.IQService;
    import IPromise = angular.IPromise;
    import ChatRoom = Model.ChatRoom;

    export class ChatRoomRepository {
        static $inject = ['$q', '$http'];

        private chatRooms:Array<ChatRoom> = [];

        constructor(private $q:IQService) {
        }

        public add(room:ChatRoom):IPromise<any> {
            var deferred = this.$q.defer();

            if (room != null) {
                this.chatRooms.push(room);
                deferred.resolve();
            } else {
                deferred.reject("ChatRoom is null")
            }

            return deferred.promise;
        }

        public get():IPromise<ChatRoom[]> {
            var deferred = this.$q.defer<ChatRoom[]>();
            deferred.resolve(this.chatRooms.map(r => r));
            return deferred.promise;
        }
    }
}