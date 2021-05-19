import { LitElement, html, customElement, property } from 'lit-element';
import userService from './user-services';
import {style} from './styles';

@customElement("register-form")
class RegisterForm extends LitElement {

	static get styles() {
		return style;
	}

	@property()
	error ?: string;

	@property()
	user ?: any;

	@property()
	errorFields: any;

	render() {
		return html`
		<form id="registerForm">
			<div class="container">
				<div class="error">
					<h3>${this.error}</h3>
				</div>
				<div>
					<label>E-Mail:</label>
					<span class="error">
						<h3>${this.errorFields.email}</h3>
					</span>
					<input type="text" name="email" @change="${this.handleChange}" .value="${this.user.email}"/>
				</div>
				<div>
					<label>Name:</label>
					<span class="error">
						<h3>${this.errorFields.name}</h3>
					</span>
					<input type="text" name="name" @change="${this.handleChange}" .value="${this.user.name}"/>
				</div>
				<div>
					<label>Last Name:</label>
					<span class="error">
						<h3>${this.errorFields.lastName}</h3>
					</span>
					<input type="text" name="lastName" @change="${this.handleChange}" .value="${this.user.lastName}"/>
				</div>
				<div>
					<label>Nickname:</label>
					<span class="error">
						<h3>${this.errorFields.nickname}</h3>
					</span>
					<input type="text" name="nickname" @change="${this.handleChange}" .value="${this.user.nickname}"/>
				</div>
				<div>
					<label>Password:</label>
					<span class="error">
						<h3>${this.errorFields.password}</h3>
					</span>
					<input type="password" name="password" @change="${this.handleChange}" .value="${this.user.password}"/>
				</div>
				<div>
					<label>Confirm Password:</label>
					<span class="error">
						<h3>${this.errorFields.confirmPassword}</h3>
					</span>
					<input type="password" name="confirmPassword" @change="${this.handleChange}" .value="${this.user.confirmPassword}"/>
				</div>
				<div>
					<button id="sendbutton" @click="${this.handleRegister}">Send</button>
				</div>
			</div>
		</form>
		`; 
	}

	constructor() {
		super();
		this.user = {email:'', name:'', lastName:'', nickname:'', password:'', confirmPassword:''};
		this.errorFields = {email:'', name:'', lastName:'', nickname:'', password:'', confirmPassword:''};
	}

	handleChange(e:any) {
		let target = e.target;
		if (target) {
			this.user[target.name] = target.value;
			this.errorFields = {email:'', name:'', lastName:'', nickname:'', password:'', confirmPassword:''};
		}
	}

	handleRegister(e:any) {
		e.preventDefault();
		userService.create(this.user, {})
			.then(((_v:any) => {
				this.error = 'An activation meil was sent.';
				this.user = {email:'', name:'', lastName:'', nickname:'', password:'', confirmPassword:''};
			}).bind(this))
			.catch(((e:any) => {
				e.response.json().then(((v:any) => {
					this.error = v.message;
					if (v.fields)
						v.fields.forEach((f:any) => this.errorFields[f.id] = f.message);
				}).bind(this));
			}).bind(this));
	}
}

declare global {
	interface HTMLElementTagNameMap {
		'register-form': RegisterForm;
	}
}