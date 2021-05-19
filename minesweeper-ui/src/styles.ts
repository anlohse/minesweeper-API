import { css } from 'lit-element'

export const style = css`
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
				color: white;
			}
			input {
				padding: 2px;
				width:calc(100% - 8px);
			}
			button {
				width:100%;
			}
			.error {
				color: red;
			}
			.error h3 {
				margin: 0px;
			}
		`;