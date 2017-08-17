package com.jky.recog;

import com.jky.recog.dll.api.CLibrary;
import com.jky.recog.util.DllUtil;

import java.util.Arrays;

/**
 * Created by DT人 on 2017/8/17 19:42.
 */
public class TestDll {
    public static void main(String[] args) {
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
    }
}
