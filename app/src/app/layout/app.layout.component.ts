import {AfterViewInit, Component, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {animate, group, query, style, transition, trigger} from '@angular/animations';
import {Subscription} from 'rxjs';

enum MenuOrientation {
  STATIC,
  OVERLAY
}

@Component({
  selector: 'app-layout',
  templateUrl: './app.layout.component.html',
  styleUrls: ['./app.layout.component.css'],
  animations: [
    trigger('slideInOut', [
      transition('* => *, :enter', [
        query(':enter, :leave', style({position: 'absolute', width: '75%'}), {optional: true}),
        query(':enter', style({transform: 'translateX(-100%)'}), {optional: true}),
        query(':leave', style({transform: 'translateX(0)'}), {optional: true}),

        group([
          query(':leave', [
            animate('500ms ease-in-out', style({
              transform: 'translateX(100%)'
            }))
          ], {optional: true}),
          query(':enter', [
            animate('500ms ease-in-out', style({
              transform: 'translateX(0)'
            }))
          ], {optional: true})
        ])
      ])
    ])
  ]
})
export class AppLayoutComponent implements OnInit, AfterViewInit, OnDestroy {
  activeTabIndex = 0;
  sidebarActive = true;
  layoutMode: MenuOrientation = MenuOrientation.STATIC;
  darkMenu = true;
  topbarMenuActive: boolean;
  sidebarClick: boolean;
  topbarItemClick: boolean;
  activeTopbarItem: any;
  documentClickListener: Function;
  private topicSubscription: Subscription;

  constructor(public renderer: Renderer2) {
  }

  ngOnInit(): void {
  }

  triggerAnimation(outlet) {
    return outlet.activatedRouteData.animation || null;
  }

  ngAfterViewInit() {
    this.documentClickListener = this.renderer.listen('body', 'click', (event) => {
      if (!this.topbarItemClick) {
        this.activeTopbarItem = null;
        this.topbarMenuActive = false;
      }

      if (!this.sidebarClick && (this.overlay || !this.isDesktop())) {
        this.sidebarActive = false;
      }

      this.topbarItemClick = false;
      this.sidebarClick = false;
    });
  }

  onTabClick(event, index: number) {
    if (index === 4) {
      this.activeTabIndex = index;
      this.sidebarActive = false;
      event.preventDefault();
      return;
    }
    if (this.activeTabIndex === index) {
      this.sidebarActive = !this.sidebarActive;
    } else {
      this.activeTabIndex = index;
      this.sidebarActive = true;
    }

    event.preventDefault();
  }

  closeSidebar(event) {
    this.sidebarActive = false;
    event.preventDefault();
  }

  onSidebarClick(event) {
    this.sidebarClick = true;
  }

  onTopbarMenuButtonClick(event) {
    this.topbarItemClick = true;
    this.topbarMenuActive = !this.topbarMenuActive;

    event.preventDefault();
  }

  onTopbarItemClick(event, item) {
    this.topbarItemClick = true;

    if (this.activeTopbarItem === item) {
      this.activeTopbarItem = null;
    } else {
      this.activeTopbarItem = item;
    }

    event.preventDefault();
  }

  onTopbarSubItemClick(event) {
    event.preventDefault();
  }

  get overlay(): boolean {
    return this.layoutMode === MenuOrientation.OVERLAY;
  }

  isDesktop() {
    return window.innerWidth > 1024;
  }

  ngOnDestroy() {
    if (this.documentClickListener) {
      this.documentClickListener();
    }
  }

}
