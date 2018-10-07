import { UserDto } from "../../../shared/models/userDto.model";
import * as UserAdminActions from '../actions/userAdmin.actions';
import { RoleDto } from "../../models/roleDto.model";

export interface UserAdminState {
    users: Array<UserDto>,
    roles: Array<RoleDto>,
    error: string;
    user: UserDto;
    edit: boolean;
  }

const initialState: UserAdminState = {
    users: null,
    roles: null,
    error: null,
    user: null,
    edit: false
};

export function userAdminReducer(state = initialState, action: UserAdminActions.UserAdminActions) {
    switch (action.type) {
        case (UserAdminActions.AU_ADMIN_ERROR):
            switch (String(action.payload)) {
                case '400' :  return {
                    ...state,
                    error: "Le formulaire est incomplet"
                };
                case '401' :  return {
                    ...state,
                    error: "Accès interdit"
                };
                case '409' :  return {
                    ...state,
                    error: "Email déjà enregistré"
                };
            }
        case (UserAdminActions.AU_CLEAR):
            return {
                ...state,
                edit: false,
                user: null
            };
        case (UserAdminActions.AU_EDIT_USER):
            return {
                ...state,
                edit: true
            };
        case (UserAdminActions.AU_GET_USERS):            
            return {
                ...state,
            };
        case (UserAdminActions.AU_SET_USER):
            return {
                ...state,
                user: action.payload
            };
        case (UserAdminActions.AU_SET_USERS):
            return {
                ...state,
                users: action.payload
            };
        case (UserAdminActions.AU_GET_ROLES):         
            return {
                ...state,
            };
        case (UserAdminActions.AU_SET_ROLES):           
            return {
                ...state,
                roles: action.payload
            };
        default:
            return state;
    }
}

export const getRoles = (userAdminState : UserAdminState) => userAdminState.roles;
export const getUsers = (userAdminState : UserAdminState) => userAdminState.users;
export const getError = (userAdminState : UserAdminState) => userAdminState.error;
export const getUser = (userAdminState : UserAdminState) => userAdminState.user;
export const getEditMode = (userAdminState : UserAdminState) => userAdminState.edit;