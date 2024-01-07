import { Game } from "./Game";

export interface Match {
    id?: number | null;
    game?: Game;
    player1Choice : number;
    player2Choice? : number;
}

export enum Choice {
    ROCK, PAPER, SCISSORS
}