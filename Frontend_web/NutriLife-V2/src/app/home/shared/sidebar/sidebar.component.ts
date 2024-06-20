import {  Component,AfterViewInit ,HostListener} from '@angular/core';

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

  }
  close(event: Event): void{
    event.preventDefault();
    const closed: HTMLElement | null = document.querySelector('.sidebar-close-btn'); 
    const sideBar: HTMLElement | null = document.querySelector('.sidebar');
    const contentBody: HTMLElement | null = document.querySelector('.content');

    if (closed && sideBar && contentBody) {
      sideBar.classList.toggle('close');
      contentBody.style.width =  'calc(100% - 60px)' ;
      contentBody.style.left =  '60px';
    }
  }
  open(event: Event): void {
    event.preventDefault();
    const width = window.innerWidth;
    const opend: HTMLElement | null = document.querySelector('.logo-img');
    const sideBar: HTMLElement | null = document.querySelector('.sidebar');
    const contentBody: HTMLElement | null = document.querySelector('.content');
    if (opend && sideBar && contentBody) {
      if(width <= 768){
        sideBar.classList.remove('close');
      }else{
        sideBar.classList.remove('close');
        contentBody.style.width =  'calc(100% - 230px)' ;
        contentBody.style.left =  '230px';
      }
      
    } 
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    event.preventDefault();
    const width = window.innerWidth;
    const sideBar: HTMLElement | null = document.querySelector('.sidebar');
    const contentBody: HTMLElement | null = document.querySelector('.content');

    if (sideBar && contentBody) {
      if (width <= 768) {
        sideBar.classList.add('close');
        contentBody.style.width =  'calc(100% - 60px)' ;
        contentBody.style.left =  '60px';
        
      } else {
        sideBar.classList.remove('close');
        contentBody.style.width =  'calc(100% - 230px)' ;
        contentBody.style.left =  '230px';
      }
    }
  }

  ngOnInit() {
    this.onResize(new Event('resize'));
  }

}
