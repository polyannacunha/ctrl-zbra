namespace UI.Pages.Home {
    import IScope = angular.IScope;
    import ChatRoom = Model.ChatRoom;
    import ChatRoomRepository = Repository.ChatRoomRepository;
    import NotificationMediator = UI.Mediators.NotificationMediator;

    //noinspection JSUnusedLocalSymbols
    export class HomeController {
        static $inject = ['ChatRoomRepository', 'NotificationMediator'];

        public rooms:Array<ChatRoom>;
        public selectedRoom:ChatRoom;

        constructor(
            private chatRoomRepository:ChatRoomRepository,
            private notificationMediator:NotificationMediator) {

            chatRoomRepository.get()
                .then((rooms) => {
                    this.rooms = rooms;
                })
                .catch(() => {
                    this.notificationMediator.info("Error loading Chat Rooms")
                });
        }

        public onRoomSelected(room:ChatRoom) {
            this.selectedRoom = room;
        }
    }
}