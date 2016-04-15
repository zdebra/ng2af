import {Http} from "angular2/http";
import {Injectable} from "angular2/core";
import {Observable} from "rxjs/Observable";
import {Response} from "angular2/http";
import {Headers} from "angular2/http";
import {AppComponent} from "./app.component";
import {Book} from "./book.model";

@Injectable()
export class BookService {

    constructor(private _http:Http) {
    }

    public getBook(id:number):Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.get(AppComponent.SERVER_URL + "/rest/book/"+id,{
            headers: headers
        });

    }

    public getAllBooks():Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.get(AppComponent.SERVER_URL + "/rest/book",{
            headers: headers
        });


    }

    public getAllAuthors():Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.get(AppComponent.SERVER_URL + "/rest/book/author",{
            headers: headers
        });


    }

    public createBook(book:any):Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.post(AppComponent.SERVER_URL + "/rest/book",JSON.stringify(book),{
            headers: headers
        });


    }

}