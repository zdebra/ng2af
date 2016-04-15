import {Http, Headers, Response} from 'angular2/http';

import {AppComponent} from './app.component';
import {Observable} from "rxjs/Observable";
import {Injectable} from "angular2/core";
import {Credentials} from "./login.model.ts";
import {Router} from "angular2/router";
import {UserService} from "./user.service";
import {Inject} from "angular2/core";

@Injectable()
export class AuthenticationService {

    constructor(private _http:Http, private _router:Router, @Inject(UserService) private _user:UserService) {
    }

    public login(creds:Credentials):Observable<Response> {

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');

        return this._http.post(AppComponent.SERVER_URL+'/rest/auth/login', creds.getJson(), {
            headers: headers
        });

    }

    public logout():Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.post(AppComponent.SERVER_URL+'/rest/auth/logout', null, {
            headers: headers
        })

    }

    public authenticate() {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            localStorage.clear();
            throw new Error("Invalid token");
        }

    }


}


