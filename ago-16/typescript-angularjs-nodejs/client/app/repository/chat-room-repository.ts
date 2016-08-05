namespace Repository {

    import IQService = angular.IQService;
    import IPromise = angular.IPromise;
    import ChatRoom = Model.ChatRoom;
    import IScope = angular.IScope;
    import IRootScopeService = angular.IRootScopeService;

    export class ChatRoomRepository {
        static $inject = ['$q', '$rootScope'];
        static onRoomsUpdatedEvent = "sessionService.onNewRoom";

        private chatRooms:Array<ChatRoom> = [];

        constructor(private $q:IQService,
                    private $rootScope:IRootScopeService) {
        }

        public add(room:ChatRoom):IPromise<any> {
            var deferred = this.$q.defer();

            if (room != null) {
                this.chatRooms.push(room);
                deferred.resolve();
                this.$rootScope.$broadcast(ChatRoomRepository.onRoomsUpdatedEvent, room);
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

        public addRoomsUpdatedListener(scope: IScope, delegate: (newRoom: ChatRoom) => void) {
            scope.$on(ChatRoomRepository.onRoomsUpdatedEvent, (event, room: ChatRoom) => {
                delegate(room);
            });
        }
    }
}