import { Action } from '@ngrx/store';
import { UserDto } from '../../../shared/models/userDto.model';
import { RoleDto } from '../../models/roleDto.model';
import { NewUserDto } from '../../models/newUserDto.model';

export const GET_USERS = 'GET_USERS';
export const SET_USERS = 'SET_USERS';
export const GET_ROLES = 'GET_ROLES';
export const SET_ROLES = 'SET_ROLES';
export const CREATE_USER = 'CREATE_USER';
export const DELETE_USER = 'DELETE_USER';
export const ADMIN_ERROR = 'ADMIN_ERROR';

export class DeleteUser implements Action {
  readonly type = DELETE_USER;
  constructor(public id: number) {}
}

export class CreateUser implements Action {
  readonly type = CREATE_USER;
  constructor(public newUser: NewUserDto) {}
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

export class AdminError implements Action {
  readonly type = ADMIN_ERROR;
  constructor(public payload: string) {}
}

export type UserAdminActions = GetUsers | AdminError | SetUsers | GetRoles | SetRoles | CreateUser | DeleteUser;