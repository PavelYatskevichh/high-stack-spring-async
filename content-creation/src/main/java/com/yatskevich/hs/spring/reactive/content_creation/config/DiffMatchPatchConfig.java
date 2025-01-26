package com.yatskevich.hs.spring.reactive.content_creation.config;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiffMatchPatchConfig {

    @Bean
    public DiffMatchPatch diffMatchPatch() {
        return new DiffMatchPatch();
    }
}
