export class UserDto {
  public id: number;
  public roleName: string;
  public firstname: string;
  public name: string;
  public email: string;
  public kidsClassName: string;
  public pwd: string;

  constructor(id: number, 
    roleName: string, 
    firstname:string, 
    name: string, 
    email:string, 
    kidsClassName: string, 
    pwd:string ) {
  }
}