import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MyListsResponse} from "../../response/myListsResponse";
import {NewListRequest} from "../../request/NewListRequest";

const baseUrl = "http://localhost:8080/listings/";

@Injectable({
  providedIn: 'root'
})
export class ListingService {

  constructor(private http: HttpClient) {
  }

  showMyLists(): Observable<MyListsResponse> {
    return this.http.get(baseUrl + "getListings");
  }

  getListById(listId: number): Observable<MyListsResponse> {
    return this.http.get(baseUrl + "getList/" + listId);
  }

  addList(newList: NewListRequest): Observable<MyListsResponse> {
    return this.http.post(baseUrl + "add/", newList);
  }

  editList(listEdited: NewListRequest, listId: number): Observable<MyListsResponse> {
    return this.http.put(baseUrl + "edit/" + listId, listEdited);
  }

  deleteList(listId: number): Observable<any> {
    return this.http.delete(baseUrl + "delete/" + listId);
  }
}
