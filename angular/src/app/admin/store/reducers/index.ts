import * as fromUserAdmin from './userAdmin.reducers';
import { ActionReducerMap } from '@ngrx/store';

export interface UserAdminState  {
    userAdmin: fromUserAdmin.UserAdminState;
}

export const reducers : ActionReducerMap<UserAdminState> = {
    userAdmin: fromUserAdmin.userAdminReducer,
}