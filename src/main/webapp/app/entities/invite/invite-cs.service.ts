import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { InviteCs } from './invite-cs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InviteCsService {

    private resourceUrl = SERVER_API_URL + 'api/invites';

    constructor(private http: Http) { }

    create(invite: InviteCs): Observable<InviteCs> {
        const copy = this.convert(invite);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(invite: InviteCs): Observable<InviteCs> {
        const copy = this.convert(invite);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<InviteCs> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to InviteCs.
     */
    private convertItemFromServer(json: any): InviteCs {
        const entity: InviteCs = Object.assign(new InviteCs(), json);
        return entity;
    }

    /**
     * Convert a InviteCs to a JSON which can be sent to the server.
     */
    private convert(invite: InviteCs): InviteCs {
        const copy: InviteCs = Object.assign({}, invite);
        return copy;
    }
}
