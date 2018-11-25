import { routerRedux } from 'dva/router';
import * as loginService from '../services/login_service';
export default {
  namespace:'global',
  state: {
    hasLogin: false,
    userName:"",
  },
  reducers: {
    signin(state, {payload: loginName}) { 
      console.log(loginName + ' has login');
      return {
        ...state,
        hasLogin: true,
        userName:loginName
      };
    },
  },
  effects: {
    *onLogin({ payload: userName }, { call, put }) {
      yield call(loginService.onLoginServer, userName);
      yield put({
        type: 'signin',
      });
      yield put(routerRedux.push('/'));
    },
    *throwError() {
      throw new Error('hi error');
    },
  }

}