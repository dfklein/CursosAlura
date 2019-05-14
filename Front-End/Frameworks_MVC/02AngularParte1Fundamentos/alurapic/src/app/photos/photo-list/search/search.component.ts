import { Component, OnDestroy, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit, OnDestroy {
    
    // O objeto Subject é um objeto observable que você se subscreve para escutar toda vez que seu valor é alterado.
    // Você o utilizou para criar um filtro para que o keyup fosse aplicado à variável filter apenas a cada
    // 0.300 segundos, deixando a aplicação mais leve durante a digitação do campo de busca.
    debounce: Subject<string> = new Subject<string>();

    // Output properties são usadas em event binding.
    @Output() onTyping = new EventEmitter<string>(); // cuidado para não importar o EventEmitter errado!

    @Input() value: string = '';

    ngOnInit(): void {
      // Isto aqui é um truque para que o keyup do filtro seja aplicado apenas a cada 0.300 segundos, melhorando
      // a performance da aplicação ao digitar a busca. Você passa um novo valor para este listener usando o
      // método next (ver photo-list.component.html)
      this.debounce
        .pipe(debounceTime(300))
        .subscribe(filter => this.onTyping.emit(filter));   // o filter que é emitido aqui é o $event que você vê em 
                                                            // photo-list.component.html. Lá você
                                                            // o atribui à filter de photo-list.component.ts
      
    } 

      // Coisas que você executa quando você sai desta view
    ngOnDestroy(): void {
        this.debounce.unsubscribe(); // para de alocar memória para o objeto Subject
    }

}