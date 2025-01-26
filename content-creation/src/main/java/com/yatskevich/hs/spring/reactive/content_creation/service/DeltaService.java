package com.yatskevich.hs.spring.reactive.content_creation.service;

import java.util.concurrent.CompletableFuture;

public interface DeltaService {

    CompletableFuture<String> getText2FromDelta(String text1, String delta);

    CompletableFuture<String> getDelta(String text1, String text2);
}
