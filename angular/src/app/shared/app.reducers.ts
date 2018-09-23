import { ActionReducerMap } from '@ngrx/store';

import * as fromSchool from './schools/store/school.reducers';
import * as fromAuth from '../auth/store/reducers/auth.reducers';
import * as fromUserAdmin from '../admin/store/reducers/userAdmin.reducers';

export interface AppState {
  school: fromSchool.State,
  auth: fromAuth.State,
  // userAdmin: fromUserAdmin.UserAdminState
}

export const reducers: ActionReducerMap<AppState> = {
  school: fromSchool.schoolReducer,
  auth: fromAuth.authReducer,
  // userAdmin: fromUserAdmin.userAdminReducer
};
