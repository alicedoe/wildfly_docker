import * as AuthActions from './auth.actions';
import { UserDto } from '../../shared/models/userDto.model';

export interface State {
  token: string;
  authenticated: boolean;
  userDto: UserDto;
  error: string;
  role: string;
}

const initialState: State = {
  token: null,
  authenticated: false,
  userDto: null,
  error: null,
  role: null
};

export function authReducer(state = initialState, action: AuthActions.AuthActions) {
  switch (action.type) {
    case (AuthActions.SIGNUP):
    case (AuthActions.SIGNIN):
      return {
        ...state,
        authenticated: true,
        error: null
      };
    case (AuthActions.ERROR):
      return {
        ...state,
        token: null,
        authenticated: false,
        userDto: null,
        error: action.payload
      };
    case (AuthActions.LOGOUT):
      localStorage.removeItem('token');
      return {
        ...state,
        token: null,
        authenticated: false,
        userDto: null,
        error: null
      };
    case (AuthActions.SET_TOKEN):
      localStorage.setItem('token', action.payload);    
      return {
        ...state,
        token: action.payload
      };
    case (AuthActions.SET_USER):
    return {
      ...state,
      userDto: new UserDto(action.payload['id'], 
                    action.payload['roleName'], 
                    action.payload['firstname'], 
                    action.payload['name'], 
                    action.payload['email'],
                    action.payload['kidsClassName'])
      };
    case (AuthActions.SET_ROLE):
    return {
      ...state,
      role: action['payload'].name
    }
    default:
      return state;
  }
}