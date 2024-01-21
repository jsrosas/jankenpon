import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Game } from '../model/Game';
import { GameStatistics } from '../model/game-statistics';

@Component({
  selector: 'app-game-statistics',
  standalone: true,
  imports: [
    MatCardModule,
  ],
  templateUrl: './game-statistics.component.html',
  styleUrl: './game-statistics.component.css'
})
export class GameStatisticsComponent {
  @Input() statistics?: GameStatistics
}
