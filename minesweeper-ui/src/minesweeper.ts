import { LitElement, html, customElement, property, css } from 'lit-element';
import { router, navigator } from 'lit-element-router';
import PubSub from 'pubsub-js';
import http from './http-service';
import './login-form';
import './register';
import './activate';
import './game-container';
import logo from 'url:~/resources/minesweeper_icon.png';
import { Match, UserData } from './types';

const TOPIC_NAME = 'minesweeper.credentials';

const DF_OPTIONS: any = {
    year: 'numeric', month: 'numeric', day: 'numeric',
	hour: 'numeric', minute: 'numeric', second: 'numeric'
};

const dateFormat = new Intl.DateTimeFormat('default', DF_OPTIONS);

@customElement("minesweeper-app")
@router
@navigator
class MinesweeperApp extends LitElement {

	static get routes() {
		return [{
			name: 'home',
			pattern: ''
		}, {
			name: 'login',
			pattern: 'login'
		}, {
			name: 'register',
			pattern: 'register'
		}, {
			name: 'activate',
			pattern: 'activate/:activationCode'
		}, {
			name: 'recover',
			pattern: 'recover/:recoveryCode'
		}, {
			name: 'not-found',
			pattern: '*'
		}];
	}

	static get styles() {
		return css`
			:host {
				display: block;
				display: flex;
				flex-direction: column;
				align-items: center;
			}

			.App {
				background-color: #282c34;
			}
			  
			.App-logo {
				height: 20vmin;
				pointer-events: none;
			}
			  
			.App-header {
				text-align: center;
				min-height: 30vh;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				font-size: calc(10px + 2vmin);
				color: white;
			}
			  
			.App-link {
				color: #61dafb;
			}
			.right-panel {
				position:absolute;
				color: #aaa;
			}
		`;
	}

	@property({
		type: Object,
		attribute: false
	})
	userData: UserData | undefined;

	@property({
		type: Object,
		attribute: false
	})
	match: Match | undefined;

	@property({ type: String })
	route: string;
	@property({ type: Object })
	params: any;
	@property({ type: Object })
	query: any;
	@property({ type: Array })
	lastGames: [];
	@property({ type: Array })
	hiScores: [];

	constructor() {
		super();
		this.route = '';
		this.lastGames = [];
		this.hiScores = [];
		const assignUser = ((user: any) => {
			this.userData = user;
		}).bind(this);
		PubSub.subscribe(TOPIC_NAME, ((_msg: any, value: any) => {
			if (!value.authToken) {
				this.userData = undefined;
				if (this.route == 'home')
					(<any>this).navigate("/login");
			} else {
				http.get('/api/user/current', {}).then(resp => resp.json().then(assignUser));
				http.get('/api/hiscores/user/10', {}).then(resp => resp.json().then(((v:any) => this.lastGames = v).bind(this)));
			}
		}).bind(this));
		http.get('/api/hiscores/10', {}).then(resp => resp.json().then(((v:any) => this.hiScores = v).bind(this)));
	}

	router(route: string, params: any, query: any, data: any) {
		this.route = route;
		this.params = params;
		this.query = query;
		console.log(route, params, query, data);
	}

	render() {
		return html`
			<div class="App">
				<header class="App-header">
					<img src="${logo}" class="App-logo" alt="logo" />
					<h2>
						Minesweeper
					</h2>
				</header>

				${
					(this.route == 'home' ? html`<game-container></game-container>`
				  : (this.route == 'login' ? html`<login-form></login-form>`
				  : (this.route == 'register' ? html`<register-form></register-form>`
				  : (this.route == 'activate' ? html`<activate-form code="${this.params.activationCode}"></activate-form>`
				  : html`<h1>Not found</h1>`))))

				}
				<div class="right-panel">
				${this.userData
					? html`
					<div class="records">
						<h2>Last Games</h2>
						<ol>
							${this.lastGames.map((m:any) => html`
								<li>${this.formatDate(m.matchDate)} - Score: ${m.score}</li>
							`)}
						</ol>
					</div>`
					: ''
				}
					<div class="records">
						<h2>Hi-Scores</h2>
						<ol>
							${this.hiScores.map((m:any) => html`
								<li>${m.nickname} - ${this.formatDate(m.matchDate)} - Score: ${m.score}</li>
							`)}
						</ol>
					</div>
				</div>
			</div>
  		`;
	}

	formatDate(dt:string) {
		let d = new Date(dt);
		return dateFormat.format(d);
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'minesweeper-app': MinesweeperApp;
	}
}