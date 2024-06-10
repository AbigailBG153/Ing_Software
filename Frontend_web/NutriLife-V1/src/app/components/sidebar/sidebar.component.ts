import {  Component,AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements AfterViewInit {
  ngAfterViewInit(){
    const sideLinks: NodeListOf<HTMLAnchorElement> = document.querySelectorAll('.sidebar .side-menu li a:not(.logout)');
    sideLinks.forEach(item => {
      const li = item.parentElement as HTMLLIElement;
      item.addEventListener('click', () => {
          sideLinks.forEach(i => {
              i.parentElement?.classList.remove('link-active');
          });
          li.classList.add('link-active');
        });
    });

    const closed: HTMLElement | null = document.querySelector('.sidebar-close-btn'); 
    const opend: HTMLElement | null = document.querySelector('.logo-img');
    const sideBar: HTMLElement | null = document.querySelector('.sidebar');
    const contentBody: HTMLElement | null = document.querySelector('.content');

    if (closed && opend && sideBar && contentBody) {
        closed.addEventListener('click', () => {
            sideBar.classList.toggle('close');
            contentBody.style.width =  'calc(100% - 60px)' ;
            contentBody.style.left =  '60px';
        });

        opend.addEventListener('click', () => {
            sideBar.classList.remove('close');
            contentBody.style.width =  'calc(100% - 230px)' ;
            contentBody.style.left =  '230px';
        });
    }


  }
  

}
