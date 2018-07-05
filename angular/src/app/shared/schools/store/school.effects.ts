import { Injectable } from '@angular/core';
import { Actions, Effect } from '@ngrx/effects';
import { switchMap, withLatestFrom, map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Store } from '@ngrx/store';

import * as SchoolActions from '../store/school.actions';
import { School } from '../school.model';
import * as fromSchool from './school.reducers';

@Injectable()
export class SchoolEffects {
  @Effect()
  getSchools = this.actions$
    .ofType(SchoolActions.GET_SCHOOLS)
    .pipe(switchMap((action: SchoolActions.GetSchools) => {
      console.log('SchoolEffects');
      return this.httpClient.get<School[]>('http://172.17.0.3:8080/application/v1/school/getall', {
        observe: 'body',
        responseType: 'json',
        headers: { 'token' : 'monsupertoken'}
      });
    }), map(
      (schools) => {
        console.log(schools);
        return {
          type: SchoolActions.SET_SCHOOLS,
          payload: schools
        };
      }
    ));

  constructor(private actions$: Actions,
    private httpClient: HttpClient,
    private store: Store<fromSchool.FeatureState>) {
  }
}
