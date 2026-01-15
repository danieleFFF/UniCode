import { Component, OnInit } from '@angular/core';

interface InfoCard {
  title: string;
  text: string;
  iconPath: string;
}

@Component({
  selector: 'app-info-cards',
  standalone: true,
  imports: [],
  templateUrl: './info-cards.html',
  styleUrl: './info-cards.scss'
})
export class InfoCards implements OnInit {
  tallCard: InfoCard | undefined;
  shortCards: InfoCard[] = [];

  private allInfoCards: InfoCard[] = [
    {
      title: 'Instant Feedback',
      text: 'Challenge yourself with our exercises using the integrated console. Write your solution, run it directly on our platform, and receive instant feedback to easily test and correct your work.',
      iconPath: 'assets/images/fileimage.png'
    },
    {
      title: 'Your Milestones',
      text: 'It\'s not just practice: track your progress with points, earn trophies, and climb the leaderboard to compare your skills.',
      iconPath: 'assets/images/statisticimage.png'
    },
    {
      title: 'Coding Challenges',
      text: 'Enhance your coding skills with practical exercises designed to strengthen techniques, master your current language, or learn multiple new ones.',
      iconPath: 'assets/images/laptopimage.png'
    }
  ];

  ngOnInit(): void {
    this.allInfoCards.forEach(card => {
      if (card.title === 'Instant Feedback') {
        this.tallCard = card;
      } else {
        this.shortCards.push(card);
      }
    });
  }
}
