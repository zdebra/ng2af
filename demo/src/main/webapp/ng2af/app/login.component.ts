import {Component, Injector} from 'angular2/core';
import {Http, Headers, Response} from 'angular2/http';
import {NgForm, ControlGroup, FormBuilder, Validators, Control, FORM_DIRECTIVES} from 'angular2/common';
import {Injectable} from 'angular2/core';
import {Credentials} from './login.model';
import {Router} from 'angular2/router';

import {AuthenticationService} from './authentication.service'

@Component({
    selector: 'login-form',
    templateUrl: 'view/login.html',
    providers: [AuthenticationService]
})
export class LoginFormComponent {

    public model = new Credentials("","");
    public message:string;

    constructor(private _auth:AuthenticationService, private _router:Router) {
        this.message = "";
    }


    public login() {
        this.authenticate(this.model);

    }

    private authenticate(creds:Credentials) {
        this._auth
            .login(creds)
            .subscribe(
                data => {
                    try {
                        this.saveToken(data);
                        this.message = "";
                        this._router.navigate(['Home']);
                    } catch (e) {
                        this.message = e.message;
                        console.log(e);
                    }
                },
                err => {
                    console.log(err);
                    this.message = "Invalid username or password!";
                }
            );
    }

    private saveToken(response: Response){
        let respBody:JSON = response.json();
        if(respBody['token'] !== undefined && respBody['token'] !== null) {
            localStorage.setItem("token",respBody['token']);
        } else {
            throw new Error("Token wasn't retrieved");
        }

    }

}