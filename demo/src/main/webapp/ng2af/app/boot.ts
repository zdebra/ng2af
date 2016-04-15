import {bootstrap} from 'angular2/platform/browser';
import {provide} from 'angular2/core';
import {ROUTER_PROVIDERS} from 'angular2/router';
import {HTTP_PROVIDERS} from 'angular2/http';

import {AppComponent} from './app.component';
import {UserService} from "./user.service";
import {AuthenticationService} from "./authentication.service";
import {FORM_PROVIDERS} from "angular2/common";

//noinspection JSValidateTypes
bootstrap(AppComponent, [HTTP_PROVIDERS, ROUTER_PROVIDERS, FORM_PROVIDERS, UserService, AuthenticationService]);