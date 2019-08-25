import { Component } from '@angular/core';
import { DictionaryService } from './dictionary.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'wd-web';
  selectedLimit: number = 10;
  options = [10, 20, 50, 100, 500, 1000];
  matchingWords: string[]

  constructor(private dictionaryService: DictionaryService) {
    this.matchingWords = null;
  }

  search(e: any) {
    this.dictionaryService.searchWord(e.target.value, this.selectedLimit).subscribe(response => {
      this.matchingWords = response.matchingWords;
     },
     err => alert('Error! ' + err.error)
    );
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.dictionaryService.uploadFile(file).subscribe(response => {
        alert('Uploaded successfully')
      },
      err => {
        console.log(err)
        alert('Error! ' + err.error)
      }
      )
    }
  }
}
