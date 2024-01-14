import { JsonPipe, UpperCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist, faScissors, faScroll } from '@fortawesome/free-solid-svg-icons';
import { GameSelectComponent } from '../game-select/game-select.component';
import { GameStatisticsComponent } from '../game-statistics/game-statistics.component';
import { Game } from '../model/Game';
import { Choice, Match } from '../model/match';
import { Player } from '../model/player';

@Component({
  selector: 'app-result',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonToggleModule,
    FontAwesomeModule,
    UpperCasePipe,
    JsonPipe,
    GameSelectComponent,
    GameStatisticsComponent,
  ],
  templateUrl: './result.component.html',
  styleUrl: './result.component.css',
  animations: [

  ]
})
export class ResultComponent {
  @Input() match?: Match;
  @Input() game?: Game;
  faHandBackFist = faHandBackFist;
  faScroll = faScroll;
  faScissors = faScissors;
  winner?: Player;

  public get computerSelectionLabel() {
    if (this.match && this.match.player2Choice || this.match?.player2Choice === 0) {
      return Choice[this.match.player2Choice];
    } else {
      return "Unable to find a choice for computer."
    }
  }

  public get resultLabel() {
    this.winner = this.game?.players?.find(player => player.id === this.match?.winnerId);
    return this.winner ? `Winner is ${this.winner?.name}` : 'It is a Tie';
  }
}
