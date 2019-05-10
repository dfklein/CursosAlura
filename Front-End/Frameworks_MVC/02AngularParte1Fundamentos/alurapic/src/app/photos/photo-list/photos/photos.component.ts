import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Photo } from '../../photo/photo';

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnChanges { // o OnChanges é uma interface utilizada para
                                                    // implementar um tipo de listener para quando
                                                    // alguma propriedade da sua classe é alterada.
                                                    // Ela é utilizada aqui porque a resposta da 
                                                    // chamada do serviço de fotos é obtida depois da
                                                    // renderização da tela. Você precisa então de algo
                                                    // que faça a tela ser atualizada quando a resposta
                                                    // com as fotos a serem carregadas chegar.

  @Input() photos:Photo[] = [];

  rows: any[] = [];

  constructor() { }

  ngOnChanges(changes: SimpleChanges) { // atenção para não tipar como SimpleChange, no singular
    // testa se as alterações informadas são na propriedade photos deste componente.
    // O que ele faz aqui é atachar uma propriedade com o nome da propriedade alterada ao objeto 
    // changes
    if(changes.photos) { 
      this.groupColumns(this.photos);
    }
  }

  groupColumns(photos: Photo[]) {
    const newRows = [];

    for(let index = 0; index < photos.length; index+3) {
      newRows.push(photos.splice(index, index+3));
    }

    return newRows;

  }

}
