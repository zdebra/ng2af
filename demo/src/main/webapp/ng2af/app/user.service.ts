import {Http, Headers, Response} from 'angular2/http';

import {AppComponent} from './app.component';
import {Observable} from "rxjs/Observable";
import {Injectable} from "angular2/core";

@Injectable()
export class UserService {

    constructor(private _http:Http) {
    }

    public getByToken():Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.get(AppComponent.SERVER_URL + "/rest/user/me",{
                headers: headers
            });

    }


    public createUser(user:any):Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.post(AppComponent.SERVER_URL + "/rest/user",JSON.stringify(user),{
            headers: headers
        });


    }
}


