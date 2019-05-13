import { Pipe, PipeTransform } from '@angular/core';
import { Photo } from '../photo/photo';

// ATENÇÃO: Não se esqueça que um pipe também precisa ser declarado em um modulo.
@Pipe({
    name:'filterByDescription'
})
export class FilterByDescription implements PipeTransform {
    
    // O primeiro argumento é o objeto no qual você quer aplicar uma transformação (no nosso caso a única aplicação é a propriedade filter de PhotoListComponent, conforme passado pelo template)
    // Já o segundo argumento contém parâmetros que você vai passar.
    // A declaração padrão dele é "...args: any[]" mas foi alterado aqui porque nesta implementação será recebido um único argumento.
    // (Description query é o que foi chamado de filter em PhotoListCoponent)
    transform(photos: Photo[], descriptionQuery:string) {
        
        descriptionQuery = descriptionQuery
            .trim()
            .toLowerCase();

        

        // Se a propriedade descriptionQuery existir, ou seja, se foi preenchida você filtra. Se não não.
        
        //if (descriptionQuery && descriptionQuery != '') {
            
            return photos.filter(photo => 
                photo.description.toLowerCase().includes(descriptionQuery)
            );

        //} else {
        //    console.log('else');
        //    return photos;
        //}
    }

}