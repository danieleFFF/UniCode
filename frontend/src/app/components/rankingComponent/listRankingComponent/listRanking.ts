import  {Component} from '@angular/core';
import {ItemRanking} from '../ItemRankingComponent/itemRanking';

@Component({
    selector: 'app-listRanking',
    imports: [ItemRanking],
    templateUrl: './listRanking.html',
    styleUrls: ['./listRanking.scss' , '../ItemRankingComponent/itemRanking.scss']
})
export class ListRankingComponent { }
