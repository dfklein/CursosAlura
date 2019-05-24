import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { LoadingType } from './loading-type';
import { startWith } from 'rxjs/operators';

@Injectable({providedIn:'root'})
export class LoadingService {

    loadingSubject = new Subject<LoadingType>();

    getLoading() {
        return this.loadingSubject
            .asObservable()
            // Isso aqui faz com que a primeira emissão deste observable, quando chamado, seja STOPPED
            .pipe(startWith(LoadingType.STOPPED));
    }

    start() {
        this.loadingSubject.next(LoadingType.LOADING)
    }

    stop() {
        this.loadingSubject.next(LoadingType.STOPPED);
    }
}