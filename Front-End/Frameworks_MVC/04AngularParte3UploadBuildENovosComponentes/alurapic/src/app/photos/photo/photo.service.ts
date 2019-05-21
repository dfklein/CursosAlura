import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Photo } from "./photo";

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

    findById(id:string) {
        return this.http.get<Photo>(API + '/photos/' + id);
    }
}
