import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {take, switchMap} from 'rxjs/operators';

import * as fromApp from './app.reducers';
import * as fromAuth from '../auth/store/auth.reducers';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: Store<fromApp.AppState>) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return this.store.select('auth')
      .pipe(take(1),
        switchMap((authState: fromAuth.State) => {
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