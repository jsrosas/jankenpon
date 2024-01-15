import { AsyncPipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable, Subscription, catchError, map, of, switchMap, tap, throwError } from 'rxjs';
import { Game } from '../model/Game';
import { Player } from '../model/player';
import { GameService } from '../service/game.service';

@Component({
  selector: 'app-game-edit',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    RouterLink,
    AsyncPipe
  ],
  templateUrl: './game-edit.component.html',
  styleUrl: './game-edit.component.css'
})

export class GameEditComponent implements OnDestroy{
  subscription: Subscription = new Subscription;
  player!: Player;
  feedback: any = {};
  game$: Observable<Game> = this.route.params.pipe(
    map(p => p['id']),
    switchMap(id => {
      if (id === 'new') {
        this.player = {
          name: '',
          defaultComputer: false
        }
        const game: Game = {
          name: '',
          matches: [],
        }
        return of(game);
      }
      return this.gameService.getGame(id).pipe(
        tap((game: Game) => {
          const player = game.players?.find((player: Player) => !player.defaultComputer);
          this.player = player ?? {name:'', defaultComputer: false}
          this.feedback = {};
        })
      );
    }),
    catchError(error => {
      this.feedback = { type: 'warning', message: 'Error loading' };
      return throwError(() => console.log(error));
    })
  );

  constructor(
    private route: ActivatedRoute, 
    private router: Router, 
    private gameService: GameService) {}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  save(game: Game) {
    const id = game.id;
    game.players = [this.player]
    const method = id ? 'put' : 'post';
    this.subscription = this.gameService.saveGame(game, method, id).subscribe({
      next: () => {
        this.feedback = {type: 'success', message: 'Save was successful!'};
        setTimeout(async () => {
          await this.router.navigate(['/games']);
        }, 1000);
      },
      error: () => {
        this.feedback = {type: 'error', message: 'Error saving'};
      }
    });
  }

  async cancel() {
    await this.router.navigate(['/games']);
  }
}
