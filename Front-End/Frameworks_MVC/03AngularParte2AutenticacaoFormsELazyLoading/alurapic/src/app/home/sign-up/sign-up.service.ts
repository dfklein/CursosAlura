import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NewUser } from './new-user';


// Esta classe realiza uma chamada assíncrona que verifica se o userName já existe.
// Usado no validador user-not-taken.validator.service.ts, que por sua vez é usado no 
// sign-up.component.ts para verificar se você está tentnado cadastrar um
// userName que já existe.

const API_URL = "http://localhost:3000";

// Inicialmente você disponibilizou este serviço para toda a aplicação. O modularizá-la você
// alterou quem disponibilizava esta injeção por ela ser utilizada apenas em um módulo (home.module.ts)
// Para tornar a sua injeção particular do home.module você a declarou lá como um dos providers.
@Injectable( /*{
    providedIn: "root"
} */)
export class SignUpService {
    constructor(private http: HttpClient) {}

    checkUserNameTaken(userName: string) {

        return this.http.get(API_URL + '/user/exists/' + userName);
    }

    signup(newUser: NewUser) {
        return this.http.post(API_URL + '/user/signup', newUser);
    }
}