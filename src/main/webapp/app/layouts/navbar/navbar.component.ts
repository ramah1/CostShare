import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from '../../shared';
import { InviteCsService} from "../../entities/invite/invite-cs.service";

import { VERSION } from '../../app.constants';
import {InviteCs} from "../../entities/invite/invite-cs.model";
import {Subscription} from "rxjs/Subscription";
import {CSUserCsService} from "../../entities/c-s-user/cs-user-cs.service";

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit, OnChanges {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    loginSubscription: Subscription;
    userInvites: InviteCs[] = [];
    private inviteChangeSubscription: Subscription;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private inviteService: InviteCsService,
        private jhiEventManager:JhiEventManager,
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
       this.loginSubscription = this.jhiEventManager.subscribe("authenticationSuccess",(response) => this.getAllInvitesForCurrenUser());
       this.inviteChangeSubscription = this.jhiEventManager.subscribe("inviteListModification", (response) => this.getAllInvitesForCurrenUser());
       this.getAllInvitesForCurrenUser();
    }
    ngOnChanges(changes: SimpleChanges): void {
        this.getAllInvitesForCurrenUser();
    }

     getAllInvitesForCurrenUser() {
        this.inviteService.getAllInvitesForUser().subscribe(res => {
            this.userInvites.length = 0;
            for (let invite of res.json){
                if(!invite.accepted){
                    this.userInvites.push(invite);
                }
            }
        });
    }

    changeLanguage(languageKey: string) {
      this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
