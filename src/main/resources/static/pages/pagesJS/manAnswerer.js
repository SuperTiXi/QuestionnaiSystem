var questionnaire = {};
var answererId = getCookie("answererId");
$(function () {
    isLoginFun();
    header();
    $("#ctl01_lblUserId").text(getCookie('userId'));
    var oTable = new TableInit();
    oTable.Init();
});

function getUserList() {
    $("#userTable").bootstrapTable('refresh');
}

function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/release/questionnaireToAnswer?answererId='+answererId,         //请求后台的URL（*）
            method: 'GET',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            //sortable:true,
            //sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            // queryParams:JSON.stringify(getCookie("tenantId")),//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            silent: true,
            showRefresh: false,                  //是否显示刷新按钮
            showToggle: false,
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            count:false,
            columns: [{
                checkbox: true,
                visible: false
            },
                {
                    field: 'id',
                    title: '序号',
                    align: 'center',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'releasedTime',
                    title: '发布时间',
                    align: 'center',
                },
                {
                    field: 'finishedTime',
                    title: '截止时间',
                    align: 'center',
                },
                {
                    field: 'type',
                    title: '类别',
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty1//表格中增加按钮
                }],
            responseHandler: function (res) {
                console.log(res);
                questionnaire = res.data;
                if(res.code === "666"){
                   // var userInfo=JSON.parse('[{"releasedTime":"asd","finishedTime":"s","type":"123456","answer":"123456"},{"releasedTime":"as43d","finishedTime":"s435","type":"1523456","answer":"1235456"},{"releasedTime":"a711sd","finishedTime":"77s","type":"127713456","answer":"12347456"}]');
                   var userInfo = res.data;
                    var NewData = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            if (userInfo[i]===null) continue;
                            var dataNewObj = {
                                'id':'',
                                "releasedTime": '',
                                "finishedTime": '',
                                "type": '',
                            };
                            dataNewObj.id=i;
                            dataNewObj.releasedTime = userInfo[i].releasedTime;
                            dataNewObj.finishedTime = userInfo[i].finishedTime;
                            dataNewObj.type = userInfo[i].type;
                            NewData.push(dataNewObj);
                        }
                        console.log(NewData)
                    }
                    var data = {
                        total: NewData.length,
                        rows: NewData
                    };
                    return data;
                }

            }

        });
    };

    // 得到查询的参数
    function queryParams(params) {
        var userName = $("#keyWord").val();
        console.log(userName);
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
            userName: userName
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}


window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        ind = row.id;
        $.cookie('userIndex', ind);
    }
};


// 表格中按钮
function addFunctionAlty1(value, row, index) {
    var btnText = '';

    btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"answer(" + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">答题</button>&nbsp;&nbsp;";

    return btnText;
}


// 按钮函数调用处
function answer(id) {
    var questionnaireId=  questionnaire[id].id;
    setCookie("type","l");
    setCookie("answerId",answererId)
    window.open("previewQuestionnaire.html?i=" + questionnaireId);
}
