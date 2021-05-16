import { LitElement, html, customElement, property, css } from 'lit-element';
import './minecell';

@customElement("game-container")
class GameContainer extends LitElement {

	static get styles() {
		return css`
			div.container {
				display: flex;
				flex-wrap: wrap;
				width: 250px;
				border-right: 1px solid #777;
				border-bottom: 1px solid #777;
			}
		`;
	}

	@property({
		type: "Array"
	})
	data: number[];

	render() {
		return html`
			<div class="container">
				${this.data.map((_n:number,i:number) => html`<mine-cell index="${i}" data="${this.data}"></mine-cell>`)}
			</div>
  		`;
	}

	constructor() {
		super();
		this.data = [0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0];
	}

}

declare global {
	interface HTMLElementTagNameMap {
		'game-container': GameContainer;
	}
}