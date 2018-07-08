import { Injectable } from '@angular/core';
import { Actions, Effect } from '@ngrx/effects';
import { switchMap, withLatestFrom, map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Store } from '@ngrx/store';

import * as SchoolActions from './school.actions';
import * as fromSchool from './school.reducers';
import { SchoolDto } from '../schoolDto.model';

@Injectable()
export class SchoolEffects {

  schoolDto:SchoolDto = new SchoolDto( 2, "Ecole de Maraussan", "Maraussan");

  @Effect()
  getSchool = this.actions$
    .ofType(SchoolActions.GET_SCHOOL)
    .pipe(switchMap((action: SchoolActions.GetSchool) => {
      let headers: HttpHeaders = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('token', 'monsupertoken');
      return this.httpClient.get<SchoolDto[]>('http://172.17.0.3:8080/application/v1/school/get/'+action.id, {
        responseType: 'json',
        headers:headers
      });
    }), map(
      (school) => {
        console.log(school);
        return {
          type: SchoolActions.SET_SCHOOLS,
          payload: school
        };
      }
    ));

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private store: Store<fromSchool.FeatureState>) {
  }
}
