import { Component } from '@angular/core';
import { UserService } from '../user/user.service';
import { Observable } from 'rxjs';
import { User } from '../user/user';
import { Router } from '@angular/router';

@Component({
    templateUrl:'./header.component.html',
    selector:'ap-header'

})
export class HeaderComponent {

    // É uma boa prática você usar um $ no nome de uma variável que guarda um observable.
    user$: Observable<User>;
    
    constructor(
            private userService: UserService,
            private router:Router) {
       this.user$ = userService.getUser();
       
    }

    logout() {
        this.userService.logout();
        this.router.navigate(['']);
    }
}