/**
 * Created by Amy on 2018/8/13.
 */
var questionnaireId = getCookie("questionnaireId")
var oTable = new TableInit();
oTable.Init();


function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#analyseTable').bootstrapTable({
            url: httpRequestUrl + '/release/queryQuestionnaireById?questionnaireId='+questionnaireId,         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            pagination: true,                   //是否显示分页（*）
            //是否启用排序
            sortable: true,
            // sortOrder: "asc",                   //排序方式
            queryParamsType: '',//默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            queryParams: queryParams,//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 100,//单页记录数
            pageList: [100],//分页步进值
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            silent: true,
            showRefresh: false,                  //是否显示刷新按钮
            showToggle: false,
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列

            columns: [{
                checkbox: true,
                visible: false
            }, {
                field: 'id',
                title: '序号',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
                {
                    field: 'question',
                    title: '题目',
                    align: 'center',
                    width: '230px',
                    sortable: true
                },
                {
                    field: 'answerInfo',
                    title: '答题信息',
                    align: 'center',
                    sortable: true
                }],
            responseHandler: function (res) {
                //console.log(res);
                if (res.code == "666") {
                    var questionnaire = res.data;
                    var questionList = JSON.parse(questionnaire.info);


                    var data = {
                        'questionnaireId':questionnaireId
                    }
                    commonAjaxGet(true,'/release/getAnswersByQuestionnaire',data,success);

                    function success(res){
                        if(res.code=="666"){
                            var answerList = res.data;
                            for (let i = 0; i < questionList.length; i++) {
                                var object = {
                                    'question':questionList[i].questionTitle,
                                    'answerInfo':''
                                }
                                var optionList = questionList[i].questionOption;
                                var optionCount = [];
                                for (let j = 0; j < optionList.length; j++) {
                                    var count = 0;
                                    for (let k = 0; k < answerList.length; k++) {

                                        if(JSON.parse(answerList[k].info)[i]==j){
                                            count++;
                                        }
                                    }
                                    optionCount.push(count);
                                }

                                object.answerInfo = JSON.stringify(optionCount);
                                $("#analyseTable").bootstrapTable('insertRow', {index: i, row: object});
                            }
                        }
                        else {
                            layer.msg(res.message);
                        }
                    }
                }

            }
        });
    };

    // 得到查询的参数
    function queryParams(params) {
        var schoolName = $("#keyWord").val();
        schoolName = schoolName == "" ? null : schoolName;
        var questionId = getCookie("questionId");
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
            questionId: questionId,
            answerBelong: schoolName,
            sortOrder: params.sortOrder,//排序
            sortName: params.sortName//排序字段
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}





//设计问卷
function designQuestionnaire() {
    var ifDesignQuestionnaire = getCookie("ifDesignQuestionnaire");
    if (ifDesignQuestionnaire == "false") {
        layer.msg("问卷处于运行状态或问卷已发布，不可设计问卷", {icon: 2})
    } else {
        $.cookie("QuestionId", getCookie("questionId"));
        window.open('designQuestionnaire.html?=' + getCookie("questionId"))
    }
}

//发送问卷
function ifSendQuestionnaire() {
    var ifSendQuestionnaire = getCookie("ifSendQuestionnaire");
    if (ifSendQuestionnaire == "false") {
        layer.msg("问卷处于未发送或暂停的状态，不可发送问卷", {icon: 2})
    } else {
        $.cookie("QuestionId", getCookie("questionId"));
        window.location.href = 'sendQuestionnaire.html';
    }
}


//预览问卷
$('#ctl02_hrefView').click(function () {
    window.open('previewQuestionnaire.html?=' + getCookie("questionId"))
});

//回车事件
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        getQuestionnaireAboutSchool();
    }
});