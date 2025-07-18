import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import localeEsPE from '@angular/common/locales/es-PE';
import { registerLocaleData } from '@angular/common';


bootstrapApplication(AppComponent, appConfig)
.catch(err => console.error(err));

registerLocaleData(localeEsPE);

