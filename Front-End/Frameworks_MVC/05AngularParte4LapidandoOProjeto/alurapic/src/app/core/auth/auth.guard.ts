import { Injectable } from '@angular/core';
import { UserService } from '../user/user.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root'})
export class AuthGuard implements CanActivate {

    constructor(
        private userService: UserService,
        private router: Router) {}

    canActivate(
        route: ActivatedRouteSnapshot, 
        state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
            
            if(!this.userService.isLogged()){
                this.router.navigate(
                    [''],
                    // Quando você passa um objeto com um valor de queryParams (que é mais um objeto)
                    // ele adiciona esses parâmetros ao redirecionamento. Aqui você adiciona a rota
                    // que o usuário tentou entrar deslogado para, caso seja uma rota protegida e ele
                    // ser forçado a fazer o login primeiro, que após o login ele seja direcionado
                    // para a rota que estava tentando acessar inicialmente. Veja no sign-in.component
                    // como você fez para acessar esses parâmetros.
                    {
                        queryParams: {
                            // "fromUrl" é um nome que você deu, poderia ser qualquer outro. Será 
                            // concatenado na sua url (http://localhost:4200/recurso?fromUrl=algumacoisa)
                            fromUrl:state.url
                        }
                    }
                );
                return false;
            }
            return true;
    }
}