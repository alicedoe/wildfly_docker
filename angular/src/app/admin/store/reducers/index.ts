import * as fromUserAdmin from './userAdmin.reducers';
import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export interface UserAdminState  {
    userAdmin: fromUserAdmin.UserAdminState;
}

export const reducers : ActionReducerMap<UserAdminState> = {
    userAdmin: fromUserAdmin.userAdminReducer,
}

export const getUserAdminStates = createFeatureSelector<UserAdminState>('userAdmin');

export const getUserAdminState = createSelector(
    
    getUserAdminStates, (state: UserAdminState) => {
        return state.userAdmin
    }
);

export const getEditMode = createSelector(
    getUserAdminState, fromUserAdmin.getEditMode
);
export const getError = createSelector(
    getUserAdminState, fromUserAdmin.getError
);
export const getRoles = createSelector(
    getUserAdminState, fromUserAdmin.getRoles
);
export const getUser = createSelector(
    getUserAdminState, fromUserAdmin.getUser   
);
export const getUsers = createSelector(
    getUserAdminState, fromUserAdmin.getUsers 
);