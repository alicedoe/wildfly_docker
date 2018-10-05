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
  }

}