import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Game } from '../model/Game';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  public getGames() {
    return this.http.get<Game[]>('api/games');
  }

  public getGame(gameId: Number) {
    return this.http.get<Game>(`api/game/${gameId}`);
  }

  public saveGame(game: Game, method: "put" | "post", gameId: Number | null | undefined) {
    return this.http[method](`/api/game${gameId ? '/' + gameId : ''}`, game)
  }
}
