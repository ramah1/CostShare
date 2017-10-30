import { BaseEntity } from './../../shared';

export class CSUserCs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public userNameId?: number,
        public userCosts?: BaseEntity[],
        public receivedInvites?: BaseEntity[],
        public sentInvites?: BaseEntity[],
        public paidId?: number,
        public groups?: BaseEntity[],
        public adminOfs?: BaseEntity[],
    ) {
    }
}
