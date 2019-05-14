import { Directive, ElementRef, HostListener, Renderer, Input } from '@angular/core';
import { renderComponent } from '@angular/core/src/render3';

// ******************** ATENÇÃO: não esqueça de importar o módulo da diretiva onde ela for utilizada
@Directive({
    selector: '[appDarkenOnHover]'  // você declara o selector entre [] quando ele será um atributo
                                    // de uma tag. (<div appDarkenOnHover></div>)
})
export class DarkenOnHoverDirective {

    @Input() brightness='70%';
    
    // A injeção do ElementRef injeta o elemento do dom no qual esta diretiva foi declarada.
    constructor(private el:ElementRef, private render:Renderer) {
        
    }

    @HostListener('mouseover') // captura o evento javascript passado como argumento.
    darkenOn() {
        // this.el.nativeElement // isto lhe permitiria manipular o dom nativamente, mas isso não é recomendado.
        this.render.setElementStyle(this.el.nativeElement, 'filter', `brightness(${this.brightness})`);
    }

    @HostListener('mouseleave') // captura o evento javascript passado como argumento.
    darkenOff() {
        this.render.setElementStyle(this.el.nativeElement, 'filter', `brightness(100%)`);
    }
}