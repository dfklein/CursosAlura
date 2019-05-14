import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

// LEMBRAR que um serviço não precisa pertencer a um módulo.
// ***** Este é um serviço que te permite saber qual plataforma está usando a aplicação (se é um Chrome
// ou outra coisa.)
@Injectable({providedIn:'root'})
export class PlatformDetectorService {

    // Esse é um tipo diferente de injeção: ao invés de injetar um tipo é como se você estivesse
    // injetando uma constante.
    constructor(@Inject(PLATFORM_ID) private platformId:string) { }

    isPlatformBrowser() {
        return isPlatformBrowser(this.platformId);
    }
}