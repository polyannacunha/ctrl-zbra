namespace UI.Pages.Home {
    import IScope = angular.IScope;
    import ChatRoom = Model.ChatRoom;
    import ChatRoomRepository = Repository.ChatRoomRepository;
    import NotificationMediator = UI.Mediators.NotificationMediator;
    import IDialogService = angular.material.IDialogService;
    import User = Model.User;
    import SessionService = Services.SessionService;

    //noinspection JSUnusedLocalSymbols
    export class HomeController {
        static $inject = ['$scope', '$mdDialog', 'SessionService', 'ChatRoomRepository', 'NotificationMediator'];

        public rooms:Array<ChatRoom>;
        public selectedRoom:ChatRoom;

        constructor(
            private $scope:IScope,
            private $mdDialog:IDialogService,
            private sessionService:SessionService,
            private chatRoomRepository:ChatRoomRepository,
            private notificationMediator:NotificationMediator) {

            $mdDialog
                .show({
                    template: `
                    <md-dialog aria-label="Mango (Fruit)"  ng-cloak>
                        <form>
                            <md-toolbar>
                                <div class="md-toolbar-tools">
                                    <h2>User Information</h2>
                                    <span flex></span>
                                </div>
                            </md-toolbar>
                            <md-dialog-content>
                                <md-input-container>
                                    <label>Name</label>
                                    <input ng-model="name">
                                </md-input-container>
                                <md-input-container>
                                    <label>Email</label>
                                    <input ng-model="email" type="email">
                                </md-input-container>
                            </md-dialog-content>
                            <md-dialog-actions layout="row">
                                <span flex></span>
                                <md-button ng-click="dialogCtrl.onSignIn()" md-autofocus>Sign in</md-button>
                            </md-dialog-actions>
                        </form>
                    </md-dialog>
                    `,
                    parent: angular.element(document.body),
                    controllerAs: 'dialogCtrl',
                    clickOutsideToClose:true,
                    controller: class {
                        static $inject = ['$mdDialog'];

                        public name: string;
                        public email: string;

                        constructor(private $mdDialog:IDialogService) {
                        }

                        public onSignIn() {
                            let user = new User();
                            user.name = this.name;
                            user.email = this.email;
                            this.$mdDialog.hide(user);
                        }
                    },
                })
                .then((user: User) => {
                    this.sessionService.joinChat(user)
                        .then(() => {
                            this.reloadRooms();
                            this.chatRoomRepository.addRoomsUpdatedListener($scope, (room: ChatRoom) => this.reloadRooms());
                        })
                        .catch(() => {
                            this.notificationMediator.info("Could not connect to server");
                        })
                });
        }

        public onRoomSelected(room:ChatRoom) {
            this.selectedRoom = room;
        }

        private reloadRooms() {
            this.chatRoomRepository.get()
                .then((rooms) => {
                    this.rooms = rooms;
                })
                .catch(() => {
                    this.notificationMediator.info("Error loading Chat Rooms")
                });
        }
    }
}