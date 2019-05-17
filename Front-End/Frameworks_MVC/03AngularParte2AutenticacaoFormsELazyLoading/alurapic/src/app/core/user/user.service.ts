import { Injectable } from '@angular/core';
import { TokenService } from '../token/token.service';
import { Subject, BehaviorSubject } from 'rxjs';
import * as jwt_decode from 'jwt-decode';

import { User } from './user';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    // Quem fizer o subscribe recebe um objeto do tipo User.
    // O BehaviorSubject é um objeto do tipo Subject (ou seja: você pode se subscrever para ficar ouvindo
    // alterações de valor).
    // Ele obriga que você passe um primeiro argumento que será o valor padrão dele quando não houver valor
    // definido.
    // A diferença dele para o Subject é que ele guarda o valor da última emissão na memória até ele ser
    // consumido por alguém
    // Você usou ele para garantir que a página principal não fosse renderizada antes do nome do usuário
    // ser obtido, garantindo assim que após o login ele estaria lá mesmo se você desse refresh na página.
    private userSubject = new BehaviorSubject<User>(null);

    private userName: string;

    constructor(private tokenService: TokenService) { 
        // Se você fechar e abrir o navegador, por exemplo, o setToken não será chamado 
        // mas o token está no LocalStorage.
        // Por isso você precisa verificar a existência do token no storage do navegador e
        // reexecutar a decodificação via construtor caso ele exista.
        this.tokenService.hasToken() && this.decodeAndNotify();
        
    }

    setToken(token: string) {
        this.tokenService.setToken(token);
        this.decodeAndNotify();

    }
    
    getUser() {
        return this.userSubject.asObservable(); // retorna um observable, o que te permite fazer um subscribe
                                                // (ou seja: ouvir a resposta quando ela vier)
    }

    // Descodifica o usuário dentro do token e notifica a apliacação sobre o objeto User obtido
    private decodeAndNotify() {
        const token = this.tokenService.getToken();
        const user = jwt_decode(token) as User; // as User é um cast
        this.userName = user.name;
        this.userSubject.next(user);
    }

    logout() {
        this.tokenService.removeToken();
        this.userSubject.next(null);
    }

    isLogged() {
        return this.tokenService.hasToken();
    }

    getUserName() {
        return this.userName;
    }
}