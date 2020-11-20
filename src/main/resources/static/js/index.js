layui.use(['layer', 'form', 'element'], function(){
    var layer = layui.layer, form = layui.form, element = layui.element;
    var body = $("#body");
    body.css('height', $(window).height() - 60);
    body.css('width', $(window).width());
    //监听提交
    form.on('submit(login)', function(data){
        // layer.alert(JSON.stringify(data.field), {
        //     title: '最终的提交信息'
        // });
        $.ajax({
            url: 'user/login',
            data: data.field,
            type: 'post',
            dataType: 'json',
            success: function (res) {
                layer.closeAll();
                layer.msg(res.msg);
                if (res.successful) {
                    $("#login").text(res.data)
                }
            }
        });
        return false;
    });
    $("#login").click(function () {
        layer.open({
            type: 1,
            title: '登录',
            content: $("#loginDiv")
        });
    })
});