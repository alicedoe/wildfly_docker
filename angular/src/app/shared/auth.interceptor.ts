import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {take, switchMap} from 'rxjs/operators';

import * as fromStore from '../auth/store/reducers/auth.reducers';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: Store<fromStore.AuthState>) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return this.store.select(fromStore.getAuthenticated)
      .pipe(take(1),
        switchMap(() => {
            if (localStorage.getItem('token')) {
                const copiedReq = req.clone({
                    headers: new HttpHeaders({'Token': localStorage.getItem('token')
                  , 'Content-Type':'application/json; charset=utf-8'})
                  });
                return next.handle(copiedReq);
            }
            return next.handle(req);          
        }));
  }
}