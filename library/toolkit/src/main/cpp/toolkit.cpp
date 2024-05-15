#include <jni.h>
#include <string>
#include <sstream>
#include <fstream>
#include <unistd.h>
#include "list"

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_lany192_toolkit_BoxToolKit_getMMKVKey(
        JNIEnv *env,
        jclass clazz) {
    std::string hello = "sdfs@fg#ghfg7sdfs22A!wWdf";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_lany192_toolkit_BoxToolKit_getCurrentProcess(
        JNIEnv *env,
        jclass clazz) {
    const std::string &path = "/proc/self/cmdline";
    ifstream file;
    file.open(path.c_str(), ios::in);
    std::string res = "";
    if (file.is_open()) {
        string temp;
        while (getline(file, temp)) {
            res += temp;
            res += '\n';
        }
    }
    file.close();
    return env->NewStringUTF(res.c_str());
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_github_lany192_toolkit_BoxToolKit_isEmulator(JNIEnv *env, jclass clazz) {
    list<string> paths;
    paths.push_front("/system/lib/libhoudini.so");
    paths.push_front("init.android_x86.rc");
    paths.push_front("fstab.android_x86");
    paths.push_front("/system/priv-app/ldAppStore");
    list<string>::iterator v;
    for (v = paths.begin(); v != paths.end(); ++v) {
        const char *str = v->c_str();
        if (access(str, R_OK) == 0) {
            return 1;
        }
    }
    return 0;
}