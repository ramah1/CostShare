import { BaseEntity } from './../../shared';

export class CostCs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public sum?: number,
        public userCosts?: BaseEntity[],
        public paidById?: number,
        public groupId?: number,
    ) {
    }
}
