import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CostshareSharedModule } from '../../shared';
import {
    CSGroupCsService,
    CSGroupCsPopupService,
    CSGroupCsComponent,
    CSGroupCsDetailComponent,
    CSGroupCsDialogComponent,
    CSGroupCsPopupComponent,
    CSGroupCsDeletePopupComponent,
    CSGroupCsDeleteDialogComponent,
    cSGroupRoute,
    cSGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cSGroupRoute,
    ...cSGroupPopupRoute,
];

@NgModule({
    imports: [
        CostshareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CSGroupCsComponent,
        CSGroupCsDetailComponent,
        CSGroupCsDialogComponent,
        CSGroupCsDeleteDialogComponent,
        CSGroupCsPopupComponent,
        CSGroupCsDeletePopupComponent,
    ],
    entryComponents: [
        CSGroupCsComponent,
        CSGroupCsDialogComponent,
        CSGroupCsPopupComponent,
        CSGroupCsDeleteDialogComponent,
        CSGroupCsDeletePopupComponent,
    ],
    providers: [
        CSGroupCsService,
        CSGroupCsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CostshareCSGroupCsModule {}
