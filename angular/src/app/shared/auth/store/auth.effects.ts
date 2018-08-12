import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Router} from '@angular/router';
import {map, switchMap, mergeMap, tap, catchError} from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import * as AuthActions from './auth.actions';
import { of } from 'rxjs';

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
            console.log(authData);
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
  getRole = this.actions$
    .ofType(AuthActions.GET_ROLE)
    .pipe(
      switchMap((authData: { name: string }) => {
        return this.httpClient.get('http://172.17.0.3:8080/application/v1/user/get/role/'+localStorage.getItem('token')).pipe(map(res => res),      
          map((authData) => {
            console.log(authData);
            return { type: AuthActions.SET_ROLE, payload: authData }; }) ,
          catchError((err: HttpErrorResponse) => {
            return of({
              type: AuthActions.ERROR,
              payload: err.error['message']
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

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private router: Router) {
  }
}
