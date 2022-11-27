$(function () {
    // isLoginFun();
    // header();
    // $("#ctl01_lblUserName").text(getCookie('userName'));
    var oTable = new TableInit();
    oTable.Init();
});
var users;
var gorupId = getCookie("groupId");
var userId = getCookie("userId")

function getUserList() {
    $("#userTable").bootstrapTable('refresh');
}

var groupId = getCookie("groupId");


function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#userTable').bootstrapTable({
            url: httpRequestUrl + '/group/listAllAnswer',         //请求后台的URL（*）
            method: 'GET',                      //请求方式（*）
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
            queryParams: null,//请求服务器时所传的参数
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
            },
            {
                field: 'operation',
                title: '操作',
                align: 'center',
                checkbox: false,
                events: operateEvents,//给按钮注册事件
                formatter: addFunctionAlty//表格中增加按钮
            }],
            responseHandler: function (res) {
                console.log(res);
                if(res.code === "666"){
                    var userInfo = res.data;
                    users = [];
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
                            users.push(dataNewObj);
                        }
                        console.log(users)
                    }
                    var data = {
                        total: users.length,
                        rows: users
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



// 这里是复选框打钩，先留着
function addFunctionAlty(value, row, index) {
   var btnText = '';
    console.log("\nvalue="+value+"\nrow="+row+"\nindex="+index+"\n");
    //btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editAnswerPage(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">修改</button>&nbsp;&nbsp;";
    btnText += "<input type=\"checkbox\" id=\"checkBox"+row.id+"\" value="+index+" class=\"btn btn-default-g ajax-link\"></input>&nbsp;&nbsp;";
    // var checkBox=document.createElement("input");
    // checkBox.setAttribute("type","checkbox");
    // checkBox.setAttribute("id",answerId++);
    return btnText;
}



function returnToMain() {
    //deleteCookie("groupId");
    window.location.href = "./manGroupRespondent.html"
}


function addRespondent() {
    var objects = document.getElementsByTagName("input")
    // var arr = []
    var url = '/group/addAnswerer';

    for(i=0; i<objects.length; ++i){
        if(objects[i].checked == true){
            var data = {
                "answererId": users[i].id,
                "groupId": groupId
            };
            // arr.push(data);
            console.log("users["+i+"].id == "+users[i].id);
            commonAjaxGet(true, url, data, function (result) {
                if (result.code == "666") {
                    layer.msg(result.message, {icon: 2});
                }else if(result.code == "50003"){
                    //用户名已存在

                } else if (result.code == "333") {
                    layer.msg(result.message, {icon: 2});
                    setTimeout(function () {
                        window.location.href = '../manGroupRespondent.html';
                    }, 1000)
                }else{
                    layer.msg(result.message, {icon: 2});
                    alert("添加失败")
                    console.log(shit);
                }
            })
        }
    }
    alert("添加成功")
    //deleteCookie("groupId")
   window.location.href = "./manGroupRespondent.html"
}
