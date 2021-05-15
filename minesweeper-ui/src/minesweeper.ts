import { LitElement, html, customElement, property } from 'lit-element';

@customElement("minesweeper-app")
class MinesweeperApp extends LitElement {

  @property({
    type: "Array"
  })
  data: [] = [];

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
    'minesweeper-app': MinesweeperApp;
  }
}