package com.kmakrutin.photo.app.api.users.service;

import com.kmakrutin.photo.app.api.users.model.AlbumResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

//@FeignClient(name = "albums-ws", fallback = AlbumService.Fallback.class )
@FeignClient(name = "albums-ws", fallbackFactory = AlbumService.AlbumFallbackFactory.class)
public interface AlbumService {


    @GetMapping("/users/{userId}/albums")
    List<AlbumResponseModel> getAlbums(@PathVariable String userId);

    @Slf4j
    @Component
    class AlbumFallbackFactory implements FallbackFactory<AlbumService> {

        @Override
        public AlbumService create(Throwable cause) {
            log.error("Failed to get albums", cause);

            return userId -> {
                AlbumResponseModel album = new AlbumResponseModel();
                album.setAlbumId(UUID.randomUUID().toString());
                album.setName("From fallback factory");
                album.setDescription(cause.getLocalizedMessage());
                album.setUserId(userId);

                return Collections.singletonList(album);
            };
        }
    }

//    @Component
//    class Fallback implements AlbumService {
//        @Override
//        public List<AlbumResponseModel> getAlbums(String userId) {
//            AlbumResponseModel album = new AlbumResponseModel();
//            album.setAlbumId(UUID.randomUUID().toString());
//            album.setName("Fallback");
//            album.setUserId(userId);
//
//            return Collections.singletonList(album);
//        }
//    }
}
