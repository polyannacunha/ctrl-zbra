namespace UI.Pages.Home {

    import IDirective = angular.IDirective;
    import MessageRepository = Repository.MessageRepository;
    import IScope = angular.IScope;
    import Message = Model.Message;
    import ChatRoom = Model.ChatRoom;
    import NotificationMediator = UI.Mediators.NotificationMediator;

    export class ChatRoomWindow implements IDirective {
        restrict = 'E';
        scope = true;
        template = `
            <md-content flex>
                <loading-overlay visible="chatRoomCtrl.loading">
                    <md-list-item ng-repeat="message in chatRoomCtrl.messages">
                        <img ng-src="{{ 'https://www.gravatar.com/avatar/' + message.user.gravatarHash + '?d=identicon' }}" class="md-avatar" alt="{{ message.user.name }}" />
                        <div class="md-list-item-text">
                            <p>{{ message.text }}</p>
                        </div>
                    </md-list-item>
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
        static $inject = ['$scope', 'MessageRepository', 'NotificationMediator'];

        private room:ChatRoom;
        private messages:Message[] = [];
        private loading:boolean;

        constructor(private $scope:IScope,
                    private messageRepository:MessageRepository,
                    private notificationMediator:NotificationMediator) {

            this.messageRepository.addOnMessageUpdatedListener($scope, (message: Message) => {
                if (message.room.id == this.room.id) {
                    this.messages.push(message);
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
    }
}