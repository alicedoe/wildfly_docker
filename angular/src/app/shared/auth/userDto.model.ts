export class UserDto {
  public id: number;
  public roleName: string;
  public firstName: string;
  public name: string;
  public email: string;
  public kidsClassName: string

  constructor(id: number, 
    roleName: string, 
    firstName:string, 
    name: string, 
    email:string, 
    kidsClassName: string) {
      this.id = id;
      this.roleName = roleName;
      this.firstName = firstName;
      this.name = name;
      this.email = email;
      this.kidsClassName = kidsClassName;
  }
}