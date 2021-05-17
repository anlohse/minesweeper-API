import { LitElement, html, customElement, property, css } from 'lit-element';
import PubSub from 'pubsub-js';
import http from './http-service';
import './login-form';
import './game-container';

const TOPIC_NAME = 'minesweeper.credentials';

interface UserData {
	
	id: number;

	name: string;

	lastName: string;

	email: string;

};

@customElement("minesweeper-app")
class MinesweeperApp extends LitElement {

	static get styles() {
		return css`
			:host {
				display: block;
			}
			div.container {
				display: flex;
				flex-wrap: wrap;
				width: 250px;
				border-right: 1px solid #777;
				border-bottom: 1px solid #777;
				margin-left: auto;
				margin-right: auto;
			}
		`;
	}

	@property({
		type: "Array"
	})
	data: number[];

	@property({
		type: "Object"
	})
	userData: UserData | undefined;

	constructor() {
		super();
		const assignUser = ((user:any) => {
			this.userData = user;
		}).bind(this);
		const assignGame = ((_match:any) => {
			
		}).bind(this);
		PubSub.subscribe(TOPIC_NAME, ((_msg:any, value:any) => {
			if (!value.authToken)
				this.userData = undefined;
			else {
				http.get('/api/user/current',{}).then(assignUser);
				http.get('/api/match',{}).then(assignGame);
			}
		}).bind(this));
		this.data = [0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0];
	}

	render() {
		return html`
			<div>
				${ !this.userData
					? html`<login-form></login-form>`
					: html`<game-container></game-container>`
				}
			</div>
  		`;
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'minesweeper-app': MinesweeperApp;
	}
}