package com.alicegabbana.cahierenligne.dto;

public class UserDto {

		private Long id;
		private String roleName;
		private String firstname;
		private String name;
		private String email;
		private String token;
		private String pwd;
		
		public String getPwd() {
			return pwd;
		}
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getRoleName() {
			return roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		@Override
		public String toString() {
			return "UserDto [id=" + id + ", roleName=" + roleName + ", firstname=" + firstname + ", name=" + name
					+ ", email=" + email + ", token=" + token + ", pwd=" + pwd + "]";
		}
}