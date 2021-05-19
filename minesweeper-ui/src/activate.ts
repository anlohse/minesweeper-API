import { LitElement, html, customElement, property } from 'lit-element';
import userService from './user-services';
import {style} from './styles';

@customElement("activate-form")
class ActivationForm extends LitElement {

	static get styles() {
		return style;
	}

	@property()
	error ?: string;

	@property()
	code ?: string;

	@property()
	email ?: string;

	render() {
		return html`
		<form id="activationForm">
			<div class="container">
				<div class="error">
					<h3>${this.error}</h3>
				</div>
				<div>
					<label>Código:</label>
					<input type="text" name="code" .value="${this.code}" @change="${this.changeCode}"/>
				</div>
				<div>
					<button id="sendbutton" @click="${this.handleActivation}">Activate</button>
				</div>
				<br/>
				<div>
					<label>E-Mail:</label>
					<input type="text" name="email" @change="${this.changeMail}"/>
				</div>
				<div>
					<button id="resendbutton" @click="${this.handleResend}">Resend code</button>
				</div>
			</div>
		</form>
		`; 
	}

	constructor() {
		super();
	}

	changeCode(e:any) {
		this.code = e.target.value;
	}

	changeMail(e:any) {
		this.email = e.target.value;
	}

	handleActivation(e:any) {
		e.preventDefault();
		userService.activate(this.code)
			.then(((_v:any) => {
				this.error = 'Activation done.';
			}).bind(this))
			.catch(((e:any) => {
				e.response.json().then(((v:any) => this.error = v.message).bind(this));
			}).bind(this));
	}
	
	handleResend(e:any) {
		e.preventDefault();
		userService.resendActivation(this.email)
			.then(((_v:any) => {
				this.error = 'A new code was sent.';
			}).bind(this))
			.catch(((e:any) => {
				e.response.json().then(((v:any) => this.error = v.message).bind(this));
			}).bind(this));
	}
}

declare global {
	interface HTMLElementTagNameMap {
		'activate-form': ActivationForm;
	}
}