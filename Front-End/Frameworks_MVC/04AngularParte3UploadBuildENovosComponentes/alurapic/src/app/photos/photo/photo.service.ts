import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Photo } from "./photo";
import { PhotoComment } from './photo-comment';
import { map, catchError } from 'rxjs/operators';
import { of, throwError } from 'rxjs';

const API = 'http://localhost:3000';

@Injectable({ providedIn: 'root' })
export class PhotoService {

    constructor(private http: HttpClient) {}

    listFromUser(userName: string) {
        return this.http
            .get<Photo[]>(API + '/' + userName + '/photos');       
    }

    listFromUserPaginated(userName: string, page: number) {
        const params = new HttpParams()
            .append('page', page.toString());

        return this.http
            .get<Photo[]>(API + '/' + userName + '/photos', { params });       
    }
    
    upload(description: string, allowComments: boolean, file: File) {
        const formData = new FormData();    // é um formulário padrão do navegador.
                                            // Você utiliza ele porque no upload você não envia um JSON.

        // A string do primeiro argumento é definida no back-end
        formData.append('description', description);
        formData.append('allowComments', allowComments ? 'true' : 'false');     // o formData não recebe tipos 
                                                                                // booleanos. Deve converter para
                                                                                // tipo string.
        formData.append('imageFile', file);

        return this.http.post(API + '/photos/upload', formData);
    }

    findById(id:number) {
        return this.http.get<Photo>(API + '/photos/' + id);
    }

    getComments(id:number) {
        return this.http.get<PhotoComment[]>(API + '/photos/' + id + '/comments');
    }

    addComments(id:number, commentText:string) {
        return this.http.post(
            API + '/photos/' + id + '/comments',
            { commentText: commentText }
        );
    }

    removePhoto(photoId:number) {
        return this.http.delete(
            API + '/photos/' + photoId
        );
    }

    like(photoId:number) {
        // Relembrando: ao passar o { observe:'response'} como terceiro parâmetro, você está
        // indicando que deseja ter acesso ao cabeçalho da resposta da requisição. Isto é feito
        // aqui para se obter o status da resposta.
        return this.http
            .post(API + '/photos/' + photoId + '/like', {}, { observe:'response'} )
            // Este mapeamento faz com que a resposta da requisição, seja ela qual for,
            // retorne true. ATENÇÃO: ao fazer isso você já está definindo uma tipagem
            // no retorno (Observable<boolean>)
            .pipe(map(resp => true))
            // O próximo pipe usa um operador do rxjs para capturar uma exceção lançada.
            // A lógica aqui é que se o status da resposta com erro for 304 (é uma regra
            // para caso o usuário já tenha curtido a foto antes) o serviço
            // retornará false ao invés de apenas lançar o erro. 
            .pipe(catchError(err => {
                return err.status == '304' ? of(false) : throwError(err);
            }));
    }
}
