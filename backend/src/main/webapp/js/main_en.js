$.dataTableSettings ={
    sDom:"<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
    sPaginationType:"bootstrap",
    bStateSave:true,
    aLengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
    aoColumnDefs: [
        {
            bSortable: false,
            aTargets: [ 0 ]
        }
    ],
	oLanguage:{
      
        sLengthMenu: "show _MENU_ nodes per page ",
        sEmptyTable: "none",
        sInfo: "total _TOTAL_ nodes",
        sInfoEmpty: "",
        sInfoFiltered: "",
    }
};
$.dataTableSettings2 ={
    sDom:"lfrti<'center' p>",
    sPaginationType:"bootstrap",
    bLengthChange: false,
    searching:false,
    bFilter: false,
    iDisplayLength: 8,
    bStateSave:true,
    aoColumnDefs: [
        {
            "bSortable": false,
            "aTargets": [ 0 ] // <-- gets last column and turns off sorting
        }
    ],
};
$.toastrSetting = {
    "closeButton": true, //�Ƿ���ʾ�رհ�ť
    "debug": false, //�Ƿ�ʹ��debugģʽ
    "positionClass": "toast-top-center",//��������λ��
    "showDuration": "300",//��ʾ�Ķ���ʱ��
    "hideDuration": "1000",//��ʧ�Ķ���ʱ��
    "timeOut": "5000", //չ��ʱ��
    "extendedTimeOut": "1000",//�ӳ�չʾʱ��
    "showEasing": "swing",//��ʾʱ�Ķ������巽ʽ
    "hideEasing": "linear",//��ʧʱ�Ķ������巽ʽ
    "showMethod": "fadeIn",//��ʾʱ�Ķ�����ʽ
    "hideMethod": "fadeOut" //��ʧʱ�Ķ�����ʽ

};
$(document).ready(function() {
    $(".datatable").dataTable($.dataTableSettings);
	$(".datatable2").dataTable($.dataTableSettings2);
    $(".datepicker").each(function () {
        this.value =new Date().format("yyyy/MM/dd");
    })
    $(".datepicker.after-now").datepicker({
        autoclose: true,
        startView: 0,
        format: "yyyy/mm/dd",
        todayHighlight: true,
        startDate:new Date(),
        defaultDate:0,
    });
    $(".datepicker.before-now").datepicker({
        autoclose: true,
        startView: 0,
        format: "yyyy/mm/dd",
        todayHighlight: true,
        endDate:new Date(),
        defaultDate:0,
    });
    $(".datepicker").not(".after-now").not(".before-now").datepicker({
        autoclose: true,
        startView: 0,
        format: "yyyy/mm/dd",
        todayHighlight: true,
        defaultDate:0,
    });
});

$.fn.dataTableExt.oApi.fnPagingInfo = function (a) {
    return {
        iStart: a._iDisplayStart,
        iEnd: a.fnDisplayEnd(),
        iLength: a._iDisplayLength,
        iTotal: a.fnRecordsTotal(),
        iFilteredTotal: a.fnRecordsDisplay(),
        iPage: Math.ceil(a._iDisplayStart / a._iDisplayLength),
        iTotalPages: Math.ceil(a.fnRecordsDisplay() / a._iDisplayLength)
    }
};
$.extend($.fn.dataTableExt.oPagination, {
    bootstrap: {
        fnInit: function (e, b, d) {
            var a = e.oLanguage.oPaginate;
            var f = function (g) {
                g.preventDefault();
                if (e.oApi._fnPageChange(e, g.data.action)) {
                    d(e)
                }
            };
            $(b).addClass("pagination").append('<ul><li class="prev disabled"><a href="#">&larr; ' + a.sPrevious + '</a></li><li class="next disabled"><a href="#">' + a.sNext + " &rarr; </a></li></ul>");
            var c = $("a", b);
            $(c[0]).bind("click.DT", {action: "previous"}, f);
            $(c[1]).bind("click.DT", {action: "next"}, f)
        }, fnUpdate: function (c, k) {
            var l = 5;
            var e = c.oInstance.fnPagingInfo();
            var h = c.aanFeatures.p;
            var g, f, d, a, m, b = Math.floor(l / 2);
            if (e.iTotalPages < l) {
                a = 1;
                m = e.iTotalPages
            } else {
                if (e.iPage <= b) {
                    a = 1;
                    m = l
                } else {
                    if (e.iPage >= (e.iTotalPages - b)) {
                        a = e.iTotalPages - l + 1;
                        m = e.iTotalPages
                    } else {
                        a = e.iPage - b + 1;
                        m = a + l - 1
                    }
                }
            }
            for (g = 0, iLen = h.length; g < iLen; g++) {
                $("li:gt(0)", h[g]).filter(":not(:last)").remove();
                for (f = a; f <= m; f++) {
                    d = (f == e.iPage + 1) ? 'class="active"' : "";
                    $("<li " + d + '><a href="#">' + f + "</a></li>").insertBefore($("li:last", h[g])[0]).bind("click", function (i) {
                        i.preventDefault();
                        c._iDisplayStart = (parseInt($("a", this).text(), 10) - 1) * e.iLength;
                        k(c)
                    })
                }
                if (e.iPage === 0) {
                    $("li:first", h[g]).addClass("disabled")
                } else {
                    $("li:first", h[g]).removeClass("disabled")
                }
                if (e.iPage === e.iTotalPages - 1 || e.iTotalPages === 0) {
                    $("li:last", h[g]).addClass("disabled")
                } else {
                    $("li:last", h[g]).removeClass("disabled")
                }
            }
        }
    }
});
$(window).bind("resize", widthFunctions);

