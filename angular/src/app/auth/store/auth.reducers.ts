import * as AuthActions from './auth.actions';
import { UserDto } from '../../shared/models/userDto.model';

export interface State {
  token: string;
  authenticated: boolean;
  userDto: UserDto;
  error: string;
}

const initialState: State = {
  token: null,
  authenticated: false,
  userDto: null,
  error: null,
};

export function authReducer(state = initialState, action: AuthActions.AuthActions) {
  switch (action.type) {
    case (AuthActions.AUTH_SIGNUP):
    case (AuthActions.AUTH_SIGNIN):
      return {
        ...state,
        authenticated: true,
        error: null
      };
    case (AuthActions.AUTH_ERROR):
      return {
        ...state,
        token: null,
        authenticated: false,
        userDto: null,
        error: action.payload
      };
    case (AuthActions.AUTH_LOGOUT):
      localStorage.removeItem('token');
      return {
        ...state,
        token: null,
        authenticated: false,
        userDto: null,
        error: null
      };
    case (AuthActions.AUTH_SET_TOKEN):
      localStorage.setItem('token', action.payload);    
      return {
        ...state,
        token: action.payload
      };
    case (AuthActions.AUTH_SET_USER):
      return {
        ...state,
        userDto: new UserDto( null,
                      action.payload['id'],                        
                      action.payload['firstname'], 
                      action.payload['name'], 
                      action.payload['email'],
                      action.payload['roleName'])
        };
    default:
      return state;
  }
}
