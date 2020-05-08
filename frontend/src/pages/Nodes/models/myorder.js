import { queryRule, removeRule, addRule, updateRule } from '@/services/api';

export default {
  namespace: 'myorder',

  state: {
    data: {
      list: [],
      pagination: {},
    },
    source_data: {
      list: [],
      pagination: {},
    },
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      yield put({
        type: 'save',
        payload: payload,
      });
    },
    *reset({ payload }, { call, put }) {
      yield put({
        type: 'save2',
        payload: payload,
      });
    },
    *sort({ payload }, { call, put }) {
      let params = payload.params;
      let dataSource = payload.myorder.source_data.list;

      if (params.sorter) {
        const s = params.sorter.split('_');
        dataSource = dataSource.sort((prev, next) => {
          if (s[1] === 'descend') {
            return next[s[0]] - prev[s[0]];
          }
          return prev[s[0]] - next[s[0]];
        });
      }

      if (params.status) {
        const status = params.status.split(',');
        let filterDataSource = [];
        status.forEach(s => {
          filterDataSource = filterDataSource.concat(
            dataSource.filter(data => parseInt(data.status, 10) === parseInt(s[0], 10))
          );
        });
        dataSource = filterDataSource;
      }

      if (params.name) {
        dataSource = dataSource.filter(data => data.name.indexOf(params.name) > -1);
      }

      let pageSize = 10;
      if (params.pageSize) {
        pageSize = params.pageSize * 1;
      }

      const result = {
        list: dataSource,
        pagination: {
          total: dataSource.length,
          pageSize,
          current: parseInt(params.currentPage, 10) || 1,
        },
      };

      // console.log(result);
      // console.log(payload);

      yield put({
        type: 'save2',
        payload: result,
      });
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload,
        source_data: action.payload,
      };
    },
    save2(state, action) {
      return {
        ...state,
        data: action.payload,
      };
    },
  },
};
