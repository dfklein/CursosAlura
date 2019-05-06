import { Component, OnInit } from '@angular/core';
import { PhotoService } from '../photo/photo.service';
import { Photo } from '../photo/photo';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements OnInit { // o OnInit é uma interface que te obriga a implementar o ngOnInit()

  title = 'alurapic';

  // Note que no TypeScript para tipar uma variável a sintaxe é {variável:tipo}
  photos:Photo[] = [];
  
  // Observe a implementação da camada de serviço feita em photo.service.ts
  constructor(private photoService: PhotoService) {

  }
  
  // ngOnInit() é um método que por padrão é executado automaticamente após a construção da sua classe
  // Por convenção é mais interessante você utilizar o construtor apenas para a injeção de dependências e
  // o ngOnInit() para execução de rotinas da construção do objeto.
  ngOnInit() : void {
      this.photoService
          .listFromUserName('flavio')
          .subscribe(photos => { 
              console.log(photos[0].description);
              this.photos = photos; 
          });

  }

}
