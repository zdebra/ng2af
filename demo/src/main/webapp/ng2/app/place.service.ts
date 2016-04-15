import {Injectable} from "angular2/core";
import {Http} from "angular2/http";
import {Observable} from "rxjs/Observable";
import {Response} from "angular2/http";
import {Headers} from "angular2/http";
import {AppComponent} from "./app.component";

@Injectable()
export class PlaceService {
    constructor(private _http:Http){
    }

    public createPlace(place:any):Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.post(AppComponent.SERVER_URL + "/rest/place",JSON.stringify(place),{
            headers: headers
        });


    }

    public getAllStores():Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.get(AppComponent.SERVER_URL + "/rest/place/store",{
            headers: headers
        });


    }

    public getAllLibraries():Observable<Response> {

        var token:string = localStorage.getItem("token");

        if(token === undefined || token === null || token === "") {
            throw new Error("Invalid token");
        }

        var headers:Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization',"Bearer " + token);

        return this._http.get(AppComponent.SERVER_URL + "/rest/place/library",{
            headers: headers
        });


    }
}
