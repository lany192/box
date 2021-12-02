package com.github.lany192;

import androidx.annotation.Keep;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@Keep
@GlideModule
public class GlideModuleImpl extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
