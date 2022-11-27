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

function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/user/list?userId=' + getCookie("userId"),         //请求后台的URL（*）
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
                    field: 'name',
                    title: '群组名',
                    align: 'center',
                },
                {
                    field: 'description',
                    title: '描述',
                    align: 'center',
                },
                {
                    field: 'createTime',
                    title: '时间',
                    align: 'center'
                },
                {
                    field: 'tenantId',
                    title: '所属租户',
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
                                "description": '',
                                'createTime': '',
                                'tenantId': '',
                            };
                            dataNewObj.id=i;
                            dataNewObj.name = userInfo[i].name;
                            dataNewObj.description = userInfo[i].description;
                            dataNewObj.createTime = userInfo[i].createTime;
                            dataNewObj.tenantId = userInfo[i].tenantId;
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
    btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"toExamGroupInfo(" + "'" + groupId[row.id] + "'"  +"," + "'" + row.tenantId + "'" +  ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";

    btnText += "<button type=\"button\" class=\"button\" id=\"btn_look\" onclick=\"toUserModGroupInf(" + "'" + groupId[row.id] + "'" + ")\" class=\"btn btn-default-g ajax-link\">修改</button>&nbsp;&nbsp;";

    return btnText;
}


// function toExamGroupInfo(id){
// function toExamGroupInfo(id, gId){
function toExamGroupInfo(gId,tid){
    //var tid = getCookie("tenantId")
    setCookie("tenantId",tid)

    setCookie("groupId", gId)
    console.log(getCookie("groupId"))
    console.log("sh"+getCookie("tenantId"))
    window.location.href = "manGroupRespondent.html" //界面跳转
}

function toUserModGroupInf(id){
    setCookie("id",id)
    window.location.href = "userModGroupInf.html" //界面跳转
}

// 修改用户状态（禁用、开启）
function changeStates(id) {
    var url = '/user/list';
    var data = {"userId": getCookie("userId")};
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
                if(res.code =="666"){
                    alert("修改群组状态为"+(state==0?"启用":"停用"));
                    getUserList()
                }
                else {
                    alert(res.message);
                }
            })


        } else if (result.code === "333") {
            layer.msg(result.message, {icon: 2});
            setTimeout(function () {
                window.location.href = '/manGroup.html';
            }, 1000)
        } else {
            layer.msg(result.message, {icon: 2})
        }

    }
}


