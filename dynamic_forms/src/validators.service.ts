import {Injectable} from "angular2/core";
import {Control} from "angular2/common";

@Injectable()
export class CustomValidators {

    public regExp(pattern:string):Function {

        return (c:Control):{[key: string]: any} => {
            if (!c.value.match(pattern)) {
                return {regExp: true};
            }
        }

    }

    public numberMax(value:Number):Function {

        return (c:Control):{[key: string]: any} => {
            if (c.value > value) {
                return {numberMax:true}
            }
        }

    }

    public numberMin(value:Number):Function {
        return (c:Control):{[key: string]: any} => {
            if (c.value < value) {
                return {numberMin:true}
            }
        }


    }

    public dateSince(value:string):Function {

        return (c:Control) => {
            var date:Date = new Date(value);
            var inputDate:Date = new Date(c.value);
            if (inputDate < date) {
                return {dateSince:true}
            }
        }

    }

    public dateUntil(value:string):Function {

        return (c:Control) => {
            var date:Date = new Date(value);
            var inputDate:Date = new Date(c.value);
            if (inputDate > date) {
                return {dateUntil:true}
            }
        }

    }

    public email():Function {
        var pattern = "[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$";
        return (c:Control):{[key: string]: any} => {
            if (!c.value.match(pattern)) {
                return {regExp: true};
            }
        }
    }
}
