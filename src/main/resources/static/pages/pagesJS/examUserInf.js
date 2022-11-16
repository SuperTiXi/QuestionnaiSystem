$(function () {
    isLoginFun();
    header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    getUserInfo();

});

// 查看用户详细信息
function getUserInfo() {
    var tenantId = getCookie('tenantId');

    var url = '/tenant/list';
    var data = {
        "id": tenantId,
    };
    commonAjaxPost(true, url, data, getUserInfoSuccess);

}

// 查看用户信息成功
function getUserInfoSuccess(result) {
    //console.log(result)
    if (result.code == "666") {
        var userInfo = result.data[0];

        $("#userNameSpan").text(userInfo.username);
        // $("#createTimeSpan").text(projectInfo.creatDate.replace(/-/g,'/'));   //原代码
        // $("#createTimeSpan").text(projectInfo.creationDate.replace(/-/g,'/'));
        $("#nameSpan").text(userInfo.name);
        $("#phoneSpan").text(userInfo.phone);
        $("#statusSpan").text(userInfo.status===0?"停用":"使用中");

    } else if (result.code === "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = '/../manUser.html';
        }, 1000)
    } else {
        layer.msg(result.message, {icon: 2})
    }
}
