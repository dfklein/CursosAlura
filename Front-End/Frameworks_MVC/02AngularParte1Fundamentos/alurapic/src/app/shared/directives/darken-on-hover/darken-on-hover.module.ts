import { NgModule } from '@angular/core';
import { DarkenOnHoverDirective } from './darken-on-hover.directive';

// ******************** ATENÇÃO: não esqueça de importar o módulo da diretiva onde ela for utilizada
@NgModule({
    declarations: [
        DarkenOnHoverDirective
    ],
    exports: [
        DarkenOnHoverDirective
    ]
})
export class DarkenOnHoverModule {

}