import { BaseEntity } from './../../shared';

export class CSUserCs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public userNameId?: number,
        public paids?: BaseEntity[],
        public userCosts?: BaseEntity[],
        public receivedInvites?: BaseEntity[],
        public sentInvites?: BaseEntity[],
        public groups?: BaseEntity[],
        public adminOfs?: BaseEntity[],
    ) {
    }
}
