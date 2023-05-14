
#include <android/log.h>
#include "Demo.h"

#define TAG "xiaojianbang"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO , TAG, __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__);

void bssFunc(){
    LOGD("bssFunc in libxiaojianbangA.so");
}

