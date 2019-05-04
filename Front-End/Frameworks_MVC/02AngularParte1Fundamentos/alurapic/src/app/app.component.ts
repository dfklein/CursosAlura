import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'alurapic';

    photos = [
        {
            url: 'https://brnodaily.cz/wp-content/uploads/2018/01/Silly_Walk_Gait-e1515188306231.jpg',
            description: 'Silly Walk'
        },
        {
            url: 'https://www.telegraph.co.uk/content/dam/news/2016/09/23/terry3_trans_NvBQzQNjv4Bq3Xh2eiz55rGmgbGmxk0kssD9y2BvVEbg2Ef1y9GLFhc.jpg?imwidth=450',
            description: 'Silly Walk'
        }
    ]

}
