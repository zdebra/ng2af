import {Injectable} from "angular2/core";
import {Http} from "angular2/http";
import {Json} from "angular2/src/facade/lang";
import {Observable} from "rxjs/Observable";
import {Response} from "angular2/http";
import {Headers} from "angular2/http";
import {AppComponent} from "./app.component";

@Injectable()
export class FormService {

    constructor(private _http:Http) {
    }

    public getFormStructure(formName:string):Observable<Response> {
        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);
        return this._http.get(AppComponent.SERVER_URL + "/rest/form/"+formName,{
            headers: headers
        });

    }

    public getOptionsFromEndpoint(endpoint:string):Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);
        return this._http.get(AppComponent.SERVER_URL + endpoint,{
            headers: headers
        });

    }

}