import { Player } from "./player";

export interface Game {
  id?: number | null;
  name: string;
  player1: Player;
  player2?: Player;
}
