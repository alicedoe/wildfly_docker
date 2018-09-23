import * as fromAuth from './auth.reducers';
import { ActionReducerMap } from '@ngrx/store';

export interface AuthState  {
    userAdmin: fromAuth.State;
}

export const reducers : ActionReducerMap<AuthState> = {
    userAdmin: fromAuth.authReducer,
}