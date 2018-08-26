import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {Router} from '@angular/router';
import {map, switchMap, mergeMap, tap, catchError} from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import * as UserAdminAction from './userAdmin.actions';
import { of, EMPTY } from 'rxjs';
import { NewUserDto } from '../../models/newUserDto.model';

@Injectable()
export class UserAdminEffects {
  
    @Effect()
    getUsers = this.actions$
      .ofType(UserAdminAction.GET_USERS)
      .pipe(
        switchMap((authData: { name: string }) => {
          return this.httpClient.get('http://172.17.0.3:8080/application/v1/user/getall').pipe(
            map(res => res),
            mergeMap((authData) => {
              return [
                { type: UserAdminAction.SET_USERS,
                  payload: authData }
              ]; }) , //mergemap
            catchError((err: HttpErrorResponse) => {
                return of({
                    type: UserAdminAction.ERROR,
                    payload: err.error['message']
                }); 
            }) //catcherror
          ) //pipe
        }) //switchmap
    );

    @Effect()
    getRoles = this.actions$
      .ofType(UserAdminAction.GET_ROLES)
      .pipe(
        switchMap((authData: { name: string }) => {
          return this.httpClient.get('http://172.17.0.3:8080/application/v1/role/getall').pipe(
            map(res => res),
            mergeMap((authData) => {
              return [
                { type: UserAdminAction.SET_ROLES,
                  payload: authData }
              ]; }) , //mergemap
            catchError((err: HttpErrorResponse) => {
                return of({
                    type: UserAdminAction.ERROR,
                    payload: err.error['message']
                }); 
            }) //catcherror
          ) //pipe
        }) //switchmap
    );

    @Effect()
    createUser = this.actions$
      .ofType(UserAdminAction.CREATE_USER)
      .pipe(
        switchMap((authData) => {
          return this.httpClient.post('http://172.17.0.3:8080/application/v1/user/add', authData['payload']).pipe(
            map(res => res),
            mergeMap((authData) => {
              console.log(authData);
              return EMPTY;
            }) , //mergemap
            catchError((err: HttpErrorResponse) => {
                return of({
                    type: UserAdminAction.ERROR,
                    payload: err.error['message']
                }); 
            }) //catcherror
          ) //pipe
        }) //switchmap
    );

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private router: Router) {
  }
}