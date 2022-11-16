$(function () {
    // isLoginFun();
    // header();
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
            url: httpRequestUrl + '/tenant/list',         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable:true,
            sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            queryParams: queryParams,//请求服务器时所传的参数
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
            }, {
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
                // console.log(res);
                if(res.code === "666"){
                    var userInfo=JSON.parse('[{"username":"asd","name":"s","phone":"123456"},{"username":"zxc","name":"z","phone":"123456"},{"username":"qwe","name":"q","phone":"123456"}]');
                    //var userInfo = res.data.list;
                    var NewData = [];
                    if (userInfo.length) {
                        for (var i = 0; i < userInfo.length; i++) {
                            var dataNewObj = {
                                "username": '',
                                "name": '',
                                'phone': '',
                            };

                            dataNewObj.username = userInfo[i].username;
                            dataNewObj.name = userInfo[i].name;
                            dataNewObj.phone = userInfo[i].phone;
                            NewData.push(dataNewObj);
                        }
                        console.log(NewData)
                    }
                    var data = {
                        total: res.data.total,
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
        id = row.id;
        $.cookie('questionId', id);
    }
};


// 表格中按钮
function addFunctionAlty1(value, row, index) {
    var btnText = '';

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"changeStatus()\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">停用</button>&nbsp;&nbsp;";

    return btnText;
}

function addFunctionAlty(value, row, index) {
    var btnText = '';

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"toExamUserInfo()\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">查看</button>&nbsp;&nbsp;";

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"toTenantModUserInf()\" class=\"btn btn-default-g ajax-link\">修改</button>&nbsp;&nbsp;";

    return btnText;
}

//重置密码

function toExamUserInfo(){
    window.location.href = "../examUserInf.html" //界面跳转
}

function toTenantModUserInf(){
    window.location.href = "../tenantModUserInf.html" //界面跳转
}

// 修改用户状态（禁用、开启）
function changeStatus() {
    var status;
    alert("修改用户状态")
}


