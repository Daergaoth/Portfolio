import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import {enableProdMode, importProvidersFrom} from '@angular/core';
import {BrowserAnimationsModule, provideAnimations} from '@angular/platform-browser/animations';

import { AppModule } from './app/app.module';
import {environment} from "./environments/environment";
import {AppComponent} from "./app/app.component";
import {BrowserModule} from "@angular/platform-browser";

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

/*bootstrapApplication(AppComponent,{
  providers:[
    importProvidersFrom([BrowserModule, BrowserAnimationsModule])
  ]
});*/

/*bootstrapApplication(AppComponent, {
  providers: [
    provideAnimations()
  ]
});*/
