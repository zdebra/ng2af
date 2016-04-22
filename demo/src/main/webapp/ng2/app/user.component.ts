import {Component} from "angular2/core";
import {Router} from "angular2/router";
import {UserService} from "./user.service";
import {ControlGroup} from "angular2/common";
import {NgForm} from "angular2/common";
import {User} from "./user.model";

@Component({
    selector: 'user',
    templateUrl: 'view/user.html',
    providers: [UserService]
})
export class UserComponent {

    public model:Object = {
        id: -1,
        username: "",
        password: "",
        email: "",
        role: "visitor"
    };

    public user:User = User.initEmptyUser();
    public admin:boolean = false;

    constructor(private _user:UserService, private _router:Router) {
    }

    private ngOnInit():void {
        try {
            this._user.getByToken().subscribe(
                data => {
                    this.user = User.fromJson(data.text());
                    this.admin = this.user.role == "ADMIN";
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

    public onSubmit(form:NgForm) {

        for(var key in form.form.controls) {
            form.form.controls[key].markAsDirty();
        }
        if(form.valid) {
            this._user.createUser(this.model).subscribe(
                data => {
                    this._router.navigate(["Home"]);
                },
                err => console.log(err)
            );
        }

    }

}