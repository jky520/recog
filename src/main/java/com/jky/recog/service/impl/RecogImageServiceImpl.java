package com.jky.recog.service.impl;

import com.jky.recog.dll.api.CLibrary;
import com.jky.recog.service.RecogImageService;
import com.jky.recog.util.DllUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

/**
 * 识别图片实现类
 * Created by DT人 on 2017/8/15 17:11.
 */
@Service
public class RecogImageServiceImpl implements RecogImageService {

    @Override
    public int orc(File f) {
        String fileProto = DllUtil.MODEL_BASE_PATH + "deploy.prototxt";
        String lableFile = DllUtil.MODEL_BASE_PATH + "label-map.txt";
        String caffeFile = DllUtil.MODEL_BASE_PATH + "nin_iter_16000.caffemodel";
        CLibrary cLibrary = CLibrary.INSTANCE;
        int hwnd = cLibrary.createClassifier(fileProto, caffeFile, 1, 0, 0, 0, -1);
        byte[]  file = DllUtil.readFile("D:\\dll\\test.jpg");
        int result = cLibrary.predictSoftmax(hwnd, file, file.length, 1);
        int[] array = new int[cLibrary.getNumOutlayers(result)];
        cLibrary.getMultiLabel(result, array);
        System.out.println(Arrays.toString(array));
        System.out.println(DllUtil.getResult(lableFile,array));
        cLibrary.releaseSoftmaxResult(result);
        cLibrary.releaseClassifier(hwnd);
        return 0;
    }
}
