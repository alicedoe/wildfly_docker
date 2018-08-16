import { Action } from '@ngrx/store';

export const TRY_SIGNUP = 'TRY_SIGNUP';
export const SIGNUP = 'SIGNUP';
export const TRY_SIGNIN = 'TRY_SIGNIN';
export const TRY_TOKEN_SIGNIN = 'TRY_TOKEN_SIGNIN';
export const SIGNIN = 'SIGNIN';
export const ERROR = 'ERROR';
export const LOGOUT = 'LOGOUT';
export const SET_TOKEN = 'SET_TOKEN';
export const SET_USER = 'SET_USER';
export const GET_ROLE = 'GET_ROLE';
export const SET_ROLE = 'SET_ROLE';

export class TrySignup implements Action {
  readonly type = TRY_SIGNUP;
  constructor(public payload: {username: string, password: string}) {}
}

export class GetRole implements Action {
  readonly type = GET_ROLE;
  constructor() {}
}

export class SetRole implements Action {
  readonly type = SET_ROLE;
  constructor(public name:string) {}
}

export class TrySignin implements Action {
  readonly type = TRY_SIGNIN;
  constructor(public payload: {username: string, password: string}) {
  }
}

export class TryTokenSignin implements Action {
  readonly type = TRY_TOKEN_SIGNIN;
  constructor() {
  }
}

export class Signup implements Action {
  readonly type = SIGNUP;
}

export class Signin implements Action {
  readonly type = SIGNIN;
}

export class Error implements Action {
  readonly type = ERROR;
  constructor(public payload: string) {}
}

export class Logout implements Action {
  readonly type = LOGOUT;
}

export class SetToken implements Action {
  readonly type = SET_TOKEN;

  constructor(public payload: string) {}
}

export class SetUser implements Action {
  readonly type = SET_USER;

  constructor(public payload: string) {}
}

export type AuthActions = Signup | Signin | Logout | SetToken | TrySignup | TrySignin | SetUser | Error | SetRole;
