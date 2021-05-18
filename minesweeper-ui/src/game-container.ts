import { LitElement, html, customElement, property } from 'lit-element';
import './minecell';
import {Match} from './types';
import http from './http-service';

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

	changeDiff(e:any) {
		this.diff = e.target.value;
	}

	assignGame(match: any) {
		if (!match) throw new Error('Match cannot be null');
		let data = dataToArray(match.data);
		this.match = match;
		this.data = data;
	}

	newGame() {
		http.get('/api/match', {params: DIFFS[this.diff]})
			.then(((resp:any) => resp.json().then(((v:any) => this.assignGame(v)).bind(this))).bind(this));
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
			</style>
			<select @change="${this.changeDiff}">
				<option value="0" selected>Easy</option>
				<option value="1">Normal</option>
				<option value="2">Hard</option>
			</select>
			<button @click="${this.newGame}">New Game</button>
			<div class="container" style="width: ${this.match ? this.match.width * 25 : 250}px;">
				${this.data.map((_n:number,i:number) => html`<mine-cell index="${i}" data="${this.data[i]}" @minemove="${this.handleMove}"></mine-cell>`)}
			</div>
  		`;
	}

	handleMove(e:any) {
		if (this.match) {
			let x = e.detail.index % this.match.width;
			let y = Math.floor(e.detail.index / this.match.width);
			sendMove({matchId: this.match.id, x, y, mark: e.detail.mark},{})
				.then(((v:any) => this.assignGame(v.match)).bind(this));
		}
	}

	constructor() {
		super();
		this.data = [];
		this.diff = 0;
	}

	connectedCallback() {
		super.connectedCallback();
		setTimeout((() => this.newGame()).bind(this), 1);
	}

	disconnectedCallback() {
		super.disconnectedCallback();
		
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'game-container': GameContainer;
	}
}