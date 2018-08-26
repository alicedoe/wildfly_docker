import { Action } from '@ngrx/store';
import { UserDto } from '../../../shared/models/userDto.model';
import { RoleDto } from '../../models/roleDto.model';
import { NewUserDto } from '../../models/newUserDto.model';

export const GET_USERS = 'GET_USERS';
export const SET_USERS = 'SET_USERS';
export const GET_ROLES = 'GET_ROLES';
export const SET_ROLES = 'SET_ROLES';
export const CREATE_USER = 'CREATE_USER';
export const ERROR = 'ERROR';

export class CreateUser implements Action {
  readonly type = CREATE_USER;
  constructor(public payload: NewUserDto) {}
}

export class GetRoles implements Action {
  readonly type = GET_ROLES;
  constructor() {}
}

export class SetRoles implements Action {
  readonly type = SET_ROLES;
  constructor(public payload: Array<RoleDto>) {}
}

export class GetUsers implements Action {
    readonly type = GET_USERS;
    constructor() {}
  }

export class SetUsers implements Action {
    readonly type = SET_USERS;
    constructor(public payload: Array<UserDto>) {}
  }

export class Error implements Action {
  readonly type = ERROR;
  constructor(public payload: string) {}
}

export type UserAdminActions = GetUsers | Error | SetUsers | GetRoles | SetRoles | CreateUser;