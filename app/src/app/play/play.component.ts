import {
  animate,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { AsyncPipe, UpperCasePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist, faScissors, faScroll } from '@fortawesome/free-solid-svg-icons';
import { Observable, Subscription, catchError, delay, map, switchMap, tap, throwError } from 'rxjs';
import { GameSelectComponent } from '../game-select/game-select.component';
import { Game } from '../model/Game';
import { Choice, Match } from '../model/match';
import { ResultComponent } from '../result/result.component';
import { GameService } from '../service/game.service';
import { PlayService } from '../service/play.service';

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
    AsyncPipe,
    ResultComponent,
    GameSelectComponent
  ],
  templateUrl: './play.component.html',
  styleUrl: './play.component.css',
  animations: [
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
export class PlayComponent implements OnDestroy{
  game$: Observable<Game> = this.route.params.pipe(
    map(p => p['id']),
    switchMap(id => this.gameService.getGame(id)),
    tap(game => {
      this.previousMatches = game.matches;
      this.feedback = {};
    }),
    catchError(error => {
      this.feedback = { type: 'warning', message: 'Error loading' };
      return throwError(() => console.log(error));
    })
  );
  match!: Match;
  resultMatch: Match | undefined = undefined;
  playerSelection!: number;
  feedback: any = {};
  faHandBackFist = faHandBackFist;
  faScroll = faScroll;
  faScissors = faScissors;
  previousMatches: Match[] = [];
  loading: boolean = false;
  subscription: Subscription = new Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService,
    private playService: PlayService) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public onPlayerSelectionEvent($event: number) {
    this.playerSelection = $event;
  }

  public get playerSelectionLabel() {
    return Choice[this.playerSelection];
  }

  async cancel() {
    await this.router.navigate(['/games']);
  }

  save(game: Game) {
    this.loading = true;
    this.resultMatch = undefined;
    const match: Match = {
      player1Choice: this.playerSelection,
      tie: false,
    }
    this.subscription = this.playService.play(game.id, match).pipe(delay(700)).subscribe(
      response => {
        this.resultMatch = response;
        this.previousMatches.push(response)
        this.loading = false;
      }
    );
  }
}
