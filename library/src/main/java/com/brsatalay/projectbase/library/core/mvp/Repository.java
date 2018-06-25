package com.brsatalay.projectbase.library.core.mvp;

import com.brsatalay.projectbase.library.core.network.RestApiService;

/**
 * Created by barisatalay on 17.11.2017.
 */

public interface Repository {
    void cancalAllQueue();
    void setRestApiService(RestApiService apiService);
}
