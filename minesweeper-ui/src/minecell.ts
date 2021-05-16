import { LitElement, html, customElement, property, css } from 'lit-element';

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
			button {
				border: 2px outset;
				background-color: #c0c0c0;
			}
		`;
	}

    @property({
        type: "Number"
    })
    index: Number = 0;

    @property({
        type: "Array"
    })
    data: number[] = [];

    render() {
        return html`
         
    `;
    }

    constructor() {
        super();
    }

}

declare global {
    interface HTMLElementTagNameMap {
        'mine-cell': MineCell;
    }
}