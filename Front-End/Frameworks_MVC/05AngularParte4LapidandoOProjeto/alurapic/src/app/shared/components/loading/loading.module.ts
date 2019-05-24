import { NgModule } from '@angular/core';
import { LoadingComponent } from './loading.component';
import { CommonModule } from '@angular/common';
import { LoadingInterceptor } from './loading.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
    declarations: [
        LoadingComponent
    ],
    exports: [
        LoadingComponent
    ],
    imports: [
        CommonModule
    ],
    // Lembrando que o providers vai garantir que quem importar este módulo
    // terá o interceptor automaticamente configurado.
    providers: [{
        provide: HTTP_INTERCEPTORS,
        useClass: LoadingInterceptor,
        multi: true // garante que o interceptador será aplicado mesmo se houverem outros.
    }]
})
export class LoadingModule {

}