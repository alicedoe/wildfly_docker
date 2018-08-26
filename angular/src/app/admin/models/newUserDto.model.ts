export class NewUserDto {
	public roleName: string;
	public firstname: string;
	public name: string;
	public email: string;
	public pwd: string;
	
	constructor(
		firstname: string,
		name: string,
		email: string,
		pwd: string, 
		roleName: string
	) {
		this.roleName = roleName;
		this.firstname = firstname;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
	}
}