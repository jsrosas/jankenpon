import { Match } from "./match";
import { Player } from "./player";

export interface Game {
  id?: number | null;
  name: string;
  players?: Player[];
  matches?: Match[]
}
