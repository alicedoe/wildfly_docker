export class UserDto {
	public id: number;
	public roleName: string;
	public firstname: string;
	public name: string;
	public email: string;
	public pwd: string;
	
	constructor(
		id: number,
		firstname: string,
		name: string,
		email: string,
		pwd: string, 
		roleName: string
	) {
		this.id = id;
		this.roleName = roleName;
		this.firstname = firstname;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
	}
}