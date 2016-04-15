import {Component} from 'angular2/core';
import {Http, Headers, Response} from 'angular2/http';
import {RouteConfig, ROUTER_DIRECTIVES, Router} from 'angular2/router';

import {LoginFormComponent} from './login.component';
import {HomeComponent} from './home.component';
import {ProfileComponent} from './profile.component'
import {AuthenticationService} from "./authentication.service";
import {BookComponent} from "./book.component";
import {PlaceComponent} from "./place.component";
import {UserComponent} from "./user.component";

@Component({
    selector: 'my-app',
    template: `
        <div class="pure-g">
            <div class="pure-u-1">
                <div class="pure-menu pure-menu-horizontal">
                    <a [routerLink]="['Home']" class="pure-menu-heading pure-menu-link">BOOK REPOSITORY</a>
                    <ul class="pure-menu-list">
                        <li class="pure-menu-item"><a [routerLink]="['Profile']" class="pure-menu-link">My profile</a></li>
                        <li class="pure-menu-item"><a [routerLink]="['Book']" class="pure-menu-link">Add book</a></li>
                        <li class="pure-menu-item"><a [routerLink]="['Place']" class="pure-menu-link">Add place</a></li>
                        <li class="pure-menu-item"><a [routerLink]="['User']" class="pure-menu-link">Add user</a></li>
                        <li class="pure-menu-item"><a (click)="clickLogout()" class="pure-menu-link">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <router-outlet></router-outlet>`,
    directives: [LoginFormComponent, ROUTER_DIRECTIVES],
    providers: [AuthenticationService]
})
@RouteConfig([
    {path: '/home', name: 'Home', component: HomeComponent},
    {path: '/', redirectTo: ['Home']},
    {path: '/login', name: 'Login', component: LoginFormComponent},
    {path: '/profile', name: 'Profile', component: ProfileComponent},
    {path: '/book', name: 'Book', component: BookComponent},
    {path: '/place', name: 'Place', component: PlaceComponent},
    {path: '/user', name: 'User', component: UserComponent}

])
export class AppComponent {

    public static SERVER_URL:string = "http://localhost:8080/book_repository";
    public token:string;

    constructor(private _router:Router, private _http:Http, private _auth:AuthenticationService) {

        try {
            this._auth.authenticate();
            this._router.navigate(["Home"]);
        } catch(e) {
            this._router.navigate(["Login"]);
        }

    }

    public clickLogout() {

        try {
            this._auth.logout().subscribe(
                data => {
                    localStorage.clear();
                    this._router.navigate(['Login']);
                },
                err => {
                    localStorage.clear();
                    this._router.navigate(["Home"]);
                }
            );
        } catch (e) {

            console.log(e);
            localStorage.clear();
            this._router.navigate(['Login']);

        }

    }

}