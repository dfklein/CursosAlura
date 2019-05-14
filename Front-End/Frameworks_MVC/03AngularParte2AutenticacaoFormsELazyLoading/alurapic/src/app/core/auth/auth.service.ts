import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const API_URL = 'http://localhost:3000';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  authenticate(userName:string, password:string) {
    // this.http.post(API_URL + '/user/login', {username: userName, password: password});
    // Lembre que quando você está passando num parâmetro uma propriedade que tem o mesmo nome de uma variável você pode passar da seguinte forma:
    return this.http.post(API_URL + '/user/login', {userName, password});
  }
}
