import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Router} from '@angular/router';
import {map, switchMap, mergeMap, tap, catchError} from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import * as AuthActions from './auth.actions';
import { Observable, of } from 'rxjs';

@Injectable()
export class AuthEffects {
  @Effect()
  authSignin = this.actions$
    .ofType(AuthActions.TRY_SIGNIN)
    .pipe(map((action: AuthActions.TrySignin) => { return action.payload; }) ,
      switchMap((authData: { username: string, password: string }) => {
        return this.httpClient.post('http://172.17.0.3:8080/application/v1/user/login/', {
          username : authData.username,
          password : authData.password }).pipe(map(res => res),      
          mergeMap((authData) => {
            this.router.navigate(['/']);
            return [
              { type: AuthActions.SIGNIN },
              { type: AuthActions.SET_TOKEN,
                payload: authData['token'] },
              { type: AuthActions.SET_USER,
                payload: authData } ]; }) ,
          catchError((err: HttpErrorResponse) => {
            return of({
              type: AuthActions.SIGNIN_FAILED,
              payload: { err }
            });
          }) ,
        )})
  );

  @Effect({dispatch: false})
  authLogout = this.actions$
    .ofType(AuthActions.LOGOUT)
    .pipe(tap(() => {
      this.router.navigate(['/']);
    }));

  @Effect({dispatch: false})
  authSigninFailed = this.actions$
    .ofType(AuthActions.SIGNIN_FAILED)
    .pipe(tap(() => {
      console.log('nop !')
    }));

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private router: Router) {
  }
}
