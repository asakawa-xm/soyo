package com.soyo.kurosaki.controller;

import com.soyo.kurosaki.bean.Album;
import com.soyo.kurosaki.bean.Json;
import com.soyo.kurosaki.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    /**
     * 创建相册
     * @param album
     * @return
     */
    @RequestMapping(value = "createAlbum",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String createAlbum(Album album) {
        return albumService.createAlbum(album);
    }

    /**
     * 获取相册列表
     * @return
     */
    @RequestMapping(value = "getAlbum",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Json getAlbum() {
        return albumService.getAlbum();
    }

    /**
     * 文件上传
     * @param file
     * @param fileId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "uploadFile",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String uploadFile(MultipartFile file, String fileId) throws IOException {
        return albumService.uploadFile(file, fileId);
    }
}
