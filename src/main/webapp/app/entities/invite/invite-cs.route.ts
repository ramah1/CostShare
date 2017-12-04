import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InviteCsComponent } from './invite-cs.component';
import { InviteCsDetailComponent } from './invite-cs-detail.component';
import { InviteCsPopupComponent } from './invite-cs-dialog.component';
import { InviteCsDeletePopupComponent } from './invite-cs-delete-dialog.component';

export const inviteRoute: Routes = [
    {
        path: 'invite-cs',
        component: InviteCsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.invite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'invite-cs/:id',
        component: InviteCsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.invite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invitePopupRoute: Routes = [
    {
        path: 'invite-cs-new',
        component: InviteCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.invite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invite-cs-new/:id',
        component: InviteCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.invite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
    ,
    {
        path: 'invite-cs/:id/edit',
        component: InviteCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.invite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invite-cs/:id/delete',
        component: InviteCsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.invite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
