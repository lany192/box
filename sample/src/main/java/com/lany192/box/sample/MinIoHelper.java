package com.lany192.box.sample;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MinIoHelper {
    private static MinIoHelper minIoHelper;
    private long transferedbytes;
    private final AmazonS3 client;

    public interface MinIoCallback {
        void progress(int progress);
    }

    private MinIoHelper() {
        client = new AmazonS3Client(new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "admin";
            }

            @Override
            public String getAWSSecretKey() {
                return "dev123456";
            }
        }, Region.getRegion(Regions.CN_NORTH_1), new ClientConfiguration());
        client.setEndpoint("http://localhost:9000");
    }

    public static MinIoHelper getInstance() {
        if (minIoHelper == null) {
            minIoHelper = new MinIoHelper();
        }
        return minIoHelper;
    }

    /*
        文件下载
     */
    public void download(String bucketName, String objectName, String savePath, MinIoCallback callback) throws Exception {
        ObjectMetadata metadata = client.getObjectMetadata(bucketName, objectName);
//        LogUtils.d(TAG, "object length = " + metadata.getContentLength() + " type = " + metadata.getContentType());
        S3Object object = client.getObject(bucketName, objectName);
        InputStream stream = object.getObjectContent();

        // 读取输入流直到EOF并打印到控制台。
        byte[] buf = new byte[1024 * 10];
        int bytesRead;
        long currentBytes = 0;
        int prevProcess = 0;
        long totalBytes = metadata.getContentLength();
        FileOutputStream fileOutputStream = new FileOutputStream(savePath);
        while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
            //System.out.println(new String(buf, 0, bytesRead));
            currentBytes += bytesRead;
            int process = (int) (currentBytes * 100 / totalBytes);
            if (callback != null && process != prevProcess) {
                callback.progress(process);
                prevProcess = process;
            }
            fileOutputStream.write(buf, 0, bytesRead);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /*
     * 文件上传
     */
    public void upload(String bucketName, String objectName, String filePath, MinIoCallback callback) {
        if (!client.doesBucketExist(bucketName)) {
            client.createBucket(bucketName);
        }
        File file = new File(filePath);
        transferedbytes = 0;
        PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, objectName, file)
                .withGeneralProgressListener(progressEvent -> {
                    if (callback != null) {
                        transferedbytes += progressEvent.getBytesTransferred();
                        int process = (int) (transferedbytes * 100 / file.length());
                        callback.progress(process);
                    }
                }));
    }
}
