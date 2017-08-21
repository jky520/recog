**项目说明**
    

    recog项目是通过调用接口获取图片，实现图片上传接口以及调用ddl识别图片。
    
    一、JNA只支持32位的jdk，这是比较坑爹的地方
    
    二、dll文件的存放问题（也是巨坑）：
        网上有很多五花八门的说法，放在C:\Windows\System32下或放在jdk的bin目录下，其实没必要搞得那么乱七八糟的，
        就放在项目下面（可以自己指定路径），那么在获取指定的dll或其他文件可以使用下面的方法，其中Lib就是文件的所在文件夹中的一个类
        //绝对路径的地址获取，注意要去空格，特别坑，还要把把“/”转换成"\\"，replaceAll("/", "\\\\")
        Lib.class.getResource("").getPath().replaceFirst("/","").replace("target/classes", "src/main/java").replaceAll("/", "\\\\").replaceAll("%20"," ")+"文件名.dll";
        
    三、在dll->api下就是获取dll文件的接口CLibrary，里面也有dll中的所有方法
    
    四、想看如可使用图片识别的方法使用例子，查看test中的TestDll文件即可
    
    五、要想知道怎么调用系统的dll就去test中找到JnaTest中查看
    
**接口使用说明**

    该项目是做图片识别，那么要想调用其接口必须遵循接口的规范
    1、接口暴露地址：http://jky.tunnel.qydev.com/image/recogImage
    2、该接口是post请求方式
    3、接口参数名file
    4、返回结果由字母和数字组成
    
**代码实例**
        
        1、CLibrary接口的部分代码
        String filePath = DllUtil.LIB_BASE_PATH + "classification_dll.dll";
        /*
    	 * 读取相应的dll
    	 */
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary(filePath, CLibrary.class);
        
        2、service实现类的代码
        public String orc(String filePath) {
            String fileProto = DllUtil.MODEL_BASE_PATH  + "deploy.prototxt";
            String lableFile = DllUtil.MODEL_BASE_PATH  + "label-map.txt";
            String caffeFile = DllUtil.MODEL_BASE_PATH  + "nin_iter_16000.caffemodel";
            CLibrary cLibrary = CLibrary.INSTANCE; // 实例化CLibrary接口
            int hwnd = cLibrary.createClassifier(fileProto, caffeFile, 1, 0, 0, 0, -1);
            byte[]  file = DllUtil.readFile(filePath);
            int result = cLibrary.predictSoftmax(hwnd, file, file.length, 1);
            int[] array = new int[cLibrary.getNumOutlayers(result)];
            cLibrary.getMultiLabel(result, array);
            System.out.println(Arrays.toString(array));
            System.out.println(DllUtil.getResult(lableFile,array));
            cLibrary.releaseSoftmaxResult(result);
            cLibrary.releaseClassifier(hwnd);
            return DllUtil.getResult(lableFile,array);
        }
