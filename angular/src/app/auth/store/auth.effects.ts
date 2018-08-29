import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Router} from '@angular/router';
import {map, switchMap, mergeMap, tap, catchError} from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import * as AuthActions from './auth.actions';
import { of, EMPTY } from 'rxjs';

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
              type: AuthActions.ERROR,
              payload: err.error['message']
            });
          }) ,
        )})
  );

  @Effect()
  authTokenSignin = this.actions$
    .ofType(AuthActions.TRY_TOKEN_SIGNIN)
    .pipe(
      switchMap((authData: { token: string }) => {
        if (localStorage.getItem('token')) {
          let token  =  localStorage.getItem('token');
          return this.httpClient.post('http://172.17.0.3:8080/application/v1/user/login/token', { token : token }).pipe( 
              map(res => res) ,      
              mergeMap((authData) => { this.router.navigate(['/']);
                return [{ type: AuthActions.SIGNIN },{ type: AuthActions.SET_USER, payload: authData } ]; }) ,
              catchError((err: HttpErrorResponse) => { 
                return of( { type: AuthActions.LOGOUT });
             }) ,
          ) //pipe
        } //if
        else { return EMPTY; }        
      }) //switchMap
  );

  @Effect({dispatch: false})
  authLogout = this.actions$
    .ofType(AuthActions.LOGOUT)
    .pipe(tap(() => {
      this.router.navigate(['/']);
    }));

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private router: Router) {
  }
}
