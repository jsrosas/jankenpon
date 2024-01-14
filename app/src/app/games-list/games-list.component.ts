import { Component, } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { Game } from '../model/Game';
import { GameService } from '../service/game.service';

@Component({
  selector: 'app-games-list',
  standalone: true,
  imports: [
    CommonModule, 
    RouterLink, 
    MatButtonModule, 
    MatTableModule, 
    MatIconModule, 
  ],
  templateUrl: './games-list.component.html',
  styleUrl: './games-list.component.css'
})
export class GamesListComponent {
  title = 'Game List';
  loading = true;
  games: Game[] = [];
  displayedColumns = ['id', 'players', 'actions'];
  feedback: any = {};

  constructor(private gameService: GameService ) {
  }

  ngOnInit() {
    this.loading = true;
    this.gameService.getGames().subscribe((data: Game[]) => {
      this.games = data;
      this.loading = false;
      this.feedback = {};
    });
  }
}
