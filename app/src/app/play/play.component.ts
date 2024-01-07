import { Component } from '@angular/core';
import { UpperCasePipe } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card'
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Game } from '../model/Game';
import { map, switchMap, of } from 'rxjs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist } from '@fortawesome/free-solid-svg-icons';
import { faScroll } from '@fortawesome/free-solid-svg-icons';
import { faScissors } from '@fortawesome/free-solid-svg-icons';
import { Choice, Match } from '../model/match';

@Component({
  selector: 'app-play',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatButtonToggleModule,
    RouterLink,
    FontAwesomeModule,
    UpperCasePipe
  ],
  templateUrl: './play.component.html',
  styleUrl: './play.component.css'
})
export class PlayComponent {
  game!: Game;
  match!: Match;
  playerSelection!: number;
  feedback: any = {};
  faHandBackFist = faHandBackFist;
  faScroll = faScroll;
  faScissors = faScissors;

  constructor(private route: ActivatedRoute, private router: Router,
    private http: HttpClient) {
  }

  ngOnInit() {
    this.route.params.pipe(
      map(p => p['id']),
      switchMap(id => {
        if (id === 'new') {
          const game: Game = {
            name: '',
          }
          return of(game);
        }
        return this.http.get<Game>(`api/game/${id}`);
      })
    ).subscribe({
      next: (game) => {
        this.game = game;
        console.log(game);
        this.feedback = {};
      },
      error: () => {
        this.feedback = {type: 'warning', message: 'Error loading'};
      }
    });
  }

  public get playerSelectionLabel(){
    return Choice[this.playerSelection];
  }

  async cancel() {
    await this.router.navigate(['/games']);
  }

  save() {
    console.log(this.playerSelection);
    const match: Match = {
      player1Choice: this.playerSelection
    }
    console.log({player1Choice: this.playerSelection});
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    this.http.post<Match>(`api/game/${this.game.id}/match`,match, {headers: headers}).subscribe(
      response => console.log(response)
    );
  }
}
