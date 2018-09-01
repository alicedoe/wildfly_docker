import { UserDto } from "../../../shared/models/userDto.model";
import * as UserAdminActions from './userAdmin.actions';
import { RoleDto } from "../../models/roleDto.model";

export interface State {
    users: Array<UserDto>,
    roles: Array<RoleDto>,
    error: string;
    user: UserDto;
  }

const initialState: State = {
    users: null,
    roles: null,
    error: null,
    user: null
};

export function userAdminReducers(state = initialState, action: UserAdminActions.UserAdminActions) {
    switch (action.type) {
        case (UserAdminActions.AU_ADMIN_ERROR):
            switch (String(action.payload)) {
                case '400' :  return {
                    ...state,
                    error: "Form not complete"
                };
                case '401' :  return {
                    ...state,
                    error: "Not authorized"
                };
                case '409' :  return {
                    ...state,
                    error: "Conflict"
                };
            }
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