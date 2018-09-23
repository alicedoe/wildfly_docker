import * as AuthActions from '../actions/auth.actions';
import { UserDto } from '../../../shared/models/userDto.model';

export interface AuthState {
  token: string;
  authenticated: boolean;
  userDto: UserDto;
  error: string;
}

const initialState: AuthState = {
  token: null,
  authenticated: false,
  userDto: null,
  error: null,
};

export function reducer(state = initialState, action: AuthActions.AuthActions) {
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

export const getToken = (state: AuthState) => state.token;
export const getAuthenticated = (state: AuthState) => state.authenticated;
export const getUser = (state: AuthState) => state.userDto;
export const getError = (state: AuthState) => state.error;