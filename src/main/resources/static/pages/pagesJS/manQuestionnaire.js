$(function () {
    isLoginFun();
    header();
    $("#ctl01_lblUserId").text(getCookie('userId'));
    var oTable = new TableInit();
    oTable.Init();
});

var groupId;
//var tenantId = getCookie("tenantId")
//console.log("11row tid="+tenantId)


function getUserList() {
    $("#userTable").bootstrapTable('refresh');
}

var str = {"id": "","name":"","type":"","state":""};

function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/questionnaire/list?creatorId='+getCookie("userId"),         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            //sortable:true,
            // sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            // data:JSON.parse('{"id": "","name":"","type":"","state":""}'),
            // queryParams:JSON.stringify('{"id": "","name":"","type":"","state":""}'),
            queryParams:JSON.stringify(str),
            // queryParams:JSON.stringify(getCookie("tenantId")),//请求服务器时所传的参数
            //queryParams:queryParams("1"),//请求服务器时所传的参数
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
                    //id", "name","type", "creator_id", "tenant_id","high_quality","state"};
                    field: 'name',
                    title: '问卷名',
                    align: 'center',
                },
                {
                    field: 'type',
                    title: '类型',
                    align: 'center',
                },
                {
                    field: 'createTime',
                    title: '设计时间',
                    align: 'center'
                },
                {
                    field: 'highQuality',
                    title: '问卷质量',
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty1//表格中增加按钮
                },
                {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                }],
            responseHandler: function (res) {
                //var userInfo=JSON.parse('[{"name":"asd","description":"s","createTime":"123456","tenantId":"asd","status":"s","operation":"asd"}]');
                console.log(res);
                if(res.code === "666"){
                    var userInfo = res.data;
                    console.log(res.data);
                    var NewData = [];
                    groupId = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            if (userInfo[i]===null) continue;
                            var dataNewObj = {
                                'id':'',
                                "name": '',
                                "type": '',
                                'createTime': '',
                                'highQuality': '',
                            };
                            dataNewObj.id=i;
                            dataNewObj.name = userInfo[i].name;
                            dataNewObj.type = userInfo[i].type;
                            dataNewObj.createTime = userInfo[i].createTime;
                            dataNewObj.highQuality = userInfo[i].highQuality;
                            NewData.push(dataNewObj);
                            groupId.push(userInfo[i].id);
                        }
                        console.log('ac'+NewData)
                        console.log('ac'+groupId)
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
        var userName = null;
        console.log(userName);
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            //pageNum: params.pageNumber,
           // pageSize: params.pageSize,
            id: "",
            name:"",
            type:"",
            state:"",
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}


window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        // ind = row.id;
        // $.cookie('userIndex', ind);
    }
};


// 表格中按钮
function addFunctionAlty1(value, row, index) {
    var btnText = '';

    btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"changeStates(" + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">更改</button>&nbsp;&nbsp;";

    return btnText;
}

function addFunctionAlty(value, row, index) {
    var btnText = '';

    //btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"toExamGroupInfo(" + "'" + getCookie("tenantId") + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";
    // btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"toExamGroupInfo(" + "'" + getCookie("tenantId") + "'" +"," + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";
    //  btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"toExamGroupInfo(" + "'" + groupId[row.id] + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";
    btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"lookQuestionnaire(" + "'" + groupId[row.id] + "'"  +")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";

    return btnText;
}


// function toExamGroupInfo(id){
// function toExamGroupInfo(id, gId){
// function toExamGroupInfo(gId,tid){
//     //var tid = getCookie("tenantId")
//     setCookie("tenantId",tid)
//
//     setCookie("groupId", gId)
//     console.log(getCookie("groupId"))
//     console.log("sh"+getCookie("tenantId"))
//     window.location.href = "manGroupRespondent.html" //界面跳转
// }

function lookQuestionnaire(questionnaireId){
    // 看问卷
    setCookie("questionnaireId",questionnaireId)
    window.location.href = 'sendQuestionnaire.html';
}
function modQuestionnaire(){
    // 修改问卷
}
// function toUserModGroupInf(id){
//     setCookie("id",id)
//     window.location.href = "userModGroupInf.html" //界面跳转
// }

// 修改状态（禁用、开启）
function changeStates(id) {
    var url = '/questionnaire/list';
    console.log("yes userid = "+getCookie("userId"))
    var data = {"creatorId": getCookie("userId")};
    commonAjaxGet(true, url, data, changeUserStates);

    // 查看用户信息成功
    function changeUserStates(result) {
        if (result.code == "666") {
            var userInfo = result.data[id];
            var state=userInfo.state;
            alert("修改群组状态成功")
            var url=(state===0?'/user/recover':'/user/delete')
            var da = userInfo.id;
            console.log(da);
            console.log('stat' + state);
            commonAjaxPost(true,url,da,function (){
                alert("修改群组状态成功")
                getUserList()
            })
        } else if (result.code === "333") {
            layer.msg(result.message, {icon: 2});
            setTimeout(function () {
                window.location.href = '../manGroup.html';
            }, 1000)
        } else {
            layer.msg(result.message, {icon: 2})
        }

    }
}

function addNewQ(){
    //在这里调整

    window.location.href = './addQuestionnaire.html';
}


