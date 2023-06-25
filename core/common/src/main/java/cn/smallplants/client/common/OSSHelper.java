package cn.smallplants.client.common;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.ContextUtils;

import java.time.LocalDate;

import cn.smallplants.client.model.entity.Picture;

public class OSSHelper {
    private volatile static OSSHelper instance = null;
    private final XLog log = XLog.tag(getClass().getSimpleName());
    private final String accessKeyId = "LTAI5t7bcak2x7FTjQdsc1yi";
    private final String secretKeyId = "XMiHrkFfUdTePcfodufIxDZrV8vosw";
    private final String bucketName = "xzwcn";
    private final String endpoint = "oss-cn-shanghai.aliyuncs.com";
    private final String securityToken = "";
    private final OSS oss;

    private OSSHelper() {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);
        ClientConfiguration config = new ClientConfiguration();
        config.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        config.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        config.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        config.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(ContextUtils.getContext(), endpoint, credentialProvider, config);
    }

    public static OSSHelper getInstance() {
        if (instance == null) {
            synchronized (OSSHelper.class) {
                if (instance == null) {
                    instance = new OSSHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 获取文件相对路径,不能以斜杆/开头
     */
    private String getFileRelativePath(String filename) {
        LocalDate localDate = LocalDate.now();
        return localDate.getYear() + "/" + localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/" + filename;
    }

    public Picture uploadFile(Picture image) {
        String objectName = getFileRelativePath(image.getName());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, image.getPath());
        putObjectRequest.setProgressCallback((request, currentSize, totalSize) -> {
            float progress = 100 * currentSize / totalSize;
            log.d("PutObject", "上传进度: " + progress);
        });
        image.setUrlAddress(oss.presignPublicObjectURL(bucketName, objectName));
        OSSAsyncTask ossAsyncTask = oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String url = oss.presignPublicObjectURL(bucketName, objectName);
                log.i("图片地址：" + url);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // Request exception
                if (clientException != null) {
                    // Local exception, such as a network exception
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    log.e("ErrorCode", serviceException.getErrorCode());
                    log.e("RequestId", serviceException.getRequestId());
                    log.e("HostId", serviceException.getHostId());
                    log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        // ossAsyncTask.cancel(); // 可以取消任务
        // ossAsyncTask.waitUntilFinished(); // 等待任务完成
        return image;
    }
}