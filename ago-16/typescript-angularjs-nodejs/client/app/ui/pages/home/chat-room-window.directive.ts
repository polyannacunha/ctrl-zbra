namespace UI.Pages.Home {

    import IDirective = angular.IDirective;
    import MessageRepository = Repository.MessageRepository;
    import IScope = angular.IScope;
    import Message = Model.Message;
    import ChatRoom = Model.ChatRoom;
    import NotificationMediator = UI.Mediators.NotificationMediator;
    import SessionService = Services.SessionService;

    export class ChatRoomWindow implements IDirective {
        restrict = 'E';
        scope = true;
        template = `
            <md-content flex layout-fill>
                <loading-overlay visible="chatRoomCtrl.loading">
                    <div layout="column" layout-fill ng-show="chatRoomCtrl.room">
                        <div flex layout-padding style="overflow-y: scroll">
                            <md-list-item class="md-2-line" ng-repeat="message in chatRoomCtrl.messages">
                                <img ng-src="{{ 'https://www.gravatar.com/avatar/' + message.user.gravatarHash + '?d=identicon' }}" class="md-avatar" alt="{{ message.user.name }}" />
                                <div class="md-list-item-text">
                                    <p>{{ message.user.name }}</p>
                                    <h3><b>{{ message.text }}</b></h3>
                                </div>
                            </md-list-item>
                        </div>
                        <div flex-grow style="background-color: rgba(0, 0, 0, .10); padding: 0 15px 0 15px">
                            <form>
                                <div layout="row">
                                    <md-input-container flex style="margin-bottom: 0">
                                        <label></label>
                                        <input ng-model="chatRoomCtrl.newMessage" placeholder="message..." autofocus>
                                    </md-input-container>
                                    <md-button class="md-primary md-raised" ng-click="chatRoomCtrl.onSendMessage(chatRoomCtrl.newMessage)" type="submit" ng-show="false">Send</md-button>
                                </div>
                            </form>
                        </div>
                    </div>
                </loading-overlay>
            </md-content>
        `;
        controller = Controller;
        controllerAs = 'chatRoomCtrl';
        bindToController = {
            room: '='
        };
    }

    class Controller {
        static $inject = ['$scope', 'MessageRepository', 'NotificationMediator', 'SessionService'];

        private room:ChatRoom;
        private loading:boolean;
        private newMessage:string;
        private messages:Message[] = [];

        constructor(private $scope:IScope,
                    private messageRepository:MessageRepository,
                    private notificationMediator:NotificationMediator,
                    private sessionService:SessionService) {

            this.messageRepository.addOnMessageUpdatedListener($scope, (message: Message) => {
                if (message.room.id == this.room.id) {
                    this.messages.push(message);
                    this.$scope.$apply();
                }
            });

            $scope.$watch("chatRoomCtrl.room", (n:ChatRoom) => {
                if (n == null) return;

                this.messages = [];
                this.loading = true;
                messageRepository.getMessagesByRoom(n.id)
                    .then(messages => {
                        this.messages = messages;
                    })
                    .catch(() => {
                        this.notificationMediator.info("Something went terribly wrong =(")
                    })
                    .finally(() => {
                        this.loading = false;
                    });
            });
        }

        public onSendMessage() {
            this.sessionService.sendMessage(this.room, this.newMessage);
            this.newMessage = '';
        }
    }
}