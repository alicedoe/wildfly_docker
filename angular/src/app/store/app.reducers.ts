import { ActionReducerMap } from '@ngrx/store';

import * as fromSchool from '../shared/schools/store/school.reducers';

export interface AppState {
  school: fromSchool.State
}

export const reducers: ActionReducerMap<AppState> = {
  school: fromSchool.schoolReducer
};
