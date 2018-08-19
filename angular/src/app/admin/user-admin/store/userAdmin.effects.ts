import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Router} from '@angular/router';
import {map, switchMap, mergeMap, tap, catchError} from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import * as UserAdminAction from './userAdmin.actions';
import { of } from 'rxjs';

@Injectable()
export class UserAdminEffects {
  
    @Effect()
    getRole = this.actions$
      .ofType(UserAdminAction.GET_ALL_USERS)
      .pipe(
        switchMap((authData: { name: string }) => {
          console.log('UserAdminEffects');
          return this.httpClient.get('http://172.17.0.3:8080/application/v1/user/getall').pipe(map(res => res),      
            map((authData) => {
              console.log(authData);
                catchError((err: HttpErrorResponse) => {
                    return of({
                        type: UserAdminAction.ERROR,
                        payload: err.error['message']
                    }); 
                }) //catcherror
            }) //map
          ) //pipe
        }) //switchmap
    );

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private router: Router) {
  }
}