function widthFunctions(g) {
    var d = $(".sidebar-nav").height() + 50;
    var c = $(window).height();
    var b = $(window).width();
    if (b > 767) {
        if (c - 80 > d) {
            var a = $("header").height();
            var f = $("footer").height();
            $("#content").css("min-height", c - 80)
        } else {
            $("#content").css("min-height", d)
        }
    }
    if (b < 980 && b > 767) {
        if ($(".main-menu-span").hasClass("span2")) {
            $(".main-menu-span").removeClass("span2");
            $(".main-menu-span").addClass("span1")
        }
        if ($(".brand").hasClass("span2")) {
            $(".brand").removeClass("span2");
            $(".brand").addClass("span1")
        }
        if ($("#content").hasClass("span10")) {
            $("#content").removeClass("span10");
            $("#content").addClass("span11")
        }
        $("a").each(function () {
            if ($(this).hasClass("quick-button-small span1")) {
                $(this).removeClass("quick-button-small span1");
                $(this).addClass("quick-button span2 changed")
            }
        });
        $(".circleStatsItem, .circleStatsItemBox").each(function () {
            var e = $(this).parent().attr("onTablet");
            var h = $(this).parent().attr("onDesktop");
            if (e) {
                $(this).parent().removeClass(h);
                $(this).parent().addClass(e)
            }
        });
        $(".tempStatBox").each(function () {
            var e = $(this).attr("onTablet");
            var h = $(this).attr("onDesktop");
            if (e) {
                $(this).removeClass(h);
                $(this).addClass(e)
            }
        });
        $("div").each(function () {
            var e = $(this).attr("onTablet");
            var h = $(this).attr("onDesktop");
            if (e) {
                $(this).removeClass(h);
                $(this).addClass(e)
            }
        })
    } else {
        if ($(".main-menu-span").hasClass("span1")) {
            $(".main-menu-span").removeClass("span1");
            $(".main-menu-span").addClass("span2")
        }
        if ($(".brand").hasClass("span1")) {
            $(".brand").removeClass("span1");
            $(".brand").addClass("span2")
        }
        if ($("#content").hasClass("span11")) {
            $("#content").removeClass("span11");
            $("#content").addClass("span10")
        }
        $("a").each(function () {
            if ($(this).hasClass("quick-button span2 changed")) {
                $(this).removeClass("quick-button span2 changed");
                $(this).addClass("quick-button-small span1")
            }
        });
        $(".circleStatsItem, .circleStatsItemBox").each(function () {
            var e = $(this).parent().attr("onTablet");
            var h = $(this).parent().attr("onDesktop");
            if (e) {
                $(this).parent().removeClass(e);
                $(this).parent().addClass(h)
            }
        });
        $(".tempStatBox").each(function () {
            var e = $(this).attr("onTablet");
            var h = $(this).attr("onDesktop");
            if (e) {
                $(this).removeClass(e);
                $(this).addClass(h)
            }
        });
        $("div").each(function () {
            var e = $(this).attr("onTablet");
            var h = $(this).attr("onDesktop");
            if (e) {
                $(this).removeClass(e);
                $(this).addClass(h)
            }
        });
        $(".widget").each(function () {
            var e = $(this).attr("onTablet");
            var h = $(this).attr("onDesktop");
            if (e) {
                $(this).removeClass(e);
                $(this).addClass(h)
            }
        })
    }
    if ($(".timeline")) {
        $(".timeslot").each(function () {
            var e = $(this).find(".task").outerHeight();
            $(this).css("height", e)
        })
    }
};
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //�·�
        "d+" : this.getDate(),                    //��
        "h+" : this.getHours(),                   //Сʱ
        "m+" : this.getMinutes(),                 //��
        "s+" : this.getSeconds(),                 //��
        "q+" : Math.floor((this.getMonth()+3)/3), //����
        "S"  : this.getMilliseconds()             //����
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};

$.compare = function (x, y){
    if (x < y) {
        return -1;
    } else if (x > y) {
        return 1;
    } else {
        return 0;
    }
}