import {Component} from "angular2/core";
import {Router} from "angular2/router";
import {PlaceService} from "./place.service";
import {NgForm} from "angular2/common";
import {User} from "./user.model";
import {UserService} from "./user.service";
import {Control} from "angular2/common";

@Component({
    selector: 'place',
    templateUrl: 'view/place.html',
    providers: [PlaceService,UserService]
})
export class PlaceComponent {

    public model:Object = {
        id: -1,
        name: "",
        placeType: "library",
        street: "",
        city: "",
        state: "",
        zip: ""
    };

    public user:User = User.initEmptyUser();
    public manager:boolean = false;

    constructor(private _place:PlaceService, private _router:Router, private _user:UserService) {
    }


    private ngOnInit():void {
        try {
            this._user.getByToken().subscribe(
                data => {
                    this.user = User.fromJson(data.text());
                    this.manager = this.user.role == "ADMIN" || this.user.role == "MANAGER";
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
            this._place.createPlace(this.model).subscribe(
                data => {
                    this._router.navigate(["Home"]);
                },
                err => console.log(err)
            );
        }
    }

}