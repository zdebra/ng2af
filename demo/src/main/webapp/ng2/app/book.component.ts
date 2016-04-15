import {Component} from "angular2/core";
import {Book} from "./book.model";
import {BookService} from "./book.service";
import {Router} from "angular2/router";
import {Json} from "angular2/src/facade/lang";
import {NgForm} from "angular2/common";
import {User} from "./user.model";
import {UserService} from "./user.service";
import {PlaceService} from "./place.service";
import {MinNumberValidator} from "./minNumber.validator";
import {MaxNumberValidator} from "./minNumber.validator";

@Component({
    selector: 'book',
    templateUrl: 'view/book.html',
    providers: [BookService, UserService, PlaceService],
    directives: [MinNumberValidator, MaxNumberValidator]
})
export class BookComponent {

    public user:User = User.initEmptyUser();
    public authors:Array<any> = [];
    public libraries:Array<any> = [];
    public stores:Array<any> = [];

    public model:any = {
        id: -1,
        name: "",
        description: "",
        author: null,
        isnb: "",
        status: 'active',
        stores: [],
        libraries: [],
        price: null,
        visibility: ["visitor", "editor", "manager"],
        dateAdded: this.format(new Date())
    };

    constructor(private _place:PlaceService, private _book:BookService, private _router:Router, private _user:UserService) {
    }

    public visibilityClick(role:string):void {
        var index = this.model.visibility.indexOf(role);
        if(index==-1) {
            this.model.visibility.push(role);
        } else {
            this.model.visibility.splice(index,1);
        }
    }

    private format(date:Date):string {
        var day = ('0' + date.getDate()).slice(-2);
        var month = ('0' + (date.getMonth() + 1)).slice(-2);
        var year = date.getFullYear();

        return year + '-' + month + '-' + day;
    }

    public storesChange(event:any):void {

        this.model.stores = [];
        event.preventDefault();
        for(var option of event.srcElement.children) {
            if(option.selected===true) {
                this.model.stores.push(option.value);
            }
        }

    }

    public librariesChange(event:any):void {

        this.model.libraries = [];
        event.preventDefault();
        for(var option of event.srcElement.children) {
            if(option.selected===true) {
                this.model.libraries.push(option.value);
            }
        }

    }

    private ngOnInit():void {
        try {
            this._user.getByToken().subscribe(
                data => {
                    this.user = User.fromJson(data.text());
                },
                err => {
                    throw new Error(err.statusText);
                }
            );
            this._book.getAllAuthors().subscribe(
                data => {
                    this.authors = data.json();
                    this.model.author = this.authors[0].id;
                },
                err => {
                    throw new Error(err.statusText);
                }
            );
            this._place.getAllLibraries().subscribe(
                data => {
                    this.libraries = data.json();
                },
                err => {
                    throw new Error(err.statusText);
                }
            );
            this._place.getAllStores().subscribe(
                data => {
                    this.stores = data.json();
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
            var modelCopy:any = {}
            for(key in this.model) {
                modelCopy[key] = this.model[key];
            }
            modelCopy.status = modelCopy.status == 'active'

            this._book.createBook(modelCopy).subscribe(
                data => {
                    this._router.navigate(["Home"]);
                },
                err => console.log(err)
            );
        }

    }

}
