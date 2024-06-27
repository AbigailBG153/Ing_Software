import{a as _}from"./chunk-OIJMDWTY.js";import{b as x,c as p,e as h,f as v,i as b,k as y,l as w,m as S,o as j}from"./chunk-GULYPYWJ.js";import{k as P,n as d}from"./chunk-ZKMA4ASZ.js";import{Eb as f,Jc as O,Pb as c,Ua as u,Va as l,Z as m,ga as C,ha as g,ob as M,xb as e,yb as r,zb as a}from"./chunk-L7JU57TG.js";var k=(()=>{let t=class t{constructor(i,n,s){this.authService=i,this.fb=n,this.router=s,this.form=this.fb.group({email:["",[p.required,p.email]],password:["",p.required]})}ngOnInit(){}login(){if(this.form.invalid)return;let i={email:this.form.value.email,password:this.form.value.password};this.authService.login(i).subscribe({next:n=>{this.router.navigate(["/home"])},error:n=>{console.error("Error en el inicio de sesi\xF3n:",n.message)}})}controlHasError(i,n){return this.form.controls[i].hasError(n)}navigateToRegister(){this.router.navigate(["/choose-user"])}};t.\u0275fac=function(n){return new(n||t)(l(_),l(S),l(P))},t.\u0275cmp=C({type:t,selectors:[["app-login"]],decls:33,vars:1,consts:[[1,"container"],[1,"cover"],["src","IMG/Login/Login_img.jpg","alt",""],[1,"text"],[1,"text-1"],[1,"text-2"],[1,"forms"],[1,"form-content"],[1,"login-form"],[1,"title"],[3,"ngSubmit","formGroup"],[1,"input-boxes"],[1,"input-box"],[1,"fas","fa-envelope"],["type","text","placeholder","Enter your email","formControlName","email"],[1,"fas","fa-lock"],["type","password","placeholder","Enter your password","formControlName","password"],["href","#"],[1,"button","input-box"],["type","submit","value","Submit"],[1,"text","sign-up-text"],[3,"click"]],template:function(n,s){n&1&&(e(0,"body")(1,"div",0)(2,"div",1),a(3,"img",2),e(4,"div",3)(5,"span",4),c(6,"Start your journey to a "),a(7,"br"),c(8," healthier life"),r(),e(9,"span",5),c(10,"Let's get connected"),r()()(),e(11,"div",6)(12,"div",7)(13,"div",8)(14,"div",9),c(15,"Login"),r(),e(16,"form",10),f("ngSubmit",function(){return s.login()}),e(17,"div",11)(18,"div",12),a(19,"i",13)(20,"input",14),r(),e(21,"div",12),a(22,"i",15)(23,"input",16),r(),e(24,"div",3)(25,"a",17),c(26,"Forgot password?"),r()(),e(27,"div",18),a(28,"input",19),r(),e(29,"div",20),c(30,"Don't have an account? "),e(31,"label",21),f("click",function(){return s.navigateToRegister()}),c(32,"Sigup now"),r()()()()()()()()()),n&2&&(u(16),M("formGroup",s.form))},dependencies:[b,x,h,v,y,w],styles:['*[_ngcontent-%COMP%]{margin:0;padding:0;box-sizing:border-box;font-family:Poppins,sans-serif}body[_ngcontent-%COMP%]{min-height:100vh;display:flex;align-items:center;justify-content:center;padding:30px}.container[_ngcontent-%COMP%]{position:relative;max-width:850px;width:100%;background:#fff;padding:40px 30px;box-shadow:0 5px 10px #0003;perspective:2700px}.container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]{position:absolute;top:0;left:50%;height:100%;width:50%;z-index:98;transition:all 1s ease;transform-origin:left;transform-style:preserve-3d}.container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]:before, .container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]:after{content:"";position:absolute;height:100%;width:100%;background:var(--primary);opacity:.5;z-index:12}.container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]:after{opacity:.3;transform:rotateY(180deg);backface-visibility:hidden}.container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]   img[_ngcontent-%COMP%]{position:absolute;height:100%;width:100%;object-fit:cover;z-index:10}.container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]{position:absolute;z-index:130;height:100%;width:100%;display:flex;flex-direction:column;align-items:center;justify-content:center}.cover[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]   .text-1[_ngcontent-%COMP%], .cover[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]   .text-2[_ngcontent-%COMP%]{font-size:26px;font-weight:600;color:#fff;text-align:center}.cover[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]   .text-2[_ngcontent-%COMP%]{font-size:15px;font-weight:500}.container[_ngcontent-%COMP%]   .forms[_ngcontent-%COMP%]{height:100%;width:100%;background:#fff}.container[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]{display:flex;align-items:center;justify-content:space-between}.form-content[_ngcontent-%COMP%]   .login-form[_ngcontent-%COMP%]{width:calc(50% - 25px)}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .title[_ngcontent-%COMP%]{position:relative;font-size:24px;font-weight:500;color:#333}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .title[_ngcontent-%COMP%]:before{content:"";position:absolute;left:0;bottom:0;height:3px;width:25px;background:var(--primary)}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .input-boxes[_ngcontent-%COMP%]{margin-top:30px}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .input-box[_ngcontent-%COMP%]{display:flex;align-items:center;height:50px;width:100%;margin:10px 0;position:relative}.form-content[_ngcontent-%COMP%]   .input-box[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]{height:100%;width:100%;outline:none;border:none;padding:0 30px;font-size:16px;font-weight:500;border-bottom:2px solid rgba(0,0,0,.2);transition:all .3s ease}.form-content[_ngcontent-%COMP%]   .input-box[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]:focus, .form-content[_ngcontent-%COMP%]   .input-box[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]:valid{border-color:var(--primary)}.form-content[_ngcontent-%COMP%]   .input-box[_ngcontent-%COMP%]   i[_ngcontent-%COMP%]{position:absolute;color:var(--primary);font-size:17px}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]{font-size:14px;font-weight:500;color:#333}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]   a[_ngcontent-%COMP%]{text-decoration:none}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .text[_ngcontent-%COMP%]   a[_ngcontent-%COMP%]:hover{text-decoration:underline}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .button[_ngcontent-%COMP%]{color:#fff;margin-top:40px}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .button[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]{color:#fff;background:var(--primary);border-radius:6px;padding:0;cursor:pointer;transition:all .4s ease}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .button[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]:hover{background:var(--primary)}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   label[_ngcontent-%COMP%]{color:var(--primary);cursor:pointer}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   label[_ngcontent-%COMP%]:hover{text-decoration:underline}.forms[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]   .login-text[_ngcontent-%COMP%]{text-align:center;margin-top:25px}@media (max-width: 730px){.container[_ngcontent-%COMP%]   .cover[_ngcontent-%COMP%]{display:none}.form-content[_ngcontent-%COMP%]   .login-form[_ngcontent-%COMP%]{width:100%}}']});let o=t;return o})();var F=[{path:"",component:k}],z=(()=>{let t=class t{};t.\u0275fac=function(n){return new(n||t)},t.\u0275mod=g({type:t}),t.\u0275inj=m({imports:[d.forChild(F),d]});let o=t;return o})();var K=(()=>{let t=class t{};t.\u0275fac=function(n){return new(n||t)},t.\u0275mod=g({type:t}),t.\u0275inj=m({imports:[O,z,j]});let o=t;return o})();export{K as LoginModule};