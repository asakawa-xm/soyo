var fileId = '';
layui.use(['layer', 'util', 'form', 'upload'], function(){
    var layer = layui.layer, util = layui.util, form = layui.form, upload = layui.upload;
    loadData();//加载数据

    loadWithHeight();//渲染宽高

    //固定块
    util.fixbar({
        bar1: true
        ,bar2: true
        ,css: {right: 50, bottom: 100}
        ,bgcolor: '#393D49'
        ,click: function(type){
            if(type === 'bar1'){
                layer.msg('icon 是可以随便换的')
            } else if(type === 'bar2') {
                layer.msg('两个 bar 都可以设定是否开启')
            }
        }
    });

    //监听相册创建提交
    form.on('submit(create)', function(data){
        // layer.alert(JSON.stringify(data.field), {
        //     title: '最终的提交信息'
        // });
        $.ajax({
            url: 'album/createAlbum',
            data: data.field,
            type: 'post',
            dataType: 'text',
            success: function (res) {
                layer.closeAll();
                layer.msg(res);
            }
        });
        return false;
    });

    // //监听上传照片提交
    // form.on('submit(insertPhoto)', function(data){
    //     // layer.alert(JSON.stringify(data.field), {
    //     //     title: '最终的提交信息'
    //     // });
    //     $.ajax({
    //         url: 'album/uploadFile',
    //         data: data.field,
    //         type: 'post',
    //         dataType: 'text',
    //         success: function (res) {
    //             layer.closeAll();
    //             layer.msg(res);
    //         }
    //     });
    //     return false;
    // });

    //新建相册弹窗
    $(".firstDiv").click(function () {
        layer.open({
            type: 1,
            title: '新建相册',
            content: $("#createAlbumDiv")
        });
    });

    /**
     * 多附件上传
     * @type {*|jQuery|HTMLElement}
     */
    if (fileId) $('#fileId').val(fileId);
    var demoListView = $('#demoList')
        , uploadListIns = upload.render({
        elem: '#testList'
        , url: ''
        , accept: 'file'
        , exts: 'jpg|png|gif'
        , size: 0 //最大允许上传的文件大小，单位 KB（0（即不限制））
        , multiple: true//是否允许多文件上传。设置 true即可开启
        , number: 0//同时可上传的文件数量0（即不限制）
        , auto: false
        , data: {
            fileId: function () {
                return $('#fileId').val();
            }
        }
        , bindAction: '#upload'//开始上传按钮
        , choose: function (obj) {
            $("#uploadTable").show();
            var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function (index, file, result) {
                var newName = file.name;
                if (file.name.length > 5) newName = newName.substring(0,5) + '...';
                var tr = $(['<tr id="upload-' + index + '">'
                    , '<td onclick="javascript:layer.msg(\'' + file.name + '\')">' + newName + '</td>'
                    , '<td>等待上传</td>'
                    , '<td>'
                    , '<button class="layui-btn layui-btn-xs demo-reload layui-hide" style="margin-left: 0">重传</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete" style="margin-left: 0">删除</button>'
                    , '</td>'
                    , '</tr>'].join(''));

                //单个重传
                tr.find('.demo-reload').on('click', function () {
                    obj.upload(index, file);
                });
                //删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });
                demoListView.append(tr);
            });
        }
        , done: function (res, index, upload) {
            if (res.code == 0) { //上传成功
                var tr = demoListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
                tds.eq(2).html(''); //清空操作
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(index, upload);
        }
        , error: function (index, upload) {
            var tr = demoListView.find('tr#upload-' + index)
                , tds = tr.children();
            tds.eq(3).html('<span style="color: #e53732;font-weight: bold"><i class="layui-icon layui-icon-close-fill"> </i>上传失败</span>');
            tds.eq(4).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });
});

/**
 * 加载数据
 */
function loadData() {
    $.ajax({
        url: 'album/getAlbum',
        dataType: 'json',
        type: 'post',
        async: false,
        success: function (res) {
            var data = res.data;
            var albumHtml = '<div class="layui-col-xs6">\n' +
                '    <div class="photoDiv">\n' +
                '        <div class="album firstDiv" style="text-align: center;background-color: #aaaaaa;">\n' +
                '            <i class="layui-icon layui-icon-picture"></i>新建相册\n' +
                '        </div>\n' +
                '    </div>\n' +
                '</div>';
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    var permission = '';
                    if (data[i].permission === '0') {
                        permission = '公开';
                    } else if (data[i].permission === '1') {
                        permission = '私密';
                    }
                    albumHtml += '<div class="layui-col-xs6">\n' +
                        '        <div class="photoDiv" onclick="clickDiv(' + data[i].id + ')">\n' +
                        '            <img class="album" src="">\n' +
                        '            <p>' + data[i].name + '</p>\n' +
                        '            <span class="photpSpan">张数</span><span class="photpSpan">' + permission + '</span>\n' +
                        '        </div>\n' +
                        '    </div>'
                }
                $("#albumBody").html(albumHtml);
            }
        }
    });
}

/**
 * 渲染宽高
 */
function loadWithHeight() {
    var photoDiv = $(".photoDiv");
    var photoDivWidth = photoDiv.innerWidth();
    photoDiv.css('height', photoDivWidth + 30);
    var album = $(".album");
    album.css('height', photoDivWidth);
    album.css('width', photoDivWidth);
    var firstDiv = $(".firstDiv");
    firstDiv.css('line-height', photoDivWidth + 'px');
}

/**
 * 相册点击弹窗
 */
function clickDiv(id) {
    fileId = id;
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.open({
            type: 1,
            title: '传相册',
            content: $("#insertPhotoDiv")
        });
    });
}

// // 触发上传按钮
// function clickUpload() {
//     if(document.all) {
//         document.getElementById("upload").click();
//     } else {
//         var e = document.createEvent("MouseEvents");
//         e.initEvent("click", true, true);
//         document.getElementById("upload").dispatchEvent(e);
//     }
// }