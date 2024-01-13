import { Component, Input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card'
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { Choice, Match } from '../model/match';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist } from '@fortawesome/free-solid-svg-icons';
import { faScroll } from '@fortawesome/free-solid-svg-icons';
import { faScissors } from '@fortawesome/free-solid-svg-icons';
import { UpperCasePipe, JsonPipe } from '@angular/common';


@Component({
  selector: 'app-result',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonToggleModule,
    FontAwesomeModule,
    UpperCasePipe,
  ],
  templateUrl: './result.component.html',
  styleUrl: './result.component.css',
  animations:[

  ]
})
export class ResultComponent {
  @Input() match?: Match;
  faHandBackFist = faHandBackFist;
  faScroll = faScroll;
  faScissors = faScissors;

  public get computerSelectionLabel(){
    if(this.match && this.match.player2Choice || this.match?.player2Choice === 0){
      return Choice[this.match.player2Choice];
    }else{
      return "Unable to find a choice for computer."
    }
  }
}
