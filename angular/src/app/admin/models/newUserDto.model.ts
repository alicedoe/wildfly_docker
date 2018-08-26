export class NewUserDto {
	public roleName: string;
	public firstname: string;
	public name: string;
	public email: string;
	public kidsClassName: string;
	public pwd: string;
	
	constructor(
		firstname: string,
		name: string,
		email: string,
		pwd: string, 
		roleName: string, 
		kidsClassName: string
	) {
		this.roleName = roleName;
		this.firstname = firstname;
		this.name = name;
		this.email = email;
		this.kidsClassName = kidsClassName;
		this.pwd = pwd;
	}
}