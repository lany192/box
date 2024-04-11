#include <jni.h>
#include <string>
#include <sstream>
#include <fstream>
using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_lany192_toolkit_BoxToolKit_stringFromJNI(
        JNIEnv* env,
        jclass clazz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_lany192_toolkit_BoxToolKit_getCurrentProcess(
        JNIEnv* env,
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