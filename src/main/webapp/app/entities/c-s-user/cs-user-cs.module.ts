import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CostshareSharedModule } from '../../shared';
import { CostshareAdminModule } from '../../admin/admin.module';
import {
    CSUserCsService,
    CSUserCsPopupService,
    CSUserCsComponent,
    CSUserCsDetailComponent,
    CSUserCsDialogComponent,
    CSUserCsPopupComponent,
    CSUserCsDeletePopupComponent,
    CSUserCsDeleteDialogComponent,
    cSUserRoute,
    cSUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cSUserRoute,
    ...cSUserPopupRoute,
];

@NgModule({
    imports: [
        CostshareSharedModule,
        CostshareAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CSUserCsComponent,
        CSUserCsDetailComponent,
        CSUserCsDialogComponent,
        CSUserCsDeleteDialogComponent,
        CSUserCsPopupComponent,
        CSUserCsDeletePopupComponent,
    ],
    entryComponents: [
        CSUserCsComponent,
        CSUserCsDialogComponent,
        CSUserCsPopupComponent,
        CSUserCsDeleteDialogComponent,
        CSUserCsDeletePopupComponent,
    ],
    providers: [
        CSUserCsService,
        CSUserCsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CostshareCSUserCsModule {}
