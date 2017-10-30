import { BaseEntity } from './../../shared';

export class InviteCs implements BaseEntity {
    constructor(
        public id?: number,
        public comment?: string,
        public groupId?: number,
        public sentToId?: number,
        public sentFromId?: number,
    ) {
    }
}
