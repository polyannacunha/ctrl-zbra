namespace UI.Pages.Home {
    import IScope = angular.IScope;
    import ChatRoom = Model.ChatRoom;

    export  class HomeController {
        static $inject = ['$scope'];

        private rooms:Array<ChatRoom>;
        private selectedRoom: ChatRoom;

        constructor(private $scope: IScope) {
        }
    }
}