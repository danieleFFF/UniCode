import {Component}  from '@angular/core';
import {PodiumItem} from '../podiumItem/podiumItem';

@Component({
    selector: 'app-podium',
    imports: [PodiumItem] ,
    templateUrl: './podium.html',
    styleUrls: ['./podium.scss' ,'../podiumItem/podiumItem.scss']
})

export class Podium {

}
