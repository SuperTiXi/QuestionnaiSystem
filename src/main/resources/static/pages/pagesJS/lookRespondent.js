$(function () {
    isLoginFun();
    header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    getUserInfo();

});

// 查看用户详细信息
function getUserInfo() {
    //var url = '/tenant/list?tenantId='+getCookie("tenantId");
    var url = '/tenant/list';
    var data={"tenantId":getCookie("tenantId")};
    // var data={};
    commonAjaxGet(true, url,data,getUserInfoSuccess);

}
console.log("fuck"+getCookie("respondentUserName"))
console.log("fuck"+getCookie("tenantId"))


// 查看用户信息成功
function getUserInfoSuccess(result) {
    //console.log(result)
    if (result.code == "666") {
        var userInfo = result.data[0];
        for (i=0; i<result.data.length; ++i){
            if(result.data[i] == null)continue;
            if(result.data[i].userName === getCookie("respondentUserName")){
                userInfo = result.data[i];
                console.log("yes!!!!!"+result.data[i].userName)
            }
            console.log(result.data[i].userName)
        }
        console.log(userInfo);
        $("#userNameSpan").text(userInfo.userName);
        // $("#createTimeSpan").text(projectInfo.creatDate.replace(/-/g,'/'));   //原代码
        // $("#createTimeSpan").text(projectInfo.creationDate.replace(/-/g,'/'));
        $("#passWordSpan").text(userInfo.password);
        $("#nameSpan").text(userInfo.name);
        $("#phoneSpan").text(userInfo.phone);
        $("#questionSpan").text(userInfo.question);
        $("#answerSpan").text(userInfo.answer);
        $("#statusSpan").text(userInfo.state===0?"停用":"使用中");

    } else if (result.code === "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'manGroupRespondent.html';
        }, 1000)
        console.log("abc")
    } else {
        layer.msg(result.message, {icon: 2})
        console.log("abc123")

    }
}
