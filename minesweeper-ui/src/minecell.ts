import { LitElement, html, customElement, property, css } from 'lit-element';
import flag from 'url:~/resources/flag.png';
import mine from 'url:~/resources/mine.png';

const MINE_MASK = 0x01;
const CLEARED_MASK = 0x02;
const FLAG_MASK = 0x04;
const EXPL_MASK = 0x08;
const EDGE_MASK = 0xf0;

@customElement("mine-cell")
class MineCell extends LitElement {

	static get styles() {
		return css`
			:host {
				border-top: 1px solid #777;
				border-left: 1px solid #777;
				background-color: #c0c0c0;
				width: 24px;
				height: 24px;
			}
			.exploded {
				background-color: red;
			}
			button {
				border: 2px outset;
				background-color: #c0c0c0;
				padding: 0px;
				width: 24px;
				height: 24px;
			}
			img {
				width: 18px;
			}
			span {
				font-size: 16px;
				width: 24px;
				height: 24px;
				padding-top: 2px;
				text-align: center;
				display: block;
				font-weight: bold;
			}
			.edge1 {
				color: blue;
			}
			.edge2 {
				color: #007f00;
			}
			.edge3 {
				color: red;
			}
			.edge4 {
				color: #003f7f;
			}
			.edge5 {
				color: #7f3f00;
			}
			.edge6 {
				color: #7f7f00;
			}
			.edge7 {
				color: #7f007f;
			}
			.edge8 {
				color: #7f7f7f;
			}
		`;
	}

	@property({
		type: "Number"
	})
	index: number = 0;

	@property({
		type: "Number"
	})
	data: number = 0;

	isNotCleared() {
		return (this.data & CLEARED_MASK) == 0;
	}

	isMine() {
		return (this.data & MINE_MASK) != 0;
	}

	isFlag() {
		return (this.data & FLAG_MASK) != 0;
	}

	isEdge() {
		return (this.data & EDGE_MASK) != 0;
	}

	getEdge() {
		return (this.data & EDGE_MASK) >> 4;
	}

	isExploded() {
		return (this.data & EXPL_MASK) != 0;
	}

	render() {
		return html`
         ${this.isNotCleared() ? html`<button @click="${this.handleClick}" @contextmenu="${this.handleMenu}">${this.isFlag() ? html`<img src="${flag}"/>` : ' '}</button>`
				: (this.isEdge() ? html`<span class="edge${this.getEdge()}">${this.getEdge()}</span>`
					: (this.isMine() ? html`<img class="${this.isExploded()?'exploded':''}" style="margin: 3px" src="${mine}"/>` : ' '))}
    `;
	}

	constructor() {
		super();
	}

	doMove(mark:boolean) {
		let event = new CustomEvent('minemove', {
			detail: {
				mark: mark,
				index: this.index
			}
		});
		this.dispatchEvent(event);
	}

	handleClick() {
		this.doMove(false);
	}

	handleMenu(e: any) {
		e.preventDefault();
		this.doMove(true);
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'mine-cell': MineCell;
	}
}