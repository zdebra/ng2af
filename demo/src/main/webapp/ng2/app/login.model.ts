export class Credentials {
    public username: string;
    public password: string;

    constructor(username: string, password: string) {
        this.username = username;
        this.password = password;
    }

    public getJson():string {

        return '{"username":"'+this.username+'", "password":"'+this.password+'"}';

    }

}