import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
// import { NavComponent } from './components/nav/nav.component';
import { ToolbarComponent } from "./components/toolbar/toolbar.component";
import { TabNavComponent } from './components/tab-nav/tab-nav.component';
import { FooterComponent } from "./components/footer/footer.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ToolbarComponent, TabNavComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
