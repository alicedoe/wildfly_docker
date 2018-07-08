import { ActionReducerMap } from '@ngrx/store';

import * as fromSchool from '../shared/schools/store/school.reducers';
import * as fromAuth from '../shared/auth/store/auth.reducers';

export interface AppState {
  school: fromSchool.State,
  auth: fromAuth.State
}

export const reducers: ActionReducerMap<AppState> = {
  school: fromSchool.schoolReducer,
  auth: fromAuth.authReducer
};
