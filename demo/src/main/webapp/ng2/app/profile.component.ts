import {Component} from 'angular2/core';
import {Router} from "angular2/router";
import {AuthenticationService} from "./authentication.service";
import {UserService} from "./user.service";
import {User} from "./user.model";

@Component({
    selector: 'profile',
    templateUrl: 'view/profile.html'
})
export class ProfileComponent {

    public user:User = User.initEmptyUser();

    constructor(private _router:Router, private _auth:AuthenticationService, private _user:UserService) {
    }

    private ngOnInit() {

        try {
            this._auth.authenticate();
            this._user.getByToken().subscribe(
                data => {
                    this.user = User.fromJson(data.text());
                },
                err => {
                    throw new Error(err.statusText);
                }
            );
        } catch(e) {

            localStorage.clear();
            this._router.navigate(["Login"]);

        }

    }


}
