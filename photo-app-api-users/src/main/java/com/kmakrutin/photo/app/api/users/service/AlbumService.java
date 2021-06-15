package com.kmakrutin.photo.app.api.users.service;

import com.kmakrutin.photo.app.api.users.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "albums-ws")
public interface AlbumService {

    @GetMapping("/users/{userId}/albums")
    List<AlbumResponseModel> getAlbums(@PathVariable String userId);
}
