import { Action } from '@ngrx/store';

export const AUTH_TRY_SIGNUP = 'AUTH_TRY_SIGNUP';
export const AUTH_SIGNUP = 'AUTH_SIGNUP';
export const AUTH_TRY_SIGNIN = 'AUTH_TRY_SIGNIN';
export const AUTH_TRY_TOKEN_SIGNIN = 'AUTH_TRY_TOKEN_SIGNIN';
export const AUTH_SIGNIN = 'AUTH_SIGNIN';
export const AUTH_ERROR = 'AUTH_ERROR';
export const AUTH_LOGOUT = 'AUTH_LOGOUT';
export const AUTH_SET_TOKEN = 'AUTH_SET_TOKEN';
export const AUTH_SET_USER = 'AUTH_SET_USER';

export class TrySignup implements Action {
  readonly type = AUTH_TRY_SIGNUP;
  constructor(public payload: {username: string, password: string}) {}
}

export class TrySignin implements Action {
  readonly type = AUTH_TRY_SIGNIN;
  constructor(public payload: {username: string, password: string}) {
  }
}

export class TryTokenSignin implements Action {
  readonly type = AUTH_TRY_TOKEN_SIGNIN;
  constructor() {
  }
}

export class Signup implements Action {
  readonly type = AUTH_SIGNUP;
}

export class Signin implements Action {
  readonly type = AUTH_SIGNIN;
}

export class Error implements Action {
  readonly type = AUTH_ERROR;
  constructor(public payload: string) {}
}

export class Logout implements Action {
  readonly type = AUTH_LOGOUT;
}

export class SetToken implements Action {
  readonly type = AUTH_SET_TOKEN;

  constructor(public payload: string) {}
}

export class SetUser implements Action {
  readonly type = AUTH_SET_USER;

  constructor(public payload: string) {}
}

export type AuthActions = Signup | Signin | Logout | SetToken | TrySignup | TrySignin | SetUser | Error ;
