import React, { PureComponent } from 'react';
import { connect } from 'dva';
import moment from 'moment';
// import echartTheme from './../themeLight'
//下面是按需加载
import echarts from 'echarts/lib/echarts';
//导入折线图
import 'echarts/lib/chart/line'; //折线图是line,饼图改为pie,柱形图改为bar
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/title';
import 'echarts/lib/component/legend';
import 'echarts/lib/component/markPoint';
import ReactEcharts from 'echarts-for-react';
import {
  notification,
  Row,
  Col,
  Card,
  Form,
  Input,
  Select,
  Icon,
  Button,
  Dropdown,
  Menu,
  InputNumber,
  DatePicker,
  Modal,
  message,
  Badge,
  Divider,
  Steps,
  Radio,
} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './ShowData.less';
import $ from 'jquery';
import { server_url, getAuth } from '@/services/api';

const { RangePicker } = DatePicker;
const range = 1; //默认筛选时间间隔1天
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');

/* eslint react/no-multi-comp:0 */
@connect(({ orders, loading }) => ({
  orders,
  loading: loading.models.rule,
}))
@Form.create()
class ShowData extends PureComponent {
  state = {
    modalVisible: false,
    updateModalVisible: false,
    selectedRows: [],
    my_order: 0,
    input_gw_id: 'all',
    input_property: 'RSSI',
    timeRange: [
      moment()
        .add(-range, 'days')
        .startOf('day'),
      moment().startOf('day'),
    ],
  };

  handleOrderSubmmit = () => {
    const { selectedRows } = this.props.orders;
    if (selectedRows.length == 0)
      notification.open({
        message: '提示',
        description: '您尚未选择筛选时间，无法提交查询。',
      });
  };

  disabledDate = current => {
    const sADay = moment()
      .add(-range, 'days')
      .endOf('day');
    return current && current > moment().startOf('day');
  };

  handleRangeChange = time0 => {
    if (time0.length == 2) this.state.timeRange = time0;
    console.log(
      moment(this.state.timeRange[0]).format('YYYY-MM-DD HH:mm:ss'),
      moment(this.state.timeRange[1]).format('YYYY-MM-DD HH:mm:ss')
    );
  };

  handleRangeReset = () => {
    this.state.timeRange = [
      moment()
        .add(-range, 'days')
        .startOf('day'),
      moment().startOf('day'),
    ];
    console.log(this.state.timeRange);
  };

  handleSelectChange = value => {
    this.state.input_property = value;
    console.log(this.state.input_property);
  };

  handleSelectChange_gw = value => {
    this.state.input_gw_id = value;
    console.log(this.state.input_gw_id);
  };

  // handleInputChange(e) {
  //   console.log(e.target.value);
  //   this.state.input_gw_id = e.target.value;
  //   console.log(this.state.input_gw_id);
  // }

  addMap = (data, gw_id) => {
    var dom1 = $('.ant-layout-content')[0];
    $('#allmap').css('height', dom1.clientHeight + 'px');

    var map = new BMap.Map('allmap', { minZoom: 12, maxZoom: 40 }); // 创建Map实例
    map.centerAndZoom(new BMap.Point(116.340967, 40.006628), 17); // 初始化地图,设置中心点坐标和地图级别

    var top_left_control = new BMap.ScaleControl({ anchor: BMAP_ANCHOR_TOP_LEFT });
    var top_left_navigation = new BMap.NavigationControl();
    map.addControl(
      new BMap.MapTypeControl({
        mapTypes: [BMAP_NORMAL_MAP],
      })
    );
    map.addControl(top_left_control);
    map.addControl(top_left_navigation);
    map.setCurrentCity('北京'); // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
    //TODO 遍历data，设置百度地图marker
    if (document.createElement('canvas').getContext) {
      // 判断当前浏览器是否支持绘制海量点
      var points = []; // 添加海量点数据
      for (var i = 0; i < data.length; i++) {
        if (gw_id == 'all') {
          points.push(new BMap.Point(data[i]['Longitude'], data[i]['Latitude']));
        } else {
          for (var j = 0; j < data[i]['rxInfo'].length; ++j) {
            if (data[i]['rxInfo'][j]['id'] == gw_id) {
              points.push(new BMap.Point(data[i]['Longitude'], data[i]['Latitude']));
              break;
            }
          }
        }
      }
      var options = {
        size: BMAP_POINT_SIZE_SMALL,
        shape: BMAP_POINT_SHAPE_CIRCLE,
        color: '#d340c3',
      };
      var pointCollection = new BMap.PointCollection(points, options); // 初始化PointCollection
      pointCollection.addEventListener('click', function(e) {
        alert('单击点的坐标为：' + e.point.lng + ',' + e.point.lat); // 监听点击事件
      });
      map.addOverlay(pointCollection); // 添加Overlay
    } else {
      alert('请在chrome、safari、IE8+以上浏览器查看本示例');
    }
  };

