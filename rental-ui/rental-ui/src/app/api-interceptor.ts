import { HttpInterceptorFn, HttpRequest, HttpHandlerFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { NgxSpinnerService } from "ngx-spinner";
import { finalize } from "rxjs";


export const APIInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn) => {
  const loadingService = inject(NgxSpinnerService);
  loadingService.show();
  if(!req.url.endsWith('/auth/login') && !req.url.endsWith('/auth/refreshToken')){
    let token  = null;
    if(typeof sessionStorage !== "undefined"){
      token = sessionStorage.getItem("token")?.toString();
    }
    const cloned = req.clone({
      setHeaders: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token,
      },
    });
    return next(cloned).pipe(finalize(() => loadingService.hide()));;
  } else {
    return next(req).pipe(finalize(() => loadingService.hide()));;
  }
};