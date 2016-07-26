namespace Services {

    import User = Model.User;
    import IHttpService = angular.IHttpService;
    import IQService = angular.IQService;
    import IPromise = angular.IPromise;

    export class SessionService {
        static $inject = ['$q', '$http', 'UserRepository'];

        constructor(private $q:IQService,
                    private $http:IHttpService) {
        }

        public authenticate(): IPromise<User[]> {
            // eventual business specific logic here
            return null;
        }
    }
}