import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CostshareSharedModule } from '../../shared';
import {
    InviteCsService,
    InviteCsPopupService,
    InviteCsComponent,
    InviteCsDetailComponent,
    InviteCsDialogComponent,
    InviteCsPopupComponent,
    InviteCsDeletePopupComponent,
    InviteCsDeleteDialogComponent,
    inviteRoute,
    invitePopupRoute,
} from './';

const ENTITY_STATES = [
    ...inviteRoute,
    ...invitePopupRoute,
];

@NgModule({
    imports: [
        CostshareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InviteCsComponent,
        InviteCsDetailComponent,
        InviteCsDialogComponent,
        InviteCsDeleteDialogComponent,
        InviteCsPopupComponent,
        InviteCsDeletePopupComponent,
    ],
    entryComponents: [
        InviteCsComponent,
        InviteCsDialogComponent,
        InviteCsPopupComponent,
        InviteCsDeleteDialogComponent,
        InviteCsDeletePopupComponent,
    ],
    providers: [
        InviteCsService,
        InviteCsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CostshareInviteCsModule {}
