import { NgClass, UpperCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist, faScissors, faScroll } from '@fortawesome/free-solid-svg-icons';
import { GameSelectComponent } from '../game-select/game-select.component';
import { GameStatisticsComponent } from '../game-statistics/game-statistics.component';
import { Game } from '../model/Game';
import { Choice, Match } from '../model/match';
import { WinnerComponent } from '../winner/winner.component';

@Component({
  selector: 'app-result',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonToggleModule,
    FontAwesomeModule,
    UpperCasePipe,
    GameSelectComponent,
    GameStatisticsComponent,
    WinnerComponent,
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

  public get computerSelectionLabel() {
    if (this.match && this.match.player2Choice || this.match?.player2Choice === 0) {
      return Choice[this.match.player2Choice];
    } else {
      return "Unable to find a choice for computer."
    }
  }
}
