import Handlebars from 'handlebars'
import { Component } from 'react'
import './App.css'

var oldHref = "http://localhost:3000"
var turn = 0

interface Cell {
  text: String;
  clazz: String;
  link: String;
}

interface Cells {
  cells: Array<Cell>,
  template: HandlebarsTemplateDelegate<any>,
  instructions: String
}

interface Props {
}

class App extends Component<Props, Cells> {
  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [
        { text: "", clazz: "playable", link: "/play?x=0&y=0" },
        { text: "", clazz: "playable", link: "/play?x=1&y=0" },
        { text: "", clazz: "playable", link: "/play?x=2&y=0" },
        { text: "", clazz: "playable", link: "/play?x=0&y=1" },
        { text: "", clazz: "playable", link: "/play?x=1&y=1" },
        { text: "", clazz: "playable", link: "/play?x=2&y=1" },
        { text: "", clazz: "playable", link: "/play?x=0&y=2" },
        { text: "", clazz: "playable", link: "/play?x=1&y=2" },
        { text: "", clazz: "playable", link: "/play?x=2&y=2" },
      ],
      template: this.loadTemplate(),
      instructions: "It is Player 0's turn"
    };
  }

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  convertToCell(p: any): Array<Cell> {
    const newCells: Array<Cell> = [];
    for (var i = 0; i < p["cells"].length; i++) {
      var c: Cell = {
        text: p["cells"][i]["text"],
        clazz: p["cells"][i]["clazz"],
        link: p["cells"][i]["link"],
      };
      newCells.push(c);
    }

    return newCells;
  }

  getTurn(p: any): String {
    return p["turn"]
  }

  getWinner(p: any): String | undefined {
    return p["winner"]
  }

  getInstr(turn: String, winner: String | undefined) {
    if (winner === undefined) return "It is Player " + turn + "'s turn"
    else return "Player " + winner + " wins!"
  }

  async newGame() {
    const response = await fetch("newgame");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "It is Player 0's turn"})
  }

  async play(url: String) {
    const href = "play?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr })
  }

  async undo() {
    const response = await fetch("undo");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr })
  }

  async switch() {
    if (
      window.location.href === "http://localhost:3000/newgame" &&
      oldHref !== window.location.href
    ) {
      this.newGame();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/play" &&
      oldHref !== window.location.href
    ) {
      this.play(window.location.href);
      oldHref = window.location.href;
    } else if (
      window.location.href === "http://localhost:3000/undo" &&
      oldHref !== window.location.href
    ) {
      this.undo();
      oldHref = window.location.href;
    }
  };

  render() {
    this.switch()
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ cells: this.state.cells, instructions: this.state.instructions }),
          }}
        />
      </div>
    )
  };
};

export default App;
