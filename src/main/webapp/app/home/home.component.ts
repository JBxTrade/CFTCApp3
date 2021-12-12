import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { IMain } from 'app/entities/main/main.model';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MainService } from 'app/entities/main/service/main.service';
import { HttpResponse } from '@angular/common/http';
import { MainDeleteDialogComponent } from 'app/entities/main/delete/main-delete-dialog.component';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  mains?: IMain[];
  mainsa?: IMain[];
  mainsr?: IMain[];

  constructor(
    private accountService: AccountService,
    private router: Router,
    protected modalService: NgbModal,
    protected translateService: TranslateService,
    protected mainService: MainService
  ) {}

  getlanguage(): boolean {
    const language = this.translateService.currentLang;

    if (language === 'fr') {
      return true;
    } else {
      return false;
    }
  }

  loada(): void {
    this.mainService.geta().subscribe((res: HttpResponse<IMain[]>) => (this.mainsa = res.body ?? []));
  }

  loadr(): void {
    this.mainService.getr().subscribe((res: HttpResponse<IMain[]>) => (this.mainsr = res.body ?? []));
  }

  reload(): void {
    this.loada();
    this.loadr();
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => ((this.account = account), this.reload()));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  delete(main: IMain): void {
    const modalRef = this.modalService.open(MainDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.main = main;
  }
}
