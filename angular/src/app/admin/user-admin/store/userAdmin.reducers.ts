import { UserDto } from "../../../shared/models/userDto.model";
import * as UserAdminActions from './userAdmin.actions';
import { RoleDto } from "../../models/roleDto.model";

export interface State {
    users: Array<UserDto>,
    roles: Array<RoleDto>,
    error: string;
  }

const initialState: State = {
    users: null,
    roles: null,
    error: null
};

export function userAdminReducers(state = initialState, action: UserAdminActions.UserAdminActions) {
    switch (action.type) {
        case (UserAdminActions.ADMIN_ERROR):
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
        case (UserAdminActions.GET_USERS):            
            return {
                ...state,
            };
        case (UserAdminActions.SET_USERS):   
        console.log(action.payload)        
            return {
                ...state,
                users: action.payload
            };
        case (UserAdminActions.GET_ROLES):            
            return {
                ...state,
            };
        case (UserAdminActions.SET_ROLES):           
            return {
                ...state,
                roles: action.payload
            };
        default:
            return state;
    }
}