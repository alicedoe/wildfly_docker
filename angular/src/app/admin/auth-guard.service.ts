import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { map, catchError } from 'rxjs/operators';
import { RoleDto } from './models/roleDto.model';
import { of, Observable } from 'rxjs';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(private httpClient: HttpClient) {
         
  }

  canActivate(): Observable<boolean> {
    let token  =  localStorage.getItem('token');
    return this.httpClient
    .get('http://172.17.0.3:8080/application/v1/user/get/'+token+'/role/admin')
    .pipe(
      map(response => response === true),
      catchError(error => of(false))
    );
//     if (localStorage.getItem('token')) {
//         let token  =  localStorage.getItem('token');
//         let role = this.httpClient
//         .get('http://172.17.0.3:8080/application/v1/user/get/'+token+'/role')
//         .pipe(    
//             map((authData: RoleDto) => {
//               return authData;
//             }) ,
//             catchError((err: HttpErrorResponse) => {
//               return of({
//                 type: HttpErrorResponse,
//                 payload: err
//               });
//             }) ,
//           )
//         role.subscribe((res:RoleDto)=>{
//             if (res.name === "admin") {
//                 console.log('yep from http')
//                 return of({
//                     type: Boolean,
//                     payload: true
//                   });
//             } else {
//                 console.log('nop from http')
//                 return of({
//                     type: Boolean,
//                     payload: false
//                   });
//             }
//         })
        
//     } else {
//         console.log('nop no token')
//         return of({
//             type: Boolean,
//             payload: false
//           }); 
//     }
//   }

}