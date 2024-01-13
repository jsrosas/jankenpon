import { Component } from '@angular/core';
import { UpperCasePipe, JsonPipe } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card'
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Game } from '../model/Game';
import { map, switchMap, of, delay } from 'rxjs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist } from '@fortawesome/free-solid-svg-icons';
import { faScroll } from '@fortawesome/free-solid-svg-icons';
import { faScissors } from '@fortawesome/free-solid-svg-icons';
import { Choice, Match } from '../model/match';
import { ResultComponent } from '../result/result.component';
import {
  trigger,
  style,
  animate,
  transition,
} from '@angular/animations';
import { GameSelectComponent } from '../game-select/game-select.component';

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
    UpperCasePipe,
    JsonPipe,
    ResultComponent,
    GameSelectComponent
  ],
  templateUrl: './play.component.html',
  styleUrl: './play.component.css',
  animations:[
    trigger('insertResult', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('500ms', style({ opacity: 0 }))
      ])
    ]),
  ]
})
export class PlayComponent {
  game!: Game;
  match!: Match;
  resultMatch: Match | undefined = undefined;
  playerSelection!: number;
  feedback: any = {};
  faHandBackFist = faHandBackFist;
  faScroll = faScroll;
  faScissors = faScissors;
  previousMatches: Match[] =[];
  loading: boolean = false;

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
            matches: []
          }
          return of(game);
        }
        return this.http.get<Game>(`api/game/${id}`);
      })
    ).subscribe({
      next: (game) => {
        this.game = game;
        this.previousMatches = game.matches;
        this.feedback = {};
      },
      error: () => {
        this.feedback = {type: 'warning', message: 'Error loading'};
      }
    });
  }

  public onPlayerSelectionEvent($event: number){
    this.playerSelection = $event;
  }

  public get playerSelectionLabel(){
    return Choice[this.playerSelection];
  }

  async cancel() {
    await this.router.navigate(['/games']);
  }

  save() {
    this.loading = true;
    this.resultMatch = undefined;
    const match: Match = {
      player1Choice: this.playerSelection,
      isTie: false,
    }
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    this.http.post<Match>(`api/game/${this.game.id}/match`,match, {headers: headers}).pipe(delay(700)).subscribe(
      response => {
        this.resultMatch = response;
        this.previousMatches.push(response)
        this.loading = false;
      }
    );
  }
}