  componentWillMount() {
    const addMap = this.addMap;
    const init_myEchart = this.init_myEchart;
    const { input_gw_id, input_property } = this.state;
    getAuth();
    $.ajax({
      type: 'get',
      url:
        'http://59.110.157.63:28000/node_data/?DEV_EUI=1111111100000006&StartTime=2019-01-01T00:00:00Z&EndTime=2019-07-16T08:07:47.069Z',
      xhrFields: {
        withCredentials: true,
      },
      crossDomain: true,
      success: data => {
        var rlt = {};
        if (data.code == 0) {
          console.log('接口调用成功！');
          $.getScript(
            'http://api.map.baidu.com/getscript?v=3.0&ak=fjQgfPruozIPlj7pMrVRn7s5MCmp4eWr',
            function() {
              init_myEchart(data.data, input_gw_id, input_property);
              addMap(data.data, input_gw_id);
            }
          );
        } else {
          alert('接口调用失败！');
        }
      },
    });
  }

  reloadData = () => {
    const addMap = this.addMap;
    const init_myEchart = this.init_myEchart;
    const { input_gw_id, input_property } = this.state;
    const { dispatch, orders } = this.props;
    dispatch({
      //参数传递
      type: 'show/range',
      payload: { range: this.state.timeRange },
    });
    // console.log(this.state.timeRange);
    // console.log(this.state.input_property);
    // console.log(this.state.input_gw_id);

    var data = {
      DEV_EUI: '1111111100000006',
      StartTime: '2019-01-01T00:00:00Z',
      EndTime: new Date().toJSON(),
    };

    $.ajax({
      type: 'get',
      url: 'http://59.110.157.63:28000/node_data/',
      data: data,
      xhrFields: {
        withCredentials: true,
      },
      crossDomain: true,
      success: data => {
        var rlt = {};
        if (data.code == 0) {
          console.log('接口调用成功！');
          $.getScript(
            'http://api.map.baidu.com/getscript?v=3.0&ak=fjQgfPruozIPlj7pMrVRn7s5MCmp4eWr',
            function() {
              init_myEchart(data.data, input_gw_id, input_property);
              addMap(data.data, input_gw_id);
            }
          );
        } else {
          alert('接口调用失败！');
        }
      },
    });
  };

  init_myEchart = (data, gw_id, input_property) => {
    let myEchart = echarts.getInstanceByDom(document.getElementById('myEchart'));
    if (myEchart == undefined) {
      myEchart = echarts.init(document.getElementById('myEchart'));
    }
    let c_data = [];
    if (gw_id == 'all') {
      gw_id = 'aa555a0000000103';
    }

    let label = 'rssi';
    if (input_property == 'SNR') {
      label = 'snr';
    }

    for (let i = 0; i < data.length; ++i) {
      for (let j = 0; j < data[i]['rxInfo'].length; ++j) {
        if (data[i]['rxInfo'][j]['id'] == gw_id) {
          c_data.push([
            new Date(data[i]['TimeStamp']),
            data[i]['rxInfo'][j][label],
            data[i]['Longitude'],
            data[i]['Latitude'],
          ]);
          break;
        }
      }
    }
    myEchart.setOption(this.getOption2(gw_id, c_data, label));
  };

