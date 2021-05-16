import { LitElement, html, customElement, property, css } from 'lit-element';

@customElement("login-form")
class LoginForm extends LitElement {

	static get styles() {
		return css`
			:host {
				display: block;
			}
			div.container {
				width: 250px;
				border-right: 1px solid #777;
				border-bottom: 1px solid #777;
				margin-left: auto;
				margin-right: auto;
			}
		`;
	}

	@property({
		type: "Object"
	})
	props: any;

	render() {
		return html`
			<div>
				<game-container></game-container>
			</div>
  		`;
	}

	constructor() {
		super();
		this.props = {
			username: '',
			password: ''
		};
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'login-form': LoginForm;
	}
}