(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[13],{n7oB:function(e,t,o){"use strict";var r=o("LNUy");Object.defineProperty(t,"__esModule",{value:!0}),t.fetch=a,t.onClickPocker=c,t.addTikcetRecord=u;var n=o("pJ0z"),s=r(o("t3Un"));function a(e,t){(0,n.subscribe)("/pocker/pockerBoard/"+t,e),(0,n.request)("/app/joinPockerBoard/"+t,{},{})}function c(e){console.log("xxxx",e.roomName),(0,n.request)("/app/onClickPocker/"+e.roomName,{},JSON.stringify(e))}function u(e){console.log("tag",JSON.stringify(e)),s.request("/poker/ticketRecord",{method:"POST",body:JSON.stringify(e)})}},t3Un:function(e,t,o){"use strict";var r=o("qhJN");Object.defineProperty(t,"__esModule",{value:!0}),t.request=u;var n=r(o("E1fM")),s=r(o("BFkJ")),a=r(o("PkBI"));function c(e){if(e.status>=200&&e.status<300)return e;var t=new Error(e.statusText);throw t.response=e,t}function u(e,t){return i.apply(this,arguments)}function i(){return i=(0,s.default)(n.default.mark(function e(t,o){var r,s,u;return n.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return console.log("url",t),console.log("options",o),e.next=4,(0,a.default)(t,o);case 4:return r=e.sent,console.log("response",r),c(r),e.next=9,r.json();case 9:return s=e.sent,u={data:s,headers:{}},r.headers.get("x-total-count")&&(u.headers["x-total-count"]=r.headers.get("x-total-count")),e.abrupt("return",u);case 13:case"end":return e.stop()}},e,this)})),i.apply(this,arguments)}}}]);