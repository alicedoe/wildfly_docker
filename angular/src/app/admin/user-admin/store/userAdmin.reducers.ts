import { UserDto } from "../../../shared/models/userDto.model";
import * as UserAdminActions from './userAdmin.actions';

export interface State {
    users: Array<UserDto>,
    error: string;
  }

const initialState: State = {
    users: null,
    error: null
};

export function userAdminReducers(state = initialState, action: UserAdminActions.UserAdminActions) {
    switch (action.type) {
        case (UserAdminActions.GET_ALL_USERS):            
            return {
                ...state,
            };
        case (UserAdminActions.ERROR):
        return {
            ...state,
            error: action.payload
            };
        default:
        return state;
    }
}