import { Injectable } from '@angular/core';
import { UserService } from '../user/user.service';
import { CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';

// Esta classe é um serviço que evita que você acesse a página de login caso o usuário já esteja logado.
@Injectable({ providedIn: 'root'})
export class AuthGuard implements CanActivate {
   
    constructor(
        private userService: UserService,
        private router: Router) { }

    // Ele retorna um boolean. Se true, permite acessar a rota. Se false não.
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
        
        if(this.userService.isLogged()){
            this.router.navigate(['user', this.userService.getUserName()])
            return false;
        }
        return true;
        
    }

}