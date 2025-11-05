import { Component } from '@angular/core';
import { NgForOf } from '@angular/common';

interface InfoCard {
  title: string;
  text: string;
}

@Component({
  selector: 'app-info-cards',
  standalone: true,
  imports: [NgForOf],
  templateUrl: './info-cards.html',
  styleUrl: './info-cards.scss'
})
export class InfoCards {
  infoCards: InfoCard[] = [
    {
      title: 'Instant Feedback',
      text: 'Challenge yourself with our exercises using the integrated code editor. Write your solution, run it directly on our platform, and receive instant feedback to easily test and correct your work.'
    },
    {
      title: 'Your Milestones',
      text: 'It\'s not just practice: track your progress with stats, earn trophies, and climb the leaderboard to compare your skills.'
    },
    {
      title: 'Coding Challenges',
      text: 'Enhance your coding skills with practical exercises designed to strengthen techniques, master your current language, or learn multiple new ones.'
    }
  ];
}