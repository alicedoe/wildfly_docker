import { Action } from '@ngrx/store';
import { UserDto } from '../../../shared/models/userDto.model';
import { RoleDto } from '../../models/roleDto.model';
import { NewUserDto } from '../../models/newUserDto.model';

export const AU_GET_USER = 'AU_GET_USER';
export const AU_GET_USERS = 'AU_GET_USERS';
export const AU_SET_USERS = 'AU_SET_USERS';
export const AU_SET_USER = 'AU_SET_USER';
export const AU_GET_ROLES = 'AU_GET_ROLES';
export const AU_SET_ROLES = 'AU_SET_ROLES';
export const AU_CREATE_USER = 'AU_CREATE_USER';
export const AU_DELETE_USER = 'AU_DELETE_USER';
export const AU_ADMIN_ERROR = 'AU_ADMIN_ERROR';

export class DeleteUser implements Action {
  readonly type = AU_DELETE_USER;
  constructor(public id: number) {}
}

export class CreateUser implements Action {
  readonly type = AU_CREATE_USER;
  constructor(public newUser: NewUserDto) {}
}

export class GetRoles implements Action {
  readonly type = AU_GET_ROLES;
  constructor() {}
}

export class SetRoles implements Action {
  readonly type = AU_SET_ROLES;
  constructor(public payload: Array<RoleDto>) {}
}

export class GetUser implements Action {
  readonly type = AU_GET_USER;
  constructor(public id: number) {}
}

export class GetUsers implements Action {
    readonly type = AU_GET_USERS;
    constructor() {}
  }

export class SetUsers implements Action {
    readonly type = AU_SET_USERS;
    constructor(public payload: Array<UserDto>) {}
  }

export class SetUser implements Action {
    readonly type = AU_SET_USER;
    constructor(public payload: UserDto) {}
  }

export class AdminError implements Action {
  readonly type = AU_ADMIN_ERROR;
  constructor(public payload: string) {}
}

export type UserAdminActions = GetUsers | GetUser | AdminError | SetUsers | SetUser | GetRoles | SetRoles | CreateUser | DeleteUser;