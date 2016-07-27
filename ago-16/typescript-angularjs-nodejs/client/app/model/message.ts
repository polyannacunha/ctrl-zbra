namespace Model {
    export class Message {
        private _id:string;
        private _text:string;
        private _user:User;
        private _date:Date;
        private _room:ChatRoom;

        get id():string {
            return this._id;
        }

        set id(value:string) {
            this._id = value;
        }

        get text():string {
            return this._text;
        }

        set text(value:string) {
            this._text = value;
        }

        get room():Model.ChatRoom {
            return this._room;
        }

        set room(value:Model.ChatRoom) {
            this._room = value;
        }

        get date():Date {
            return this._date;
        }

        set date(value:Date) {
            this._date = value;
        }

        get user():Model.User {
            return this._user;
        }

        set user(value:Model.User) {
            this._user = value;
        }
    }
}