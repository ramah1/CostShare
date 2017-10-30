import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CSUserCsComponent } from './cs-user-cs.component';
import { CSUserCsDetailComponent } from './cs-user-cs-detail.component';
import { CSUserCsPopupComponent } from './cs-user-cs-dialog.component';
import { CSUserCsDeletePopupComponent } from './cs-user-cs-delete-dialog.component';

export const cSUserRoute: Routes = [
    {
        path: 'cs-user-cs',
        component: CSUserCsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cs-user-cs/:id',
        component: CSUserCsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cSUserPopupRoute: Routes = [
    {
        path: 'cs-user-cs-new',
        component: CSUserCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cs-user-cs/:id/edit',
        component: CSUserCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cs-user-cs/:id/delete',
        component: CSUserCsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
