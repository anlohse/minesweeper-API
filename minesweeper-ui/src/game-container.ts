import { LitElement, html, customElement, property } from 'lit-element';
import './minecell';
import {Match} from './types';
import http from './http-service';
import face_normal from 'url:~/resources/face_normal.png';
import face_risky from 'url:~/resources/face_risky.png';
import face_dead from 'url:~/resources/face_dead.png';
import face_win from 'url:~/resources/face_win.png';

const LATEST_GAME_ID = 'minesweeper.latestMatchId';

const DIFFS = [
	{width: 9, height: 9, mines: 9},
	{width: 16, height: 16, mines: 40},
	{width: 30, height: 16, mines: 99}
];

function dataToArray(b64:string) : number[] {
	let s = atob(b64);
	let v : number[] = [];
	for (let i = 0; i < s.length; i++)
		v[i] = s.charCodeAt(i);
	return v;
}

const sendMove = async (entity:any, options:any) => {
	options = http.createPostOptions(entity, options);
	return await (await http.post('/api/match', options)).json();
}

@customElement("game-container")
class GameContainer extends LitElement {

	@property({
		type: Object,
		attribute: false
	})
	match?: Match;

	@property({
		type: Array,
		attribute: false
	})
	data: number[];
		
	@property({
		type: Number
	})
	diff: number;

	@property({
		type: Number
	})
	timer: number;

	interval: any;

	@property({
		type: String
	})
	face: any;

	changeDiff(e:any) {
		this.diff = e.target.value;
	}

	startTimer() {
		if (!this.interval) 
			this.interval = setInterval((() => this.timer++).bind(this),1000);
	}

	stopTimer() {
		if (this.interval) {
			clearInterval(this.interval);
			this.interval = undefined;
		}
	}

	assignGame(match: any) {
		if (!match) throw new Error('Match cannot be null');
		let data = dataToArray(match.data);
		this.match = match;
		this.data = data;
	}

	newGame() {
		http.get('/api/match', {params: DIFFS[this.diff]})
			.then(((resp:any) => resp.json().then(((v:any) => {
				this.assignGame(v);
				this.timer = 0;
				this.face = face_normal;
				this.startTimer();
				window.localStorage.setItem(LATEST_GAME_ID, v.id);
			}).bind(this))).bind(this));
	}

	loadGame() {
		let matchId = window.localStorage.getItem(LATEST_GAME_ID);
		if (matchId) {
			http.get('/api/match/'+matchId, {})
				.then(((resp:any) => resp.json().then(((v:any) => {
					this.assignGame(v);
					this.timer = v.timer;
					this.face = face_normal;
					this.startTimer();
				}).bind(this))).bind(this))
				.catch(((_e:any) => {
					this.newGame();
				}).bind(this));
		} else
			this.newGame();
	}

	render() {
		return html`
			<style>
				div.container {
					display: flex;
					flex-wrap: wrap;
					border-right: 1px solid #777;
					border-bottom: 1px solid #777;
					margin-left: auto;
					margin-right: auto;
				}
				.gamehead {
					display: flex;
					align-items: center;
					width: 250px;
					margin-left: auto;
					margin-right: auto;
					margin-bottom: 10px;
				}
				.gamehead div {
					border: 3px inset;
					padding: 6px;
					padding-top: 17px;
					padding-bottom: 17px;
					font-size: 16px;
					width: 48px;
					text-align: right;
					font-weight: bold;
					margin-left: 20px;
					margin-right: 20px;
					color: #00ff00;
				}
				button {
					border: 4px outset;
					background-color: #c0c0c0;
					padding: 0px;
				}
			</style>
			<div>
				<select @change="${this.changeDiff}">
					<option value="0" selected>Easy</option>
					<option value="1">Normal</option>
					<option value="2">Hard</option>
				</select>
			</div>
			<div class="gamehead">
				<div>${this.match?this.match.mines:null}</div>
				<button @click="${this.newGame}"><img src="${this.face}"></button>
				<div>${this.timer}</div>
			</div>
			<div class="container" style="width: ${this.match ? this.match.width * 25 : 250}px;">
				${this.data.map((_n:number,i:number) => html`<mine-cell index="${i}" data="${this.data[i]}" @minemove="${this.handleMove}"></mine-cell>`)}
			</div>
  		`;
	}

	processMove(vo:any) {
		this.assignGame(vo.match);
		switch(vo.moveStatus) {
			case 'NONE':
			case 'EASY':
				this.face = face_normal;
				setTimeout((()=>this.face=face_normal).bind(this),1500);
				break;
			case 'RISKY':
				this.face = face_risky;
				setTimeout((()=>this.face=face_normal).bind(this),1500);
				break;
			case 'GAMEOVER':
				this.face = face_dead;
				this.stopTimer();
				window.localStorage.setItem(LATEST_GAME_ID, '');
				break;
			case 'WIN':
				this.face = face_win;
				this.stopTimer();
				window.localStorage.setItem(LATEST_GAME_ID, '');
				break;
		}
}

	handleMove(e:any) {
		if (this.match) {
			let x = e.detail.index % this.match.width;
			let y = Math.floor(e.detail.index / this.match.width);
			sendMove({matchId: this.match.id, x, y, mark: e.detail.mark, timer: this.timer},{})
				.then(this.processMove.bind(this));
		}
	}

	constructor() {
		super();
		this.data = [];
		this.diff = 0;
		this.timer = 0;
		this.face = face_normal;
	}

	connectedCallback() {
		super.connectedCallback();
		setTimeout((() => this.loadGame()).bind(this), 1);
	}

	disconnectedCallback() {
		super.disconnectedCallback();
		this.stopTimer();
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'game-container': GameContainer;
	}
}