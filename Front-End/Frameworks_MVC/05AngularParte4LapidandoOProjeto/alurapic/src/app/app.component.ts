import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { filter, map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    // Esse mecanismo abaixo vai recuperar as rotas dos arquivos *.routing.module.ts uma
    // variável de nome data que foi setada lá. Ela contem o título da aba da página.
    this.router.events
      // O NavigationEnd é um tipo de evento. Este evento é o que é disparado quando a rota
      // termina com sucesso.
      .pipe(filter(event => event instanceof NavigationEnd))
      .pipe(map(() => this.activatedRoute))
      // O pipe acima é meio desnecessário. Ele fez só para encurtar o acesso ao ActivatedRoute
      .pipe(map(route => {
        while(route.firstChild) route = route.firstChild;
          return route;
      }))
      .pipe(switchMap(route =>route.data))
      .subscribe(event => this.titleService.setTitle(event.title));


  } 

  
}
