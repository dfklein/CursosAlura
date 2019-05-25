import { Directive, Renderer, OnInit } from "@angular/core";
import { Input } from "@angular/core";
import { ElementRef } from "@angular/core";
import { UserService } from "../../../core/user/user.service";

@Directive({
    selector: '[showIfLogged]'
})
export class ShowIfLoggedDirective implements OnInit { 

    currentDisplay: string;
    
    constructor(
        private element: ElementRef<any>,
        private renderer: Renderer,
        private userService: UserService
    ) {}

    ngOnInit(): void {

        // O mecanismo abaixo é para fazer com que a diretiva funcione em elementos mesmo que
        // não sejam recarregados. 
        // Havia um bug no menu (hamburguer) que possuía elementos que não podiam ser visíveis
        // caso o usuário estivesse deslogado. Como ao dar logout o header não era recarregado,
        // o ítem continuava visível mesmo com o usuário deslogado.
        // Ele funciona se inscrevendo para ouvir alterações de usuário em toda a aplicação para
        // reexecutar a lógica de exibição de ítens quando isto acontece.
        // 1 - Guarda o estilo atual do elemento que usa esta diretiva em uma string.
        this.currentDisplay = getComputedStyle(this.element.nativeElement).display;
        this.userService.getUser().subscribe(user => {
            if(user) {
                // 2 - Se tem usuário logado, mantém o estilo atual do elemento.
                this.renderer.setElementStyle(this.element.nativeElement, 'display', this.currentDisplay);
            } else {
                this.currentDisplay = getComputedStyle(this.element.nativeElement).display;
                this.renderer.setElementStyle(this.element.nativeElement, 'display', 'none');
            }
        })

        // Versão anterior desta função.
        // !this.userService.isLogged() 
        //    && this.renderer.setElementStyle(this.element.nativeElement, 'display', 'none');
    }
}