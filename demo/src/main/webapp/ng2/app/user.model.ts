export class User {

    public id:number;
    public username:string;
    public email:string;
    public role:string;
    public lastLoggedIn:number;
    public created:number;
    public booksAdded:Array<number>;


    constructor(id:number, username:string, email:string, role:string, lastLoggedIn:number, created:number, booksAdded:Array<number>) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.lastLoggedIn = lastLoggedIn;
        this.created = created;
        this.booksAdded = booksAdded;
    }

    public static fromJson(jsonObj:string):User {

        var data:JSON = JSON.parse(jsonObj);

        return new User(data['id'], data['username'], data['email'], data['role'], data['lastLoggedIn'],
            data['created'], data['booksAdded']);

    }

    public static initEmptyUser():User {
        return new User(null,null,null,null,null,null,null);
    }

    public toJson() {

        return  `"{"id":`+this.id+`,"username":"`+this.username+`","email":"`+this.email+`","role":"`+this.role+`",
            "lastLoggedIn":`+this.lastLoggedIn+`,"created":`+this.created+`,"booksAdded":`+this.booksAdded+`}"`;

    }



}
