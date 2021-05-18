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

	render() {
		return html`
		<form id="registerForm">
			<div class="container">
				<div class="error">
					<h3>${this.error}</h3>
				</div>
				<div>
					<label>E-Mail:</label>
					<input type="text" name="email" @change="${this.handleChange}"/>
				</div>
				<div>
					<label>Name:</label>
					<input type="text" name="name" @change="${this.handleChange}"/>
				</div>
				<div>
					<label>Last Name:</label>
					<input type="text" name="lastName" @change="${this.handleChange}"/>
				</div>
				<div>
					<label>Nickname:</label>
					<input type="text" name="nickname" @change="${this.handleChange}"/>
				</div>
				<div>
					<label>Password:</label>
					<input type="password" name="password" @change="${this.handleChange}"/>
				</div>
				<div>
					<label>Confirm Password:</label>
					<input type="password" name="confirmPassword" @change="${this.handleChange}"/>
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
	}

	handleChange(e:any) {
		let target = e.target;
		if (target) {
			this.user[target.name] = target.value;
		}
	}

	handleRegister(e:any) {
		e.preventDefault();
		userService.create(this.user, {})
			.then(((_v:any) => {
				this.error = 'An activation meil was sent.';
			}).bind(this))
			.catch(((e:any) => {
				e.response.json().then(((v:any) => this.error = v.message).bind(this));
			}).bind(this));
	}
}

declare global {
	interface HTMLElementTagNameMap {
		'register-form': RegisterForm;
	}
}