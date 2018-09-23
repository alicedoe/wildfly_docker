import * as fromAuth from './auth.reducers';
import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export interface AuthState  {
    auth: fromAuth.AuthState;
}

export const reducers : ActionReducerMap<AuthState> = {
    auth: fromAuth.reducer,
}

export const getAuthStates = createFeatureSelector<AuthState>('auth');

export const getAuthState = createSelector(
    getAuthStates, (state: AuthState) => state.auth
);

export const getAuthenticated = createSelector(
    getAuthState, fromAuth.getAuthenticated
);
export const getTokens = createSelector(
    getAuthState, fromAuth.getToken
);
export const getUser = createSelector(
    getAuthState, fromAuth.getUser
);
export const getError = createSelector(
    getAuthState, fromAuth.getError    
);