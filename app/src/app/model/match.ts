import { Game } from "./Game";
import { GameStatistics } from "./game-statistics";

export interface Match {
    id?: number | null;
    winnerId?: number | null;
    game?: Game;
    player1Choice : number;
    player2Choice? : number;
    isTie: boolean;
    gameStatistics?: GameStatistics;
}

export enum Choice {
    ROCK, PAPER, SCISSORS
}