import {User} from "./user.model";
export class Book {

    constructor(public id:number, public added:User, public name:string, public description:string, public author:string,
                public dateAdded:number, public isnb:string) {
    }

    public static fromJson(jsonObj:string):Book {

        var data:JSON = JSON.parse(jsonObj);

        return new Book(data['id'],User.fromJson(JSON.stringify(data['added'])),data['name'],data['description'],
            data['author'], data['dateAdded'], data['isnb']);

    }

    public static arrayFromJson(jsonObj:string):Book[] {

        var data:JSON = JSON.parse(jsonObj);
        var books:Book[] = new Array<Book>();

        for(var key in data) {
            var b = data[key];
            books.push(new Book(b['id'],User.fromJson(JSON.stringify(b['added'])),b['name'],b['description'],
                b['author'], b['dateAdded'], b['isnb']));
        }

        return books;

    }

    public static initEmptyBook():Book {
        return new Book(null,User.initEmptyUser(),null,null,null,null,null);
    }

    public static initEmptyBookArray():Book[] {
        var books:Book[] = new Array<Book>();
        books.push(this.initEmptyBook());
        return books;
    }

    public toJson():string {
        return `{"id":`+this.id+`,"name":"`+this.name+`","description":"`+this.description+`"
        ,"author":"`+this.author+`","isnb":"`+this.isnb+`"}`;
    }
}