$(function () {
    isLoginFun();
    header();
    $("#ctl01_lblUserName").text(getCookie('name'));
    getUserInfo();

});

// 查看用户详细信息
function getUserInfo() {
    //var url = '/tenant/list?tenantId='+getCookie("tenantId");
    var url = '/user/list';
    var data={"userId":getCookie("userId")};
    commonAjaxGet(true, url,data,getGroupInfoSuccess);

}

// 查看用户信息成功
function getGroupInfoSuccess(result) {
    //console.log(result)
    if (result.code == "666") {
        var userInfo = result.data[getCookie("userIndex")];
        console.log(userInfo);
        $("#userNameSpan").text(userInfo.name);
        $("#passWordSpan").text(userInfo.description);
        $("#nameSpan").text(userInfo.createTime);
        $("#phoneSpan").text(userInfo.tenantId);
        $("#statusSpan").text(userInfo.state===0?"停用":"使用中");

    } else if (result.code === "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = '/../manGroup.html';
        }, 1000)
    } else {
        layer.msg(result.message, {icon: 2})
    }
}
