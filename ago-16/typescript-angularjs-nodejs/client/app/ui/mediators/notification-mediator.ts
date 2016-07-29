namespace UI.Mediators {
    import IToastService = angular.material.IToastService;
    export class NotificationMediator {
        static $inject = ["$mdToast"];

        constructor(private mdToast:IToastService) {
        }

        public info(text:string) {
            this.mdToast.showSimple(text);
        }
    }
}