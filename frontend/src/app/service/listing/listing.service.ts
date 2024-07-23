import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MyListsResponse} from "../../response/myListsResponse";

const baseUrl = "http://localhost:8080/listings/getListings";

@Injectable({
  providedIn: 'root'
})
export class ListingService {

  constructor(private http: HttpClient) {
  }

  showMyLists(): Observable<MyListsResponse> {

    return this.http.get(baseUrl);

  }
}
