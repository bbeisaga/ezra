import { ApplicationConfig, LOCALE_ID } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter } from '@angular/router';
import { ZonaHorariaDefectoService } from './services/zona-horaria-defecto.service';

import { MediaMatcher } from '@angular/cdk/layout';
import { DatePipe } from '@angular/common';
import { HTTP_INTERCEPTORS, provideHttpClient, withFetch } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { routes } from './app.routes';
import { providePrimeNG } from 'primeng/config';


export const  appConfig: ApplicationConfig = {
 
  providers: [ZonaHorariaDefectoService,
    { provide: LOCALE_ID, useValue: 'es-PE' },
    provideAnimationsAsync(),
            providePrimeNG({
            theme: {
                preset: 'Aura' // Cambia 'saga-blue' por el tema que desees usar
            }
        }),
    DatePipe,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    MediaMatcher, 
    provideRouter(routes),
    provideHttpClient(withFetch())
  ],
  }



/* export const appConfig: ApplicationConfig = {
    providers: [
        provideAnimationsAsync(),
        providePrimeNG({
            theme: {
                preset: Aura
            }
        })
    ]
}; */