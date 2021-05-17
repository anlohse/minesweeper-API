import { LitElement, html, customElement, css } from 'lit-element';
import http from './http-service';

@customElement("login-form")
class LoginForm extends LitElement {

	static get styles() {
		return css`
			:host {
				display: block;
			}
			div.container {
				width: 250px;
				border: 1px solid #777;
				margin-left: auto;
				margin-right: auto;
				padding: 8px;
			}
			div.container div {
				margin-top: 8px;
			}
			label, input, button {
				display: block;
			}
			label {
				width: 100%;
			}
			input {
				padding: 2px;
				width:calc(100% - 8px);
			}
			button {
				width:100%;
			}
		`;
	}

	render() {
		return html`
			<form id="loginForm">
				<input type="hidden" name="grant_type" value="password" />
				<div class="container">
					<div>
						<label>Username:</label>
						<input type="text" name="username"/>
					</div>
					<div>
						<label>Password:</label>
						<input type="password" name="password"/>
					</div>
					<div>
						<button id="loginbutton" @click="${this.handleLogin}">Login</button>
					</div>
				</div>
			</form>
  		`;
	}

	constructor() {
		super();
	}

	handleLogin(e: any) {
		e.preventDefault();
		let formEl = this.shadowRoot?this.shadowRoot.getElementById('loginForm'):null;
		if (!formEl) return;
		var form = new FormData(<HTMLFormElement>formEl);
		http.post('/oauth/token', { body: form, headers: {"Authorization": "Basic d3JpdGVyOjEyMw=="} })
			.then((res: any) => {
				console.log(res);
			});
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'login-form': LoginForm;
	}
}