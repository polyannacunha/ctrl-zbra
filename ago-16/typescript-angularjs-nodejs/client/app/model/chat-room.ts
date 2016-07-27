namespace Model {
    export class ChatRoom {
        private _id:string;
        private _name:string;
        private _users:Array<User>;

        get id():string {
            return this._id;
        }

        set id(value:string) {
            this._id = value;
        }

        get name():string {
            return this._name;
        }

        set name(value:string) {
            this._name = value;
        }

        get users():Array<User> {
            return this._users;
        }

        set users(value:Array<User>) {
            this._users = value;
        }
    }
}