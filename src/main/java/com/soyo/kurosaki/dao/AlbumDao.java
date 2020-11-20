package com.soyo.kurosaki.dao;

import com.soyo.kurosaki.bean.Album;
import com.soyo.kurosaki.bean.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AlbumDao {

    void createAlbum(Album album);
    List<Album> selectByaccount(String account);
    void insertPhoto(Photo photo);

}
