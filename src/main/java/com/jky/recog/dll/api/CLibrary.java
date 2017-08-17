package com.jky.recog.dll.api;

import com.jky.recog.util.DllUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * JNA接口工具
 * Created by DT人 on 2017/8/16 16:00.
 */
public interface CLibrary extends Library {
    String filePath = DllUtil.LIB_BASE_PATH + "classification_dll.dll";
    /*
	 * 读取相应的dll
	 */
    CLibrary INSTANCE = (CLibrary)
            Native.loadLibrary(filePath, CLibrary.class);

    // 实现dll的相应方法
    /**
     * 创建分类器
     * @param prototxtFile 协议文件
     * @param modelFile 权重模型文件
     * @param scaling 图像缩放系数,输入一幅图过后，每个像素值会乘以这个缩放系数，如果系数为1，则不做任何操作（不损失效率）
     * @param meanFile 均值文件,如果没有均值文件，不提供就好了，这里本来是文本型的，不提供，给0就好了
     * @param meanNum 通道均值个数,输入的一副图，会减去这个均值指定的值，实现数据的归一化操作。这个参数对应的是通道均值成员的个数，如果不使用，请给0
     * @param meanValue 通道均值,如上一个参数介绍的那样，他应该是个小数数组，但是这里被忽略了所以给的整数
     * @param gpuId gpu_id,指定使用哪个GPU执行识别操作，由于易不能够支持GPU，所以这个参数在win32的时候是没有意义的
     * @return 创建一个分类器，返回值是分类器的句柄，如果提供的文件不存在，程序会产生异常，这一点需要自己控制好
     */
    public int createClassifier(String prototxtFile,String modelFile,float scaling,int meanFile,int meanNum,int meanValue,int gpuId);

    /**
     * 预测分类
     * @param classifierHwnd 分类器句柄
     * @param picData 图像数据 支持bmp、jpg、jpeg、png、tif等格式
     * @param picLen 数据长度
     * @param limit 最可信的前几个
     * @return 预测softmax分类结果，返回结果句柄SoftmaxResult
     */
    public int predictSoftmax(int classifierHwnd,byte[] picData,int picLen,int limit);

    /**
     * 获取多分类结果标签
     * @param softmaxResultHwnd 分类结果句柄
     * @param lables 标签结果 该参数给的数组必须大于等于getNumOutlayers得到的个数否则会错误
     */
    public void getMultiLabel(int softmaxResultHwnd,int[] lables);

    /**
     * 获取输出层个数
     * @param softmaxResultHwnd
     * @return
     */
    public int getNumOutlayers(int softmaxResultHwnd);

    /**
     * 释放分类结果
     * @param softmaxResultHwnd
     */
    public void releaseSoftmaxResult(int softmaxResultHwnd);

    /**
     * 释放分类器
     * @param classifierHwnd
     */
    public void releaseClassifier(int classifierHwnd);
}
