import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {map, switchMap, mergeMap, tap, catchError} from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import * as UserAdminAction from './userAdmin.actions';
import { of } from 'rxjs';
import { UserDto } from '../../../shared/models/userDto.model';

@Injectable()
export class UserAdminEffects {

  constructor( private actions$: Actions,
    private httpClient: HttpClient ) { }
  
  @Effect()
  getUsers$ = this.actions$
    .ofType(UserAdminAction.AU_GET_USERS)
    .pipe(
      switchMap(() => {

        return this.httpClient.get('http://172.17.0.3:8080/application/v1/user/getall')
        
        .pipe(

          map(res => res),

          mergeMap((authData) => {
            return [
              { type: UserAdminAction.AU_SET_USERS,
                payload: authData }
            ]; }) , // mergemap

          catchError((err: HttpErrorResponse) => {
              return of({
                  type: UserAdminAction.AU_ADMIN_ERROR,
                  payload: err.status
              }); }) // catcherror

        ) // 1st pipe

      }) // switchmap
  ); // 2nd pipe

  // @Effect()
  // editUser$ = this.actions$
  //   .ofType(UserAdminAction.AU_EDIT_USER)
  //   .pipe(
  //     switchMap((authData: { id: number }) => {
  //       return this.httpClient.get('http://172.17.0.3:8080/application/v1/user/get/'+authData.id)        
  //       .pipe(
  //         map(res => res),
  //         mergeMap((authData) => {
  //           return [
  //             { type: UserAdminAction.AU_SET_USER,
  //               payload: authData }
  //           ]; }) , // mergemap
  //         catchError((err: HttpErrorResponse) => {
  //             return of({
  //                 type: UserAdminAction.AU_ADMIN_ERROR,
  //                 payload: err.status
  //             }); }) // catcherror
  //       ) // 1st pipe
  //     }) // switchmap
  // ); // 2nd pipe

  // @Effect()
  // saveUser$ = this.actions$
  //   .ofType(UserAdminAction.AU_SAVE_USER)
  //   .pipe(
  //     switchMap((authData: { newUser: UserDto }) => {
  //       return this.httpClient.put('http://172.17.0.3:8080/application/v1/user/update/', authData.newUser)        
  //       .pipe(
  //         map(res => res),
  //         mergeMap((authData) => {
  //           console.log(authData);
  //           return [{
  //             type: UserAdminAction.AU_GET_USERS
  //           },{
  //             type: UserAdminAction.AU_SET_USERS
  //           }] }) , // mergemap
  //         catchError((err: HttpErrorResponse) => {
  //             return of({
  //                 type: UserAdminAction.AU_ADMIN_ERROR,
  //                 payload: err.status
  //             }); }) // catcherror
  //       ) // 1st pipe
  //     }) // switchmap
  // ); // 2nd pipe

  @Effect()
  getRoles$ = this.actions$
    .ofType(UserAdminAction.AU_GET_ROLES)
    .pipe(
      switchMap(() => {
        return this.httpClient.get('http://172.17.0.3:8080/application/v1/role/getall').pipe(
          map(res => res),
          mergeMap((authData) => {
            return [
              { type: UserAdminAction.AU_SET_ROLES,
                payload: authData }
            ]; }) , //mergemap
          catchError((err: HttpErrorResponse) => {
              return of({
                  type: UserAdminAction.AU_ADMIN_ERROR,
                  payload: err.status
              }); 
          }) //catcherror
        ) //pipe
      }) //switchmap
  );

  @Effect()
  createUser$ = this.actions$
    .ofType(UserAdminAction.AU_CREATE_USER)
    .pipe(
      switchMap((createUser) => {
        return this.httpClient.post('http://172.17.0.3:8080/application/v1/user/add', createUser['newUser']).pipe(
          map(res => res),
          mergeMap(() => {
            return [{
              type: UserAdminAction.AU_GET_USERS
            },{
              type: UserAdminAction.AU_SET_USERS
            }]
          }) , //mergemap
          catchError((err: HttpErrorResponse) => {
            return of({
                type: UserAdminAction.AU_ADMIN_ERROR,
                payload: err.status
            }); 
          }) //catcherror
        ) //pipe
      }) //switchmap
  );

  // @Effect()
  // deleteUser$ = this.actions$
  //   .ofType(UserAdminAction.AU_DELETE_USER)
  //   .pipe(
  //     switchMap((authData: { id: string }) => {
  //       return this.httpClient.delete('http://172.17.0.3:8080/application/v1/user/delete/'+authData.id).pipe(
  //         map(res => res),
  //         mergeMap(() => {
  //           return [{
  //             type: UserAdminAction.AU_GET_USERS
  //           },{
  //             type: UserAdminAction.AU_SET_USERS
  //           }];              
  //         }) , //mergemap
  //         catchError((err: HttpErrorResponse) => {
  //           return of(
  //             {
  //               type: UserAdminAction.AU_ADMIN_ERROR,
  //               payload: err.status
  //           }); 
  //         }) //catcherror
  //       ) //pipe
  //     }) //switchmap
  // );

}