import{c as u}from"./chunk-ZKMA4ASZ.js";import{Y as s,ca as h,gb as n,r,rc as l}from"./chunk-L7JU57TG.js";var e="banking_auth",I=(()=>{let t=class t{constructor(){this.apiUrl="http://localhost:8080/ApiRest/v1",this.http=h(u),this._auth=n(null),this.auth=l(()=>this._auth());let i=localStorage.getItem(e);i&&this._auth.set(JSON.parse(i))}login(i){return this.http.post(`${this.apiUrl}/auth/token`,i).pipe(r(o=>(localStorage.setItem(e,JSON.stringify(o)),this._auth.set(o),o.user)))}logout(){localStorage.removeItem(e),this._auth.set(null)}};t.\u0275fac=function(o){return new(o||t)},t.\u0275prov=s({token:t,factory:t.\u0275fac,providedIn:"root"});let a=t;return a})();export{I as a};
