import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Match } from '../model/match';

@Injectable({
  providedIn: 'root'
})
export class PlayService {
  headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

  constructor(private http: HttpClient) { }

  public play(gameId: Number | undefined | null, match: Match) {
    return this.http.post<Match>(`api/game/${gameId}/match`, match, { headers: this.headers })
  }
}
