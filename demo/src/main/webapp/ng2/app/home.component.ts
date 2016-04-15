import {Component} from 'angular2/core';
import {Http, Headers, Response} from 'angular2/http';
import {RouteConfig, ROUTER_DIRECTIVES, Router} from 'angular2/router';

import {LoginFormComponent} from './login.component';
import {UserService} from "./user.service";
import {ProfileComponent} from './profile.component'
import {AuthenticationService} from "./authentication.service";
import {BookService} from "./book.service";
import {Book} from "./book.model";

@Component({
    selector: 'home',
    templateUrl: 'view/home.html',
    directives: [ROUTER_DIRECTIVES],
    providers: [BookService]

})
export class HomeComponent {

    public books:Book[] = Book.initEmptyBookArray();

    constructor(private _router:Router, private _user:UserService, private _auth:AuthenticationService,
                private _book:BookService) {
    }


    private ngOnInit() {
        try {
            this._auth.authenticate()
        } catch(e) {
            this._router.navigate(["Login"]);
        }

        this._book.getAllBooks().subscribe(
            data=> {
                this.books = Book.arrayFromJson(data.text());
            },
            err => {
                console.log(err);
            }
        );

    }

}