  getOption2 = (gw_id, data, label) => {
    var option = {
      title: {
        left: 'center',
        text: '网关 ' + gw_id + ' 的 ' + label + ' 数据',
      },
      tooltip: {
        trigger: 'axis',
        formatter: function(params) {
          params = params[0];
          var date = params.value[0];
          return (
            date.toLocaleString() +
            '<br />' +
            params.value[3] +
            '&#176N,' +
            params.value[2] +
            '&#176E<br />' +
            params.value[1]
          );
        },
        axisPointer: {
          animation: false,
        },
      },
      xAxis: {
        type: 'time',
        splitLine: {
          show: false,
        },
      },
      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
          show: true,
        },
      },
      dataZoom: [
        {
          show: true,
          realtime: true,
          start: 0,
          end: 100,
          xAxisIndex: [0],
          top: 'bottom',
        },
        {
          type: 'inside',
          filterMode: 'none',
        },
      ],
      series: [
        {
          name: '模拟数据',
          type: 'line',
          showSymbol: true,
          hoverAnimation: true,
          data: data,
        },
      ],
    };

    return option;
  };

  render() {
    const { loading, dispatch } = this.props;
    const { modalVisible, updateModalVisible, stepFormValues, my_order, input_gw_id } = this.state;

    const parentMethods = {
      handleModalVisible: this.handleModalVisible,
    };
    const updateMethods = {
      handleUpdateModalVisible: this.handleUpdateModalVisible,
    };

    
    var url_dev_eui = this.props.location.query;
    console.log(url_dev_eui)

    return (
      <PageHeaderWrapper title="数据展示">
        <Card bordered={false}>
          <div className={styles.tableListForm}>
            <RangePicker
              disabledDate={this.disabledDate}
              onChange={time0 => this.handleRangeChange(time0)}
              defaultValue={[
                moment()
                  .add(-range, 'days')
                  .startOf('day'),
                moment().startOf('day'),
              ]}
              format="YYYY-MM-DD HH:mm:ss"
              showTime={{ format: 'HH' }}
              onOk={time0 => this.handleRangeChange(time0)}
            />{' '}
            &nbsp;
            <Select
              id="data_property"
              defaultValue="RSSI"
              style={{ width: 120 }}
              onChange={this.handleSelectChange}
            >
              <Option value="RSSI">RSSI</Option>
              <Option value="SNR">SNR</Option>
            </Select>{' '}
            &nbsp;
            <Select
              id="gw_id"
              defaultValue="all"
              style={{ width: 240 }}
              onChange={this.handleSelectChange_gw}
            >
              <Option value="aa555a0000000101">网关号 aa555a0000000101</Option>
              <Option value="aa555a0000000102">网关号 aa555a0000000102</Option>
              <Option value="aa555a0000000103">网关号 aa555a0000000103</Option>
              <Option value="aa555a0000000104">网关号 aa555a0000000104</Option>
              <Option value="aa555a0000000105">网关号 aa555a0000000105</Option>
              <Option value="all">网关号 全部</Option>
            </Select>
            {/* <Input id="gw_id" style={{ width: 240 }} onChange={this.handleInputChange}
              addonBefore="aa555a" defaultValue="0000000101"
            /> */}
            <Button style={{ marginLeft: '30px' }} type="primary" onClick={() => this.reloadData()}>
              查询
            </Button>
          </div>
          <br />
        </Card>
        <Card>
          {/* <ReactEcharts option={this.getOption2()} theme="Imooc"  style={{height:'300px'}}/> */}
          <div id="myEchart" style={{ height: '300px' }} />
        </Card>
        <Card id="allmap" />
      </PageHeaderWrapper>
    );
  }
}

export default ShowData;
