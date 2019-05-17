import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

import { UserService } from '../user/user.service';

const API_URL = 'http://localhost:3000';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private userService: UserService) { }

  authenticate(userName:string, password:string) {
    // this.http.post(API_URL + '/user/login', {username: userName, password: password});
    // Lembre que quando você está passando num parâmetro uma propriedade que tem o mesmo nome de uma variável você pode passar da seguinte forma:
    return this.http
      // o terceiro argumento de post ({ observe : 'response'}) é passado como forma de você ter acesso
      // a toda a resposta gerada. Sem isso você não tem acesso a nada, incluindo o header de onde você
      // precisa tirar o token.
      .post(API_URL + '/user/login', {userName, password}, { observe : 'response'})
      // Este pipe e o operador tap garantem que um código será executado antes do método retornar
      // a resposta da requisição para a classe que chamou o serviço.
      .pipe(tap(
          res => {
            const authToken = res.headers.get('x-access-token');
            this.userService.setToken(authToken);
          }
      ));
  }
}
