#include "Native.h"
#include <string>
#include <pthread.h>
#include <jni.h>
#include <android/log.h>
//#include <sys/ptrace.h>
#include "MD5.h"


#define TAG "xiaojianbang"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO , TAG, __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__);


extern void bssFunc();

char* jstring2cstr(JNIEnv* env, jstring jstr){
	char* rtn = nullptr;
	jclass clazz = env->FindClass("java/lang/String");
	jstring strEncode = env->NewStringUTF("GB2312");
	jmethodID mid = env->GetMethodID(clazz, "getBytes", "(Ljava/lang/String;)[B");
	auto barr = (jbyteArray)env->CallObjectMethod(jstr, mid, strEncode);
	jsize alen = env->GetArrayLength(barr);
	jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
	if(alen > 0){
		rtn = (char*)malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	env->ReleaseByteArrayElements(barr, ba, 0);
	return rtn;
}

extern "C" JNIEXPORT jint JNICALL Java_com_xiaojianbang_ndk_NativeHelper_add(JNIEnv* env, jclass type, jint a, jint b, jint c){
    return a + b + c;
}

void xiugaiStr(char** str){
    strcat(*str, " QQ24358757");
}

jstring _strcat(JNIEnv* env, jclass type){
    char final[50];
    char* cstr = strcpy(final, "xiaojianbang");
    xiugaiStr(&cstr);
	return env->NewStringUTF(cstr);
}

void ::NativeHelper::registerNativeMethod(JNIEnv *env) {
    auto clazz = env->FindClass("com/xiaojianbang/ndk/NativeHelper");
    JNINativeMethod methods[] = {
            {"encode", "()Ljava/lang/String;", (void *)_strcat},
    };
    env->RegisterNatives(clazz, methods, 1);
}

__attribute__ ((constructor, visibility("hidden"))) void initArrayTest3(){
    LOGD("initArrayTest3");
}

__attribute__ ((constructor(101))) void initArrayTest1(){
    LOGD("initArrayTest1");
}

__attribute__ ((constructor(300))) void initArrayTest2(){
    LOGD("initArrayTest2");
}

extern "C" void _init(){	//函数名必须为_init  //.init_proc
    LOGD("_init");
}

void* myThread(void* a){

    for (int i = 0; i < 10; ++i) {
        LOGD("myThread: %d", i);
    }

    pthread_exit(0);
}

void myInit(){
    LOGD("myInit");
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved){
    //ptrace(0, 0 ,0 ,0);
    myInit();
    bssFunc();
    JNIEnv* env = nullptr;
    if(vm->GetEnv((void **)(&env), JNI_VERSION_1_6) != JNI_OK) return -1;

    pthread_t thread;
    pthread_create(&thread, nullptr, myThread, nullptr);
    pthread_join(thread, nullptr);

    NativeHelper::registerNativeMethod(env);
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_xiaojianbang_ndk_NativeHelper_md5(JNIEnv *env, jclass clazz, jstring jstr) {
    char* cstr = jstring2cstr(env, jstr);
    unsigned char encodeStr[16];
    char strBuf[33] = {0};
    char pbuf[32];
    MD5_CTX md5;
    MD5Init(&md5);
    MD5Update(&md5, reinterpret_cast<unsigned char *>(cstr), strlen(cstr));
    MD5Final(&md5, encodeStr);
    int i = 0;
    for(; i<16; i++){
        sprintf(pbuf,"%02x", encodeStr[i]);
        strncat(strBuf, pbuf, 2);
    }
    env->ReleaseStringUTFChars(jstr, cstr);
    return env->NewStringUTF(strBuf);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_xiaojianbang_ndk_NativeHelper_readSomething(JNIEnv *env, jclass clazz) {

    char line[512];
    FILE* fp;
    fp = fopen("/proc/self/maps", "r");
    if (fp) {
        while (fgets(line, 512, fp)) {
            LOGD("maps: %s", line);
            if (strstr(line, "frida")) {
                LOGD("find frida: %s", line);
            }
        }
        fclose(fp);
    }

    char* path = getenv("PATH");
    LOGD("path: %s", path);

    const char* command = "getprop ro.product.model";
    FILE* file = popen(command, "r");
    if (file) {
        while (fgets(line, 512, file)) {
            LOGD("getprop ro.product.model: %s", "100");
            LOGD("getprop ro.product.model: %s", line);
            LOGD("getprop ro.product.model: %s", "200");
        }
        pclose(file);
    }

    path = getenv("PATH");
    LOGD("path: %s", path);

}
