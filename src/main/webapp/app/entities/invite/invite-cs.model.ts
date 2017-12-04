import { BaseEntity } from './../../shared';

export class InviteCs implements BaseEntity {
    constructor(
        public id?: number,
        public comment?: string,
        public accepted?: boolean,
        public groupId?: number,
        public groupName?: string,
        public sentToId?: number,
        public sentFromId?: number,
    ) {
        this.accepted = false;
    }
}
