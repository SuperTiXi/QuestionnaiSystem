$(function () {
    isLoginFun();
    header();
    $("#ctl01_lblUserName").text(getCookie('tenantId'));
    $("#IDSpan").text("chargeInfo.tenantId");
    getUserInfo();

});

// 查看用户详细信息
function getUserInfo() {
    var url = '/admin/charging';
    var data = {
    };
    commonAjaxGet(true, url, data, getTenantChargeSuccess);

}

// 查看用户信息成功
function getTenantChargeSuccess(result) {
    //console.log(result)
    if (result.code == "666") {
        var charges = result.data;
        if (charges.length) {
            for (var i = 0; i < charges.length; i++) {
                if (charges[i]===null || charges[i].tenantId!==getCookie("tenantId")) continue;
                $("#IdSpan").text(charges[i].tenantId);
                // $("#createTimeSpan").text(projectInfo.creatDate.replace(/-/g,'/'));   //原代码
                // $("#createTimeSpan").text(projectInfo.creationDate.replace(/-/g,'/'));
                $("#feeSpan").text(charges[i].balance); //获得金额
                $("#balanceSpan").text(charges[i].requiredMoney); //获得余额
                break;
            }
        }

    } else if (result.code === "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = '/manUser.html';
        }, 1000)
    } else {
        layer.msg(result.message, {icon: 2})
    }
}
