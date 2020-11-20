package com.soyo.kurosaki.service;

import com.soyo.kurosaki.bean.Album;
import com.soyo.kurosaki.bean.Json;
import com.soyo.kurosaki.bean.Photo;
import com.soyo.kurosaki.dao.AlbumDao;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
@Transactional
public class AlbumService {

    @Autowired
    AlbumDao albumDao;

    /**
     * 创建相册
     * @param album
     * @return
     */
    public String createAlbum(Album album) {
        album.setAccount((String)SecurityUtils.getSubject().getSession().getAttribute("account"));
        albumDao.createAlbum(album);
        return "创建成功";
    }

    /**
     * 获取相册列表
     * @return
     */
    public Json getAlbum() {
        Json json = new Json();
        json.setData(albumDao.selectByaccount((String)SecurityUtils.getSubject().getSession().getAttribute("account")));
        return json;
    }

    /**
     * 文件上传
     * @param file
     * @param fileId
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file, String fileId) throws IOException {
        if (file != null && !file.isEmpty()) {
            String path = "D:\\file\\";
            File file1 = new File(path);
            if (!file1.exists()) {file1.mkdirs();}
            String[] split = file.getOriginalFilename().split("\\.");
            String fileN = split[0] + UUID.randomUUID() + "." + split[1];//文件名字加上uuid防止文件名重复
            File destFile = new File(path,fileN);
            Photo photo = new Photo();
            photo.setAlbumId(fileId);
            photo.setName(fileN);
            photo.setPath(path + fileN);
            file.transferTo(destFile);
            albumDao.insertPhoto(photo);
            return "上传成功";
        } else {
            return "上传失败";
        }
    }
}
