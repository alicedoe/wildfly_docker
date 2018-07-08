import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Router} from '@angular/router';
import {map, switchMap, mergeMap, mapTo} from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import * as AuthActions from './auth.actions';
import { stringify } from 'querystring';

@Injectable()
export class AuthEffects {
  @Effect()
  authSignin = this.actions$
    .ofType(AuthActions.TRY_SIGNIN)
    .pipe(map((action: AuthActions.TrySignin) => {
      return action.payload;
    })
    , switchMap((authData: { username: string, password: string }) => {
      return this.httpClient.post('http://172.17.0.3:8080/application/v1/user/login/', {
        authData: authData
      });
    })
    , mergeMap((authData) => {
      this.router.navigate(['/']);
      return [
        {
          type: AuthActions.SIGNIN
        },
        {
          type: AuthActions.SET_TOKEN,
          payload: "tttttttttt"
        }
      ];
    }));

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private router: Router) {
  }
}
