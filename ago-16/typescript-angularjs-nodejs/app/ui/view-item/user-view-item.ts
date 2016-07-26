namespace ViewItem {
    import User = Model.User;

    export class UserViewItem {
        private _user:User;

        get id():string {
            return this._user.id;
        }

        get name():string {
            return this._user.name;
        }

        get email():string {
            return this._user.email;
        }

        get gravatarHash():string {
            return md5(this._user.email.toLowerCase())
        }
    }
}