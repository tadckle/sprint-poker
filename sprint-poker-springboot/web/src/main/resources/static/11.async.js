(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[11,12],{"2hvg":function(e,t,r){"use strict";var n=r("LNUy");n(r("t3Un"))},U9DL:function(e,t,r){"use strict";var n=r("LNUy"),s=r("qhJN");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=s(r("E1fM")),u=(n(r("2hvg")),{namespace:"dashboard",state:{},reducers:{},effects:{queryPokerNotes:a.default.mark(function e(){return a.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:case"end":return e.stop()}},e,this)}),deletePokerNote:a.default.mark(function e(){return a.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:case"end":return e.stop()}},e,this)}),addPokerNote:a.default.mark(function e(){return a.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:case"end":return e.stop()}},e,this)})}});t.default=u},t3Un:function(e,t,r){"use strict";var n=r("qhJN");Object.defineProperty(t,"__esModule",{value:!0}),t.request=c;var s=n(r("E1fM")),a=n(r("BFkJ")),u=n(r("PkBI"));function o(e){if(e.status>=200&&e.status<300)return e;var t=new Error(e.statusText);throw t.response=e,t}function c(e,t){return i.apply(this,arguments)}function i(){return i=(0,a.default)(s.default.mark(function e(t,r){var n,a,c;return s.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return console.log("url",t),console.log("options",r),e.next=4,(0,u.default)(t,r);case 4:return n=e.sent,console.log("response",n),o(n),e.next=9,n.json();case 9:return a=e.sent,c={data:a,headers:{}},n.headers.get("x-total-count")&&(c.headers["x-total-count"]=n.headers.get("x-total-count")),e.abrupt("return",c);case 13:case"end":return e.stop()}},e,this)})),i.apply(this,arguments)}}}]);