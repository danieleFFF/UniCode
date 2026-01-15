import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CourseDataService, TopicFromDB } from '../../services/course-data';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Navbar } from "../../layout/navbar/navbar";

@Component({
  selector:'app-theory',
  standalone:true,
  imports:[CommonModule, Navbar],
  templateUrl:'./theory.html',
  styleUrl:'./theory.scss'
})

export class Theory implements OnInit{
  public currentTopic:TopicFromDB|null=null;
  public topicDifficulty:'Easy'|'Medium'|'Hard'|undefined;
  public errorMessage:string|null=null;
  public safeVideoUrl:SafeResourceUrl|null=null;
  public courseTitle:string='';

  constructor(
    private route:ActivatedRoute,
    private router:Router,
    public courseService:CourseDataService,
    private sanitizer:DomSanitizer
  ){}

  ngOnInit():void{
    this.route.paramMap.subscribe(params=>{
      const languageKey=params.get('language');
      const topicIndexStr=params.get('topicIndex');
      if(languageKey&&topicIndexStr){
        const topicIndex=+topicIndexStr;
        const courseInfo=this.courseService.getCourseInfo(languageKey);
        if(courseInfo){
          this.courseTitle=courseInfo.title;
        }
        this.loadTopicContent(languageKey,topicIndex);
      }
      else{
        this.router.navigate(['/']);
      }
    });
  }

  private loadTopicContent(key:string, index:number):void{
    this.courseService.getTopic(key,index).subscribe({
      next:(topic)=>{
        if(topic){
          this.currentTopic=topic;
          this.errorMessage=null;
          if(topic.urlVideo){
            const videoId=this.extractVideoId(topic.urlVideo);
            if(videoId){
              const embedUrl=`https://www.youtube.com/embed/${videoId}`;
              this.safeVideoUrl=this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
            }
            else{
              this.safeVideoUrl=null;
            }
          }
          else{
            this.safeVideoUrl=null;
          }
          this.topicDifficulty=topic.difficulty;
        }
        else{
          this.errorMessage="Topic not found.";
          this.currentTopic=null;
        }
      },
      error:(err)=>{
        console.error(err);
        this.errorMessage="Error loading topic from server.";
      }
    });
  }

  private extractVideoId(url:string):string|null{ //serve ad estrarre l'id del video youtube (che è una stringa di 11 caratteri) da un url in input
    if(!url){
      return null;
    }
    const regExp=/^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
    const match=url.match(regExp);
    if(match&&match[2].length===11){
      return match[2];
    }
    else{
      return null;
    }
  }
}
