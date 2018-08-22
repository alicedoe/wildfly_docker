import { Action } from '@ngrx/store';
import { UserDto } from '../../../shared/models/userDto.model';

export const GET_ALL_USERS = 'GET_ALL_USERS';
export const SET_ALL_USERS = 'SET_ALL_USERS';
export const ERROR = 'ERROR';

export class GetAllUsers implements Action {
    readonly type = GET_ALL_USERS;
    constructor() {}
  }

export class SetAllUsers implements Action {
    readonly type = SET_ALL_USERS;
    constructor(public payload: Array<UserDto>) {}
  }

export class Error implements Action {
  readonly type = ERROR;
  constructor(public payload: string) {}
}

export type UserAdminActions = GetAllUsers | Error | SetAllUsers;