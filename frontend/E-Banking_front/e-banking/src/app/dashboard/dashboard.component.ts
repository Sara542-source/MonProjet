import { Component } from '@angular/core';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { RouterOutlet } from '@angular/router';
import { ChatbotComponent } from '../chatbot/chatbot.component';
@Component({
  selector: 'app-dashboard',
  imports: [SidebarComponent,RouterOutlet,ChatbotComponent],
  templateUrl: './dashboard.component.html',
  styleUrls : ['./dashboard.component.css']
})
export class DashboardComponent {

}
