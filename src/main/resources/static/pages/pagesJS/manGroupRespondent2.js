$(function () {
    //$("#ctl01_lblUserName").text(getCookie('groupId'));
    var oTable = new TableInit();
    oTable.Init();
});
//setCookie("groupId", "0d6df5ab6494429593761640c047ca63");
var groupId = getCookie("groupId");
console.log("shit"+groupId)
console.log("shit"+getCookie("groupId"))
var respondents;
//setCookie("respondentUserName","ztx");
//setCookie("tenantId","c0b11fdb65c24f0bafc5269357507757");
var tenantId = getCookie("tenantId")

function getUserList() {
    $("#respondentTable").bootstrapTable('refresh');
}

function TableInit() {

    var oTableInit = new Object();
    //console.log(groupId);
    //初始化Table
    oTableInit.Init = function () {
        $('#respondentTable').bootstrapTable({
            url: httpRequestUrl + '/group/list?groupId='+groupId,         //请求后台的URL（*）
            method: 'GET',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            //sortable:true,
            //sortOrder: "asc",                   //排序方式
            //queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            //queryParams: groupId,//请求服务器时所传的参数
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
                    field: 'username',
                    title: '用户名',
                    align: 'center',
                },
                {
                    field: 'name',
                    title: '昵称',
                    align: 'center',
                },
                {
                    field: 'phone',
                    title: '手机号',
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
                if(res.data == null){
                    console.log("res is null")
                }else{
                    if(res.code === "666"){
                        var userInfo = res.data;
                        respondents = [];
                        if (userInfo.length) {
                            for (var i = 0; i < userInfo.length; i++) {
                                var dataNewObj = {
                                    "id": '',
                                    "username": '',
                                    "name": '',
                                    'phone': '',
                                };
                                dataNewObj.id = userInfo[i].id;
                                dataNewObj.username = userInfo[i].userName;
                                dataNewObj.name = userInfo[i].name;
                                dataNewObj.phone = userInfo[i].phone;
                                respondents.push(dataNewObj);
                            }
                        }
                        var data = {
                            total: respondents.length,
                            rows: respondents
                        };
                        return data;
                    }
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
        id = row.id;
        $.cookie('questionId', id);
    }
};


// 表格中按钮
function addFunctionAlty1(value, row, index) {
    var btnText = '';

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"changeStatus(" + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">停用</button>&nbsp;&nbsp;";

    return btnText;
}

function addFunctionAlty(value, row, index) {
    var btnText = '';
    console.log("please fuck: "+row.id)
    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"lookAnswerPage(" + "'" + row.username + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";
    //btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"lookAnswerPage(" + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";
    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editAnswerPage(" + "'" + row.id + "'" + ")\" class=\"btn btn-default-g ajax-link\">修改</button>&nbsp;&nbsp;";

    return btnText;
}



// 添加答者到群组
function addNewRespondentToGroupPage() {
    console.log(groupId);
    setCookie('groupId', groupId);
    setCookie('tenantId', getCookie("tenantId"))
    window.location.href = './addNewRespondentToGroup.html';
}

function editAnswerPage(editRespondentId) {
    setCookie('respondentId', editRespondentId)
    setCookie('tenantId', getCookie("tenantId"))
    alert("修改答者")
    window.location.href = './userModRespondentInf.html';
}

// function lookAnswerPage(rowId) {
function lookAnswerPage(username) {
    setCookie('respondentUserName', username)
    setCookie('tenantId', getCookie("tenantId"))
    console.log(getCookie("username"))
    console.log("tenantId"+getCookie("tenantId"))
    alert("查看答者")
    window.location.href = './lookRespondent.html';
}

// 修改用户状态（禁用、开启）
function changeStatus(id) {
// 停用答者
    alert("修改答者状态")
    var url = '/group/list';
    var data ={
        "groupId":groupId
    }
    commonAjaxGet(true, url, data, changeUserStates);
    // 查看用户信息成功
    function changeUserStates(result) {
        //console.log(result)
        if (result.code == "666") {
            var userInfo = result.data[id];
            console.log(userInfo);
            var state=userInfo.state;
            var url=(state===0?'/tenant/recover':'/tenant/delete')
            var da = {
                'userName':userInfo.userName,
                'phone':userInfo.phone
            };
            commonAjaxPost(true,url,da,function (res){
                if(res.code =="666"){
                    alert("修改用户状态为"+(state==0?"启用":"停用"));
                    getUserList()
                }
                else {
                    alert(res.message);
                }
            })


        } else if (result.code === "333") {
            layer.msg(result.message, {icon: 2});
            setTimeout(function () {
                window.location.href = '/manUser.html';
            }, 1000)
        } else {
            layer.msg(result.message, {icon: 2})
        }

    }
}

function searchRespondent() {

    alert("搜索")
}
